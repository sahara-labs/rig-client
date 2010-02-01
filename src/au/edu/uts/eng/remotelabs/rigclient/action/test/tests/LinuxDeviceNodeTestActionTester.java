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
 * @date 12th December 2010
 *
 * Changelog:
 * - 30/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.action.test.AbstractTestAction;
import au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction;
import au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction.DeviceNode;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Tests the {@link LinuxDeviceNodeTestAction} class.
 */
public class LinuxDeviceNodeTestActionTester extends TestCase
{
    /** Object of class under test. */
    private LinuxDeviceNodeTestAction test;
    
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
        this.test = new LinuxDeviceNodeTestAction();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetUp() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn("1800");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn("10");
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn("rw-rw-rw-");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn("666");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn("mdiponio");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn("1001");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn("10");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn("1");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn("3");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn("mem");
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn("/dev/random");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_2")).andReturn("b");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_2")).andReturn("rwxrwxrwx");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_2")).andReturn("777");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_2")).andReturn("foo");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_2")).andReturn("11");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_2")).andReturn("bar");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_2")).andReturn("12");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_2")).andReturn("2");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_2")).andReturn("4");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_2")).andReturn("rand");
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_3")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(2, nodes.size());
        
        fi = AbstractTestAction.class.getDeclaredField("runInterval");
        fi.setAccessible(true);
        assertEquals(1800, fi.getInt(this.test));
        
        fi = LinuxDeviceNodeTestAction.class.getDeclaredField("failThreshold");
        fi.setAccessible(true);
        assertEquals(10, fi.getInt(this.test));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testNodeSetup() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn("rw-rw-rw-");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn("666");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn("mdiponio");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn("1001");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn("10");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn("1");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn("3");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn("mem");
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn("/dev/random");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_2")).andReturn("b");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_2")).andReturn("rwxrwxrwx");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_2")).andReturn("777");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_2")).andReturn("foo");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_2")).andReturn("11");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_2")).andReturn("bar");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_2")).andReturn("12");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_2")).andReturn("2");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_2")).andReturn("4");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_2")).andReturn("rand");
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_3")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(2, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertEquals("/dev/null", node.getPath());
        
        fi = DeviceNode.class.getDeclaredField("fileType");
        fi.setAccessible(true);
        assertEquals('c', fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("octalPermissions");
        fi.setAccessible(true);
        assertEquals(666, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("permissionStr");
        fi.setAccessible(true);
        assertEquals("rw-rw-rw-", fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("owner");
        fi.setAccessible(true);
        assertEquals("mdiponio", fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("uid");
        fi.setAccessible(true);
        assertEquals(1001, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("group");
        fi.setAccessible(true);
        assertEquals("root", fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("gid");
        fi.setAccessible(true);
        assertEquals(10, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("majorNumber");
        fi.setAccessible(true);
        assertEquals(1, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("minorNumber");
        fi.setAccessible(true);
        assertEquals(3, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("driverName");
        fi.setAccessible(true);
        assertEquals("mem", fi.get(node));
        
        node = nodes.get(1);
        assertEquals("/dev/random", node.getPath());
        
        fi = DeviceNode.class.getDeclaredField("fileType");
        fi.setAccessible(true);
        assertEquals('b', fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("octalPermissions");
        fi.setAccessible(true);
        assertEquals(777, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("permissionStr");
        fi.setAccessible(true);
        assertEquals("rwxrwxrwx", fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("owner");
        fi.setAccessible(true);
        assertEquals("foo", fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("uid");
        fi.setAccessible(true);
        assertEquals(11, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("group");
        fi.setAccessible(true);
        assertEquals("bar", fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("gid");
        fi.setAccessible(true);
        assertEquals(12, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("majorNumber");
        fi.setAccessible(true);
        assertEquals(2, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("minorNumber");
        fi.setAccessible(true);
        assertEquals(4, fi.get(node));
        
        fi = DeviceNode.class.getDeclaredField("driverName");
        fi.setAccessible(true);
        assertEquals("rand", fi.get(node));
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTest() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn("rw-rw-rw-");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn("666");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn("1");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn("3");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn("mem");
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFile() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileNotExist() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/does_not_exist");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/does_not_exist", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertEquals("Does not exist.", node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertEquals("Does not exist.", node.getReason());

    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileType() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileWrongType() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("b");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());

    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFilePermission() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn("rw-rw-rw-");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileWrongPermission() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn("rwxrwx---");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileOctalPermissions() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn("666");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileWrongOctalPermissions() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn("777");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileUser() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileWrongUser() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn("mdiponio");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());

    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileUid() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileWrongUid() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn("1001");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileGroup() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileWrongGroup() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn("mdiponio");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());

    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileGid() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileWrongGid() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn("100");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileMajor() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn("1");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileWrongMajor() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn("5");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileMinor() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn("3");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileWrongMinor() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn("5");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileIgnoreWrongMajor() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn("5");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestFileIgnoreWrongMinor() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn("5");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestDevice() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn("mem");
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestNoDevice() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn("does_not_exist");
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestWrongDeviceMajor() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn("tty");
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(1, node.getFails());
        assertNotNull(node.getReason());
        
        this.test.doTest();
        assertEquals(2, node.getFails());
        assertNotNull(node.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#doTest()}.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestIgnoreDevice() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn(null);
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn("tty");
        replay(this.mockConfig);
        
        this.test.setUp();
        
        Field fi = LinuxDeviceNodeTestAction.class.getDeclaredField("deviceNodes");
        fi.setAccessible(true);
        List<DeviceNode> nodes = (List<DeviceNode>) fi.get(this.test);
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        
        DeviceNode node = nodes.get(0);
        assertNotNull(node);
        assertEquals("/dev/null", node.getPath());
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
        
        this.test.doTest();
        assertEquals(0, node.getFails());
        assertNull(node.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#getReason()}.
     */
    @Test
    public void testGetReason()
    {
           reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn("5");
        
        /* This device node is correct!. */
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn("rw-rw-rw-");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn("666");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn("1");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn("3");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn("mem");
        
        /* This device node is incorrect -> Wrong device driver. */
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn("/dev/random");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_2")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_2")).andReturn("/dev/random");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_2")).andReturn("666");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_2")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_2")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_2")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_2")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_2")).andReturn("1");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_2")).andReturn("8");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_2")).andReturn("rand");
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_3")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        for (int i = 0; i < 5; i++)
        {
            this.test.doTest();
            assertNull(this.test.getReason());
        }
        
        this.test.doTest();
        assertNotNull(this.test.getReason());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#getStatus()}.
     */
    @Test
    public void testGetStatus()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Interval", "300")).andReturn(null);
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Fail_Threshold", "3")).andReturn("5");
        
        /* This device node is correct!. */
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_1")).andReturn("/dev/null");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_1")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_1")).andReturn("rw-rw-rw-");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_1")).andReturn("666");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_1")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_1")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_1")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_1")).andReturn("1");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_1")).andReturn("3");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_1")).andReturn("mem");
        
        /* This device node is incorrect -> Wrong device driver. */
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_2")).andReturn("/dev/random");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Type_2")).andReturn("c");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Permission_2")).andReturn("/dev/random");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Octal_Permission_2")).andReturn("666");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_User_2")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_UID_2")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Group_2")).andReturn("root");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_GID_2")).andReturn("0");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Major_Number_2")).andReturn("1");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Minor_Number_2")).andReturn("8");
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Driver_2")).andReturn("rand");
        
        expect(this.mockConfig.getProperty("LinuxDeviceNode_Test_Path_3")).andReturn(null);
        replay(this.mockConfig);
        
        this.test.setUp();
        
        for (int i = 0; i < 5; i++)
        {
            this.test.doTest();
            assertTrue(this.test.getStatus());
        }
        
        this.test.doTest();
        assertFalse(this.test.getStatus());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.action.test.LinuxDeviceNodeTestAction#getActionType()}.
     */
    @Test
    public void testGetActionType()
    {
        assertEquals("Linux device node test", this.test.getActionType());
    }

}


