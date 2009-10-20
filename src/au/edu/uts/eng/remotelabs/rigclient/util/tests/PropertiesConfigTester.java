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
 * @date 16th October 2009
 *
 * Changelog:
 * - 16/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.util.tests;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig;
import junit.framework.TestCase;

/**
 * Tests the <code>PropertiesConfig</code> class.
 */
public class PropertiesConfigTester extends TestCase
{
    /** Location of test file. */
    public static final String TEST_FILE = "test/resources/test.properties";
    
    /** Object of class under test. */
    private PropertiesConfig config;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        this.config = new PropertiesConfig(PropertiesConfigTester.TEST_FILE);
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig#getProperty(java.lang.String)}.
     */
    @Test
    public void testGetProperty()
    {
        for (int i = 1; i <= 10; i++)
        {
            assertEquals("Value" + i, this.config.getProperty("Prop" + i));
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig#getAllProperties()}.
     */
    @Test
    public void testGetAllProperties()
    {
        Map<String, String> all = this.config.getAllProperties();
        
        assertEquals(10, all.size());
        
        for (int i = 1; i <= 10; i++)
        {
            assertTrue(all.containsKey("Prop" + i));
            assertEquals("Value" + i, all.get("Prop" + i));
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig#setProperty(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testSetProperty()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig#reload()}.
     */
    @Test
    public void testReload()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig#serialise()}.
     */
    @Test
    public void testSerialise()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig#getConfigurationInfomation()}.
     */
    @Test
    public void testGetConfigurationInfomation()
    {
        assertNotNull(this.config.getConfigurationInfomation());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig#dumpConfiguration()}.
     */
    @Test
    public void testDumpConfiguration()
    {
        String dump = this.config.dumpConfiguration();
        assertNotNull(dump);
        
        for (int i = 1; i <= 10; i++)
        {
            assertTrue(dump.indexOf("Prop" + i) >= 0);
            assertTrue(dump.indexOf("Value" + i) > 0);
        }
    }

}
