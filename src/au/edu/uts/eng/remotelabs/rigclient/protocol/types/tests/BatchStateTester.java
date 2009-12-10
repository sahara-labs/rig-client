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
 * - 10/12/2009 - mdiponio - Initial file creation.
 */

package au.edu.uts.eng.remotelabs.rigclient.protocol.types.tests;

import java.io.ByteArrayInputStream;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.StAXUtils;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.protocol.types.BatchState;

/**
 * Tests the {@link BatchState} class.
 */
public class BatchStateTester extends TestCase
{
    @Test
    public void testParseClear() throws Exception
    {
       String str = "<state>CLEAR</state>";
       
       BatchState obj = BatchState.Factory.parse(
               StAXUtils.createXMLStreamReader(new ByteArrayInputStream(str.getBytes())));
       assertEquals("CLEAR", obj.getValue());
       assertEquals(BatchState.CLEAR, obj);
    }
    
    @Test
    public void testParseInProgres() throws Exception
    {
       String str = "<state>IN_PROGRESS</state>";
       
       BatchState obj = BatchState.Factory.parse(
               StAXUtils.createXMLStreamReader(new ByteArrayInputStream(str.getBytes())));
       assertEquals("IN_PROGRESS", obj.getValue());
       assertEquals(BatchState.IN_PROGRESS, obj);
    }
    
    @Test
    public void testParseComplete() throws Exception
    {
       String str = "<state>COMPLETE</state>";
       
       BatchState obj = BatchState.Factory.parse(
               StAXUtils.createXMLStreamReader(new ByteArrayInputStream(str.getBytes())));
       assertEquals("COMPLETE", obj.getValue());  
       assertEquals(BatchState.COMPLETE, obj);
    }
    
    @Test
    public void testParseFailed() throws Exception
    {
       String str = "<state>FAILED</state>";
       
       BatchState obj = BatchState.Factory.parse(
               StAXUtils.createXMLStreamReader(new ByteArrayInputStream(str.getBytes())));
       assertEquals("FAILED", obj.getValue());
       assertEquals(BatchState.FAILED, obj);
    }
    
    @Test
    public void testParseNotSupported() throws Exception
    {
       String str = "<state>NOT_SUPPORTED</state>";
       
       BatchState obj = BatchState.Factory.parse(
               StAXUtils.createXMLStreamReader(new ByteArrayInputStream(str.getBytes())));
       assertEquals("NOT_SUPPORTED", obj.getValue());
       assertEquals(BatchState.NOT_SUPPORTED, obj);
    }
    
    @Test
    public void testSerialize() throws Exception
    {
        BatchState obj = BatchState.CLEAR;
        
        OMElement ele = obj.getOMElement(new QName("", "batchState"), OMAbstractFactory.getOMFactory());
        String str = ele.toStringWithConsume();
        assertTrue(str.contains("CLEAR"));
    }
}
