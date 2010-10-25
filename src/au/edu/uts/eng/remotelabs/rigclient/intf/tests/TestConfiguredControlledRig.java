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
 * @date 9th November 2009
 *
 * Changelog:
 * - 09/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.intf.tests;

import au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.IAction;
import au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner;
import au.edu.uts.eng.remotelabs.rigclient.rig.internal.ConfigurationActionLoader;

/**
 * An controlled rig that uses a configured batch runner (i.e. configuration
 * may be used to specified at run time what to run, what arguments to provide
 * and what environment variables to set.)
 */
public class TestConfiguredControlledRig extends AbstractControlledRig
{
    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#instantiateBatchRunner(java.lang.String, java.lang.String)
     */
    @Override
    protected AbstractBatchRunner instantiateBatchRunner(String fileName, String userName)
    {
       return new TestConfiguredBatchRunner(fileName, userName);
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig#init()
     */
    @Override
    protected void init()
    {
        /* DODGY This is unfortunately a copy/paste job from ConfiguredControlledRig.init. */
        final ConfigurationActionLoader loader = new ConfigurationActionLoader();

        /* Access actions. */
        for (IAction action : loader.getConfiguredActions(ActionType.ACCESS))
        {
            this.registerAction(action, ActionType.ACCESS);
        }

        /* Slave access actions. */
        for (IAction action : loader.getConfiguredActions(ActionType.SLAVE_ACCESS))
        {
            this.registerAction(action, ActionType.SLAVE_ACCESS);
        }

        /* Notification actions. */
        for (IAction action : loader.getConfiguredActions(ActionType.NOTIFY))
        {
            this.registerAction(action, ActionType.NOTIFY);
        }

        /* Reset actions. */
        for (IAction action : loader.getConfiguredActions(ActionType.RESET))
        {
            this.registerAction(action, ActionType.RESET);
        }

        /* Test actions. */
        for (IAction action : loader.getConfiguredActions(ActionType.TEST))
        {
            this.registerAction(action, ActionType.TEST);
        }

        /* Activity detection actions. */
        for (IAction action : loader.getConfiguredActions(ActionType.DETECT))
        {
            this.registerAction(action, ActionType.DETECT);
        }
    }

}
