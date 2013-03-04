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
 * @date 4th December 2009
 *
 * Changelog:
 * - 04/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.type.tests;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;

/**
 * Mock IRig for factory test.
 */
public class MockIRig implements IRig
{

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getAllRigAttributes()
     */
    @Override
    public Map<String, String> getAllRigAttributes()
    {
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getCapabilities()
     */
    @Override
    public String[] getCapabilities()
    {
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getName()
     */
    @Override
    public String getName()
    {
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getRigAttribute(java.lang.String)
     */
    @Override
    public String getRigAttribute(String key)
    {
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRig#getType()
     */
    @Override
    public String getType()
    {
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#getMaintenanceReason()
     */
    @Override
    public String getMaintenanceReason()
    {
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#getMonitorReason()
     */
    @Override
    public String getMonitorReason()
    {
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#isMonitorStatusGood()
     */
    @Override
    public boolean isMonitorStatusGood()
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#isNotInMaintenance()
     */
    @Override
    public boolean isNotInMaintenance()
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#setInterval(int)
     */
    @Override
    public boolean setInterval(int interval)
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#setMaintenance(boolean, java.lang.String, boolean)
     */
    @Override
    public boolean setMaintenance(boolean offline, String reason, boolean runTests)
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#startTests()
     */
    @Override
    public void startTests()
    {
        /* Does nothing. */
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigExerciser#stopTests()
     */
    @Override
    public void stopTests()
    {
        /* Does nothing. */
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#addSlave(java.lang.String, boolean)
     */
    @Override
    public boolean addSlave(String name, boolean passive)
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#assign(java.lang.String)
     */
    @Override
    public boolean assign(String name)
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#hasPermission(java.lang.String, au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session)
     */
    @Override
    public boolean hasPermission(String name, Session ses)
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#isActivityDetected()
     */
    @Override
    public boolean isActivityDetected()
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#isInSession(java.lang.String)
     */
    @Override
    public Session isInSession(String name)
    {
        return null;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#isSessionActive()
     */
    @Override
    public boolean isSessionActive()
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#notify(java.lang.String)
     */
    @Override
    public boolean notify(String message)
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#revoke()
     */
    @Override
    public boolean revoke()
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#revokeSlave(java.lang.String)
     */
    @Override
    public boolean revokeSlave(String name)
    {
        return false;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#getSessionUsers()
     */
    @Override
    public Map<String, Session> getSessionUsers()
    {
        return null;
    }

    /**
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession#detectSessionFiles()
     */
    @Override
    public Set<File> detectSessionFiles()
    {
        return Collections.emptySet();
    }

}
