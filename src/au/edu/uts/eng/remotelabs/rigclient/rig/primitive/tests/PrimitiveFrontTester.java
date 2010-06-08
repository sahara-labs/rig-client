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
 * @date 30th November 2009
 *
 * Changelog:
 * - 30/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests;

import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveResponse;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session;
import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront;

/**
 * Tests the {@link PrimitiveFront} class.
 */
public class PrimitiveFrontTester extends TestCase
{
    /** Object of class under test. */
    private PrimitiveFront front;

    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception
    {
        this.front = new PrimitiveFront();
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequest()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
       request.setAction("test");
       request.addParameter("param1", "val1");
       request.addParameter("param2", "val2");
       request.addParameter("param3", "val3");
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertTrue(resp.wasSuccessful());
       assertEquals(0, resp.getErrorCode());
       assertNull(resp.getErrorReason());
       
       Map<String, String> res = resp.getResults();
       assertTrue(res.containsKey("param1"));
       assertEquals("val1", res.get("param1"));
       assertTrue(res.containsKey("param1"));
       assertEquals("val2", res.get("param2"));
       assertTrue(res.containsKey("param1"));
       assertEquals("val3", res.get("param3"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequestStatic()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
       request.setAction("testStatic");
       request.addParameter("param1", "val1");
       request.addParameter("param2", "val2");
       request.addParameter("param3", "val3");
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertTrue(resp.wasSuccessful());
       assertEquals(0, resp.getErrorCode());
       assertNull(resp.getErrorReason());
       
       Map<String, String> res = resp.getResults();
       assertTrue(res.containsKey("param1"));
       assertEquals("val1", res.get("param1"));
       assertTrue(res.containsKey("param1"));
       assertEquals("val2", res.get("param2"));
       assertTrue(res.containsKey("param1"));
       assertEquals("val3", res.get("param3"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequestException()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
       request.setAction("exception");
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertFalse(resp.wasSuccessful());
       assertEquals(-7, resp.getErrorCode());
       assertNotNull(resp.getErrorReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequestNotExistentController()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("FooController");
       request.setAction("test");
       request.addParameter("param1", "val1");
       request.addParameter("param2", "val2");
       request.addParameter("param3", "val3");
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertFalse(resp.wasSuccessful());
       assertEquals(-2, resp.getErrorCode());
       assertNotNull(resp.getErrorReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequestNotExistentAction()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
       request.setAction("foo");
       request.addParameter("param1", "val1");
       request.addParameter("param2", "val2");
       request.addParameter("param3", "val3");
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertFalse(resp.wasSuccessful());
       assertEquals(-3, resp.getErrorCode());
       assertNotNull(resp.getErrorReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequestWrongParamSigAction()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
       request.setAction("wrongParamSig");
       request.addParameter("param1", "val1");
       request.addParameter("param2", "val2");
       request.addParameter("param3", "val3");
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertFalse(resp.wasSuccessful());
       assertEquals(-3, resp.getErrorCode()); //Not found because invalid sig
       assertNotNull(resp.getErrorReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequestWrongResponseSigAction()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
       request.setAction("wrongSig");
       request.addParameter("param1", "val1");
       request.addParameter("param2", "val2");
       request.addParameter("param3", "val3");
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertFalse(resp.wasSuccessful());
       assertEquals(-6, resp.getErrorCode());
       assertNotNull(resp.getErrorReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequestAllowedMaster()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MasterOnlyAclController");
       request.setAction("test");
       request.setRequestor("mdiponio");
       request.setRole(Session.MASTER);
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertTrue(resp.wasSuccessful());
       assertEquals(0, resp.getErrorCode());
       assertNull(resp.getErrorReason());
       assertTrue(resp.getResults().containsKey("woot"));
       assertEquals(resp.getResults().get("woot"), "woot");
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequestSlaveActiveNotAllowed()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MasterOnlyAclController");
       request.setAction("test");
       request.setRequestor("mdiponio");
       request.setRole(Session.SLAVE_ACTIVE);
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertFalse(resp.wasSuccessful());
       assertEquals(-9, resp.getErrorCode());
       assertNotNull(resp.getErrorReason());
       assertFalse(resp.getResults().containsKey("woot"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#routeRequest(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testRouteRequestSlavePassiveNotAllowed()
    {
       PrimitiveRequest request = new PrimitiveRequest();
       request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MasterOnlyAclController");
       request.setAction("test");
       request.setRequestor("mdiponio");
       request.setRole(Session.SLAVE_PASSIVE);
       
       PrimitiveResponse resp = this.front.routeRequest(request);
       assertNotNull(resp);
       assertFalse(resp.wasSuccessful());
       assertEquals(-9, resp.getErrorCode());
       assertNotNull(resp.getErrorReason());
       assertFalse(resp.getResults().containsKey("woot"));
    }

    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveFront#expungeCache()}.
     */
    @Test
    public void testExpungeCache()
    {
        PrimitiveRequest request = new PrimitiveRequest();
        request.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
        request.setAction("callCount");

        PrimitiveResponse resp = this.front.routeRequest(request);
        assertNotNull(resp);
        assertTrue(resp.wasSuccessful());
        assertEquals(0, resp.getErrorCode());
        assertNull(resp.getErrorReason());
        assertEquals(1, Integer.parseInt(resp.getResult("count")));
        
        resp = this.front.routeRequest(request);
        assertNotNull(resp);
        assertTrue(resp.wasSuccessful());
        assertEquals(0, resp.getErrorCode());
        assertNull(resp.getErrorReason());
        assertEquals(2, Integer.parseInt(resp.getResult("count")));
        
        resp = this.front.routeRequest(request);
        assertNotNull(resp);
        assertTrue(resp.wasSuccessful());
        assertEquals(0, resp.getErrorCode());
        assertNull(resp.getErrorReason());
        assertEquals(3, Integer.parseInt(resp.getResult("count")));
        
        this.front.expungeCache();
        
        resp = this.front.routeRequest(request);
        assertNotNull(resp);
        assertTrue(resp.wasSuccessful());
        assertEquals(0, resp.getErrorCode());
        assertNull(resp.getErrorReason());
        assertEquals(1, Integer.parseInt(resp.getResult("count")));
    }

}
