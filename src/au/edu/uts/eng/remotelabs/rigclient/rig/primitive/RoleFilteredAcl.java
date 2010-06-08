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
 * @author Michael Diponio (mdiponio)
 * @date 7th June 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.primitive;

import java.util.ArrayList;
import java.util.List;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session;

/**
 * Implementation of primitve control access control class which filters access
 * to actions for slave roles based on action control lists. The following list
 * shows how access is determined on a per role basis:
 * <ul>
 *  <li><em>Master</em> - The master user has implicit access to all actions.</li>
 *  <li><em>Slave Active</em> - Slave active users have explicit access to the 
 *  actions in the slave active access control list and inherited access from the
 *  slave passive access control list.</li>
 *  <li><em>Slave Passive</em> - Slave passive users have explicit access to only 
 *  the actions in the slave passive access control list.</li>
 * </ul>
 * To set up access, populate the lists <code>slaveActiveActions</code> and
 * <code>slavePassiveActions</code> with the names of actions (not including the
 * 'Action' method name suffix) for setting access for slave active and slave passive
 * roles respectively.
 */
public class RoleFilteredAcl implements IPrimitiveAcl 
{
    /** List of actions the slave active role can perform. */ 
    protected final List<String> slaveActiveActions = new ArrayList<String>();
    
    /** List of actions the slave passive role can perform. */
    protected final List<String> slavePassiveActions = new ArrayList<String>();
        
    @Override
    public boolean allowRole(Session session, String action)
    {
        switch (session)
        {
            case MASTER:
                return true;
            case SLAVE_ACTIVE:
                return this.slaveActiveActions.contains(action) || this.slavePassiveActions.contains(action);
            case SLAVE_PASSIVE:
                return this.slavePassiveActions.contains(action);
            default:
                return false;
        }
    }

}
