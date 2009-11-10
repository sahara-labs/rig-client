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
 * @date 10th November 2009
 *
 * Changelog:
 * - 10/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.tests;

import java.util.List;
import java.util.Map;

import au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner;
import au.edu.uts.eng.remotelabs.rigclient.rig.control.tests.MockBatchRunner;

/**
 * Mock controlled rig for testing.
 */
public class MockControlledRig extends AbstractControlledRig
{
    /** Runner command. */
    private String comm;
    
    /** Runner arguments. */
    private List<String> args;
    
    /** Runner environment variables. */
    private Map<String, String> env;

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#instantiateBatchRunner(java.lang.String, java.lang.String)
     */
    @Override
    protected AbstractBatchRunner instantiateBatchRunner(String fileName, String userName)
    {
        return new MockBatchRunner(fileName, userName, this.comm, this.args, this.env, true);
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig#init()
     */
    @Override
    protected void init()
    {
        /* Doesn't need to do any thing. */
    }

    /**
     * @param comm the comm to set
     */
    public void setComm(String comm)
    {
        this.comm = comm;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(List<String> args)
    {
        this.args = args;
    }

    /**
     * @param env the env to set
     */
    public void setEnv(Map<String, String> env)
    {
        this.env = env;
    }

}
