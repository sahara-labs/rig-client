/**
 * SAHARA Rig Client
 * 
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2009, University of Technology, Sydney
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
 * @date 22nd October 2009
 *
 * Changelog:
 * - 22/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig;

import au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner;
import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront;

/**
 * Abstract rig type class for rigs which provide direct control of the 
 * rig using the rig client.
 */
public abstract class AbstractControlledRig extends AbstractRig implements IRigControl
{
    /** Batch runner. */
    private AbstractBatchRunner runner;
    
    /** Primitive front controller. */
    private PrimitiveFront front;
    
    /**
     * Constructor.
     */
    public AbstractControlledRig()
    {
        this.front = new PrimitiveFront();
    }
    
    @Override
    public PrimitiveResponse performPrimitive(final PrimitiveRequest req)
    {
        this.logger.debug("Performing a primitive control operation with the following signature: controller:" + 
                req.getController() + ", method: " + req.getAction() + ".");
        return this.front.routeRequest(req);
    }

    @Override
    public void expungePrimitiveControllerCache()
    {
        this.front.expungeCache();
    }
    
    /**
     * Returns the primitive front controller.
     */
    public PrimitiveFront getFrontController()
    {
        return this.front;
    }

    @Override
    public boolean performBatch(final String fileName, final String userName)
    {
        /* Make sure no batch control is currently running. */
        if (this.isBatchRunning())
        {
            this.logger.warn("Attempting to start a new batch control invocation, cannot start a new invocation.");
            return false;
        }
        
        this.runner = this.instantiateBatchRunner(fileName, userName);
        this.logger.debug("Performing batch control using a batch runner of type " + 
                this.runner.getClass().getSimpleName() + " with uploaded instruction file "+ fileName);
        
        /* Start the batch runner. */
        final Thread thr = new Thread(this.runner);
        thr.start();
        
        /* Wait until it has been started. */
        int timeCount = 0;
        final int timeOut = Integer.parseInt(this.configuration.getProperty("Batch_Timeout", "60"));
        this.logger.debug("Loaded batch start up timeout as " + timeOut + " seconds.");
        while (timeOut > 0 && !(this.runner.isStarted() || this.runner.isFailed()))
        {
            try
            {
                Thread.sleep(1000);
                if (++timeCount >= timeOut)
                {
                    this.logger.warn("Batch process has not started in " + timeCount + " seconds, aborting " +
                    		"batch control.");
                    this.runner.terminate();
                    return false;
                }
            }
            catch (InterruptedException e)
            {
                /* This could only happen when the Rig Client is about to 
                 * shutdown so return false to notify that the batch
                 * process (probably) won't be started and run. */ 
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isBatchRunning()
    {
        if (this.runner == null) return false;
        
        return this.runner.isInSetup() || this.runner.isRunning();
    }

    @Override
    public boolean abortBatch()
    {
        if (!this.isBatchRunning())
        {
            this.logger.debug("Unable to abort batch since it is not running.");
            return true;
        }
        
        this.runner.terminate();
        
        final int termTimeOut = Integer.parseInt(this.configuration.getProperty("Batch_Termination_TimeOut", "10"));
        int termTimeCount = 0;
        while (this.runner.isRunning() && (termTimeCount < termTimeOut))
        {
            ++termTimeCount;
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                /* This could only occurr at rig client shutdown, so provide
                 * immediate response to not hold up shutdown. */
                return !this.isBatchRunning();
            }
        }
        
        return !this.isBatchRunning();
    }
    
    @Override
    public void clearBatchState()
    {
        if (this.runner != null)
        {
            if ((this.runner.isInSetup() || this.runner.isRunning()) && !this.abortBatch())
            {
                this.logger.error("When clearing batch control state, the previous batch invocation is still running " +
                		"and failed to be aborted. This could leak to process leaks and should be investigated.");
            }
            
            this.runner = null;
        }
    }

    @Override
    public int getBatchProgress()
    {
        /* No batch invocation has been started. */
        if (this.runner == null) return 0;
        if (!(this.runner.isInSetup() || this.runner.isStarted())) return 0;
        
        /* Batch invocation failed or finished. */
        if (!this.isBatchRunning()) return 100;
        
        /* Batch invocation is running, so try and get a progression value. */
        /* DODGY This makes the assumption that the batch process prints an 
         * integer to standard out specifying the percentage complete and if 
         * this integer is updated, it should print a number on a new line. So
         * the standard output should look like:
         * 
         *     <progress number>[space]<optional junk>[new line]
         * 
         * For example:
         * mdiponio@eng047151~$ runprocess.sh
         * 1 <optional junk>\n
         * 2 <optional junk>\n
         * ...
         * 100 <optional junk>\n
         * mdiponio@eng047151~$ 
         */
        final String stdOutRead = this.runner.getBatchStandardOut();
        final String lines[] = stdOutRead.split(System.getProperty("line.separator"));
        final String words[] = lines[lines.length - 1].split(" ", 2);
        try
        {
            final int progress = Integer.parseInt(words[0]);
            return progress > 1 && progress <= 100 ? progress : -1;
        }
        catch (NumberFormatException nfe)
        {
            /* DODGY Oh, well, the assumption we made is wrong. */
            return -1;
        }
    }

    @Override
    public BatchResults getBatchResults()
    {
        final BatchResults results = new BatchResults();
        if (this.runner == null)
        {
            /* No batch process has been run. */
            results.setState(BatchState.CLEAR);
        }
        else
        {
            /* Results from the batch process run. */
            results.setState(this.getBatchState());
            results.setInstructionFile(this.runner.getInstructionFilePath());
            results.setStandardOut(this.runner.getAllStandardOut());
            results.setStandardErr(this.runner.getAllStandardErr());
            results.setExitCode(this.runner.getExitCode());
            results.setResultsFiles(this.runner.getResultsFiles());
        }
        return results;
    }

    @Override
    public BatchState getBatchState()
    {
        /* Not started. */
        if (this.runner == null) return BatchState.CLEAR;
        
        /* Failed. */
        if (this.runner.isFailed()) return BatchState.FAILED;
        
        /* Aborted. */
        if (this.runner.isKilled()) return BatchState.ABORTED;
        
        /* Running - in progress. */
        if (this.runner.isInSetup() || this.runner.isRunning()) return BatchState.IN_PROGRESS;
        
        /* Not started. */
        if (!this.runner.isStarted()) return BatchState.CLEAR;
        
        /* Must have completed successfully. */
        return BatchState.COMPLETE;
    }
    
    /**
     * Creates and returns an instance of <code>AbstractBatchRunner</code>.
     * This method is not guarded so should not return <code>null</code> or 
     * through an exception unless you intend to crash the Rig Client.
     * 
     * @param fileName uploaded instruction file path
     * @param userName initiating users name
     * @return {@link AbstractBatchRunner} instance
     */
    protected abstract AbstractBatchRunner instantiateBatchRunner(final String fileName, final String userName);
}
