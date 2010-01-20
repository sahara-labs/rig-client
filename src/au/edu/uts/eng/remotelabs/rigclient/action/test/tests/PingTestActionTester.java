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
import static org.easymock.EasyMock.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.action.test.PingTestAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Tests the {@link PingTestAction} class.
 */
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
        expect(this.mockConfig.getProperty("Ping_Test_Command", "ping")).andReturn("ping");
        expect(this.mockConfig.getProperty("Ping_Test_Args", args)).andReturn(args);
        
        replay(this.mockConfig);
        
        meth = PingTestAction.class.getDeclaredMethod("setUp");
        meth.setAccessible(true);
        meth.invoke(this.pinger);
        
        verify(this.mockConfig);
    }

}
