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
 * - 07/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.server.tests;


import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;

import junit.framework.TestCase;

import org.apache.axiom.om.util.StAXUtils;
import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.StatusResponseType;
import au.edu.uts.eng.remotelabs.rigclient.server.EmbeddedJettyServer;
import au.edu.uts.eng.remotelabs.rigclient.server.IServer;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;

/**
 * Tests the EmbeddedJettyServer class.
 */
public class EmbeddedJettyServerTester extends TestCase
{
    /** Object of class under test. */
    private IServer server;
    
    /** Mock configuration. */
    private IConfig config;

    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception
    {
        this.server = new EmbeddedJettyServer();
        
        this.config = createMock(IConfig.class);
        Field fld = EmbeddedJettyServer.class.getDeclaredField("config");
        fld.setAccessible(true);
        fld.set(this.server, this.config);
    }
    
    @Test
    public void testStartStop() throws Exception
    {
        reset(this.config);
        expect(this.config.getProperty("Rig_Client_IP_Address"))
            .andReturn("");
        expect(this.config.getProperty("Listening_Network_Interface"))
            .andReturn("");
        expect(this.config.getProperty("Listening_Port"))
            .andReturn("7654");
        expect(this.config.getProperty("Concurrent_Requests"))
            .andReturn("20");  
        replay(this.config);
        
        assertTrue(this.server.startListening());
        
        Thread.sleep(5000); // Some grace period to start up the Jetty server.
        
        URL url = new URL(this.server.getAddress()[0] + "/getStatus?void=void"); // Should only be one address.
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        
        assertEquals(200, conn.getResponseCode());
        assertEquals("application/xml; charset=UTF-8", conn.getContentType());
        assertEquals("GET", conn.getRequestMethod());
        assertTrue(conn.getHeaderField("Server").startsWith("Jetty"));
        
        GetStatusResponse resp = GetStatusResponse.Factory.parse(StAXUtils.createXMLStreamReader(conn.getInputStream()));
        StatusResponseType stat = resp.getGetStatusResponse();
        assertFalse(stat.getIsInMaintenance());
        assertFalse(stat.getIsInSession());
        assertFalse(stat.getIsMonitorFailed());
        
        assertTrue(this.server.stopListening());
        Thread.sleep(5000);
        
        try
        {
            conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            fail("Server still running...");
        }
        catch (IOException ex)
        {
            /* Test succeeds because the server should be shutdown. */
            assertEquals("Connection refused", ex.getMessage());
        }
        
        verify(this.config);
        
    }
    
    @Test
    public void testGenerateAddress() throws Exception
    {
        reset(this.config);
        expect(this.config.getProperty("Rig_Client_IP_Address"))
            .andReturn("");
        expect(this.config.getProperty("Listening_Network_Interface"))
            .andReturn("");      
        replay(this.config);
        
        Method mtd = EmbeddedJettyServer.class.getDeclaredMethod("generateAddress", String.class, 
                int.class, String.class);
        mtd.setAccessible(true);
        Object o = mtd.invoke(this.server, "http://", 7070, "/foo/bar");
        assertTrue(o instanceof String);
        
        String str = (String)o;
        assertTrue(str.startsWith("http://"));
        assertTrue(str.contains("7070"));
        assertTrue(str.endsWith("/foo/bar"));
    }
    
    @Test
    public void testGenerateAddressConfiguredIP() throws Exception
    {
        reset(this.config);
        expect(this.config.getProperty("Rig_Client_IP_Address"))
            .andReturn("127.0.0.1");
        expect(this.config.getProperty("Listening_Network_Interface"))
            .andReturn("");
        replay(this.config);
        
        Method mtd = EmbeddedJettyServer.class.getDeclaredMethod("generateAddress", String.class, 
                int.class, String.class);
        mtd.setAccessible(true);
        Object o = mtd.invoke(this.server, "http", 7070, "/foo/bar");
        assertTrue(o instanceof String);
        
        String str = (String)o;
        assertEquals("http://127.0.0.1:7070/foo/bar", str);
    }
    
    @Test
    public void testGenerateAddressConfiguredNicName() throws Exception
    {
        reset(this.config);
        expect(this.config.getProperty("Rig_Client_IP_Address"))
            .andReturn("");
        
        Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
        NetworkInterface nic = nics.nextElement();
        expect(this.config.getProperty("Listening_Network_Interface"))
            .andReturn(nic.getName());
        replay(this.config);
        
        Method mtd = EmbeddedJettyServer.class.getDeclaredMethod("generateAddress", String.class, 
                int.class, String.class);
        mtd.setAccessible(true);
        Object o = mtd.invoke(this.server, "http", 7070, "/foo/bar");
        assertTrue(o instanceof String);
        
        Enumeration<InetAddress> addrs = nic.getInetAddresses();
        InetAddress addr = null;
        while (addrs.hasMoreElements() && !((addr = addrs.nextElement()) instanceof Inet4Address));
        if (addr == null)
        {
            fail("Unable to find an IPv4 address for the test host.");
        }
        
        String str = (String)o;
        assertEquals("http://" + addr.getCanonicalHostName() + ":7070/foo/bar", str);
    }
}
