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
 * @date 5th September 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.util.tests;

import au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig;
import junit.framework.TestCase;

/**
 * Tests the {@link PropertiesIntersectionConfig} class.
 */
public class PropertiesIntersectionConfigTester extends TestCase
{
    private PropertiesIntersectionConfig config;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        System.setProperty("prop.file", "test/resources/PropertiesIntersection/test.properties");
        System.setProperty("prop.extension.dir", "test/resources/PropertiesIntersection/conf.d");
        this.config = new PropertiesIntersectionConfig();
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        // TODO  
    }
    

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#PropertiesIntersectionConfig()}.
     */
    public void testPropertiesIntersectionConfig()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#getProperty(java.lang.String)}.
     */
    public void testGetPropertyString()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#getProperty(java.lang.String, java.lang.String)}.
     */
    public void testGetPropertyStringString()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#getAllProperties()}.
     */
    public void testGetAllProperties()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#setProperty(java.lang.String, java.lang.String)}.
     */
    public void testSetProperty()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#removeProperty(java.lang.String)}.
     */
    public void testRemoveProperty()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#reload()}.
     */
    public void testReload()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#serialise()}.
     */
    public void testSerialise()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#dumpConfiguration()}.
     */
    public void testDumpConfiguration()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#getConfigurationInfomation()}.
     */
    public void testGetConfigurationInfomation()
    {
        fail("Not yet implemented");
    }

}
