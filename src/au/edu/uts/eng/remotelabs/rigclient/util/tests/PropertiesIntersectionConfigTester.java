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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;
import au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig;

/**
 * Tests the {@link PropertiesIntersectionConfig} class.
 */
public class PropertiesIntersectionConfigTester extends TestCase
{
    public static final String TEST_CANON = "test/resources/PropertiesIntersection/test.properties";
    public static final String TEST_EXT = "test/resources/PropertiesIntersection/conf.d";
    
    /** Object of class under test. */
    private PropertiesIntersectionConfig config;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        System.setProperty("prop.file", TEST_CANON);
        System.setProperty("prop.extension.dir", TEST_EXT);
        this.config = new PropertiesIntersectionConfig();
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        byte buf[] = new byte[1024];
        int len = 0;
        
        FileInputStream fis = new FileInputStream(TEST_CANON + ".example");
        FileOutputStream fos = new FileOutputStream(TEST_CANON);
        while ((len = fis.read(buf)) > 0)
        {
            fos.write(buf, 0, len);
        }
        fis.close();
        fos.close();
        
        for (int i = 1; i <= 5; i++)
        {
            fis = new FileInputStream(TEST_EXT + "/0" + i + "test.properties.example");
            fos = new FileOutputStream(TEST_EXT + "/0" + i + "test.properties");
            while ((len = fis.read(buf)) > 0)
            {
                fos.write(buf, 0, len);
            }
            fis.close();
            fos.close();
        }
        
        for (File f : new File(TEST_CANON.substring(0, TEST_CANON.lastIndexOf('/'))).listFiles())
        {
            if (f.getName().endsWith(".backup")) f.delete();
        }
        
        for (File f : new File(TEST_EXT).listFiles())
        {
            if (f.getName().endsWith(".backup")) f.delete();
        }
    }
    

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#PropertiesIntersectionConfig()}.
     */
    @SuppressWarnings("unchecked")
    public void testPropertiesIntersectionConfig() throws Exception
    {
        Field f = PropertiesIntersectionConfig.class.getDeclaredField("canonicalFile");
        f.setAccessible(true);
        assertEquals(TEST_CANON, f.get(this.config));
        
        f = PropertiesIntersectionConfig.class.getDeclaredField("extensionLocation");
        f.setAccessible(true);
        assertEquals(TEST_EXT, f.get(this.config));
        
        f = PropertiesIntersectionConfig.class.getDeclaredField("canonicalProps");
        f.setAccessible(true);
        Properties props = (Properties)f.get(this.config);
        assertNotNull(props);
        assertEquals(9, props.size());
        for (int i = 1; i < 10; i++)
        {
            assertEquals(props.get("Prop" + i), "Value" + i);
        }
        
        f = PropertiesIntersectionConfig.class.getDeclaredField("extensionProps");
        f.setAccessible(true);
        Map<String, Properties> ext = (Map<String, Properties>)f.get(this.config);
        assertEquals(5, ext.size());
        String keys[] = ext.keySet().toArray(new String[5]);
        for (int i = 0; i < 5; i++)
        {
            assertEquals(System.getProperty("user.dir") + '/' + TEST_EXT + "/0" + (i + 1) + "test.properties", keys[i]);
            Properties p = ext.get(keys[i]);
            assertNotNull(p);
            for (int j = 0; j < 10; j++)
            {
                assertEquals(p.getProperty("Prop" + (i + 1) + j), "Value" + (i + 1) + j);
            }
        }
        
        f = PropertiesIntersectionConfig.class.getDeclaredField("props");
        f.setAccessible(true);
        Map<String, String> intersect = (Map<String, String>)f.get(this.config);
        assertEquals(59, intersect.size());
        for (int i = 1; i < 60; i++)
        {
            assertEquals(intersect.get("Prop" + i), "Value" + i);
        }
        
        for (String s : intersect.values())
        {
            assertFalse("SHOULD_NOT_BE_LOADED".equals(s));
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#getProperty(java.lang.String)}.
     */
    @SuppressWarnings("unchecked")
    public void testGetPropertyString() throws Exception
    {
        Field f = PropertiesIntersectionConfig.class.getDeclaredField("props");
        f.setAccessible(true);
        Map<String, String> intersect = (Map<String, String>)f.get(this.config);
        assertEquals(59, intersect.size());
        for (int i = 1; i < 60; i++)
        {
            String v = this.config.getProperty("Prop" + i);
            assertEquals(v, "Value" + i);
            assertFalse(v.charAt(0) == ' ');
            assertFalse(v.charAt(v.length() - 1) == ' ');
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#getProperty(java.lang.String, java.lang.String)}.
     */
    public void testGetPropertyStringString()
    {
        assertEquals("Value1", this.config.getProperty("Prop1", "Does_Exist"));
        assertEquals("Does_Not_Exist", this.config.getProperty("Prop100", "Does_Not_Exist"));
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#getAllProperties()}.
     */
    public void testGetAllProperties()
    {
        Map<String, String> props = this.config.getAllProperties();
        assertEquals(59, props.size());
        for (int i = 1; i < 60; i++)
        {
            assertEquals("Value" + i, props.get("Prop" + i));
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#setProperty(java.lang.String, java.lang.String)}.
     */
    @SuppressWarnings("unchecked")
    public void testSetProperty() throws Exception
    {
        for (int i = 1; i < 60; i++)
        {
            assertEquals("Value" + i, this.config.getProperty("Prop" + i));
            this.config.setProperty("Prop" + i, "Value" + (60 - i));
            assertEquals("Value" + (60 - i), this.config.getProperty("Prop" + i));
        }
        
        /* Check the main properties store. */
        Field f = PropertiesIntersectionConfig.class.getDeclaredField("props");
        f.setAccessible(true);
        Map<String, String> intersect = (Map<String, String>)f.get(this.config);
        assertEquals(59, intersect.size());
        
        for (int i = 1; i < 60; i++)
        {
            assertEquals("Value" + (60 - i), intersect.get("Prop" + i));
        }
        
        /* Check the backing properties. */
        /* Canonical should have 1 to 9. */
        f = PropertiesIntersectionConfig.class.getDeclaredField("canonicalProps");
        f.setAccessible(true);
        Properties can = (Properties)f.get(this.config);
        assertEquals(9, can.size());
        for (int i = 1; i < 10; i++)
        {
            assertEquals("Value" + (60 - i), can.get("Prop" + i));
        }
        
        /* There should be 5 groups of 10. */
        f = PropertiesIntersectionConfig.class.getDeclaredField("extensionProps");
        f.setAccessible(true);
        Map<String, Properties> ext = (Map<String, Properties>)f.get(this.config);
        int k = 1;
        for (Properties p : ext.values())
        {
            for (int j = 0; j < 10; j++)
            {
                assertEquals("Value" + (60 - k * 10 - j), p.get("Prop" + k + j));
            }
            k++;
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#removeProperty(java.lang.String)}.
     */
    @SuppressWarnings("unchecked")
    public void testRemoveProperty() throws Exception
    {
        Field f = PropertiesIntersectionConfig.class.getDeclaredField("props");
        f.setAccessible(true);
        Map<String, String> intersect = (Map<String, String>)f.get(this.config);
        assertEquals(59, intersect.size());
        for (int i = 1; i < 60; i++)
        {
            assertEquals("Value" + i, this.config.getProperty("Prop" + i));
        }
        
        this.config.removeProperty("Prop1");
        this.config.removeProperty("Prop2");
        this.config.removeProperty("Prop3");
        this.config.removeProperty("Prop4");
        this.config.removeProperty("Prop5");
        this.config.removeProperty("Prop6");
        this.config.removeProperty("Prop7");
        this.config.removeProperty("Prop8");
        this.config.removeProperty("Prop9");
        
        for (int i = 1; i < 10; i++)
        {
            assertNull(this.config.getProperty("Prop" + i));
        }
        
        for (int i = 10; i < 60; i++)
        {
            assertEquals("Value" + i, this.config.getProperty("Prop" + i));
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#reload()}.
     */
    @SuppressWarnings("unchecked")
    public void testReload() throws Exception
    {
        /* Make configuration dirty. */
        this.config.setProperty("Prop60", "Value60");
        this.config.setProperty("Prop61", "Value61");
        this.config.setProperty("Prop62", "Value62");
        this.config.setProperty("Prop63", "Value63");
        this.config.setProperty("Prop64", "Value64");
        this.config.setProperty("Prop65", "Value65");
        this.config.setProperty("Prop66", "Value66");
        this.config.setProperty("Prop67", "Value67");
        this.config.setProperty("Prop68", "Value68");
        this.config.setProperty("Prop69", "Value69");
        
        Field f = PropertiesIntersectionConfig.class.getDeclaredField("props");
        f.setAccessible(true);
        Map<String, String> intersect = (Map<String, String>)f.get(this.config);
        assertEquals(69, intersect.size());
        for (int i = 1; i < 70; i++)
        {
            String v = this.config.getProperty("Prop" + i);
            assertEquals(v, "Value" + i);
            assertFalse(v.charAt(0) == ' ');
            assertFalse(v.charAt(v.length() - 1) == ' ');
        }
        
        this.config.reload();
        
        intersect = (Map<String, String>)f.get(this.config);
        assertEquals(59, intersect.size());
        for (int i = 1; i < 60; i++)
        {
            String v = this.config.getProperty("Prop" + i);
            assertEquals(v, "Value" + i);
            assertFalse(v.charAt(0) == ' ');
            assertFalse(v.charAt(v.length() - 1) == ' ');
        }
        
        for (int i = 60; i < 70; i++)
        {
            assertFalse(intersect.containsKey("Prop" + i));
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#serialise()}.
     */
    @SuppressWarnings("unchecked")
    public void testSerialise() throws Exception
    {
        /* Modify some properties. */
        for (int i = 1; i < 60; i++)
        {
            assertEquals("Value" + i, this.config.getProperty("Prop" + i));
            this.config.setProperty("Prop" + i, "Value" + (60 - i));
            assertEquals("Value" + (60 - i), this.config.getProperty("Prop" + i));
        }
        
        this.config.serialise();
        
        PropertiesIntersectionConfig cleanLoad = new PropertiesIntersectionConfig();
        Field f = PropertiesIntersectionConfig.class.getDeclaredField("props");
        f.setAccessible(true);
        Map<String, String> intersect = (Map<String, String>)f.get(cleanLoad);
        assertEquals(59, intersect.size());
        
        for (int i = 1; i < 60; i++)
        {
            assertEquals("Value" + (60 - i), intersect.get("Prop" + i));
        }
        
        /* Check the backing properties. */
        /* Canonical should have 1 to 9. */
        f = PropertiesIntersectionConfig.class.getDeclaredField("canonicalProps");
        f.setAccessible(true);
        Properties can = (Properties)f.get(cleanLoad);
        assertEquals(9, can.size());
        for (int i = 1; i < 10; i++)
        {
            assertEquals("Value" + (60 - i), can.get("Prop" + i));
        }
        
        /* There should be 5 groups of 10. */
        f = PropertiesIntersectionConfig.class.getDeclaredField("extensionProps");
        f.setAccessible(true);
        Map<String, Properties> ext = (Map<String, Properties>)f.get(cleanLoad);
        int k = 1;
        for (Properties p : ext.values())
        {
            for (int j = 0; j < 10; j++)
            {
                assertEquals("Value" + (60 - k * 10 - j), p.get("Prop" + k + j));
            }
            k++;
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#serialise()}.
     */
    public void testSerialiseNewProperty() throws Exception
    {
        /* Add some new properties. */
        this.config.setProperty("newprop1", "newval1");
        this.config.setProperty("newprop2", "newval2");
        this.config.setProperty("Commented_Out_Prop", "foobar");
        
        this.config.serialise();
        
        PropertiesIntersectionConfig cleanLoad = new PropertiesIntersectionConfig();
        
        /* Check the backing properties. */
        assertNotNull(cleanLoad.getProperty("Commented_Out_Prop"));
        assertEquals("foobar", cleanLoad.getProperty("Commented_Out_Prop"));
        assertNotNull(cleanLoad.getProperty("newprop1"));
        assertEquals("newval1", cleanLoad.getProperty("newprop1"));
        assertNotNull(cleanLoad.getProperty("newprop2"));
        assertEquals("newval2", cleanLoad.getProperty("newprop2"));
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#dumpConfiguration()}.
     */
    public void testDumpConfiguration()
    {
        String dump = this.config.dumpConfiguration();
        assertNotNull(dump);
        assertEquals(59, dump.split("\n").length);
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig#getConfigurationInfomation()}.
     */
    public void testGetConfigurationInfomation()
    {
        String confInfo = this.config.getConfigurationInfomation();
        assertNotNull(confInfo);
        assertTrue(confInfo.contains(TEST_CANON));
        assertTrue(confInfo.contains(TEST_EXT));
    }

}
