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
 * @date 5th October 2009
 *
 * Changelog:
 * - 05/10/2009 - mdiponio - Initial file creation.
 * - 22/10/2009 - mdiponio - Fixed Javadoc enum.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Interface for rig session operations.
 */
public interface IRigSession
{
    /**
     * Specifies if that state a user may be in with respect to 
     * sessions.
     */
    public enum Session
    {
        /** Master user. */
        MASTER,
        /** Active slave user. */
        SLAVE_ACTIVE,
        /** Passive slave user. */
        SLAVE_PASSIVE,
        /** Not in session. */
        NOT_IN
    }
    
    /**
     * Checks if a session is active (i.e. a master user is present as a user).
     * 
     * @return true if a session is active, false otherwise
     */
    public boolean isSessionActive();
    
    /**
     * Starts a rig session, assigning the master user to access the rig.
     * 
     * @param name master users name
     * @return true if successful, false otherwise
     */
    public boolean assign(String name);
    
    /**
     * Ends a rig session, revoking the master users access to the rig.
     * 
     * @return true if successful, false otherwise
     */
    public boolean revoke();
    
    /**
     * Adds a slave user to the rig session. The meaning of the passive flag is
     * specific to the rig type but may mean view only restrictions. This will 
     * fail if the rig is not in session, as slave users may not initiate 
     * sessions.
     * 
     * @param name slave users name
     * @return true if successful, false otherwise
     */
    public boolean addSlave(String name, boolean passive);
    
    /**
     * Revokes a slave user from the rig session. Does not end the rig session.
     * If the supplied user is not an existing slave user this method will 
     * succeed (as they will not have slave access).
     * 
     * @param name slave users name
     * @return true if successful, false otherwise
     */
    public boolean revokeSlave(String name);
    
    /**
     * Displays a notification message to the user(s) who are in session. 
     * If this method is invoked when a session is not active, it will
     * return <code>false</code>.
     * 
     * @param message message to display
     * @return true if successful
     */
    public boolean notify(String message);
    
    /**
     * Checks if the user has the specified permission type. The session
     * permission hierarchy is from highest to lowest:
     * 
     * <ol>
     *     <li>Master</li>
     *     <li>Active Slave</li>
     *     <li>Passive Slave</li>
     *     <li>Not in session</li>
     *  </ol>
     *  
     * So for example, a master user has the permission of an active slave
     * and below and an active slave has permission of a passive slave 
     * and below but not a master user permission.
     * 
     * By definition, a master user is also an active slave and a passive
     * slave. Semantically, a session user is NOT not in session, however,
     * an in session user should be able to do anything a not in session
     * user can do (which just happens to be nothing!).
     * 
     * @param name user name to check
     * @param ses <code>Session</code> session enumeration
     * @return true if the user has the permission (or equivalent).
     */
    public boolean hasPermission(String name, Session ses);
    
    /**
     * Checks the users session. The return may be:
     *  
     * <ul>
     *     <li><code>Session.MASTER</code> - master user - session initiator,
     *            should have complete access to rig, including assigning 
     *            slave users.</li>
     *     <li><code>Session.SLAVE_ACTIVE</code> - active slave user (should 
     *            have the same rig access rigs as the master user,
     *            except assigning slave users.</li>
     *     <li>code>Session.SLAVE_PASSIVE</code> - passive slave user (should have 
     *            restricted access, perhaps read-only access.</li>
     *     <li><code>Session.NOT_IN</code> - user not in session.</li>
     * </ul> 
     * 
     * @param name the name of to user to check if in session
     * @return enumeration value of <code>Session</code>
     */
    public Session isInSession(String name);
    
    /**
     * Detect if any activity is present on the rig (i.e. if a user has been
     * assigned, they are actually using the experiment.).
     * <strong>NOTE:</strong> If this cannot be detected for the experiment 
     * type, the response should be <code>true</code> as a <code>false</code>
     * response may mean the rig is eligible for reassignment. 
     * 
     * @return <code>true</code> if activity is detected
     */
    public boolean isActivityDetected();
    
    /**
     * Returns the list of session users with their associated types.
     * 
     * @return session user list
     */
    public Map<String, Session> getSessionUsers();
    
    /**
     * Provides a list of session files that have been generated in this session.
     * 
     * @return list of session files
     */
    public Set<File> detectSessionFiles();
}
