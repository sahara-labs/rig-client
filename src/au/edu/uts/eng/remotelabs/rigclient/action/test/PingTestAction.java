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
 * @date 19th January 2010
 *
 * Changelog:
 * - 19/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Test action which determines if a host is up by pinging the host.
 * <p />
 * The behavior of ping test is:
 * <ol>
 *  <li>Test run interval - by default is 30 seconds but may be configured
 *  by setting the property 'Ping_Test_Interval' in standard configuration
 *  to a value in seconds.</li>
 *  <li>Periodicity - is periodic.</li>
 *  <li>Set interval - ignored, not honoured.</li> 
 * </ol>
 * The configuration properties for PingTestAction are:
 * <ul>
 *  <li><tt>Ping_Test_Host_&lt;n&gt;</tt> - The host names for the ping test
 *  to verify for response, where <tt>'n'</tt> is from 1 to the nth
 *  host to ping test, in order. The <tt>'Ping_Test_Host_1'</tt> property 
 *  is mandatory and each subsequent property is optional.</li>
 *  <li><tt>Ping_Test_Comand</tt> - The command to execute a ping. This is 
 *  optional, with the default being 'ping' which is expected to be in 
 *  $PATH in UNIX and %PATH% in Windows.</li>
 *  <li><tt>Ping_Test_Args</tt> - The arguments to supply to the ping command.
 *  Ideally this should cause the ping command to ping the host once and have
 *  a timeout of a few seconds. The host address is always the last argument.</li>
 *  <li><tt>Ping_Test_Interval</tt> - The time between ping tests in seconds.</li>
 *  <li><tt>Ping_Test_Fail_Threshold</tt> - The number of times ping must
 *  fail before the ping test fails.</li>
 * </ul>
 */
public class PingTestAction extends AbstractTestAction
{
    /** Default 'ping' command. */
    public static final String DEFAULT_PING = "ping";
    
    /** The hosts and their associated failures. */
    private final Map<String, Long> hosts;
    
    /** Ping process builder. */
    private ProcessBuilder pingBuilder;
    
    /** The number of times ping can fail before the test fails. */
    private int failThreshold;
    
    
    public PingTestAction()
    {
        super();
        this.runInterval = 30;
        this.isPeriodic = true;
        this.isSetIntervalHonoured = false;
        
        this.hosts = new HashMap<String, Long>();
    }
    
    @Override
    public void setUp()
    {
        List<String> command = new ArrayList<String>();
        String tmp;
        
        command.add(this.config.getProperty("Ping_Test_Command", PingTestAction.DEFAULT_PING));
        
    }
    
    @Override
    public void doTest()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void tearDown()
    {
        /* Does nothing. */
    }

    @Override
    public String getReason()
    {
        for (Entry<String, Long> host : this.hosts.entrySet())
        {
            if (host.getValue() > this.failThreshold)
            {
                return "Host " + host.getKey() + " has not responded to ping " + host.getValue() + " times."; 
            }
        }
        return null;
    }

    @Override
    public boolean getStatus()
    {
        for (Entry<String, Long> host : this.hosts.entrySet())
        {
            if (host.getValue() > this.failThreshold)
            {
                this.logger.debug("Failing ping test as host " + host.getKey() + " has failed " + host.getValue() +
                        " times (threshold is " + this.failThreshold + ").");
                return false;
            }
        }
        return true;
    }

    @Override
    public String getActionType()
    {
        return "Ping test";
    }

}
