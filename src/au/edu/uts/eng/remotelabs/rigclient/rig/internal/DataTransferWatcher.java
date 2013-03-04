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
 * @date 5th November 2009.
 */


package au.edu.uts.eng.remotelabs.rigclient.rig.internal;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.axis2.AxisFault;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.status.SchedulingServerProviderStub;
import au.edu.uts.eng.remotelabs.rigclient.status.StatusUpdater;
import au.edu.uts.eng.remotelabs.rigclient.status.types.AddSessionFiles;
import au.edu.uts.eng.remotelabs.rigclient.status.types.SessionFile;
import au.edu.uts.eng.remotelabs.rigclient.status.types.SessionFiles;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Processes new session files to transfer them to the Scheduling Server.  
 */
public class DataTransferWatcher extends Thread
{
    /** Configured transfer method. */
    private final TransferMethod method;
    
    /** The rig client. */
    private final IRig rig;
    
    /* Interface to Scheduling Server. */
    private SchedulingServerProviderStub stub;
    
    /** Whether the rig is in session. */
    private boolean inSession;
    
    /** The current session user. */
    private String currentUser;
    
    /** Session files. */
    private final Map<String, Set<File>> sessionFiles;
    
    /** Transferred files. */
    private final Map<String, List<File>> transferFiles;
    
    /** Whether data transfers are disabled. */
    private boolean isDisabled;
    
    /** Whether to shutdown. */
    private boolean shutdown;
    
    /** Logger. */
    private final ILogger logger;
    
    public DataTransferWatcher(IRig rig)
    {
        this.logger = LoggerFactory.getLoggerInstance();
        this.logger.debug("Creating the data transfer watcher service.");
        
        this.setName("Data Watcher");
        
        this.rig = rig;
        this.sessionFiles = new HashMap<String, Set<File>>();
        this.transferFiles = new HashMap<String, List<File>>();

        IConfig conf = ConfigFactory.getInstance();
        
        TransferMethod meth;
        try
        {
            meth = TransferMethod.valueOf(conf.getProperty("Data_Transfer_Method", "WEBDAV"));
            this.logger.debug("Using data transfer method '" + this.method + "'.");
        }
        catch (IllegalArgumentException ex)
        {
            this.logger.error("Data transfer method '" + conf.getProperty("Data_Transfer_Method") + "' is not a valid " +
            		"option, using 'WEBDAV' transfer method.");
            meth = TransferMethod.WEBDAV;
        }
        this.method = meth;

        try
        {
            this.stub = new SchedulingServerProviderStub(StatusUpdater.getSchedulingServerEndpoint());
        }
        catch (Exception e)
        {
            this.isDisabled = true;
            this.logger.error("Unable to load Scheduling Server location, disabling data file transfers.");
        }
    }

    @Override
    public void run()
    {
        /* Some error in configuration. */
        if (this.isDisabled) return;
        
        while (!this.shutdown)
        {
            if (this.inSession)
            {
                /* Poll for session files. */
                this.sessionFiles.get(this.currentUser).addAll(this.rig.detectSessionFiles());
            }
            
            for (Entry<String, Set<File>> e : this.sessionFiles.entrySet())
            {
                /* Request parameters. */
                AddSessionFiles request = new AddSessionFiles();
                
                SessionFiles filesParam = new SessionFiles();
                filesParam.setName(this.rig.getName());
                filesParam.setUser(e.getKey());
                request.setAddSessionFiles(filesParam);
                
                for (File f : e.getValue())
                {
                    /* Check the file has not already been sent. */
                    if (this.transferFiles.get(e.getKey()).contains(f)) continue;
                    
                    SessionFile fileParam = new SessionFile();
                    fileParam.setName(f.getName());
                }
                
            }
            
            try
            {
                Thread.sleep(60000);
            }
            catch (InterruptedException e)
            { /* Expected interrupt. */ }
        }
    }
        
    /**
     * Notification a user has finished their session.
     * 
     * @param user user who has started their session.
     */
    public synchronized void sessionStarted(String user)
    {
        this.inSession = true;
        this.currentUser = user;
        this.sessionFiles.put(this.currentUser, new HashSet<File>());
    }
    
    /**
     * Notification a session has completed.
     */
    public synchronized void sessionComplete()
    {
        this.sessionFiles.get(this.currentUser).addAll(this.rig.detectSessionFiles());
        this.currentUser = null;
        this.inSession = true;
        
        /* Interrupt the thread so it will send through data files. */
        this.interrupt();
    }

    public void shutdown()
    {
        this.shutdown = true;
    }
    
    /** List of possible transfer methods. */
    enum TransferMethod
    {
        /** Attachment to SOAP notification message. */
        ATTACHMENT, /** Shared filesystem with Scheduling Server. */
        FILESYSTEM, /** WebDAV Scheduling Server server. */
        WEBDAV
    }
}
