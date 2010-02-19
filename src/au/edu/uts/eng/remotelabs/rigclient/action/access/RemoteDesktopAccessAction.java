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
 * @author Tania Machet (tmachet)
 * @date 19th February 2010
 *
 * Changelog:
 * - 19/02/2010 - tmachet - start again with Remote Desktop
 */
package au.edu.uts.eng.remotelabs.rigclient.action.access;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Windows Terminal Services access action. Performs the access action by
 * adding and removing users from the configurable Remote Desktop Users group 
 * which controls who may remote login to a Windows console using RDP. 
 * <p>
 * This action only works for Windows, on other platforms a 
 * {@link IllegalStateException} will be thrown on construction.
 * <p>
 * The configuration properties for RemoteDesktopAccessAction are:
 * <ul>
 * <li><tt>Remote_Desktop_Windows_Domain</tt> - specifies the Windows/
 * Samba domain the user is part of (i.e their name 
 * is '\\&lt;Windows_Domain&gt;\&lt;name&gt;')

 * Access is granted with the <code>assign</code> method that adds users 
 * to the user group if they do not exist there yet.
 * Access is revoked with the <code>revoke</code> method that removes
 * the user from the group   
 *  
 *  @author tmachet 
 *
 */
public class RemoteDesktopAccessAction implements IAccessAction
{

    /** Logger. */
    protected ILogger logger;    
    
    /** Default user group for remote desktop access. */
    public static final String DEFAULT_GROUPNAME = "Remote Desktop Users";

    /** Default command for changing user groups for windows */
    public static final String DEFAULT_COMMAND = "net";

    /** Default command for changing user groups for windows */
    public static final String DEFAULT_LOCALGROUP = "localgroup";

    /** Domain name. */
    private final String domainName;

    /** Default user group for remote desktop access. */
    private String failureReason;

    /**
     * Constructor.
     */
    public RemoteDesktopAccessAction() 
    {
        final String os = System.getProperty("os.name");
        this.logger = LoggerFactory.getLoggerInstance();

        /* RDP access only valid for Windows - check that the OS is windows */
        if (os.startsWith("Windows"))
        {
            /* Get domain if it is configured */
            this.domainName = ConfigFactory.getInstance().getProperty("Remote_Desktop_Windows_Domain");
            if (this.domainName == null)
            {
                this.logger.info("Windows domain name not found, so not using a domain name.");
            }
        }
        else
        {
            throw new IllegalStateException("Remote Desktop Action is only valid for WINDOWS platforms not " + os);
        }
    }
    
    /* (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction#assign(java.lang.String)
     */
    @Override
    public boolean assign(String name)
    {
        synchronized(this){
        /* --------------------------------------------------------------------
         * 1. Check whether user is in RDP user group
         * a. sets up net process
         * b. executes process
         * c. check output to see whether user is in the group
         * -------------------------------------------------------------------- */
            List<String> command = new ArrayList<String>();
            
            command.add(RemoteDesktopAccessAction.DEFAULT_COMMAND);
            command.add(RemoteDesktopAccessAction.DEFAULT_LOCALGROUP);
            command.add(ConfigFactory.getInstance().getProperty("Remote_Desktop_Groupname",RemoteDesktopAccessAction.DEFAULT_GROUPNAME));

            try
            {
                Process proc = executeCommand(command);
                
                
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            

            
        }
         
        
         
        
        
        /* --------------------------------------------
         * 2. If not, add user to group
         * a. sets up net add process
         * b. executes process
         * --------------------------------------------
         * 
         * --------------------------------------------
         * 3. Check whether user is in RDP user group
         * a. sets up net process
         * b. executes process
         * c. check output to see whether user is in the group
         * --------------------------------------------
         * 
         * 
         */
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Executes the access action specified using the command and working directory
     * 
     * @param command
     * @return Process
     * @throws Exception
     */
    private Process executeCommand(List<String> command) throws Exception
    {
        //*Check that the command was passed */
        if (command == null)
        {
            this.logger.warn("No command file has been specified for the access action.  Access action failed.");
            throw new Exception("No command to exectue!");
        }
        this.logger.debug("Access action commands and arguments are " + command.toString());

        final ProcessBuilder builder = new ProcessBuilder(command);
        final File workingDir = new File(System.getProperty("java.io.tmpdir"));
        builder.directory(workingDir);
        final Map<String, String> env = builder.environment();

        Process accessProc = null;
        try
        {
            accessProc = builder.start();
            this.logger.info("Invoked access action command");
            accessProc.waitFor();
        }
        finally
        {
            this.cleanup(accessProc);
        }
        
        return accessProc;
    }

    /**
     * Clean up output streams from Process
     * @param proc Process to be cleaed up
     */
    private void cleanup(Process proc)
    {
        this.logger.debug("Cleaning a access action control invocation.");
        
        try
        {
            if (proc.getInputStream() != null) 
            {
                proc.getInputStream().close();
            }
            if (proc.getErrorStream() != null) 
            {
                proc.getErrorStream().close();
            }
        }
        catch (IOException ex)
        {
            this.logger.warn("Failed to clean a batch control invocation because of error " + ex.getMessage());
        }
        
    }

    /* (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction#revoke(java.lang.String)
     */
    @Override
    public boolean revoke(String name)
    {
        /* 
         * --------------------------------------------
         * 1. End users sessions
         * a. Set up process for qwinsta
         * b. Execute process
         * c. Check user session exists using qwinsta output
         * d. for each session, set up process for log-off
         * e. execute log-off process
         * --------------------------------------------
         * 
         * --------------------------------------------
         * 2. Check whether user is in RDP user group
         * a. sets up net process
         * b. executes process
         * c. check output to see whether user is in the group
         * --------------------------------------------
         * 
         * --------------------------------------------
         * 3. If yes, remove user from group
         * a. sets up net delete process
         * b. executes process
         * --------------------------------------------
         * 
         */
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAction#getActionType()
     */
    @Override
    public String getActionType()
    {
        // TODO Auto-generated method stub
        /* return string for remote desktop access action */
        return null;
    }

    /* (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAction#getFailureReason()
     */
    @Override
    public String getFailureReason()
    {
        // TODO Auto-generated method stub
        /* get instance variable for failure reason */
        return null;
    }
    
    /**
     * Method to check that a user is not already assigned to a user group
     * before adding them.
     * 
     * @param name of user to be checked
     * @return true if user is in group
     */
    private boolean isUserInGroup(String name)
    {
        return false;
    }

    /**
     * Method to read the specified InoutStream 
     * 
     * @param the input stream to be read
     * @return String read from input stream
     */
    private String readOutput(InputStream is)
    {
        return null;
    }
}
