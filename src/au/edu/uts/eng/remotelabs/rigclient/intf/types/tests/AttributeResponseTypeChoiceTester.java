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
 * @date 9th December 2009
 *
 * Changelog:
 * - 10/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.intf.types.tests;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.StAXUtils;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.intf.types.AttributeResponseTypeChoice;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.ErrorType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.GetAttributeResponse;


/**
 * Tests the {@link AttributeResponseTypeChoice} class.
 */
public class AttributeResponseTypeChoiceTester extends TestCase
{
    @Test
    public void testParseValue() throws Exception
    {
        String str = "<value>fpga1</value>";
        
        AttributeResponseTypeChoice obj = AttributeResponseTypeChoice.Factory.parse(StAXUtils.createXMLStreamReader(new ByteArrayInputStream(str.getBytes())));
        assertNotNull(obj);
        assertEquals("fpga1", obj.getValue());
    }
    
     @Test
    public void testParseError() throws Exception
    {
        String str = " <error>\n" + 
        		"        <code>9</code>\n" + 
        		"        <operation>Finding attribute Not_Found.</operation>\n" + 
        		"        <reason>Attribute Not_Found not found.</reason>\n" + 
        		"      </error>";
        
        AttributeResponseTypeChoice obj = AttributeResponseTypeChoice.Factory.parse(StAXUtils.createXMLStreamReader(new ByteArrayInputStream(str.getBytes())));
        assertNotNull(obj);
        
        ErrorType err = obj.getError();
        assertNotNull(err);
        assertEquals(9, err.getCode());
        assertEquals("Finding attribute Not_Found.", err.getOperation());
        assertEquals("Attribute Not_Found not found.", err.getReason());
    }
    
    @Test
    public void testSerializeValue() throws Exception
    {
        AttributeResponseTypeChoice obj = new AttributeResponseTypeChoice();
        obj.setValue("foo");
        
        OMElement ele = obj.getOMElement(GetAttributeResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        String str = ele.toStringWithConsume();
        assertTrue(str.contains("<value>foo</value>"));
    }
    
    @Test
    public void testSerializeError() throws Exception
    {
        AttributeResponseTypeChoice obj = new AttributeResponseTypeChoice();
        ErrorType err = new ErrorType();
        obj.setError(err);
        err.setCode(9);
        err.setOperation("Finding attribute Not_Found.");
        err.setReason("Attribute Not_Found not found.");
        
        OMElement ele = obj.getOMElement(GetAttributeResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        String str = ele.toStringWithConsume();
        assertTrue(str.contains("<code>9</code>"));
        assertTrue(str.contains("<operation>Finding attribute Not_Found.</operation>"));
        assertTrue(str.contains("<reason>Attribute Not_Found not found.</reason>"));
    }
}
