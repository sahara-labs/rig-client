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
 * - 09/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.protocol.types.tests;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.StAXUtils;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AttributeResponseType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AttributeResponseTypeChoice;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse;


/**
 * Tests the {@link AttributeResponseType} class.
 */
public class AttributeResponseTypeTester extends TestCase
{
    @Test
    public void testParse() throws Exception
    {
        String str = "<ns1:getAttributeResponse xmlns:ns1=\"http://remotelabs.eng.uts.edu.au/rigclient/protocol\">\n" + 
        		"      <attribute>Rig_Name</attribute>\n" + 
        		"      <value>fpga1</value>\n" + 
        		"    </ns1:getAttributeResponse>";
        
        AttributeResponseType obj = AttributeResponseType.Factory.parse(StAXUtils.createXMLStreamReader(new ByteArrayInputStream(str.getBytes())));
        assertNotNull(obj);
        assertEquals("Rig_Name", obj.getAttribute());
        assertEquals("fpga1", obj.getAttributeResponseTypeChoice().getValue());
    }
    
    @Test
    public void testSerialize() throws Exception
    {
        AttributeResponseType obj = new AttributeResponseType();
        obj.setAttribute("Rig_Name");
        AttributeResponseTypeChoice co = new AttributeResponseTypeChoice();
        co.setValue("fpga1");
        obj.setAttributeResponseTypeChoice(co);
        
        OMElement ele = obj.getOMElement(GetAttributeResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        String str = ele.toStringWithConsume();
        assertTrue(str.contains("<attribute>Rig_Name</attribute>"));
        assertTrue(str.contains("<value>fpga1</value>"));
    }
}
