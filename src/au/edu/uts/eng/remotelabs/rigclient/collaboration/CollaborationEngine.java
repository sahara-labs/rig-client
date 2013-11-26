/**
 * SAHARA Rig Client
 * 
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2013, Barath Kannan
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
 * @author Barath Kannan
 * @date 26th November 2013
 */

package au.edu.uts.eng.remotelabs.rigclient.collaboration;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session;
import au.edu.uts.eng.remotelabs.rigclient.type.RigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

public class CollaborationEngine implements Runnable
{

    /** Singleton instance. */
    private static CollaborationEngine instance = null;

    /** Logger. */
    private static ILogger logger;

    /** Interface to Rig */
    private static IRig rig;

    private static int numUsers = 0;

    /** Controls allowed for each non-master user in the current rig session */
    private static volatile Map<String, Boolean> userControls;

    private static Set<String> userControlsSet;

    private static String master;

    private static String mode;

    private static volatile Set<String> registeredSlaveActions;

    private static CommunicationDirectory comDir;

    /** Thread for maintaining user data from the Rig interface **/
    private static Thread collaborationThread;

    private void init()
    {
        CollaborationEngine.logger = LoggerFactory.getLoggerInstance();
        CollaborationEngine.rig = RigFactory.getRigInstance();
        CollaborationEngine.userControlsSet = new TreeSet<String>();
        CollaborationEngine.comDir = new CommunicationDirectory();
        this.updateUserList();

        CollaborationEngine.collaborationThread = new Thread(this);
        CollaborationEngine.collaborationThread.start();
        CollaborationEngine.setModeMS();

        CollaborationEngine.registeredSlaveActions = new TreeSet<String>();
        CollaborationEngine.registeredSlaveActions.add("dataAction");
        CollaborationEngine.registeredSlaveActions.add("pushMessageAction");

        CollaborationEngine.logger.debug("Collaboration Engine start sequence successful.");
    }

    public static synchronized void restart()
    {
        if (CollaborationEngine.numUsers == 0)
        {
            CollaborationEngine.instance = new CollaborationEngine();
            CollaborationEngine.instance.init();
        }
    }

    @Override
    public void run()
    {
        try
        {
            while (!Thread.interrupted())
            {
                this.updateUserList();
                Thread.sleep(500);

                if (CollaborationEngine.numUsers == 0)
                {
                    CollaborationEngine.collaborationThread.interrupt();
                }
            }
        }
        catch (final InterruptedException e)
        {
            /* Shutting down. */
        }
    }

    public static String getMasterUser()
    {
        return CollaborationEngine.master.toString();
    }

    public static boolean hasControl(final String user)
    {
        if (user.equals(CollaborationEngine.master))
        {
            return true;
        }
        if (CollaborationEngine.getMode().equals("Free For All"))
        {
            return true;
        }
        return CollaborationEngine.userControlsSet.contains(user);
    }

    public static synchronized void assignControlToUser(final String user)
    {
        if (CollaborationEngine.getMode().equals("Master/Slave"))
        {
            CollaborationEngine.userControlsSet.add(user);
        }
        else if (CollaborationEngine.getMode().equals("Baton Pass"))
        {
            CollaborationEngine.userControlsSet.clear();
            CollaborationEngine.userControlsSet.add(user);
        }
    }

    public static synchronized void removeControlFromUser(final String user)
    {
        CollaborationEngine.userControlsSet.remove(user);
    }

    private synchronized void updateUserList()
    {
        final Map<String, Boolean> dirtyControls = new TreeMap<String, Boolean>();

        for (final Entry<String, Session> e : CollaborationEngine.rig.getSessionUsers().entrySet())
        {
            switch (e.getValue())
            {
                case MASTER:
                    CollaborationEngine.master = e.getKey();
                    break;
                case SLAVE_ACTIVE:
                    if (CollaborationEngine.userControlsSet.contains(e.getKey()))
                    {
                        dirtyControls.put(e.getKey(), true);
                    }
                    else
                    {
                        dirtyControls.put(e.getKey(), false);
                    }
                    break;
                case SLAVE_PASSIVE:
                    if (CollaborationEngine.userControlsSet.contains(e.getKey()))
                    {
                        dirtyControls.put(e.getKey(), true);
                    }
                    else
                    {
                        dirtyControls.put(e.getKey(), false);
                    }
                    break;
            }
        }

        CollaborationEngine.numUsers = CollaborationEngine.rig.getSessionUsers().size();
        CollaborationEngine.userControls = dirtyControls;
    }

    public static String getUserList()
    {
        return CollaborationEngine.userControls.toString();
    }

    public static CommunicationDirectory getDirectory()
    {
        return CollaborationEngine.comDir;
    }

    public static String getMode()
    {
        return CollaborationEngine.mode;
    }

    public static synchronized void setModeFFA()
    {
        CollaborationEngine.mode = "Free For All";
        CollaborationEngine.userControlsSet.clear();
    }

    public static synchronized void setModeMS()
    {
        CollaborationEngine.mode = "Master/Slave";
        CollaborationEngine.userControlsSet.clear();
    }

    public static synchronized void setModeBP()
    {
        CollaborationEngine.mode = "Baton Pass";
        CollaborationEngine.userControlsSet.clear();
    }

    public static boolean checkSlaveAction(final String action)
    {
        if (CollaborationEngine.registeredSlaveActions.contains(action))
        {
            return true;
        }
        return false;
    }

    public static synchronized void registerSlaveAction(final String action)
    {
        CollaborationEngine.registeredSlaveActions.add(action);
    }

}
