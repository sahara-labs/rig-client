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
 * @author Tania Machet (tmachet)
 * @date 12th February 2010
 *
 * Changelog:
 * - 12/02/2010 - tmachet - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.access.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.action.access.ExecAccessAction;
import au.edu.uts.eng.remotelabs.rigclient.action.access.RemoteDesktopAccessAction;
import au.edu.uts.eng.remotelabs.rigclient.action.test.JPEGFrameCameraTestAction;
import au.edu.uts.eng.remotelabs.rigclient.action.test.JPEGFrameCameraTestAction.Camera;
import au.edu.uts.eng.remotelabs.rigclient.action.test.tests.JPEGFrameCameraTestActionTester;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;


/**
 * Test class for testing Remote Desktop Access action
 * 
 * @author tmachet
 *
 */
public class RemoteDesktopAccessActionTester extends TestCase
{
    /** Object of class under test. */
    private RemoteDesktopAccessAction action;
    
    /** Mock configuration class. */
    private IConfig mockConfig;
    
    
    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception
    {
        this.mockConfig = createMock(IConfig.class);
        expect(this.mockConfig.getProperty("Remote_Desktop_Windows_Domain")).andReturn(null);
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
        expect(this.mockConfig.getProperty("Remote_Desktop_Groupname","Remote Desktop Users")).andReturn("Remote Desktop Users");
        replay(this.mockConfig);
        
        Field configField = ConfigFactory.class.getDeclaredField("instance");
        configField.setAccessible(true);
        configField.set(null, this.mockConfig);
        
        LoggerFactory.getLoggerInstance(); 
        this.action = new RemoteDesktopAccessAction();
        
    }

    /**
     * @throws java.lang.Exception
     */
    @Override
    @After
    public void tearDown() throws Exception
    {
        // Do nothing yet
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.access.RemoteDesktopAccessAction#setupAccessAction()}.
     */
    @Test
    public void testSetupAccessAction() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Remote_Desktop_Groupname","Remote Desktop Users")).andReturn("Remote Desktop Users");
        replay(this.mockConfig);

        this.action.setupAccessAction();
        
        Field f = ExecAccessAction.class.getDeclaredField("command");
        f.setAccessible(true);
        String comm = f.get(this.action).toString();
        assertTrue(comm.contains("net"));
        assertTrue(comm.contains("localgroup"));
        assertTrue(comm.contains("Remote Desktop Users"));
        
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.access.RemoteDesktopAccessAction#verifyAccessAction()}.
     */
    @Test
    public void testVerifyAccessAction()
    {
       // fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.access.RemoteDesktopAccessAction#RemoteDesktopAccessAction()}.
     */
    @Test
    public void testRemoteDesktopAccessAction()
    {
      //  fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.access.RemoteDesktopAccessAction#assign(java.lang.String)}.
     */
    @SuppressWarnings("unused")
    @Test
    public void testAssign()
    {
        reset(this.mockConfig);
        replay(this.mockConfig);

        final String os = System.getProperty("os.name");

        if (os.startsWith("Windows"))
        {
            boolean result = this.action.assign("tmachet");
            
            assertTrue(result);
            
        }
        
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.access.RemoteDesktopAccessAction#revoke(java.lang.String)}.
     */
    @Test
    public void testRevoke()
    {
       // fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.access.RemoteDesktopAccessAction#getFailureReason()}.
     */
    @Test
    public void testGetFailureReason()
    {
       // fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.access.RemoteDesktopAccessAction#getActionType()}.
     */
    @Test
    public void testGetActionType()
    {
       String result = this.action.getActionType();
       assertEquals(result, "Windows Remote Desktop Access.");
       
    }

}
