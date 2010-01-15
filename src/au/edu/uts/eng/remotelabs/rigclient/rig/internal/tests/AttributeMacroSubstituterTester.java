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
 * @date 14th January 2010
 *
 * Changelog:
 * - 14/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.internal.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.internal.AttributeMacroSubstituter;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;

/**
 * Tests the {@link AttributeMacroSubstituter} class.
 */
public class AttributeMacroSubstituterTester extends TestCase
{
    /** Object of class under test. */
    private AttributeMacroSubstituter subs;
    
    /** Mock configuration. */
    private IConfig mockConfig;

    @Override
    @Before
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
        
        expect(this.mockConfig.getProperty("Rig_Client_IP_Address")).andReturn(null);
        expect(this.mockConfig.getProperty("Listening_Network_Interface")).andReturn(null);
        replay(this.mockConfig);
        
        ConfigFactory.getInstance();
        Field f = ConfigFactory.class.getDeclaredField("instance");
        f.setAccessible(true);
        f.set(null, this.mockConfig);
        
        this.subs = new AttributeMacroSubstituter();
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.AttributeMacroSubstituter#substitueMacros(java.lang.String)}.
     */
    @Test
    public void testSubstitueMacrosNoDetect() throws Exception
    {
        String str = "this_has_no_macros";
        String subStr = this.subs.substitueMacros(str);
        assertEquals(str, subStr);
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.AttributeMacroSubstituter#substitueMacros(java.lang.String)}.
     */
    @Test
    public void testSubstitueMacrosDetectIP() throws Exception
    {
        String ipStr = "http://__IP__//camera1";
        String str = this.subs.substitueMacros(ipStr);
        
        assertNotNull(str);
        assertTrue(str.startsWith("http://"));
        assertTrue(str.endsWith("camera1"));
        assertFalse(str.contains("__"));
        
        String[] tok = str.split("//");
        assertEquals(3, tok.length);
        assertEquals("http:", tok[0]);
        assertEquals("camera1", tok[2]);
        assertEquals(this.detectIpFromExec(null), tok[1]);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.AttributeMacroSubstituter#substitueMacros(java.lang.String)}.
     */
    @Test
    public void testSubstitueMacrosDetectHostName() throws Exception
    {
        String hsStr = "http://__HOSTNAME__//camera1";
        String str = this.subs.substitueMacros(hsStr);
        
        assertNotNull(str);
        assertTrue(str.startsWith("http://"));
        assertTrue(str.endsWith("camera1"));
        assertFalse(str.contains("__"));
        
        String[] tok = str.split("//");
        assertEquals(3, tok.length);
        assertEquals("http:", tok[0]);
        assertEquals("camera1", tok[2]);
        assertEquals(this.detectHostNameFromExec(null), tok[1]);
    }

    /**
     * Attempt to determine the host name address of the specified network device. If
     * the <code>null</code> is provided as the network interface, the first
     * device is used. This uses an exec call to determine the information
     * to allow comparison with java.net provided values.
     * 
     * @return return hostname address, or null if not found
     * @throws Exception error finding value
     */
    private String detectHostNameFromExec(String device) throws Exception
    {
        if (device != null && device.equals("lo"))
        {
            return "localhost";
        }
        String ip = this.detectIpFromExec(device);
        if (ip == null) return null;
        
        ProcessBuilder builder = new ProcessBuilder("nslookup", ip);
        Process proc = builder.start();
        if (proc.waitFor() == 0)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            String toks[];
            while ((line = reader.readLine()) != null)
            {
                toks = line.split(" ");
                if (System.getProperty("os.name").equals("Linux") && toks[0].trim().endsWith("name"))
                {
                    return toks[2].substring(0, toks[2].length() - 1);
                }
                else if (System.getProperty("os.name").startsWith("Windows") && toks[0].trim().equals("Name"))
                {
                    return toks[1];
                }
            }
        }
        
        
        return null;
    }
    
    /**
     * Attempt to determine the IP address of the specified network device. If
     * the <code>null</code> is provided as the network interface, the first
     * device is used. This uses an exec call to determine the information
     * to allow comparison with java.net provided values.
     * 
     * @return return IP address, or null if not found
     * @throws Exception error finding value
     */
    private String detectIpFromExec(String device) throws Exception
    {
        if (System.getProperty("os.name").equals("Linux"))
        {
            ProcessBuilder builder = new ProcessBuilder("ifconfig");
            if (device != null) builder.command().add(device);
            
            Process proc = builder.start();
            if (proc.waitFor() == 0)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    line = line.trim();
                    if (line.startsWith("inet addr:"))
                    {
                        String parts[] = line.split(" ");
                        parts = parts[1].split(":");
                        return parts[1];
                    }
                }
            }            
        }
        else if (System.getProperty("os.name").startsWith("Windows"))
        {
            // TODO Implement detecting IP for Windows ipconfig command
        }
        
        return null;
    }
}
