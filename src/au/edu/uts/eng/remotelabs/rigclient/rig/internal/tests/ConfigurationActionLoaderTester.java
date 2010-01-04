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
 * @date <day> <month> 2009
 *
 * Changelog:
 * - 01/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.internal.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.IAction;
import au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig.ActionType;
import au.edu.uts.eng.remotelabs.rigclient.rig.internal.ConfigurationActionLoader;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;

/**
 * Tests the {@link ConfigurationActionLoader} class.
 */
public class ConfigurationActionLoaderTester extends TestCase
{
    /** Object of class under test. */
    private ConfigurationActionLoader loader;
    
    /** Mock configuration. */
    private IConfig mockConfig;

    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception
    {
        /* Replace the default configuration class. */
        this.mockConfig = createMock(IConfig.class);
        expect(this.mockConfig.getProperty("Logger_Type"))
            .andReturn("SystemErr");
        expect(this.mockConfig.getProperty("Log_Level"))
            .andReturn("DEBUG");
        expect(this.mockConfig.getProperty("Action_Package_Prefixes", ""))
            .andReturn("au.edu.uts.eng.remotelabs;au.edu.uts.eng.remotelabs.rigclient.rig.internal.tests;" +
            		"au.edu.uts.eng.remotelabs.rigclient.rig.internal");
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
        Field field = ConfigFactory.class.getDeclaredField("instance");        
        field.setAccessible(true);
        field.set(null, this.mockConfig);
        
        this.loader = new ConfigurationActionLoader();
    }
    

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.ConfigurationActionLoader#getConfiguredActions(au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig.ActionType)}.
     */
    @Test
    public void testGetConfiguredActionsAccess()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Access_Actions"))
                .andReturn("MockAccessActionOne;.tests.MockAccessActionTwo");
        replay(this.mockConfig);
        IAction[] actions = this.loader.getConfiguredActions(ActionType.ACCESS);
        assertEquals(2, actions.length);
        assertTrue(actions[0] instanceof MockAccessActionOne);
        assertTrue(actions[1] instanceof MockAccessActionTwo);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.ConfigurationActionLoader#getConfiguredActions(au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig.ActionType)}.
     */
    @Test
    public void testGetConfiguredActionsAccessNullConf()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Access_Actions"))
                .andReturn(null);
        replay(this.mockConfig);
        IAction[] actions = this.loader.getConfiguredActions(ActionType.ACCESS);
        assertEquals(0, actions.length);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.ConfigurationActionLoader#getConfiguredActions(au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig.ActionType)}.
     */
    @Test
    public void testGetConfiguredActionsAccessEmptyConf()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Access_Actions"))
                .andReturn("");
        replay(this.mockConfig);
        IAction[] actions = this.loader.getConfiguredActions(ActionType.ACCESS);
        assertEquals(0, actions.length);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.ConfigurationActionLoader#getConfiguredActions(au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig.ActionType)}.
     */
    @Test
    public void testGetConfiguredActionsAccessWrongType()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Access_Actions"))
                .andReturn("NotAccessAction");
        replay(this.mockConfig);
        IAction[] actions = this.loader.getConfiguredActions(ActionType.ACCESS);
        assertEquals(0, actions.length);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.ConfigurationActionLoader#getConfiguredActions(au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig.ActionType)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testloadedPackPrefixes()
    {
        try
        {
            Field field = ConfigurationActionLoader.class.getDeclaredField("packagePrefixes");
            field.setAccessible(true);
            
            Object obj = field.get(this.loader);
            assertTrue(obj instanceof List);
            List<String> list = (List<String>)obj;
            assertTrue(list.contains("au.edu.uts.eng.remotelabs"));
            assertTrue(list.contains("au.edu.uts.eng.remotelabs.rigclient.rig.internal.tests"));
            assertTrue(list.contains("au.edu.uts.eng.remotelabs.rigclient.rig.internal"));
        }
        catch (Exception e)
        {
            fail("Field packagePrefixes not found.");
        }
    }
}
