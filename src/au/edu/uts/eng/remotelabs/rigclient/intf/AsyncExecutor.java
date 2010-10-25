/**
 * SAHARA Rig Client
 * 
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2010, University of Technology, Sydney
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of the University of Technology, Sydney nor the names 
 *    of its contributors may be used to endorse or promote products derived from 
 *    this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Michael Diponio (mdiponio)
 * @date 25th October 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.intf;

import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.axis2.AxisFault;

import au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig.ActionType;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.status.SchedulingServerProviderStub;
import au.edu.uts.eng.remotelabs.rigclient.status.StatusUpdater;
import au.edu.uts.eng.remotelabs.rigclient.status.types.AllocateCallback;
import au.edu.uts.eng.remotelabs.rigclient.status.types.AllocateCallbackResponse;
import au.edu.uts.eng.remotelabs.rigclient.status.types.CallbackRequestType;
import au.edu.uts.eng.remotelabs.rigclient.status.types.ErrorType;
import au.edu.uts.eng.remotelabs.rigclient.status.types.ReleaseCallback;
import au.edu.uts.eng.remotelabs.rigclient.status.types.ReleaseCallbackResponse;
import au.edu.uts.eng.remotelabs.rigclient.type.RigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Executes asynchronous jobs.
 */
public class AsyncExecutor implements Runnable
{
    enum Job
    {
        /** Run allocation. */
        ALLOCATE, /** Run release. */
        RELEASE;
    }
    
    /** Jobs to execute asynchronously. */
    private BlockingQueue<Job> jobs;
    
    /** Whether a job is currently is executing. */
    private boolean isExecuting;
    
    /** Currently isExecuting job. */
    private Job currentJob;
    
    /** The user name of the user getting allocated. */
    private String username;
        
    /** Logger. */
    private ILogger logger;
    
    public AsyncExecutor()
    {
        this.logger = LoggerFactory.getLoggerInstance();   
        this.jobs = new LinkedBlockingQueue<Job>(2);
        this.isExecuting = false;
    }

    @Override
    public void run()
    {
        this.logger.debug("Async operations exeuctor starting up.");
        
        while (!Thread.interrupted())
        {
            try
            {
                this.currentJob = this.jobs.take();
                this.isExecuting = true;
                
                switch (this.currentJob)
                {
                    case ALLOCATE:
                        this.runAllocate();
                        break;
                    case RELEASE:
                        this.runRelease();
                        break;
                }
                
                this.isExecuting = false;
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Submits an allocate job. This succeeds only if no other job is in
     * progress.
     *  
     * @param username name of user to allocate
     * @return true if allocate job successfully submitted
     */
    public boolean submitAllocate(String username)
    {
        if (this.isExecuting)
        {
            this.logger.warn("Failing submitting allocate job because a job is already running.");
            return false;
        }
        
        this.username = username;
        try
        {
            this.jobs.put(Job.ALLOCATE);
        }
        catch (InterruptedException e)
        {
            this.logger.warn("Failed submitting allocate job because shutting down.");
            return false;
        }
        
        return true;
    }
    
    /**
     * Submits a release job. This succeeds if either no other job is running
     * or only an allocate job is running. If allocate is running, only one 
     * subsequent job may be queued.
     * 
     * @return true if release job successfully submitted
     */
    public boolean submitRelease()
    {
        if (this.isExecuting && this.currentJob == Job.RELEASE)
        {
            this.logger.warn("Failed submitting release job because release is currently executing.");
            return false;
        }
        else if (this.jobs.size() > 1)
        {
            this.logger.warn("Failed submitting release job because a job is already queued.");
            return false;
        }
                
        try
        {
            this.jobs.put(Job.RELEASE);
        }
        catch (InterruptedException e)
        {
            this.logger.warn("Failed submiiting release job because shutting down.");
            return false;
        }

        return true;
    }
    
    /**
     * Runs allocation.
     */
    private void runAllocate()
    {
        IRig rig = RigFactory.getRigInstance();
        
        this.logger.debug("Running async allocation of user '" + this.username + "'.");
        
        AllocateCallback request = new AllocateCallback();
        CallbackRequestType callback = new CallbackRequestType();
        request.setAllocateCallback(callback);
        callback.setName(rig.getName());
        
        boolean success = rig.assign(this.username);
        if (success)
        {
            callback.setSuccess(true);
        }
        else
        {
            callback.setSuccess(false);
            ErrorType err = new ErrorType();
            err.setCode(16);
            callback.setError(err);
            
            
            /* Abstract rig IRig implementations should use actions that 
             * provide a failure reason. */
            if (rig instanceof AbstractRig)
            {
                AbstractRig aRig = (AbstractRig)rig;
                String failureReason = aRig.getActionFailureReason(ActionType.ACCESS);
                err.setReason(failureReason != null ? failureReason : "Action failure"); 
            }
            else
            {
                err.setReason("Action failure");
            }
        }
        
        try
        {
            SchedulingServerProviderStub stub = new SchedulingServerProviderStub(this.getEndpoint());
            AllocateCallbackResponse response = stub.allocateCallback(request);
            
            /* If the response indicates a problem, remove allocation. */
            if (response.getAllocateCallbackResponse().getSuccessful())
            {
                this.logger.debug("Async allocate callback was successful.");
            }
            else
            {
                this.logger.warn("Async allocate callback failed with reason '" + 
                        response.getAllocateCallbackResponse().getErrorReason() + "'. Going to roll back allocation");
                rig.revoke();
            }
        }
        catch (AxisFault e)
        {
            this.logger.warn("Async allocate callback failed with fault '" + e.getMessage() + 
                    "'. Going to roll back allocation");
            rig.revoke();
        }
        catch (RemoteException e)
        {
            this.logger.warn("Async allocate callback failed with remote execption reason '" + e.getMessage() + 
            "'. Going to roll back allocation");
            rig.revoke();
        }
    }

    /**
     * Runs release.
     */
    private void runRelease()
    {
        IRig rig = RigFactory.getRigInstance();
        
        this.logger.debug("Running async release.");
        
        ReleaseCallback request = new ReleaseCallback();
        CallbackRequestType callback = new CallbackRequestType();
        request.setReleaseCallback(callback);
        callback.setName(rig.getName());
        
        boolean success = rig.revoke();
        if (success)
        {
            callback.setSuccess(true);
        }
        else
        {
            callback.setSuccess(false);
            ErrorType err = new ErrorType();
            err.setCode(16);
            callback.setError(err);
            
            
            /* Abstract rig IRig implementations should use actions that 
             * provide a failure reason. */
            if (rig instanceof AbstractRig)
            {
                AbstractRig aRig = (AbstractRig)rig;
                String failureReason = aRig.getActionFailureReason(ActionType.ACCESS);
                err.setReason(failureReason != null ? failureReason : "Action failure"); 
            }
            else
            {
                err.setReason("Action failure");
            }
        }
        
        try
        {
            SchedulingServerProviderStub stub = new SchedulingServerProviderStub(this.getEndpoint());
            ReleaseCallbackResponse response = stub.releaseCallback(request);
            
            /* If the response indicates a problem, remove allocation. */
            if (response.getReleaseCallbackResponse().getSuccessful())
            {
                this.logger.debug("Async release callback was successful.");
            }
            else
            {
                this.logger.warn("Async release callback failed with reason '" + 
                        response.getReleaseCallbackResponse().getErrorReason() + "'.");
            }
        }
        catch (AxisFault e)
        {
            this.logger.warn("Async release callback failed with fault '" + e.getMessage() + "'.");
        }
        catch (RemoteException e)
        {
            this.logger.warn("Async release callback failed with remote execption reason '" + e.getMessage() + "'.");
        }
    }
    
    /**
     * Returns the Scheduling Server Rig Provider end point address.
     * 
     * @return address rig provider end point
     */
    private String getEndpoint() throws RemoteException
    {
        IConfig config = ConfigFactory.getInstance();
        
        /* Generate the end point URL. */
        StringBuilder ep = new StringBuilder();
        ep.append("http://");
        
        String tmp = config.getProperty("Scheduling_Server_Address");
        if (tmp == null || tmp.length() < 1)
        {
            this.logger.error("Unable to load the Scheduling Server address. Ensure the property " +
                    "'Scheduling_Server_Address' is set with a valid host name or IP address.");
            throw new RemoteException("Unable to load scheduling server address.");
        }
        ep.append(tmp);
        
        tmp = config.getProperty("Scheduling_Server_Port", "8080");
        try
        {
            ep.append(':');
            ep.append(Integer.parseInt(tmp)); // Check to ensure the port number is valid
        }
        catch (NumberFormatException ex)
        {
            this.logger.error("Invalid Scheduling Server port number loaded. Ensure the property " +
                    "'Scheduling_Server_Port' is either set to a valid port number or not set (defaults to 8080).");
            throw new RemoteException("Invalid port number for scheduling server.");
        }
        
        /* Only need to use new address because this a is v3 feature. */
        ep.append(StatusUpdater.SS_URL_SUFFIX);
        this.logger.debug("Scheduling server end point address for async callback is " + ep.toString() + '.');
        
        return ep.toString();
    }
}
