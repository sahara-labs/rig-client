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
 * @date 18th January 2010
 *
 * Changelog:
 * - 18/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test;

import au.edu.uts.eng.remotelabs.rigclient.rig.ITestAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Abstract test action class which uses a run flag to implement the 
 * <code>ITestAction.run</code> contract which specifies a persistent
 * thread pausing   
 * <ul>	
 *  <li>The between test pause is a random time between 0 seconds and the
 *  set run interval seconds.</li>
 *  <li>The set run interval may be set to be honoured </li>
 *  <li> 
 * </ul>
 * Abstract test action which uses a run flag to implement the test action 
 * <code>run</code> contract of a persistent thread pausing when the
 * <code>stopTest</code> method is called. The between test pause is a 
 * random interval 0 seconds and the maximum interval time. 
 */
public abstract class AbstractTestAction implements ITestAction
{
    /** Flag to specify if the test is in run mode or wait mode. */
    protected boolean runTest;
    
    /** The maximum amount of seconds between test runs. */
    protected int runInterval = 30;
    
    /** Whether a call to setInterval will actually cause the test run 
     *  interval to be changed (i.e. if the specified test interval is 
     *  honoured). The default is to honour the specified test interval. */
    protected final boolean honourSetInterval = true;
    
    /** Logger. */
    protected final ILogger logger;
    
    /** Configuration. */
    protected final IConfig config;
    
    public AbstractTestAction()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        this.config = ConfigFactory.getInstance();
    }
    
    @Override
    public void run()
    {
        while (!Thread.interrupted())
        {
            
        }
        
    }
    
    @Override
    public void setInterval(int interval)
    {
        if (this.honourSetInterval)
        {
            this.logger.info("Changing the test interval for " + this.getActionType() + " to " + interval + ".");
            this.runInterval = interval;
        }
        else
        {
            this.logger.info("Ignoring request to change test interval for " + this.getActionType() + " to "
                    + interval + ". Keeping test interval at " + this.runInterval + ".");
        }
    }

    @Override
    public void startTest()
    {
        this.logger.debug("Starting " + this.getActionType() + " exerciser test.");
        this.runTest = true;
    }

    @Override
    public void stopTest()
    {
        this.logger.debug("Stopping " + this.getActionType() + " exerciser test.");
        this.runTest = false;
    }

    @Override
    public String getFailureReason()
    {
        /* This method is redundant in <code>ITestAction</code implementations
         * as the error reason method is <code>getReason</code>. */
        return null;
    }
}
