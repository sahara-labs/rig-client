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
 * @date 12th December 2009
 *
 * Changelog:
 * - 12/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.intf.types.tests;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.StAXUtils;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.intf.types.ErrorType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.NotifyResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.OperationResponseType;


/**
 * Tests the {@link OperationResponseType} class.
 */
public class OperationResponseTypeTester extends TestCase
{
    @Test
    public void testParse() throws Exception
    {
        String xmlStr = "<ns1:notifyResponse xmlns:ns1=\"http://remotelabs.eng.uts.edu.au/rigclient/protocol\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns1:OperationResponseType\">\n" + 
                "            <success>false</success>\n" + 
                "            <ns1:error>\n" + 
                "               <code>3</code>\n" + 
                "               <operation>Fail!</operation>\n" + 
                "               <reason>Invalid permission.</reason>\n" + 
                "            </ns1:error>" +
                "         </ns1:notifyResponse>";
        
        OperationResponseType resp = OperationResponseType.Factory.parse(
                StAXUtils.createXMLStreamReader(new ByteArrayInputStream(xmlStr.getBytes())));
        assertNotNull(resp);
        assertFalse(resp.getSuccess());
        
        ErrorType err = resp.getError();
        assertNotNull(err);
        assertEquals(3, err.getCode());
        assertEquals("Fail!", err.getOperation());
        assertEquals("Invalid permission.", err.getReason());
    }
    
    @Test
    public void testParseEmptyReason() throws Exception
    {
        String xmlStr = "<ns1:allocateResponse xmlns:ns1=\"http://remotelabs.eng.uts.edu.au/rigclient/protocol\">" +
        		"<success>true</success><ns1:error><code>0</code><operation>Allocate rig to user user.</operation>" +
        		"<reason></reason></ns1:error></ns1:allocateResponse>";
        
        OperationResponseType resp = OperationResponseType.Factory.parse(
                StAXUtils.createXMLStreamReader(new ByteArrayInputStream(xmlStr.getBytes())));
        assertNotNull(resp);
        assertTrue(resp.getSuccess());
        
        ErrorType err = resp.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertEquals("Allocate rig to user user.", err.getOperation());
    }
    
    @Test
    public void testParseWillFail() throws Exception
    {
        String xmlStr = "<ns1:allocateResponse xmlns:ns1=\"http://remotelabs.eng.uts.edu.au/rigclient/protocol\">\n" + 
        		"  <success>true</success>\n" + 
        		"  <ns1:error>\n" + 
        		"    <code>0</code>\n" + 
        		"    <operation>Allocate rig to user user.</operation>\n" + 
        		"    <reason/>\n" + 
        		"  </ns1:error>\n" + 
        		"</ns1:allocateResponse>\n" + 
        		"";
        
        OperationResponseType resp = OperationResponseType.Factory.parse(
                StAXUtils.createXMLStreamReader(new ByteArrayInputStream(xmlStr.getBytes())));
        assertNotNull(resp);
        assertTrue(resp.getSuccess());
        
        ErrorType err = resp.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertEquals("Allocate rig to user user.", err.getOperation());
    }
    
    
    @Test
    public void testParseWillCallback() throws Exception
    {
        String xmlStr = "<ns1:notifyResponse xmlns:ns1=\"http://remotelabs.eng.uts.edu.au/rigclient/protocol\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns1:OperationResponseType\">\n" + 
                "            <success>true</success>\n" + 
                "            <willCallback>true</willCallback>\n" +
                "            <ns1:error>\n" + 
                "               <code>3</code>\n" + 
                "               <operation>Fail!</operation>\n" + 
                "               <reason>Invalid permission.</reason>\n" + 
                "            </ns1:error>" +
                "         </ns1:notifyResponse>";
        
        OperationResponseType resp = OperationResponseType.Factory.parse(
                StAXUtils.createXMLStreamReader(new ByteArrayInputStream(xmlStr.getBytes())));
        assertNotNull(resp);
        assertTrue(resp.getSuccess());
        assertTrue(resp.getWillCallback());
        
        ErrorType err = resp.getError();
        assertNotNull(err);
        assertEquals(3, err.getCode());
        assertEquals("Fail!", err.getOperation());
        assertEquals("Invalid permission.", err.getReason());
    }
    
    @Test
    public void testSerialise() throws Exception
    {
        OperationResponseType op = new OperationResponseType();
        op.setSuccess(true);
        
        ErrorType err = new ErrorType();
        err.setCode(3);
        err.setOperation("Test.");
        err.setReason("Invalid permission.");
        err.setTrace("Trace.");
        op.setError(err);
        
        OMElement ele = op.getOMElement(NotifyResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        String eleStr = ele.toStringWithConsume();
        assertNotNull(eleStr);

        assertTrue(eleStr.contains("<code>3</code>"));
        assertTrue(eleStr.contains("<reason>Invalid permission.</reason>"));
        assertTrue(eleStr.contains("<operation>Test.</operation>"));
        assertTrue(eleStr.contains("<trace>Trace.</trace>"));
    }
}
