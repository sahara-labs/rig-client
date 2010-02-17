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
 * @author Tania Machet (tmachet)
 * @date 16th January 2010
 *
 * Changelog:
 * - 16/01/2010 - tmachet- Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;

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
 *  <li><tt>Remote_Desktop_Windows_Domain</tt> - specifies the Windows/
 *  Samba domain the user is part of (i.e their name 
 *  is '\\&lt;Windows_Domain&gt;\&lt;name&gt;')
 *  <li><tt>Remote_Desktop_Groupname</tt> - the name of the user group
 *   to which the user must be added for Remote Desktop access permissions
 *  </ul> 
 *  Access is granted with the <code>assign</code> method that adds users 
 *  to the user group if they do not exist there yet.
 *  Access is revoked with the <code>revoke</code> method that removes
 *  the user from the group   
 */
public class RemoteDesktopAccessAction extends ExecAccessAction
{
    /** Default user group for remote desktop access. */
    public static final String DEFAULT_GROUPNAME = "Remote Desktop Users";

    /** Default command for changing user groups for windows */
    public static final String DEFAULT_COMMAND = "net";

    /** Default command for changing user groups for windows */
    public static final String DEFAULT_LOCALGROUP = "localgroup";

    /** Domain name. */
    private final String domainName;
    
    /** group name for user group that has remote desktop access. */
    protected String groupName;

    /** user name to be assigned access. */
    protected String userName;
    

    /**
     * Constructor.
     */
    public RemoteDesktopAccessAction() 
    {
        final String os = System.getProperty("os.name");

        // RDP access only valid for WIndows - check that the OS is windows
        if (os.startsWith("Windows"))
        {
            // Get domain if it is configured
            this.domainName = ConfigFactory.getInstance().getProperty("Remote_Desktop_Windows_Domain");
            if (this.domainName == null)
            {
                this.logger.info("Windows domain name not found, so not using a domain name.");
            }
            
            // Get Remote Desktop User group name
            this.groupName = ConfigFactory.getInstance().getProperty("Remote_Desktop_Groupname",RemoteDesktopAccessAction.DEFAULT_GROUPNAME);
            this.logger.debug("Remote Desktop User group is " + this.groupName);

            //Set up command command and arguments for remote access
            this.setupAccessAction();
            
        }
        else
        {
            throw new IllegalStateException("Remote Desktop Action is only valid for WINDOWS platforms not " + os);
        }
    }
    

    /* *
     * The action to assign users to a Remote Desktop session is done by adding them to 
     * a configurable user group that has permissions for the Remote Desktop.
     * 
     *  The user is first checked to see if it already in the group, if not, a command
     *  is set up and executed to add the user to the group, and the result verified.
     *  
     *  Additional arguments for assign are:
     *  <ul>
     *  <li> <tt> Domain </tt> - optional configurable windows domain of user
     *  <li> <tt> User Name </tt> - name of the user
     *   
     */
    @Override
    public boolean assign(String name)
    {
        synchronized(this){
            
            final boolean failedFlag;
            final String exitCode;
            
            this.userName = name;
    
            // Check whether this user already belongs to the group, if so continue
            if(!this.checkUserInGroup())
            {
                /* Add the command argument user name (with the Domain name if it is configured) and /ADD */
                if (this.domainName != null)
                {
                    this.commandArguments.add(this.domainName + "\\" + this.userName);
                }
                else
                {
                     this.commandArguments.add(this.userName);
                }
                this.commandArguments.add("/ADD");
                this.logger.debug("Remote Desktop Access assign - arguments are"  + this.commandArguments.toString());
                
                /* Execute the command ie net localgroup groupname (domain/)username /ADD */
                exitCode = this.executeAccessAction();
                
                if(exitCode == null)
                {
                    this.logger.error("Remote Desktop Access action failed, command unsuccessful");
                    failedFlag = true;
                }
                else
                {
                    if(!this.verifyAccessAction(exitCode))
                    {
                        this.logger.error("Remote Desktop Access revoke action failed, exit code is" + exitCode);
                        failedFlag = true;
                    }
                    else
                    {
                        failedFlag = false;
                    }
                }
                
                /* Remove the command arguments user name (with the Domain name if it is configured) and /ADD */
                if (this.domainName != null)
                {
                    this.commandArguments.remove(this.domainName + "\\" + this.userName);
                }
                else
                {
                    this.commandArguments.remove(this.userName);
                }                
                this.commandArguments.remove("/ADD");
    
                
                return failedFlag;
                
            }
            else
            {
                this.logger.info("User " + this.userName + " is already in the group " + this.groupName);
                return true;
            }
        }
    }

    /**
     * Method to check that a user is not already assigned to a user group
     * before adding them.
     * 
     * 
     */
    private boolean checkUserInGroup()
    {
        /* Execute the command to determine the users in the group
        * ie net localgroup groupname */
        this.executeAccessAction();
        
        if(this.getAccessOutputString().contains(this.userName))
        {
            return true;
        }
        
        return false;

    }

    /*
     * This method terminates a user's session if they are still logged on
     * using the <code>qwinsta</code> and <code>logoff</code> command line
     * programs, and then revokes their user privileges for Remote Desktop so
     * that they cannot log on again 
     * 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction#revoke(java.lang.String)
     */
    @Override
    public boolean revoke(String name)
    {
        synchronized(this){
            final boolean failed;
            final String exitCode;

            /* End user's session using qwinsta command line program*/
            endUsersSession();
    
            /* Add the command argument user name (with the Domain name if it is configured) and /DELETE */
            if (this.domainName != null)
            {
                this.commandArguments.add(this.domainName + "\\" + this.userName);
            }
            else
            {
                 this.commandArguments.add(this.userName);
            }
            this.commandArguments.add("/DELETE");
            this.logger.debug("Remote Desktop Access revoke - arguments are"  + this.commandArguments.toString());
            
    
            // Execute the command ie net localgroup groupname (domain/)username /DELETE
            exitCode = this.executeAccessAction();
            
            if(exitCode == null)
            {
                this.logger.error("Remote Desktop Access revoke action failed, command unsuccessful");
                failed = true;
            }
            else
            {
                if(this.checkUserInGroup())
                {
                    this.logger.error("Remote Desktop Access revoke action failed, user " + this.userName + " still in group.");
                    failed = true;
                }
                else
                {
                    failed = false;
                }
            }
    
            if(failed == true)
            {
                return false;
            }
            else
            {
                /* Remove the command arguments user name (with the Domain name if it is configured) and /DELETE */
                if (this.domainName != null)
                {
                    this.commandArguments.remove(this.domainName + "\\" + this.userName);
                }
                else
                {
                    this.commandArguments.remove(this.userName);
                }                
                this.commandArguments.remove("/DELETE");
                return true;
            }
        }
        
    }

    /**
     * Ends the users session by cheking using <code> qwinsta username </code> whether
     * the session exists and then logging the user off.
     * 
     * @return boolean result of loggoffs
     */
    private boolean endUsersSession()
    {
        //Set up process to run qwinsta command and get user sessions 
        final ProcessBuilder sessionBuilder = new ProcessBuilder();
        final List<String> sessionCommand = new ArrayList<String>();
        sessionCommand.add("qwinsta");
        sessionCommand.add(this.userName);
        
        try
        {
            // Start the process
            final Process proc = sessionBuilder.start();
            this.logger.debug("Session ID command invoked at " + this.getTimeStamp('/', ' ', ':'));
            if (proc.waitFor() == 0)
            {
                //Read process output
                InputStream is = proc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line = null;
                String[] qwinstaSplit;

                try
                {
                    while ( (line = br.readLine()) != null )
                    {
                        //Split result of process output to get sessionID
                        if (line.contains(this.userName))
                        {
                            qwinstaSplit = line.split("\\s");
                            String sessionID = qwinstaSplit[2];
                            this.logger.debug("Session ID read is " + sessionID);
                            
                            //Logoff all found sessions
                            if (logoffSession(sessionID))
                            {
                                this.logger.debug("Session ID ended is " + sessionID);
                            }
                            
                        }
                    }


                }
                catch (IOException e)
                {
                    this.logger.warn("Could not read qwinsta output. Error message " + e.getMessage() + ".");
                    return false;
                }
            }
            
            return true;
        } 
        catch(Exception e)
        {
            this.logger.warn("Checking for Session ID with exception of type " + e.getClass().getName() + " and with " +
                    "message " + e.getMessage());
            return false;
        }
        
    }

    /**
     * Use the windows "logoff" command to end a session with the given
     * session ID
     * 
     * @param sessionID
     * @return boolean result of logoff command
     */
    private boolean logoffSession(String sessionID)
    {
        final ProcessBuilder logoffBuilder = new ProcessBuilder();
        final List<String> logoffCommand = new ArrayList<String>();
        logoffCommand.add("logoff");
        logoffCommand.add(sessionID);
        
        try
        {
            final Process proc = logoffBuilder.start();
            this.logger.debug("Logoff command invoked at " + this.getTimeStamp('/', ' ', ':'));
            if(proc.waitFor() !=0 )
            {
                this.logger.warn("Logoff command returned unexpected result");
                return false;
            }
            return true;
        } 
        catch(Exception e)
        {
            this.logger.warn("Logoff command failed with exception of type " + e.getClass().getName() + " and with " +
                    "message " + e.getMessage());
            return false;
        }
    }


    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAction#getFailureReason()
     */
    @Override
    public String getFailureReason()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAction#getActionType()
     */
    @Override
    public String getActionType()
    {
        return "Windows Remote Desktop Access.";
    }
    
    /**
     * Sets up access action common parts.  
     * 
     * This supplies the:
     * <ul>
     *     <li><strong>Command</strong> - The common command for assign and revoke "net"
     *     <li><strong>Command arguments</strong> - The command parameters ie localgroup",
     *     configurable remote desktop group name
     */
    @Override
    public void setupAccessAction()
    {
        
        this.command = RemoteDesktopAccessAction.DEFAULT_COMMAND;
        this.commandArguments.add(RemoteDesktopAccessAction.DEFAULT_LOCALGROUP);
        this.commandArguments.add(this.groupName);
        
    }
    
    /**
     * Verifies the result of the process.  
     * 
     * This is done using the Windows exit code recieved from the last command.
     */
    @Override
    public boolean verifyAccessAction(String exitCode)
    {
        if(exitCode != null)
        {
            this.logger.warn("Verifying Access Action, output is " + this.getAccessOutputString());
            this.logger.warn("Verifying Access Action, std error is " + this.getAccessErrorString());

            return false;
        }
        
        return true;
            
    }
    
}
