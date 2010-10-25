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
package au.edu.uts.eng.remotelabs.rigclient.intf.types.tests;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.StAXUtils;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.intf.types.AbortBatchControl;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AuthRequiredRequestType;

/**
 * Tests the {@link AbortBatchControl} class.
 */
public class AbortBatchControlTester extends TestCase
{
    @Test
    public void testParse() throws Exception
    {
        String xmlStr = "<ns1:abortBatchControl xmlns:ns1=\"http://remotelabs.eng.uts.edu.au/rigclient/protocol\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns1:AuthRequiredRequestType\">\n" + 
        		"            <identityToken>abc123</identityToken>\n" + 
        		"            <requestor>mdiponio</requestor>\n" + 
        		"         </ns1:abortBatchControl>";
        
        AbortBatchControl obj = AbortBatchControl.Factory.parse(StAXUtils.createXMLStreamReader(new ByteArrayInputStream(xmlStr.getBytes())));
        assertEquals("abc123", obj.getAbortBatchControl().getIdentityToken());
        assertEquals("mdiponio", obj.getAbortBatchControl().getRequestor());
    }
    
    @Test
    public void testSerialise() throws Exception
    {
        AbortBatchControl obj = new AbortBatchControl();
        AuthRequiredRequestType req = new AuthRequiredRequestType();
        req.setIdentityToken("123abc");
        req.setRequestor("tmachet");
        obj.setAbortBatchControl(req);
        
        OMElement ele = obj.getOMElement(AbortBatchControl.MY_QNAME, OMAbstractFactory.getOMFactory());
        String eleStr = ele.toStringWithConsume();
        assertNotNull(eleStr);

        assertTrue(eleStr.contains("abortBatchControl"));
        assertTrue(eleStr.contains("<identityToken>123abc</identityToken>"));
        assertTrue(eleStr.contains("<requestor>tmachet</requestor>"));
    }
}
