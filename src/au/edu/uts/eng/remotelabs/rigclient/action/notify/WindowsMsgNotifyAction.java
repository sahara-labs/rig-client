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
 * @date 26th February 2010
 *
 * Changelog:
 * - 26/02/2010 - tmachet - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.notify;

import java.util.ArrayList;
import java.util.List;

import au.edu.uts.eng.remotelabs.rigclient.action.access.RemoteDesktopAccessAction;
import au.edu.uts.eng.remotelabs.rigclient.rig.INotifyAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * This class allows notification to be sent to users for Windows (XP, NT, 2000, 2003
 * Me, 98 and 95). The notification is sent using the <code>net send</code> command.
 * The implementation assumes that the messenger service is enabled on the computer.
 * <p>
 * This notification action does not work for Windows Vista.  
 * <p>
 * @author tmachet
 *
 */
public class WindowsMsgNotifyAction implements INotifyAction
{
    /** Default command for changing user groups for windows */
    public static final String DEFAULT_COMMAND = "net";

    /** Default command for changing user groups for windows */
    public static final String DEFAULT_ACTION = "send";

    /** Default user group for remote desktop access. */
    private String failureReason;

    /** Logger. */
    protected ILogger logger;

    /**
     * Constructor.
     */
    public WindowsMsgNotifyAction()
    {
        this.logger = LoggerFactory.getLoggerInstance();

        /* Windows notification only valid for windows and not for Vista */
        if (System.getProperty("os.name").startsWith("Windows") && !System.getProperty("os.name").contains("Vista"))
        {
            this.logger.info("Preparing to send notification.");
        }
        else
        {
            throw new IllegalStateException("Windows Message Notify Action is only valid for a WINDOWS platforms not " + System.getProperty("os.name"));
        }
    }

    @Override
    public boolean notify(String message, String[] users)
    {
        synchronized (this)
        {
            Boolean hasSucceeded = true;
            final List<String> command = new ArrayList<String>();

            command.add(WindowsMsgNotifyAction.DEFAULT_COMMAND);
            command.add(WindowsMsgNotifyAction.DEFAULT_ACTION);
            
            for (String us : users)
            {
                try
                {
                    List<String> commandNotify = command;
                    commandNotify.add(us);
                    commandNotify.add(message);
                    
                    this.logger.debug("Command to be executed is " + commandNotify.toString());

                    ProcessBuilder builder = new ProcessBuilder(commandNotify);
                    int result = builder.start().waitFor();
                    
                    if (result != 0)
                    {
                        hasSucceeded = false;
                        this.failureReason = "Unable to send Windows notification " + message + " to all users";
                        this.logger.info("Attempt to send notification message " + message + " to the user " 
                                + us + "failed with error code " + result + '.');
                    }
                }
                catch (final Exception e)
                {
                    this.failureReason = "Unable to send Windows notification " + message + " to all users";
                    this.logger.info("Attempt to send notification meassage " + message + " to the user " 
                            + us + "failed with error " + e.getMessage() + '.');
                    hasSucceeded = false;
                }
            }
            return hasSucceeded;
        }
    }

    @Override
    public String getActionType()
    {
        return "Windows Message Notification Action";
    }

    @Override
    public String getFailureReason()
    {
        return this.failureReason;
    }

}
