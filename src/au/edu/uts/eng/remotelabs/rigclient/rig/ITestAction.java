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
 */
package au.edu.uts.eng.remotelabs.rigclient.rig;

/**
 * Interface for an exerciser test.  Each test runs in its own thread which
 * is started on test construction. The <code>run</code> method should be
 * defensive to not allow this thread to terminate. Causing this thread to
 * terminate will cause the test to terminate and not be re-startable. 
 */
public interface ITestAction extends IAction, Runnable
{
    /**
     * Starts the test. Should notify the test thread to resume testing.
     */
    public void startTest();
    
    /**
     * Stops the test. Should notify the test thread to release any held
     * resources, then pause until the <code>startTest</code> method is
     * called to resume testing. Should <strong>NOT</strong> cause the 
     * test thread to terminate.
     */
    public void stopTest();
    
    /**
     * Gets the rig status. <code>true</code> is returned if the tested
     * rig attribute is integral, <code>false</code> is returned if 
     * the test fails.
     *
     * @return true if test integral, false otherwise
     */
    public boolean getStatus();
    
    /**
     * Gets the reason a test has failed. <code>null</code> is returned
     * if the test has not failed.
     * 
     * @return reason for failure
     */
    public String getReason();
    
    /**
     * Sets the test interval which specifies how often the test executes
     * in minutes.
     * 
     * @param interval test interval in minutes
     */
    public void setInterval(int interval);
}
