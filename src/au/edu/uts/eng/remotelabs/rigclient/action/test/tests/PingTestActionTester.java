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
 * @author <First> <Last> (mdiponio)
 * @date <Day> <Month> 2010
 *
 * Changelog:
 * - 20/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test.tests;


import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.action.test.AbstractTestAction;
import au.edu.uts.eng.remotelabs.rigclient.action.test.PingTestAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Tests the {@link PingTestAction} class.
 */
@Deprecated
public class PingTestActionTester extends TestCase
{
    /** Object of class under test. */
    private PingTestAction pinger;
    
    /** Mock configuration. */
    private IConfig mockConfig;
    
    @Before
    @Override
    public void setUp() throws Exception
    {
        this.mockConfig = createMock(IConfig.class);
        expect(this.mockConfig.getProperty("Logger_Type"))
                .andReturn("SystemErr");
        expect(this.mockConfig.getProperty("Log_Level"))
                .andReturn("DEBUG");
        expect(this.mockConfig.getProperty("Default_Log_Format", "[__LEVEL__] - [__ISO8601__] - __MESSAGE__"))
                .andReturn("[__LEVEL__] - [__ISO8601__] - __MESSAGE__");
        expect(this.mockConfig.getProperty("FATAL_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("PRIORITY_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("ERROR_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("WARN_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("INFO_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("DEBUG_Log_Format")).andReturn(null);
        replay(this.mockConfig);
        
        ConfigFactory.getInstance();
        Field f = ConfigFactory.class.getDeclaredField("instance");
        f.setAccessible(true);
        f.set(null, this.mockConfig);
        
        LoggerFactory.getLoggerInstance(); 
        this.pinger = new PingTestAction();
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSetup() throws Exception
    {
        Method meth = PingTestAction.class.getDeclaredMethod("getDefaultPingArgs");
        meth.setAccessible(true);
        String args = (String)meth.invoke(this.pinger);
        assertNotNull(args);
        List<String> argsList = Arrays.asList(args.split(" "));
        if (System.getProperty("os.name").startsWith("Linux"))
        {
            assertEquals(5, argsList.size());
            assertTrue(argsList.contains("-c"));
            assertTrue(argsList.contains("1"));
            assertTrue(argsList.contains("-q"));
            assertTrue(argsList.contains("-W"));
            assertTrue(argsList.contains("5"));
        }
        else if (System.getProperty("os.name").startsWith("Windows"))
        {
            assertEquals(4, argsList.size());
            assertTrue(argsList.contains("-n"));
            assertTrue(argsList.contains("1"));
            assertTrue(argsList.contains("-w"));
            assertTrue(argsList.contains("5000"));
            
        }
        
        assertEquals("ping", PingTestAction.DEFAULT_PING);
        
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Ping_Test_Command", "ping")).andReturn("/bin/ping");
        expect(this.mockConfig.getProperty("Ping_Test_Args", args)).andReturn("-c 1 -q");
        expect(this.mockConfig.getProperty("Ping_Test_Host_1")).andReturn("127.0.0.1");
        expect(this.mockConfig.getProperty("Ping_Test_Host_2")).andReturn("138.25.49.129");
        expect(this.mockConfig.getProperty("Ping_Test_Host_3")).andReturn(null);
        expect(this.mockConfig.getProperty("Ping_Test_Interval", "30")).andReturn("60");
        expect(this.mockConfig.getProperty("Ping_Test_Fail_Threshold", "3")).andReturn("5");
        replay(this.mockConfig);
        
        meth = PingTestAction.class.getDeclaredMethod("setUp");
        meth.setAccessible(true);
        meth.invoke(this.pinger);
        
        Field f = PingTestAction.class.getDeclaredField("hosts");
        f.setAccessible(true);
        Map<String, Integer> hosts = (Map<String, Integer>)f.get(this.pinger);
        assertNotNull(hosts);
        assertTrue(hosts.containsKey("127.0.0.1"));
        assertTrue(hosts.containsKey("138.25.49.129"));
        
        f = PingTestAction.class.getDeclaredField("pingBuilder");
        f.setAccessible(true);
        ProcessBuilder bldr = (ProcessBuilder)f.get(this.pinger);
        assertNotNull(bldr);
        List<String> comm = bldr.command();
        assertEquals("/bin/ping", comm.get(0));
        assertEquals("-c", comm.get(1));
        assertEquals("1", comm.get(2));
        assertEquals("-q", comm.get(3));
        
        f = PingTestAction.class.getDeclaredField("failThreshold");
        f.setAccessible(true);
        assertEquals(5, f.getInt(this.pinger));
        
        f = AbstractTestAction.class.getDeclaredField("runInterval");
        f.setAccessible(true);
        assertEquals(60, f.getInt(this.pinger));
        
        assertTrue(this.pinger.getStatus());
        assertEquals(null, this.pinger.getReason());
        
        verify(this.mockConfig);   
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testRun() throws Exception
    {
        Thread thr = new Thread(this.pinger);
        
        Method meth = PingTestAction.class.getDeclaredMethod("getDefaultPingArgs");
        meth.setAccessible(true);
        String args = (String)meth.invoke(this.pinger);
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Ping_Test_Command", "ping")).andReturn("/bin/ping");
        expect(this.mockConfig.getProperty("Ping_Test_Args", args)).andReturn("-c 1 -q");
        expect(this.mockConfig.getProperty("Ping_Test_Host_1")).andReturn("127.0.0.1");
        expect(this.mockConfig.getProperty("Ping_Test_Host_2")).andReturn(null);
        expect(this.mockConfig.getProperty("Ping_Test_Interval", "30")).andReturn("3");
        expect(this.mockConfig.getProperty("Ping_Test_Fail_Threshold", "3")).andReturn("5");
        replay(this.mockConfig);
        
        /* This should succeed pinging local host. */
        thr.start();
        this.pinger.startTest();
        
        /* Wait for approx 10 iterations. */
        Thread.sleep(30000);
        
        this.pinger.stopTest();
        thr.interrupt();
        
        assertTrue(this.pinger.getStatus());
        assertNull(this.pinger.getReason());
        assertNull(this.pinger.getFailureReason());
        
        Field f = PingTestAction.class.getDeclaredField("hosts");
        f.setAccessible(true);
        Map<String, Integer> hosts = (Map<String, Integer>)f.get(this.pinger);
        assertNotNull(hosts);
        assertTrue(hosts.containsKey("127.0.0.1"));
        assertEquals(Integer.valueOf(0), hosts.get("127.0.0.1"));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testRunFailPing() throws Exception
    {
        Thread thr = new Thread(this.pinger);
        
        Method meth = PingTestAction.class.getDeclaredMethod("getDefaultPingArgs");
        meth.setAccessible(true);
        String args = (String)meth.invoke(this.pinger);
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Ping_Test_Command", "ping")).andReturn("/bin/ping");
        expect(this.mockConfig.getProperty("Ping_Test_Args", args)).andReturn("-c 1 -q");
        expect(this.mockConfig.getProperty("Ping_Test_Host_1")).andReturn("127.0.0.1");
        expect(this.mockConfig.getProperty("Ping_Test_Host_2")).andReturn("some.unknown.host");
        expect(this.mockConfig.getProperty("Ping_Test_Host_3")).andReturn(null);
        expect(this.mockConfig.getProperty("Ping_Test_Interval", "30")).andReturn("5");
        expect(this.mockConfig.getProperty("Ping_Test_Fail_Threshold", "3")).andReturn("2");
        replay(this.mockConfig);
        
        /* This should succeed pinging local host. */
        thr.start();
        this.pinger.startTest();
        
        /* Wait for approx 10 iterations. */
        Thread.sleep(60000);
        
        this.pinger.stopTest();
        thr.interrupt();
        
        assertFalse(this.pinger.getStatus());
        assertNotNull(this.pinger.getReason());
        assertNull(this.pinger.getFailureReason());
        
        Field f = PingTestAction.class.getDeclaredField("hosts");
        f.setAccessible(true);
        Map<String, Integer> hosts = (Map<String, Integer>)f.get(this.pinger);
        assertNotNull(hosts);
        assertTrue(hosts.containsKey("127.0.0.1"));
        assertEquals(Integer.valueOf(0), hosts.get("127.0.0.1"));
        
        assertTrue(hosts.containsKey("some.unknown.host"));
        assertTrue(2 < hosts.get("some.unknown.host"));
    }
    
    @Test
    public void testGetActionType()
    {
        assertEquals("Ping test", this.pinger.getActionType());
    }

}
