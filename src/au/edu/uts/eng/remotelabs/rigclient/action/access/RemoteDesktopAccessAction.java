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
import java.util.Vector;

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
    
    /**
     * Constructor.
     */
    public RemoteDesktopAccessAction() 
    {
        final String os = System.getProperty("os.name");

        /* RDP access only valid for Windows - check that the OS is windows */
        if (os.startsWith("Windows"))
        {
            /* Get domain if it is configured */
            this.domainName = ConfigFactory.getInstance().getProperty("Remote_Desktop_Windows_Domain");
            if (this.domainName == null)
            {
                this.logger.info("Windows domain name not found, so not using a domain name.");
            }

            /* Set up command command and arguments for remote access */
            this.setupAccessAction();
        }
        else
        {
            throw new IllegalStateException("Remote Desktop Action is only valid for WINDOWS platforms not " + os);
        }
    }
    
    @Override
    public boolean assign(String name)
    {
        synchronized(this){
            int exitCode = 0;
            String userName = name;
            List<String> command = new ArrayList<String>();
            
            command.add(RemoteDesktopAccessAction.DEFAULT_COMMAND);
            command.add(RemoteDesktopAccessAction.DEFAULT_LOCALGROUP);
            command.add(ConfigFactory.getInstance().getProperty("Remote_Desktop_Groupname",RemoteDesktopAccessAction.DEFAULT_GROUPNAME));
    
            /* Check whether this user already belongs to the group, if so continue */
            if(this.isUserInGroup(command))
            {
                this.logger.debug("User " + userName + " is already in the group ");
                return true;
            }
            
            /* Add the command argument user name (with the Domain name if it is configured) and /ADD */
            if (this.domainName != null)
            {
                command.add(this.domainName + "\\" + userName);
            }
            else
            {
                command.add(userName);
            }
            command.add("/ADD");
            this.logger.debug("Remote Desktop Access assign - arguments are"  + command.toString());
                
            /* Execute the command i.e. net localgroup groupname (domain/)username /ADD */
            try
            {
                exitCode = this.executeAccessAction(command, null, null);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
                
             /* Remove the command arguments user name (with the Domain name if it is configured) and /ADD */
             if (this.domainName != null)
             {
                 command.remove(this.domainName + "\\" + userName);
             }
             else
             {
                 command.remove(userName);
             }                
             command.remove("/ADD");

              return exitCode == 1 || exitCode == 2;
        }
    }

    /**
     * Method to check that a user is not already assigned to a user group
     * before adding them.
     * 
     * @return true if user is in group
     */
    private boolean isUserInGroup( List<String> command)
    {
        /* Execute the command to determine the users in the group
        * i.e. net localgroup groupname */
        try
        {
            this.executeAccessAction(command, null, null);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //TODO FIX - need to make variables local!!!
        //if(this.getAccessOutputString().contains(userName))
        //{
        //    return true;
        //}
        return false;
    }

    /*
     * 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction#revoke(java.lang.String)
     */
    @Override
    public boolean revoke(String name)
    {
        /* This method terminates a user's session if they are still logged on
        * using the qwinsta and logoff command line programs, and then revokes
        * their user privileges for Remote Desktop so that they cannot log on again */
        synchronized(this){
            String userName = name;
            int exitCode = 0;
            List<String> command = new ArrayList<String>();

            command.add(RemoteDesktopAccessAction.DEFAULT_COMMAND);
            command.add(RemoteDesktopAccessAction.DEFAULT_LOCALGROUP);
            command.add(ConfigFactory.getInstance().getProperty("Remote_Desktop_Groupname",RemoteDesktopAccessAction.DEFAULT_GROUPNAME));

            /* End user's session using qwinsta command line program*/
            endUsersSession(userName);
    
            /* Add the command argument user name (with the Domain name if it is configured) and /DELETE */
            if (this.domainName != null)
            {
                command.add(this.domainName + "\\" + userName);
            }
            else
            {
                 command.add(userName);
            }
            command.add("/DELETE");
            this.logger.debug("Remote Desktop Access revoke - arguments are"  + command.toString());
            
            /* Execute the command i.e. net localgroup groupname (domain/)username /DELETE */
            try
            {
                exitCode = this.executeAccessAction(command, null, null);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (exitCode != 2 && exitCode != 1 || this.isUserInGroup(command) )
            {
                this.logger.warn("Remote Desktop Access revoke failed for user "  + userName);
                return false;
            }
            if (this.domainName != null)
            {
                command.remove(this.domainName + "\\" + userName);
            }
            else
            {
                command.remove(userName);
            }                
            command.remove("/DELETE");

            return true;
        }
    }

    /**
     * Ends the users session by checking using <code> qwinsta username </code> whether
     * the session exists and then logging the user off.
     * 
     * @return result of log-off, true if successful
     */
    private boolean endUsersSession(String name)
    {
        /* Set up process to run qwinsta command and get user sessions */ 
        ProcessBuilder sessionBuilder = new ProcessBuilder();
        List<String> sessionCommand = new Vector<String>();
        String userName = name;
        
        sessionCommand.add("qwinsta");
        sessionCommand.add(userName);
        try
        {
            /* Start the process */
            Process proc = sessionBuilder.start();
            this.logger.debug("Session ID command invoked at " + this.getTimeStamp('/', ' ', ':'));
            if (proc.waitFor() == 0)
            {
                /*Read process output */
                InputStream is = proc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                String qwinstaSplit[];

                while (br.ready() && (line = br.readLine()) != null )
                {
                    /*Split result of process output to get sessionID */
                    if (line.contains(userName))
                    {
                        qwinstaSplit = line.split("\\s");
                        String sessionID = qwinstaSplit[2];
                        this.logger.debug("Session ID read is " + sessionID);
                            
                        /* Log off all found sessions */
                        if (logoffSession(sessionID))
                        {
                            this.logger.debug("Session ID ended is " + sessionID);
                        }
                    }
                }
             }
             return true;
        } 
        catch(Exception e)
        {
            this.logger.warn("Checking for user's Session ID failed with exception of type " + e.getClass().getName() + " and with " +
                    "message " + e.getMessage() + ".");
            return false;
        }
    }

    /**
     * Use the windows "logoff" command to end a session with the given
     * session ID
     * 
     * @param sessionID
     * @return result of log off command, true if successful
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
                    "message " + e.getMessage() + ".");
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
    
    @Override
    public void setupAccessAction()
    {
        /*  All done in assign */
    }
    
    @Override
    public boolean verifyAccessAction()
    {
        // Do nothing - verified in assign
        return true;
    }
    
}
