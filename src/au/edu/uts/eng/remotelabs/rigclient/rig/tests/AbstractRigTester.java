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
 * Changelog:
 * - 14/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.tests;


import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig.ActionType;

import au.edu.uts.eng.remotelabs.rigclient.rig.ITestAction;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;


/**
 * Tests the <code>AbstractRig</code> class (actually its the
 * <code>MockRig</code> class which is instantiated, since 
 * <code>AbstractRig</code> is abstract. 
 */
@SuppressWarnings("all")
public class AbstractRigTester
{
    /** Class under test. */
    private MockRig rig;
    
    /** Mock configuration class. */
    IConfig mockConfig;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        this.mockConfig = createMock(IConfig.class);
        expect(this.mockConfig.getProperty("Logger_Type"))
                .andStubReturn("SystemErr");
        expect(this.mockConfig.getProperty("Log_Level"))
                .andStubReturn("DEBUG");
        expect(this.mockConfig.getProperty("Action_Failure_Threshold"))
                .andStubReturn("3");
        replay(this.mockConfig);
        
        Field configField = ConfigFactory.class.getDeclaredField("instance");
        configField.setAccessible(true);
        configField.set(null, this.mockConfig);
        
        this.rig = new MockRig();
    }
    
    /** 
     * Tests the <code>AbstractRig.setMaintenance</code> method with
     * a session active. This should terminate the session and revoke
     * the user.
     */
    @Test
    public void testMaintenanceRunTests()
    {
        ITestAction test = createMock(ITestAction.class);
        expect(test.getActionType()).andReturn("MockTest");
        test.run();
        expectLastCall().once();
        replay();
        this.rig.register(test, ActionType.TEST);
        verify();
        
        reset();
        test.startTest();
        expectLastCall().once();
        replay();
        
        assertTrue(this.rig.setMaintenance(true, "Test reason", true));
        assertFalse(this.rig.isNotInMaintenance());
        assertEquals("Test reason", this.rig.getMaintenanceReason());
        
        assertTrue(this.rig.setMaintenance(false, null, true));
        
        assertTrue(this.rig.isNotInMaintenance());
        assertNull(this.rig.getMaintenanceReason());
        verify();
    }
    
    /** 
     * Tests the <code>AbstractRig.setMaintenance</code> method with
     * a session active. This should terminate the session and revoke
     * the user.
     */
    @Test
    public void testMaintenanceStopTests()
    {
        
    }
    
    /** 
     * Tests the <code>AbstractRig.setMaintenance</code> method with
     * a session active. This should terminate the session and revoke
     * the user.
     */
    @Test
    public void testMaintenanceInSession()
    {
        String name = "SessionUser";
        assertFalse(this.rig.isSessionActive());
        assertTrue(this.rig.assign(name));
        assertTrue(this.rig.isSessionActive());
        Session type = this.rig.isInSession(name);

        assertTrue(this.rig.isInSession(name) == Session.MASTER);
        assertTrue(this.rig.isNotInMaintenance());
        
        /* Not setting maintenance, so should no affect running session. */
        assertTrue(this.rig.setMaintenance(false, null, false));
        assertTrue(this.rig.isSessionActive());
        assertTrue(this.rig.isInSession(name) == Session.MASTER);
        assertTrue(this.rig.isNotInMaintenance());
        
        assertTrue(this.rig.setMaintenance(true, "Test run", false));
        assertFalse(this.rig.isNotInMaintenance());
        assertEquals("Test run", this.rig.getMaintenanceReason());
        assertFalse(this.rig.isSessionActive());
        assertTrue(this.rig.isInSession(name) == Session.NOT_IN);
    }
    
    /**
     * Tests the <code>AbstractRig.setMaintenance</code>,
     * <code>AbstractRig.isNotInMaintenance</code> and 
     * <code>AbstractRig.getMaintenance</code> methods.
     * The test flow is:
     *    - Set maintenance with a reason.
     *    - Check maintenance is set with the correct reason.
     *    - Clear maintenance
     *    - Check maintenance is clear and reason not set.
     */
    @Test
    public void testMaintenance()
    {
        assertTrue(this.rig.setMaintenance(true, "Test reason", false));
        assertFalse(this.rig.isNotInMaintenance());
        assertEquals("Test reason", this.rig.getMaintenanceReason());
        
        assertTrue(this.rig.setMaintenance(false, null, false));
        
        assertTrue(this.rig.isNotInMaintenance());
        assertNull(this.rig.getMaintenanceReason());
    }
    
    /**
     * Tests the <code>AbstractRig.getRigName()</code> method.
     */
    @Test
    public void testGetName()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Rig_Name"))
            .andReturn("test1");
        replay(this.mockConfig);
        
        assertEquals("test1", this.rig.getName());
        verify(this.mockConfig);
    }
    
    /**
     * Tests the <code>AbstractRig.getRigType()</code> method.
     */
    @Test
    public void testGetType()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Rig_Type"))
            .andReturn("TestType");
        replay(this.mockConfig);
        
        assertEquals("TestType", this.rig.getType());
        verify(this.mockConfig);
    }
    
    /**
     * Tests the <code>AbstractRig.getCapabilities()</code> method.
     */
    @Test
    public void testGetCapabilities()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Rig_Capabilites"))
            .andReturn("cap1,cap2,cap3");
        replay(this.mockConfig);
        
        String[] caps = this.rig.getCapabilities();
        assertNotNull(caps);
        assertEquals(3, caps.length);
        
        /* Order is not important. */
        assertTrue(this.inArray(caps, "cap1"));
        assertTrue(this.inArray(caps, "cap2"));
        assertTrue(this.inArray(caps, "cap3"));
        
        verify(this.mockConfig);
    }
    
    /**
     * Tests the <code>AbstractRig.getCapabilities()</code> method with
     * white space in the configuration string.
     */
    @Test
    public void testGetCapabilitiesWithWhitespace()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Rig_Capabilites"))
            .andReturn("  cap1 ,  cap2  ,  cap3  ");
        replay(this.mockConfig);
        
        String[] caps = this.rig.getCapabilities();
        assertNotNull(caps);
        assertEquals(3, caps.length);
        
        /* Order is not important. */
        assertTrue(this.inArray(caps, "cap1"));
        assertTrue(this.inArray(caps, "cap2"));
        assertTrue(this.inArray(caps, "cap3"));
        
        verify(this.mockConfig);
    }
    
    /**
     * Tests the <code>AbstractRig.getAllRigAttributes()</code> method.
     */
    @Test
    public void testGetAllRigAttributes()
    {
        // Setup
        Map<String, String> prop = new HashMap<String, String>();
        prop.put("Key1", "Val1");
        prop.put("Key2", "Val2");
        prop.put("Key3", "Val3");
        prop.put("Key4", "Val4");
        prop.put("Key5", "Val5");
        
        reset(this.mockConfig);
        expect(this.mockConfig.getAllProperties())
                .andReturn(prop);
        replay(this.mockConfig);
        
        /* Check all key value pairs are the same. */
        Map<String, String> attrib = this.rig.getAllRigAttributes();
        for (String k : attrib.keySet())
        {
            assertTrue(prop.containsKey(k));
            assertEquals(attrib.get(k), prop.get(k));
        }
        
        /* Remove the keys to ensure they are the only ones
         * present. */
        assertEquals("Val1", attrib.remove("Key1"));
        assertEquals("Val2", attrib.remove("Key2"));
        assertEquals("Val3", attrib.remove("Key3"));
        assertEquals("Val4", attrib.remove("Key4"));
        assertEquals("Val5", attrib.remove("Key5"));
        assertTrue(attrib.isEmpty());
        
        verify(this.mockConfig);
    }
    
    /**
     * Tests the <code>AbstractRig.getAllRigAttributes()</code> method.
     */
    @Test
    public void testGetAttribute()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("IP"))
            .andReturn("192.168.0.1");
        replay(this.mockConfig);
        
        assertEquals("192.168.0.1", this.rig.getRigAttribute("IP"));
        verify(this.mockConfig);
    }
    
    /**
     * Tests the <code>AbstractRig.getAllRigAttributes()</code> method
     * with a property that isn't found.
     */
    @Test
    public void testGetAttributeIsNull()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Not_Found"))
            .andReturn(null);
        replay(this.mockConfig);
        
        assertNull(this.rig.getRigAttribute("Not_Found"));
        verify(this.mockConfig);
    }
    
    /**
     * Returns true if the key is in the array.
     * 
     * @param arr array to search
     * @param val value to search for 
     * @return true if in array, false otherwise
     */
    private boolean inArray(String[] arr, String val)
    {
        for (String s : arr)
        {
            if (val.equals(s)) return true;
        }
        return false;
    }
}
