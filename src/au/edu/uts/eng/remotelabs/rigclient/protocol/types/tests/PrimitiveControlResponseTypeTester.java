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
 * @date 16th January 2010
 *
 * Changelog:
 * - 16/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.protocol.types.tests;


import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.StAXUtils;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PrimitiveControlResponseType;

/**
 * Tests the {@link PrimitiveControlResponseType} class.
 */
public class PrimitiveControlResponseTypeTester extends TestCase
{
    @Test
    public void testParse() throws Exception
    {
        String xml = "<ns1:performPrimitiveControlResponse xmlns:ns1=\"http://remotelabs.eng.uts.edu.au/rigclient/protocol\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns1:PrimitiveControlResponseType\">\n" + 
        		"  <success>true</success>\n" + 
        		"  <error>\n" + 
        		"    <code>10</code>\n" + 
        		"    <operation>primitive</operation>\n" + 
        		"    <reason>not supported</reason>\n" + 
        		"  </error>\n" + 
        		"  <wasSuccessful>Successful</wasSuccessful>\n" + 
        		"  <result>\n" + 
        		"    <name>foo</name>\n" + 
        		"    <value>bar</value>\n" + 
        		"  </result>\n" + 
        		"</ns1:performPrimitiveControlResponse>";
        
        PrimitiveControlResponseType prim = PrimitiveControlResponseType.Factory.parse(
                StAXUtils.createXMLStreamReader(new ByteArrayInputStream(xml.getBytes())));
        
        assertNotNull(prim);
        assertTrue(prim.getSuccess());
        assertEquals("Successful", prim.getWasSuccessful());
        
        ErrorType err = prim.getError();
        assertEquals(10, err.getCode());
        assertEquals("primitive", err.getOperation());
        assertEquals("not supported", err.getReason());
        
        ParamType[] res = prim.getResult();
        assertNotNull(res);
        assertEquals(1, res.length);
        assertEquals("foo", res[0].getName());
        assertEquals("bar", res[0].getValue());
    }
    
    @Test
    public void testSerialise() throws Exception
    {
        PrimitiveControlResponseType prim = new PrimitiveControlResponseType();
        prim.setSuccess(true);
        prim.setWasSuccessful("Successful");
        ParamType param = new ParamType();
        param.setName("foo");
        param.setValue("bar");
        prim.setResult(new ParamType[]{param});
        ErrorType err = new ErrorType();
        err.setCode(10);
        err.setOperation("primitive");
        err.setReason("not supported");
        prim.setError(err);
        
        OMElement ele = prim.getOMElement(PerformPrimitiveControlResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        String xml = ele.toStringWithConsume();
        
        assertNotNull(xml);
        assertTrue(xml.contains("performPrimitiveControlResponse"));
        assertTrue(xml.contains("<success>true</success>"));
        assertTrue(xml.contains("<wasSuccessful>Successful</wasSuccessful>"));
        assertTrue(xml.contains("<name>foo</name>"));
        assertTrue(xml.contains("<value>bar</value>"));
        
        String parts[] = xml.split("error");
        assertEquals(3, parts.length); // There should only be one error.
        assertTrue(parts[0].contains("<success>true</success>")); // Preceding error
        assertTrue(parts[1].contains("<operation>primitive</operation>")); // Part of error
        assertTrue(parts[2].contains("<wasSuccessful>Successful</wasSuccessful>")); // After error
    }
}
