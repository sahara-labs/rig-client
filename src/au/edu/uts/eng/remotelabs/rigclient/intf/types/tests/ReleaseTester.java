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
 * @date 10th December 2009
 *
 * Changelog:
 * - 17/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.intf.types.tests;

import java.io.ByteArrayInputStream;

import javax.xml.stream.XMLStreamReader;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.StAXUtils;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.intf.types.Release;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.UserType;


/**
 * Tests the {@link Release} class.
 */
public class ReleaseTester extends TestCase
{
    @Test
    public void testParse() throws Exception
    {
        String xmlString = "<ns1:release xmlns:ns1=\"http://remotelabs.eng.uts.edu.au/rigclient/protocol\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns1:UserType\">\n" +
                "            <identityToken>abc123</identityToken>\n" + 
                "            <requestor>tmachet</requestor>\n" +
                "            <user>mdiponio</user>\n" + 
                "         </ns1:release>";
        XMLStreamReader reader = StAXUtils.createXMLStreamReader(new ByteArrayInputStream(xmlString.getBytes()));
        Release rel = Release.Factory.parse(reader);
        UserType user = rel.getRelease();
        assertEquals("mdiponio", user.getUser());
        assertEquals("tmachet", user.getRequestor());
        assertEquals("abc123", user.getIdentityToken());
    }
    
    @Test
    public void testSerialise() throws Exception
    {
        Release rel = new Release();
        UserType user = new UserType();
        user.setIdentityToken("abc123");
        user.setUser("mdiponio");
        rel.setRelease(user);
        
        OMElement ele = rel.getOMElement(Release.MY_QNAME, OMAbstractFactory.getOMFactory());
        assertNotNull(ele);
        String xml = ele.toStringWithConsume();
        assertNotNull(xml);
        assertFalse(xml.isEmpty());
        assertTrue(xml.contains("<identityToken>abc123</identityToken>"));
        assertTrue(xml.contains("<user>mdiponio</user>"));
    }
}
