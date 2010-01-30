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
 * @author <First> <Last> (tmachet)
 * @date <Day> <Month> 2010
 *
 * Changelog:
 * - 19/01/2010 - tmachet - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.access.tests;

import au.edu.uts.eng.remotelabs.rigclient.action.access.ExecAccessAction;

/**
 * @author tmachet
 *
 */
public class MockAccessAction extends ExecAccessAction
{
    /**
     * Constructor  - sets instruction file name.
     * 
     * @param fileName
     * @param user 
     */
    public MockAccessAction()
    {
        super();
    }
    
    /* (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.action.access.ExecAccessAction#setupAccessAction()
     */
    @Override
    protected void setupAccessAction()
    {
        // set up action to echo out comment
        this.command = "echo";
        this.commandArguments.add("testing");
        this.commandArguments.add("testing");
        this.commandArguments.add("123");
        
        this.workingDirectory = "/tmp";
        
    }

    /* 
     * (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.action.access.ExecAccessAction#verifyAccessAction()
     */
    @Override
    protected boolean verifyAccessAction()
    {
        return false;
    }

    /* (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction#assign(java.lang.String)
     */
    @Override
    public boolean assign(String name)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction#revoke(java.lang.String)
     */
    @Override
    public boolean revoke(String name)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAction#getActionType()
     */
    @Override
    public String getActionType()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAction#getFailureReason()
     */
    @Override
    public String getFailureReason()
    {
        return null;
    }

}
