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
 * @date 7th October 2009
 *
 * Changelog:
 * - 07/10/2009 - mdiponio - Initial file creation.
 * - 21/10/2009 - mdiponio - Fixed bugs in setMonitorBadFromActionFailure and
 *                           assign and revoke methods.
 * - 22/10/2009 - mdiponio - Refactored setMonitorBadFromActionFailure to
 *                           a more sensible name and fixed enum Javadoc.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Abstract rig type class that contains lists of the following actions:
 * 
 * <ul>
 *    <li>IAcessAction</li>
 *    <li>ISlaveAccessAction</li>
 *    <li>IResetAction</li>
 *    <li>INotifyAction</li>
 *    <li>ITestActionM</li>
 * </ul>
 *   
 * When a method is called, the corresponding action list is iterated through
 * and the action initiating method is called. For example, if the
 * <code>startTests</code> method is called, the <code>ITestAction</code> list 
 * is iterated through and the <code>startTest</code> method is called.
 */
public abstract class AbstractRig implements IRig
{
    /** 
     * Action types that may be registered with <code>registerAction</code>.
     */
    public enum ActionType 
    {
        /** Master access and revocation. */
        ACCESS,
        /** Slave access and revocation. */
        SLAVE_ACCESS,
        /** User notification. */
        NOTIFY,
        /** Rig reset. */
        RESET,
        /** Monitor test. */
        TEST
    }
    
    /** Session users in the form key => user name, value => user type */
    private Map<String, Session> sessionUsers;
    
    /** Access action list. */
    private List<IAccessAction> accessActions;
    
    /** Slave access action list. */
    private List<ISlaveAccessAction> slaveActions;
    
    /** Notify action list. */
    private List<INotifyAction> notifyActions;
    
    /** Reset action list. */
    private List<IResetAction> resetActions;
    
    /** Test action list. */
    private List<ITestAction> testActions;
    
    /** Test actions thread group. */
    private ThreadGroup testThreads;
    
    /** Maintenance flag. */
    private boolean inMaintenance;
    
    /** Maintenance reason. */
    private String maintenanceReason;
    
    /** Threshold for number of action failures before maintenance mode
     *  is set. */
    private int actionFailureThreshold;
    
    /** Count for action failures which is incremented after a each action
     *  failure. <strong>NOTE:</strong> the failure count is only ever 
     *  cleared when maintenance is cleared, not after a subsequent
     *  successful action. */
    private Map<IAction, Integer> actionFailureCount;
    
    /** Rig client configuration. */
    private IConfig configuration;
    
    /** Logger. */
    private ILogger logger;    
    
    /**
     * Constructor, initialises the action lists.
     */
    public AbstractRig()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        this.logger.debug("Creating a new AbstractRig instance");
        
        this.configuration = ConfigFactory.getInstance();
        
        this.sessionUsers = new HashMap<String, Session>();
        
        this.accessActions = new ArrayList<IAccessAction>();
        this.slaveActions = new ArrayList<ISlaveAccessAction>();
        this.notifyActions = new ArrayList<INotifyAction>();
        this.resetActions = new ArrayList<IResetAction>();
        this.testActions = new ArrayList<ITestAction>();
        this.testThreads = new ThreadGroup("Test Threads");
        
        this.inMaintenance = false;

        try
        {
            this.actionFailureThreshold = Integer.parseInt(this.configuration.getProperty("Action_Failure_Threshold"));
            this.logger.info("Loaded action fail threshold as " + this.actionFailureThreshold);
        }
        catch (NumberFormatException nfe)
        {
            this.actionFailureThreshold = 3;
            this.logger.error("Failed to load the action failure threshold configuration item, so using " + 
                    this.actionFailureThreshold +  "as the default. Please check the configuration " +
            		this.configuration.getConfigurationInfomation() + " and ensure the property " +
                    "'Action_Failure_Threshold' is present and populated with a valide integer. " +
                    "(RC1_Configuration_Failure");
        }
        this.actionFailureCount = new HashMap<IAction, Integer>();
    }
    
    /**
     * Initialise method which derived classes should implement to register rig type
     * specific actions.
     * 
     * This method is called by the <code>RigFactory</code> to complete the
     * initialisation of the rig type class.
     */
    protected abstract void init();

    /**
     * Registers an action to be performed when its corresponding method(s) is
     * called.
     *  
     * @param action instance of IAction to be registered, a <code>null</code>
     *              action will throw a <code>NullPointerException</code> as a 
     *              null action may not be registered
     * @param type action type, the type must corresponded to its action type 
     *              interface (e.g. ActionType.Test corresponds to
     *              ITest action).
     * @return true if the action was successfully registered
     */
    protected boolean registerAction(final IAction action, final ActionType type)
    {
        switch (type)
        {
            case ACCESS: /* Adding an access action. */
                this.logger.debug("Requested to register an access action type.");
                if (!(action instanceof IAccessAction))
                {
                    this.logger.error("Provided action type instance with class name " + 
                            action.getClass().getCanonicalName() + "is not an access action type (must be derived " +
                            "from au.edu.uts.edu.remotelabs.rigclient.rig.IAccessAction. Action type registration" +
                            "has failed. (RC24_Failed_Action_Reg");
                    return false;
                }
                
                for (IAccessAction a : this.accessActions)
                {
                    /* Only the instance is checked not the type. This is to 
                     * support multiple uses of the same type. It is assumed
                     * using the same instance is a bug. */
                    if (a == action)
                    {
                        this.logger.error("Cannot register the same action instance twice. (RC24_Failed_Action_Reg)");
                        return false;
                    }
                }
                /* Looks good so actually register the action. */
                final IAccessAction access = (IAccessAction)action;
                this.logger.info("Registering an access action with provided type of " + access.getActionType());
                return this.accessActions.add(access);
                
            case SLAVE_ACCESS: /* Adding a slave access action. */
                this.logger.debug("Requested to register an slave access action type.");
                if (!(action instanceof ISlaveAccessAction))
                {
                    this.logger.error("Provided action type instance with class name " + 
                            action.getClass().getCanonicalName() + "is not a slave access action type (must be " +
                            "derived from au.edu.uts.edu.remotelabs.rigclient.rig.ISlaveAccessAction. " +
                            "Action type registration has failed. (RC24_Failed_Action_Reg");
                    return false;
                }
                
                for (ISlaveAccessAction a : this.slaveActions)
                {
                    /* Only the instance is checked not the type. This is to 
                     * support multiple uses of the same type. It is assumed
                     * using the same instance is a bug. */
                    if (a == action)
                    {
                        this.logger.error("Cannot register the same action instance twice. (RC24_Failed_Action_Reg)");
                        return false;
                    }
                }
                /* Looks good so actually register the action. */
                final ISlaveAccessAction slave = (ISlaveAccessAction)action;
                this.logger.info("Registering a slave access action with provided type of " + slave.getActionType());
                return this.slaveActions.add(slave);
                
            case NOTIFY: /* Adding a notify action. */
                this.logger.debug("Requested to register an notify access action type.");
                if (!(action instanceof INotifyAction))
                {
                    this.logger.error("Provided action type instance with class name " + 
                            action.getClass().getCanonicalName() + "is not a notify action type (must be " +
                            "derived from au.edu.uts.edu.remotelabs.rigclient.rig.INotifyAction. " +
                            "Action type registration has failed. (RC24_Failed_Action_Reg");
                    return false;
                }
                
                for (INotifyAction a : this.notifyActions)
                {
                    /* Only the instance is checked not the type. This is to 
                     * support multiple uses of the same type. It is assumed
                     * using the same instance is a bug. */
                    if (a == action)
                    {
                        this.logger.error("Cannot register the same action instance twice. (RC24_Failed_Action_Reg)");
                        return false;
                    }
                }
                /* Looks good so actually register the action. */
                final INotifyAction notify = (INotifyAction)action;
                this.logger.info("Registering a notify access action with provided type of " + notify.getActionType());
                return this.notifyActions.add(notify);
                
            case RESET: /* Adding a reset action. */
                this.logger.debug("Requested to register an reset access action type.");
                if (!(action instanceof IResetAction))
                {
                    this.logger.error("Provided action type instance with class name " + 
                            action.getClass().getCanonicalName() + "is not a reset action type (must be " +
                            "derived from au.edu.uts.edu.remotelabs.rigclient.rig.IResetAction. " +
                            "Action type registration has failed. (RC24_Failed_Action_Reg");
                    return false;
                }
                
                for (IResetAction a : this.resetActions)
                {
                    /* Only the instance is checked not the type. This is to 
                     * support multiple uses of the same type. It is assumed
                     * using the same instance is a bug. */
                    if (a == action)
                    {
                        this.logger.error("Cannot register the same action instance twice. (RC24_Failed_Action_Reg)");
                        return false;
                    }
                }
                /* Looks good so actually register the action. */
                final IResetAction reset = (IResetAction)action;
                this.logger.info("Registering a reset access action with provided type of " + reset.getActionType());
                return this.resetActions.add(reset);
                
            case TEST: /* Adding a test action. */
                this.logger.debug("Requested to register an test access action type.");
                if (!(action instanceof ITestAction))
                {
                    this.logger.error("Provided action type instance with class name " + 
                            action.getClass().getCanonicalName() + "is not a test action type (must be " +
                            "derived from au.edu.uts.edu.remotelabs.rigclient.rig.ITestAction. " +
                            "Action type registration has failed. (RC24_Failed_Action_Reg");
                    return false;
                }
                
                for (ITestAction a : this.testActions)
                {
                    /* Only the instance is checked not the type. This is to 
                     * support multiple uses of the same type. It is assumed
                     * using the same instance is a bug. */
                    if (a == action)
                    {
                        this.logger.error("Cannot register the same action instance twice. (RC24_Failed_Action_Reg)");
                        return false;
                    }
                }
                /* Looks good so actually register the action. */
                final ITestAction test = (ITestAction)action;
                this.logger.info("Registering a test access action with provided type of " + test.getActionType());
                
                /* Test actions run it their own thread, so create one and start it. */
                new Thread(this.testThreads, test).start();
                return this.testActions.add(test);
                
            default:
                /* DODGY This is an impossible situation that _should_ never be hit. 
                 * Java type safety ahoy! */
                this.logger.error("Look out the window, the pigs are flying!");
                throw new RuntimeException("Look out the window, the pigs are flying!");
        }
    }
    
    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getAllRigAttributes()
     */
    @Override
    public Map<String, String> getAllRigAttributes()
    {
        return this.configuration.getAllProperties();
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getCapabilities()
     */
    @Override
    public String[] getCapabilities()
    {
        final String cap = this.configuration.getProperty("Rig_Capabilites");
        this.logger.debug("Loaded Rig_Capabilites configuration item as " + cap);
        
        if (cap == null)
        {
            this.logger.error("Rig client capabilities configuration item not found. Please check configuration" +
            		"at " + this.configuration.getConfigurationInfomation() + " and ensure the field " +
            		"'Rig_Capabilites' is present and populated with a comma seperated list of rig client " +
            		"capability tokens. (RC1_Configuration_Failure)");
            return null;
        }
        
        /* Extract tokens from configuration string. */
        String tokens[] = cap.split(",");
        final StringBuffer buf = new StringBuffer();
        buf.append("Rig client capabilites are ");
        for (int i = 0; i < tokens.length; i++)
        {
            tokens[i] = tokens[i].trim();
            buf.append(i);
            buf.append(": ");
            buf.append(tokens[i]);
            if (i != tokens.length - 1) buf.append(", ");
        }
        this.logger.info(buf.toString());
        
        return tokens;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getName()
     */
    @Override
    public String getName()
    {
        final String name = this.configuration.getProperty("Rig_Name");
        this.logger.debug("Loaded Rig_Name configuration item as " + name);
        if (name == null)
        {
            this.logger.error("Rig client name configuration item not found. Please check configuration" +
                    "at " + this.configuration.getConfigurationInfomation() + " and ensure the field " +
                    "'Rig_Name' is present and populated with a rig name string. (RC1_Configuration_Failure)");
        }
        else
        {
            this.logger.info("Rig name is " + name);
        }
        return name;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getRigAttribute(java.lang.String)
     */
    @Override
    public String getRigAttribute(final String key)
    {
        this.logger.debug("Requested to provide the " + key + " rig attribute.");
        if (key == null)
        {
            this.logger.info("Received a null rig attribute request, provided a null response.");
            return null;
        }
        
        final String value = this.configuration.getProperty(key);
        if (value != null)
        {
            this.logger.info("Found rig attribute value, value of " + key + " is " + value);
        }
        else
        {
            this.logger.info("Rig attribute value for " + key + " not found.");
        }
        return value;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getType()
     */
    @Override
    public String getType()
    {
        final String name = this.configuration.getProperty("Rig_Type");
        this.logger.debug("Loaded Rig_Type configuration item as " + name);
        if (name == null)
        {
            this.logger.error("Rig client type configuration item not found. Please check configuration" +
                    "at " + this.configuration.getConfigurationInfomation() + " and ensure the field " +
                    "'Rig_Type' is present and populated with a rig type string. (RC1_Configuration_Failure)");
        }
        else
        {
            this.logger.info("Rig type is " + name);
        }
        return name;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#getMaintenanceReason()
     */
    @Override
    public String getMaintenanceReason()
    {
        return this.maintenanceReason;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#getMonitorReason()
     */
    @Override
    public String getMonitorReason()
    {
        final StringBuffer buf = new StringBuffer();
        for (ITestAction test : this.testActions)
        {
            if (test == null) continue;
            if (!test.getStatus())
            {
                buf.append(test.getActionType());
                buf.append(": ");
                buf.append(test.getReason());
                buf.append(' ');
            }
        }
        
        if (this.maintenanceReason != null)
        {
            buf.append(this.maintenanceReason);
        }
        
        if (buf.length() == 0)
        {
            return null;
        }
        
        this.logger.info("Monitor bad reason:" + buf.toString());
        return buf.toString();
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#isMonitorStatusGood()
     */
    @Override
    public boolean isMonitorStatusGood()
    {
        if (this.inMaintenance) return false;
        for (ITestAction test : this.testActions)
        {
            if (test == null) continue;
            if (!test.getStatus()) return false;
        }
        return true;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#isNotInMaintenance()
     */
    @Override
    public boolean isNotInMaintenance()
    {
        return !this.inMaintenance;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#setInterval(int)
     */
    @Override
    public boolean setInterval(final int interval)
    {
        this.logger.info("Setting test interval to " + interval + " minutes.");
        for (ITestAction test : this.testActions)
        {
            if (test == null) continue;
            test.setInterval(interval);                
        }
        
        /* DODGY In hindsight, the interface of setInterval shouldn't return
         * a value. */
        return true;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#setMaintenance(boolean, java.lang.String, boolean)
     */
    @Override
    public boolean setMaintenance(final boolean offline, final String reason, final boolean runTests)
    {
        if (offline)
        {
            if (this.isSessionActive())
            {
                this.logger.warn("Terminating session and revoking users because maintenance" +
                		"mode is being set.");
                this.revoke();
            }
            this.logger.warn("Putting the rig into maintenance mode. Provided reason " + reason);
            this.inMaintenance = true;
            this.maintenanceReason = reason;
            
            if (runTests)
            {
                this.logger.info("Monitor tests will be run in maintenance mode.");
                this.startTests();
            }
            else
            {
                this.logger.info("Monitor tests will not be run in maintenance mode.");
                this.stopTests();
            }
        }
        else
        {
            this.logger.info("Taking the rig out of maintenance mode.");
            this.inMaintenance = false;
            this.maintenanceReason = null;
            this.logger.info("Monitor tests are going to be started.");
            this.startTests();
        }
        
        return true;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#startTests()
     */
    @Override
    public void startTests()
    {
        this.logger.debug("Starting exerciser tests.");
        for (ITestAction test : this.testActions)
        {
            if (test == null) continue;
            test.startTest();
        }
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#stopTests()
     */
    @Override
    public void stopTests()
    {
        this.logger.debug("Stopping exerciser tests.");
        for (ITestAction test : this.testActions)
        {
            if (test == null) continue;
            test.stopTest();
        }
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#addSlave(java.lang.String, boolean)
     */
    @Override
    public boolean addSlave(final String name, final boolean passive)
    {
        this.logger.debug("Adding slave user " + name + " with " + (passive ? "passive" : "active") + " access.");
        
        /* Slaves can't be assigned if there is no session. */
        if (!this.isSessionActive()) return false;
        
        if (this.sessionUsers.containsKey(name))
        {
            final Session currentPerm = this.sessionUsers.get(name);
            if (passive && currentPerm == Session.SLAVE_PASSIVE) // Requested passive, already a passive user
            {
                this.logger.warn("User " + name + " is already a passive slave user, nothing to do. " +
                		"(RC22_Failed_Slave_Alloc_Already_Slave)");
                return false;
            }
            else if (!passive && currentPerm == Session.SLAVE_ACTIVE) // Requested active, already an active user
            {
                this.logger.warn("User " + name + " is already an active slave user, nothing to do. " +
                		"(RC22_Failed_Slave_Alloc_Already_Slave)");
                return false;
            }
            else if (currentPerm == Session.SLAVE_ACTIVE || currentPerm == Session.SLAVE_PASSIVE)
            {
                this.logger.info("Revoking slave user (" + name + ") to change their permission.");
                if (!this.revokeSlave(name))
                {
                    this.logger.warn("Failed changing slave permission because revocation of previous slave " +
                    		"permission failed revocation (RC23_Failed_Slave_Revoke_Action");
                    return false;
                }    
            }
        }
        
        for (ISlaveAccessAction action : this.slaveActions)
        {
            if (action == null) continue;
            if (!action.assign(name, passive))
            {
                this.setMaintenanceFromActionFailure(action, ActionType.SLAVE_ACCESS);
                return false;
            }
        }
        
        this.sessionUsers.put(name, passive ? Session.SLAVE_PASSIVE : Session.SLAVE_ACTIVE);
        return true;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#assign(java.lang.String)
     */
    @Override
    public boolean assign(final String name)
    {
        this.logger.debug("Assigning master access to " + name);
        
        /* Check there isn't an existing session (master user). */
        if (this.sessionUsers.containsValue(Session.MASTER))
        {
            this.logger.warn("Failed allocation, the rig is already in session with another user. " +
            		"(RC6_Failed_Alloc_In_Session)");
            return false;
        }
        
        /* Check the rig is in a state to be assigned. */
        if (!(this.isMonitorStatusGood() && this.isNotInMaintenance()))
        {
            String message = "Failed allocation, the rig is not in an operable state.";
            if (this.getMaintenanceReason() != null)
            {
                message += " The rig is in maintenance with reason " + this.getMaintenanceReason() + "."; 
            }
            if (this.getMonitorReason() != null)
            {
                message += " The rig is detected to be bad with reason " + this.getMonitorReason() + ".";
            }
            this.logger.warn(message);
            return false;
        }
        
        /* Stop tests. */
        this.stopTests();
         
        for (IAccessAction action : this.accessActions)
        {
            if (action == null) continue;            
            if (!action.assign(name))
            {
                this.setMaintenanceFromActionFailure(action, ActionType.ACCESS);
                this.startTests();
                return false;
            }
        }
        
        this.sessionUsers.put(name, Session.MASTER);
        return true;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#hasPermission(java.lang.String, au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session)
     */
    @Override
    public boolean hasPermission(final String name, final Session ses)
    {
        if (!this.sessionUsers.containsKey(name))
        {
            return ses == Session.NOT_IN;
        }
        
        switch (this.sessionUsers.get(name))
        {
            case MASTER:
                return ses == Session.MASTER || ses == Session.SLAVE_ACTIVE || ses == Session.SLAVE_PASSIVE ||
                        ses == Session.NOT_IN;
            case SLAVE_ACTIVE:
                return ses == Session.SLAVE_ACTIVE || ses == Session.SLAVE_PASSIVE || ses == Session.NOT_IN;
            case SLAVE_PASSIVE:
                return ses == Session.SLAVE_PASSIVE || ses == Session.NOT_IN;
        }
        
        this.logger.error("All permission states unaccounted for, someone has been been naughty" +
        		"and put a Session.NOT_IN user as a session user. Please file bug report. (RC22_Unexpected_Error)");
        throw new RuntimeException("Error in AbstractRig->hasPermission");
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#isInSession(java.lang.String)
     */
    @Override
    public Session isInSession(final String name)
    {
        if (!this.sessionUsers.containsKey(name))
        {
            return Session.NOT_IN;
        }
        
        return this.sessionUsers.get(name);
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#notify(java.lang.String)
     */
    @Override
    public boolean notify(final String message)
    {
        if (!this.isSessionActive()) return false;
        
        boolean ret = true;
        final String typeSafe[] = new String[0];
        this.logger.debug("Running notification for all users with message " + message);
        for (INotifyAction action : this.notifyActions)
        {
            if (action == null) continue;
            if (!action.notify(message, this.sessionUsers.keySet().toArray(typeSafe)))
            {
                this.setMaintenanceFromActionFailure(action, ActionType.NOTIFY);
                ret = false;
            }
        }
        return ret;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#revoke()
     */
    @Override
    public boolean revoke()
    {
        this.logger.debug("Terminating a session and revoking access from master user.");
        boolean ret = true;
        
        /* First check there is a master user. */
        if (!this.isSessionActive())
        {
            this.logger.warn("Unable to terminate a session as there is no currently running session. " +
            		"(RC11_Failed_Success_Req_Wrong_User)");
            return false;
        }

        String user = null;
        Session perm;
        for (Entry<String, Session> entry : this.getSessionUsersClone().entrySet())
        {
            user = entry.getKey();
            perm = entry.getValue();
            
            if (perm == Session.MASTER) // If master, run revoke actions  
            {
                this.logger.info("Terminating and revoking master session user: " + user); 
                for (IAccessAction action : this.accessActions)
                {
                    if (action == null) continue;
                    if (!action.revoke(user))
                    {
                        this.setMaintenanceFromActionFailure(action, ActionType.ACCESS);
                        ret = false;
                    }
                }
            }
            else if ((perm == Session.SLAVE_ACTIVE || perm == Session.SLAVE_PASSIVE) && !this.revokeSlave(user))
            {
                // If slave, invoke slave revoke
                ret = false;
            }
        }        
        
        /* Remove the session users. */
        this.sessionUsers.clear();
        
        /* Reset the rig. */
        this.logger.debug("Running the rig reset actions.");
        for (IResetAction action : this.resetActions)
        {
            if (action == null) continue;
            if (!action.reset())
            {
                this.setMaintenanceFromActionFailure(action, ActionType.RESET);
                ret = false;
            }
        }
        
        /* Start the tests. */
        this.startTests();
        return ret;
    }

    /*
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#isSessionActive()
     */
    @Override
    public boolean isSessionActive()
    {
        boolean isMaster = false;
        for (Session ses : this.sessionUsers.values())
        {
            if (ses == Session.MASTER) isMaster = true;            
        }
        
        this.logger.debug("Session check providing: " + (isMaster ? "Session is active." : "Session is not active"));
        return isMaster;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#revokeSlave(java.lang.String)
     */
    @Override
    public boolean revokeSlave(final String name)
    {
        boolean ret = true;
        this.logger.debug("Attempting to revoke slave access from: " + name);
        
        /* First check the user is actually a slave user. */
        final Session perm = this.isInSession(name);
        if (perm != Session.SLAVE_ACTIVE && perm != Session.SLAVE_PASSIVE)
        {
            this.logger.warn("Failed revoking slave access, provided user " + name + " is not a slave user. " +
            		"(RC22_Unexpected_Error)");
            return false;
        }
        
        /* Remove user from being a slave user. */
        this.sessionUsers.remove(name);
        
        /* Run the slave revocation actions. */
        for (ISlaveAccessAction action : this.slaveActions)
        {
            if (action == null) continue;
            if (!action.revoke(name, perm == Session.SLAVE_PASSIVE ? true : false))
            {
                this.setMaintenanceFromActionFailure(action, ActionType.SLAVE_ACCESS);
                ret = false;
            }
        }
        
        return ret;
    }
    
    /**
     * Cleans up this class. This should be called before the rig client
     * is shutdown. 
     */
    public void cleanUp()
    {
        this.testThreads.interrupt();
    }
    
    /**
     * Puts the rig into maintenance mode because of a failure reported by
     * an action.
     * 
     * @param action action which failed
     */
    protected void setMaintenanceFromActionFailure(final IAction action, final ActionType type)
    {
        String ac = null, errCode = null;
        switch (type)
        {
            case ACCESS:
                ac = "Session access";
                errCode = "RC7_Failed_Alloc_Action";
                break;
            case SLAVE_ACCESS:
                ac = "Slave access";
                errCode = ""; // TODO slave access failure error code
                break;
            case NOTIFY:
                ac = "Notification";
                errCode = "RC10_Failed_Notif_Action";
                break;
            case RESET:
                ac = "Device reset";
                errCode = "RC9_Failed_Reset_Action";
                break;
            case TEST:
                ac = "Exerciser test";
                errCode = ""; // TODO exerciser test failure error code
                break;
        }
        this.logger.error(ac + " action of type " + action.getActionType() + " failed with reason " + 
                action.getFailureReason() + ". (" + errCode + ")");
        
        if (this.actionFailureCount.containsKey(action))
        {
            this.actionFailureCount.put(action, (this.actionFailureCount.get(action)) + 1);
            this.logger.info("Incrementing action failure for " + action.getActionType() + ". Current value is " +
            		+ this.actionFailureCount.get(action) + ". Threshold for action failues is " + 
            		this.actionFailureThreshold + ".");
        }
        else
        {
            this.actionFailureCount.put(action, 1);
            this.logger.info("First action failure for " + action.getActionType() + "Threshold for action failures" +
            		" is " + this.actionFailureThreshold + ".");
        }
        
        if (this.actionFailureCount.get(action) >= this.actionFailureThreshold)
        {
            this.logger.error("Rig has been put into maintenance mode because an action failure " + 
                    action.getActionType() + " count has reached the failure threshold. " +
                    "(RC25_Action_Failure_Thres_Exceeded)");
            this.inMaintenance = true;
            this.maintenanceReason = ac + " action failed with reason " + action.getFailureReason(); 
        }
    }
    
    /**
     * Returns a deep clone of the session users hash map.
     * 
     * @return session users map clone
     */
    private Map<String, Session> getSessionUsersClone()
    {
        final Map<String, Session> sessions = new HashMap<String, Session>(this.sessionUsers.size());
        for (Entry<String, Session> entry : this.sessionUsers.entrySet())
        {
            sessions.put(entry.getKey(), entry.getValue());
        }
        return sessions;
    }

}
