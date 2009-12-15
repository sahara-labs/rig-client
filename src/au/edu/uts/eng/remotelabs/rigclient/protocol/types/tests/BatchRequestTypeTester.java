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
import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.StAXUtils;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.protocol.types.BatchRequestType;


/**
 * Tests the {@link BatchRequestType} class.
 */
public class BatchRequestTypeTester extends TestCase
{
    @Test
    public void testParse() throws Exception
    {
        String str = "<batchRequest xmlns:ns1=\"http://remotelabs.eng.uts.edu.au/rigclient/protocol\" " +
        		"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ns1:BatchRequestType\">" +
        		"   <identityToken>abc123</identityToken>" +
        		"   <requestor>mdiponio</requestor>" +
        		"   <batchFile>SWdub3JhbmNlIGlzIFN0cmVuZ3RoCgpUaHJvdWdob3V0IHJlY29yZGVkIHRpbWUsIGFuZCBwcm9iYWJse" +
        		"SBzaW5jZSB0aGUgZW5kIG9mIHRoZSBOZW9saXRoaWMgQWdlLCB0aGVyZSBoYXZlIGJlZW4gdGhyZWUga2luZHMgb2YgcGVv" +
        		"cGxlIGluIHRoZSB3b3JsZCwgdGhlIEhpZ2gsIHRoZSBNaWRkbGUsIGFuZCB0aGUgTG93LiBUaGV5IGhhdmUgYmVlbiBzdWJ" +
        		"kaXZpZGVkIGluIG1hbnkgd2F5cywgdGhleSBoYXZlIGJvcm5lIGNvdW50bGVzcyBkaWZmZXJlbnQgbmFtZXMsIGFuZCB0aG" +
        		"VpciByZWxhdGl2ZSBudW1iZXJzLCBhcyB3ZWxsIGFzIHRoZWlyIGF0dGl0dWRlIHRvd2FyZHMgb25lIGFub3RoZXIsIGhhd" +
        		"mUgdmFyaWVkIGZyb20gYWdlIHRvIGFnZTogYnV0IHRoZSBlc3NlbnRpYWwgc3RydWN0dXJlIG9mIHNvY2lldHkgaGFzIG5l" +
        		"dmVyIGFsdGVyZWQuIEV2ZW4gYWZ0ZXIgZW5vcm1vdXMgdXBoZWF2YWxzIGFuZCBzZWVtaW5nbHkgaXJyZXZvY2FibGUgY2h" +
        		"hbmdlcywgdGhlIHNhbWUgcGF0dGVybiBoYXMgYWx3YXlzIHJlYXNzZXJ0ZWQgaXRzZWxmLCBqdXN0IGFzIGEgZ3lyb3Njb3" +
        		"BlIHdpbGwgYWx3YXlzIHJldHVybiB0byBlcXVpbGlicml1bSwgaG93ZXZlciBmYXIgaXQgaXMgcHVzaGVkIG9uZSB3YXkgb" +
        		"3IgdGhlIG90aGVyCgpUaGUgYWltcyBvZiB0aGVzZSB0aHJlZSBncm91cHMgYXJlIGVudGlyZWx5IGlycmVjb25jaWxhYmxl" +
        		"LiBUaGUgYWltIG9mIHRoZSBIaWdoIGlzIHRvIHJlbWFpbiB3aGVyZSB0aGV5IGFyZS4gVGhlIGFpbSBvZiB0aGUgTWlkZGx" +
        		"lIGlzIHRvIGNoYW5nZSBwbGFjZXMgd2l0aCB0aGUgSGlnaC4gVGhlIGFpbSBvZiB0aGUgTG93LCB3aGVuIHRoZXkgaGF2ZS" +
        		"BhbiBhaW0tLWZvciBpdCBpcyBhbiBhYmlkaW5nIGNoYXJhY3RlcmlzdGljIG9mIHRoZSBMb3cgdGhhdCB0aGV5IGFyZSB0b" +
        		"28gbXVjaCBjcnVzaGVkIGJ5IGRydWRnZXJ5IHRvIGJlIG1vcmUgdGhhbiBpbnRlcm1pdHRlbnRseSBjb25zY2lvdXMgb2Yg" +
        		"YW55dGhpbmcgb3V0c2lkZSB0aGVpciBkYWlseSBsaXZlcy0taXMgdG8gYWJvbGlzaCBhbGwgZGlzdGluY3Rpb25zIGFuZCB" +
        		"jcmVhdGUgYSBzb2NpZXR5IGluIHdoaWNoIGFsbCBtZW4gc2hhbGwgYmUgZXF1YWwuIFRodXMgdGhyb3VnaG91dCBoaXN0b3" +
        		"J5IGEgc3RydWdnbGUgd2hpY2ggaXMgdGhlIHNhbWUgaW4gaXRzIG1haW4gb3V0bGluZXMgcmVjdXJzIG92ZXIgYW5kIG92Z" +
        		"XIgYWdhaW4uIEZvciBsb25nIHBlcmlvZHMgdGhlIEhpZ2ggc2VlbSB0byBiZSBzZWN1cmVseSBpbiBwb3dlciwgYnV0IHNvb" +
        		"25lciBvciBsYXRlciB0aGVyZSBhbHdheXMgY29tZXMgYSBtb21lbnQgd2hlbiB0aGV5IGxvc2UgZWl0aGVyIHRoZWlyIGJlb" +
        		"GllZiBpbiB0aGVtc2VsdmVzIG9yIHRoZWlyIGNhcGFjaXR5IHRvIGdvdmVybiBlZmZpY2llbnRseSwgb3IgYm90aC4gVGhle" +
        		"SBhcmUgdGhlbiBvdmVydGhyb3duIGJ5IHRoZSBNaWRkbGUsIHdobyBlbmxpc3QgdGhlIExvdyBvbiB0aGVpciBzaWRlIGJ5I" +
        		"HByZXRlbmRpbmcgdG8gdGhlbSB0aGF0IHRoZXkgYXJlIGZpZ2h0aW5nIGZvciBsaWJlcnR5IGFuZCBqdXN0aWNlLiBBcyBzb" +
        		"29uIGFzIHRoZXkgaGF2ZSByZWFjaGVkIHRoZWlyIG9iamVjdGl2ZSwgdGhlIE1pZGRsZSB0aHJ1c3QgdGhlIExvdyBiYWNrI" +
        		"GludG8gdGhlaXIgb2xkIHBvc2l0aW9uIG9mIHNlcnZpdHVkZSwgYW5kIHRoZW1zZWx2ZXMgYmVjb21lIHRoZSBIaWdoLiBQc" +
        		"mVzZW50bHkgYSBuZXcgTWlkZGxlIGdyb3VwIHNwbGl0cyBvZmYgZnJvbSBvbmUgb2YgdGhlIG90aGVyIGdyb3Vwcywgb3IgZ" +
        		"nJvbSBib3RoIG9mIHRoZW0sIGFuZCB0aGUgc3RydWdnbGUgYmVnaW5zIG92ZXIgYWdhaW4uIE9mIHRoZSB0aHJlZSBncm91c" +
        		"HMsIG9ubHkgdGhlIExvdyBhcmUgbmV2ZXIgZXZlbiB0ZW1wb3JhcmlseSBzdWNjZXNzZnVsIGluIGFjaGlldmluZyB0aGVpc" +
        		"iBhaW1zLiBJdCB3b3VsZCBiZSBhbiBleGFnZ2VyYXRpb24gdG8gc2F5IHRoYXQgdGhyb3VnaG91dCBoaXN0b3J5IHRoZXJlI" +
        		"GhhcyBiZWVuIG5vIHByb2dyZXNzIG9mIGEgbWF0ZXJpYWwga2luZC4gRXZlbiB0b2RheSwgaW4gYSBwZXJpb2Qgb2YgZGVjb" +
        		"GluZSwgdGhlIGF2ZXJhZ2UgaHVtYW4gYmVpbmcgaXMgcGh5c2ljYWxseSBiZXR0ZXIgb2ZmIHRoYW4gaGUgd2FzIGEgZmV3I" +
        		"GNlbnR1cmllcyBhZ28uIEJ1dCBubyBhZHZhbmNlIGluIHdlYWx0aCwgbm8gc29mdGVuaW5nIG9mIG1hbm5lcnMsIG5vIHJlZ" +
        		"m9ybSBvciByZXZvbHV0aW9uIGhhcyBldmVyIGJyb3VnaHQgaHVtYW4gZXF1YWxpdHkgYSBtaWxsaW1ldHJlIG5lYXJlci4gR" +
        		"nJvbSB0aGUgcG9pbnQgb2YgdmlldyBvZiB0aGUgTG93LCBubyBoaXN0b3JpYyBjaGFuZ2UgaGFzIGV2ZXIgbWVhbnQgbXVja" +
        		"CBtb3JlIHRoYW4gYSBjaGFuZ2UgaW4gdGhlIG5hbWUgb2YgdGhlaXIgbWFzdGVycy4KCkJ5IHRoZSBsYXRlIG5pbmV0ZWVud" +
        		"GggY2VudHVyeSB0aGUgcmVjdXJyZW5jZSBvZiB0aGlzIHBhdHRlcm4gaGFkIGJlY29tZSBvYnZpb3VzIHRvIG1hbnkgb2JzZ" +
        		"XJ2ZXJzLiBUaGVyZSB0aGVuIHJvc2Ugc2Nob29scyBvZiB0aGlua2VycyB3aG8gaW50ZXJwcmV0ZWQgaGlzdG9yeSBhcyBhI" +
        		"GN5Y2xpY2FsIHByb2Nlc3MgYW5kIGNsYWltZWQgdG8gc2hvdyB0aGF0IGluZXF1YWxpdHkgd2FzIHRoZSB1bmFsdGVyYWJsZ" +
        		"SBsYXcgb2YgaHVtYW4gbGlmZS4gVGhpcyBkb2N0cmluZSwgb2YgY291cnNlLCBoYWQgYWx3YXlzIGhhZCBpdHMgYWRoZXJlb" +
        		"nRzLCBidXQgaW4gdGhlIG1hbm5lciBpbiB3aGljaCBpdCB3YXMgbm93IHB1dCBmb3J3YXJkIHRoZXJlIHdhcyBhIHNpZ25pZ" +
        		"mljYW50IGNoYW5nZS4gSW4gdGhlIHBhc3QgdGhlIG5lZWQgZm9yIGEgaGllcmFyY2hpY2FsIGZvcm0gb2Ygc29jaWV0eSBoY" +
        		"WQgYmVlbiB0aGUgZG9jdHJpbmUgc3BlY2lmaWNhbGx5IG9mIHRoZSBIaWdoLiBJdCBoYWQgYmVlbiBwcmVhY2hlZCBieSBra" +
        		"W5ncyBhbmQgYXJpc3RvY3JhdHMgYW5kIGJ5IHRoZSBwcmllc3RzLCBsYXd5ZXJzLCBhbmQgdGhlIGxpa2Ugd2hvIHdlcmUgc" +
        		"GFyYXNpdGljYWwgdXBvbiB0aGVtLCBhbmQgaXQgaGFkIGdlbmVyYWxseSBiZWVuIHNvZnRlbmVkIGJ5IHByb21pc2VzIG9mI" +
        		"GNvbXBlbnNhdGlvbiBpbiBhbiBpbWFnaW5hcnkgd29ybGQgYmV5b25kIHRoZSBncmF2ZS4gVGhlIE1pZGRsZSwgc28gbG9uZ" +
        		"yBhcyBpdCB3YXMgc3RydWdnbGluZyBmb3IgcG93ZXIsIGhhZCBhbHdheXMgbWFkZSB1c2Ugb2Ygc3VjaCB0ZXJtcyBhcyBmc" +
        		"mVlZG9tLCBqdXN0aWNlLCBhbmQgZnJhdGVybml0eS4gTm93LCBob3dldmVyLCB0aGUgY29uY2VwdCBvZiBodW1hbiBicm90a" +
        		"GVyaG9vZCBiZWdhbiB0byBiZSBhc3NhaWxlZCBieSBwZW9wbGUgd2hvIHdlcmUgbm90IHlldCBpbiBwb3NpdGlvbnMgb2YgY" +
        		"29tbWFuZCwgYnV0IG1lcmVseSBob3BlZCB0byBiZSBzbyBiZWZvcmUgbG9uZy4gSW4gdGhlIHBhc3QgdGhlIE1pZGRsZSBoY" +
        		"WQgbWFkZSByZXZvbHV0aW9ucyB1bmRlciB0aGUgYmFubmVyIG9mIGVxdWFsaXR5LCBhbmQgdGhlbiBoYWQgZXN0YWJsaXNoZ" +
        		"WQgYSBmcmVzaCB0eXJhbm55IGFzIHNvb24gYXMgdGhlIG9sZCBvbmUgd2FzIG92ZXJ0aHJvd24uIFRoZSBuZXcgTWlkZGxlI" +
        		"Gdyb3VwcyBpbiBlZmZlY3QgcHJvY2xhaW1lZCB0aGVpciB0eXJhbm55IGJlZm9yZWhhbmQuIFNvY2lhbGlzbSwgYSB0aGVvc" +
        		"nkgd2hpY2ggYXBwZWFyZWQgaW4gdGhlIGVhcmx5IG5pbmV0ZWVudGggY2VudHVyeSBhbmQgd2FzIHRoZSBsYXN0IGxpbmsga" +
        		"W4gYSBjaGFpbiBvZiB0aG91Z2h0IHN0cmV0Y2hpbmcgYmFjayB0byB0aGUgc2xhdmUgcmViZWxsaW9ucyBvZiBhbnRpcXVpd" +
        		"HksIHdhcyBzdGlsbCBkZWVwbHkgaW5mZWN0ZWQgYnkgdGhlIFV0b3BpYW5pc20gb2YgcGFzdCBhZ2VzLiBCdXQgaW4gZWFja" +
        		"CB2YXJpYW50IG9mIFNvY2lhbGlzbSB0aGF0IGFwcGVhcmVkIGZyb20gYWJvdXQgMTkwMCBvbndhcmRzIHRoZSBhaW0gb2YgZ" +
        		"XN0YWJsaXNoaW5nIGxpYmVydHkgYW5kIGVxdWFsaXR5IHdhcyBtb3JlIGFuZCBtb3JlIG9wZW5seSBhYmFuZG9uZWQuIFRoZ" +
        		"SBuZXcgbW92ZW1lbnRzIHdoaWNoIGFwcGVhcmVkIGluIHRoZSBtaWRkbGUgeWVhcnMgb2YgdGhlIGNlbnR1cnksIEluZ3NvY" +
        		"yBpbiBPY2VhbmlhLCBOZW8tQm9sc2hldmlzbSBpbiBFdXJhc2lhLCBEZWF0aC1Xb3JzaGlwLCBhcyBpdCBpcyBjb21tb25se" +
        		"SBjYWxsZWQsIGluIEVhc3Rhc2lhLCBoYWQgdGhlIGNvbnNjaW91cyBhaW0gb2YgcGVycGV0dWF0aW5nIFVOZnJlZWRvbSBh" +
        		"bmQgSU5lcXVhbGl0eS4gVGhlc2UgbmV3IG1vdmVtZW50cywgb2YgY291cnNlLCBncmV3IG91dCBvZiB0aGUgb2xkIG9uZXM" +
        		"gYW5kIHRlbmRlZCB0byBrZWVwIHRoZWlyIG5hbWVzIGFuZCBwYXkgbGlwLXNlcnZpY2UgdG8gdGhlaXIgaWRlb2xvZ3kuIEJ" +
        		"1dCB0aGUgcHVycG9zZSBvZiBhbGwgb2YgdGhlbSB3YXMgdG8gYXJyZXN0IHByb2dyZXNzIGFuZCBmcmVlemUgaGlzdG9yeSB" +
        		"hdCBhIGNob3NlbiBtb21lbnQuIFRoZSBmYW1pbGlhciBwZW5kdWx1bSBzd2luZyB3YXMgdG8gaGFwcGVuIG9uY2UgbW9yZSw" +
        		"gYW5kIHRoZW4gc3RvcC4gQXMgdXN1YWwsIHRoZSBIaWdoIHdlcmUgdG8gYmUgdHVybmVkIG91dCBieSB0aGUgTWlkZGxlLCB" +
        		"3aG8gd291bGQgdGhlbiBiZWNvbWUgdGhlIEhpZ2g7IGJ1dCB0aGlzIHRpbWUsIGJ5IGNvbnNjaW91cyBzdHJhdGVneSwgdGh" +
        		"lIEhpZ2ggd291bGQgYmUgYWJsZSB0byBtYWludGFpbiB0aGVpciBwb3NpdGlvbiBwZXJtYW5lbnRseS4KClRoZSBuZXcgZG9j" +
        		"dHJpbmVzIGFyb3NlIHBhcnRseSBiZWNhdXNlIG9mIHRoZSBhY2N1bXVsYXRpb24gb2YgaGlzdG9yaWNhbCBrbm93bGVkZ2Us" +
        		"IGFuZCB0aGUgZ3Jvd3RoIG9mIHRoZSBoaXN0b3JpY2FsIHNlbnNlLCB3aGljaCBoYWQgaGFyZGx5IGV4aXN0ZWQgYmVmb3J" +
        		"lIHRoZSBuaW5ldGVlbnRoIGNlbnR1cnkuIFRoZSBjeWNsaWNhbCBtb3ZlbWVudCBvZiBoaXN0b3J5IHdhcyBub3cgaW50ZW" +
        		"xsaWdpYmxlLCBvciBhcHBlYXJlZCB0byBiZSBzbzsgYW5kIGlmIGl0IHdhcyBpbnRlbGxpZ2libGUsIHRoZW4gaXQgd2FzI" +
        		"GFsdGVyYWJsZS4gQnV0IHRoZSBwcmluY2lwYWwsIHVuZGVybHlpbmcgY2F1c2Ugd2FzIHRoYXQsIGFzIGVhcmx5IGFzIHRo" +
        		"ZSBiZWdpbm5pbmcgb2YgdGhlIHR3ZW50aWV0aCBjZW50dXJ5LCBodW1hbiBlcXVhbGl0eSBoYWQgYmVjb21lIHRlY2huaWN" +
        		"hbGx5IHBvc3NpYmxlLiBJdCB3YXMgc3RpbGwgdHJ1ZSB0aGF0IG1lbiB3ZXJlIG5vdCBlcXVhbCBpbiB0aGVpciBuYXRpdm" +
        		"UgdGFsZW50cyBhbmQgdGhhdCBmdW5jdGlvbnMgaGFkIHRvIGJlIHNwZWNpYWxpemVkIGluIHdheXMgdGhhdCBmYXZvdXJlZ" +
        		"CBzb21lIGluZGl2aWR1YWxzIGFnYWluc3Qgb3RoZXJzOyBidXQgdGhlcmUgd2FzIG5vIGxvbmdlciBhbnkgcmVhbCBuZWVk" +
        		"IGZvciBjbGFzcyBkaXN0aW5jdGlvbnMgb3IgZm9yIGxhcmdlIGRpZmZlcmVuY2VzIG9mIHdlYWx0aC4gSW4gZWFybGllciB" +
        		"hZ2VzLCBjbGFzcyBkaXN0aW5jdGlvbnMgaGFkIGJlZW4gbm90IG9ubHkgaW5ldml0YWJsZSBidXQgZGVzaXJhYmxlLiBJbm" +
        		"VxdWFsaXR5IHdhcyB0aGUgcHJpY2Ugb2YgY2l2aWxpemF0aW9uLiBXaXRoIHRoZSBkZXZlbG9wbWVudCBvZiBtYWNoaW5lI" +
        		"HByb2R1Y3Rpb24sIGhvd2V2ZXIsIHRoZSBjYXNlIHdhcyBhbHRlcmVkLiBFdmVuIGlmIGl0IHdhcyBzdGlsbCBuZWNlc3Nh" +
        		"cnkgZm9yIGh1bWFuIGJlaW5ncyB0byBkbyBkaWZmZXJlbnQga2luZHMgb2Ygd29yaywgaXQgd2FzIG5vIGxvbmdlciBuZWN" +
        		"lc3NhcnkgZm9yIHRoZW0gdG8gbGl2ZSBhdCBkaWZmZXJlbnQgc29jaWFsIG9yIGVjb25vbWljIGxldmVscy4gVGhlcmVmb3J" +
        		"lLCBmcm9tIHRoZSBwb2ludCBvZiB2aWV3IG9mIHRoZSBuZXcgZ3JvdXBzIHdobyB3ZXJlIG9uIHRoZSBwb2ludCBvZiBzZWl" +
        		"6aW5nIHBvd2VyLCBodW1hbiBlcXVhbGl0eSB3YXMgbm8gbG9uZ2VyIGFuIGlkZWFsIHRvIGJlIHN0cml2ZW4gYWZ0ZXIsIGJ" +
        		"1dCBhIGRhbmdlciB0byBiZSBhdmVydGVkLiBJbiBtb3JlIHByaW1pdGl2ZSBhZ2VzLCB3aGVuIGEganVzdCBhbmQgcGVhY2V" +
        		"mdWwgc29jaWV0eSB3YXMgaW4gZmFjdCBub3QgcG9zc2libGUsIGl0IGhhZCBiZWVuIGZhaXJseSBlYXN5IHRvIGJlbGlldmU" +
        		"gaXQuIFRoZSBpZGVhIG9mIGFuIGVhcnRobHkgcGFyYWRpc2UgaW4gd2hpY2ggbWVuIHNob3VsZCBsaXZlIHRvZ2V0aGVyIGl" +
        		"uIGEgc3RhdGUgb2YgYnJvdGhlcmhvb2QsIHdpdGhvdXQgbGF3cyBhbmQgd2l0aG91dCBicnV0ZSBsYWJvdXIsIGhhZCBoYXVu" +
        		"dGVkIHRoZSBodW1hbiBpbWFnaW5hdGlvbiBmb3IgdGhvdXNhbmRzIG9mIHllYXJzLiBBbmQgdGhpcyB2aXNpb24gaGFkIGhhZ" +
        		"CBhIGNlcnRhaW4gaG9sZCBldmVuIG9uIHRoZSBncm91cHMgd2hvIGFjdHVhbGx5IHByb2ZpdGVkIGJ5IGVhY2ggaGlzdG9yaWN" +
        		"hbCBjaGFuZ2UuIFRoZSBoZWlycyBvZiB0aGUgRnJlbmNoLCBFbmdsaXNoLCBhbmQgQW1lcmljYW4gcmV2b2x1dGlvbnMgaGFkI" +
        		"HBhcnRseSBiZWxpZXZlZCBpbiB0aGVpciBvd24gcGhyYXNlcyBhYm91dCB0aGUgcmlnaHRzIG9mIG1hbiwgZnJlZWRvbSBvZiB" +
        		"zcGVlY2gsIGVxdWFsaXR5IGJlZm9yZSB0aGUgbGF3LCBhbmQgdGhlIGxpa2UsIGFuZCBoYXZlIGV2ZW4gYWxsb3dlZCB0aGV" +
        		"pciBjb25kdWN0IHRvIGJlIGluZmx1ZW5jZWQgYnkgdGhlbSB0byBzb21lIGV4dGVudC4gQnV0IGJ5IHRoZSBmb3VydGggZGV" +
        		"jYWRlIG9mIHRoZSB0d2VudGlldGggY2VudHVyeSBhbGwgdGhlIG1haW4gY3VycmVudHMgb2YgcG9saXRpY2FsIHRob3VnaHQ" +
        		"gd2VyZSBhdXRob3JpdGFyaWFuLiBUaGUgZWFydGhseSBwYXJhZGlzZSBoYWQgYmVlbiBkaXNjcmVkaXRlZCBhdCBleGFjdG" +
        		"x5IHRoZSBtb21lbnQgd2hlbiBpdCBiZWNhbWUgcmVhbGl6YWJsZS4gRXZlcnkgbmV3IHBvbGl0aWNhbCB0aGVvcnksIGJ5IH" +
        		"doYXRldmVyIG5hbWUgaXQgY2FsbGVkIGl0c2VsZiwgbGVkIGJhY2sgdG8gaGllcmFyY2h5IGFuZCByZWdpbWVudGF0aW9uLi" +
        		"BBbmQgaW4gdGhlIGdlbmVyYWwgaGFyZGVuaW5nIG9mIG91dGxvb2sgdGhhdCBzZXQgaW4gcm91bmQgYWJvdXQgMTkzMCwgcH" +
        		"JhY3RpY2VzIHdoaWNoIGhhZCBiZWVuIGxvbmcgYWJhbmRvbmVkLCBpbiBzb21lIGNhc2VzIGZvciBodW5kcmVkcyBvZiB5ZW" +
        		"Fycy0taW1wcmlzb25tZW50IHdpdGhvdXQgdHJpYWwsIHRoZSB1c2Ugb2Ygd2FyIHByaXNvbmVycyBhcyBzbGF2ZXMsIHB1Ym" +
        		"xpYyBleGVjdXRpb25zLCB0b3J0dXJlIHRvIGV4dHJhY3QgY29uZmVzc2lvbnMsIHRoZSB1c2Ugb2YgaG9zdGFnZXMsIGFuZC" +
        		"B0aGUgZGVwb3J0YXRpb24gb2Ygd2hvbGUgcG9wdWxhdGlvbnMtLW5vdCBvbmx5IGJlY2FtZSBjb21tb24gYWdhaW4sIGJ1dC" +
        		"B3ZXJlIHRvbGVyYXRlZCBhbmQgZXZlbiBkZWZlbmRlZCBieSBwZW9wbGUgd2hvIGNvbnNpZGVyZWQgdGhlbXNlbHZlcyBlbm" +
        		"xpZ2h0ZW5lZCBhbmQgcHJvZ3Jlc3NpdmUuCgpJdCB3YXMgb25seSBhZnRlciBhIGRlY2FkZSBvZiBuYXRpb25hbCB3YXJzLC" +
        		"BjaXZpbCB3YXJzLCByZXZvbHV0aW9ucywgYW5kIGNvdW50ZXItcmV2b2x1dGlvbnMgaW4gYWxsIHBhcnRzIG9mIHRoZSB3b3" +
        		"JsZCB0aGF0IEluZ3NvYyBhbmQgaXRzIHJpdmFscyBlbWVyZ2VkIGFzIGZ1bGx5IHdvcmtlZC1vdXQgcG9saXRpY2FsIHRoZW9" +
        		"yaWVzLiBCdXQgdGhleSBoYWQgYmVlbiBmb3Jlc2hhZG93ZWQgYnkgdGhlIHZhcmlvdXMgc3lzdGVtcywgZ2VuZXJhbGx5IGN" +
        		"hbGxlZCB0b3RhbGl0YXJpYW4sIHdoaWNoIGhhZCBhcHBlYXJlZCBlYXJsaWVyIGluIHRoZSBjZW50dXJ5LCBhbmQgdGhlIG1ha" +
        		"W4gb3V0bGluZXMgb2YgdGhlIHdvcmxkIHdoaWNoIHdvdWxkIGVtZXJnZSBmcm9tIHRoZSBwcmV2YWlsaW5nIGNoYW9zIGhhZCB" +
        		"sb25nIGJlZW4gb2J2aW91cy4gV2hhdCBraW5kIG9mIHBlb3BsZSB3b3VsZCBjb250cm9sIHRoaXMgd29ybGQgaGFkIGJlZW4g" +
        		"ZXF1YWxseSBvYnZpb3VzLiBUaGUgbmV3IGFyaXN0b2NyYWN5IHdhcyBtYWRlIHVwIGZvciB0aGUgbW9zdCBwYXJ0IG9mIGJ1c" +
        		"mVhdWNyYXRzLCBzY2llbnRpc3RzLCB0ZWNobmljaWFucywgdHJhZGUtdW5pb24gb3JnYW5pemVycywgcHVibGljaXR5IGV4cG" +
        		"VydHMsIHNvY2lvbG9naXN0cywgdGVhY2hlcnMsIGpvdXJuYWxpc3RzLCBhbmQgcHJvZmVzc2lvbmFsIHBvbGl0aWNpYW5zLiB" +
        		"UaGVzZSBwZW9wbGUsIHdob3NlIG9yaWdpbnMgbGF5IGluIHRoZSBzYWxhcmllZCBtaWRkbGUgY2xhc3MgYW5kIHRoZSB1cHB" +
        		"lciBncmFkZXMgb2YgdGhlIHdvcmtpbmcgY2xhc3MsIGhhZCBiZWVuIHNoYXBlZCBhbmQgYnJvdWdodCB0b2dldGhlciBieSB0" +
        		"aGUgYmFycmVuIHdvcmxkIG9mIG1vbm9wb2x5IGluZHVzdHJ5IGFuZCBjZW50cmFsaXplZCBnb3Zlcm5tZW50LiBBcyBjb21wY" +
        		"XJlZCB3aXRoIHRoZWlyIG9wcG9zaXRlIG51bWJlcnMgaW4gcGFzdCBhZ2VzLCB0aGV5IHdlcmUgbGVzcyBhdmFyaWNpb3VzLC" +
        		"BsZXNzIHRlbXB0ZWQgYnkgbHV4dXJ5LCBodW5ncmllciBmb3IgcHVyZSBwb3dlciwgYW5kLCBhYm92ZSBhbGwsIG1vcmUgY29" +
        		"uc2Npb3VzIG9mIHdoYXQgdGhleSB3ZXJlIGRvaW5nIGFuZCBtb3JlIGludGVudCBvbiBjcnVzaGluZyBvcHBvc2l0aW9uLiBU" +
        		"aGlzIGxhc3QgZGlmZmVyZW5jZSB3YXMgY2FyZGluYWwuIEJ5IGNvbXBhcmlzb24gd2l0aCB0aGF0IGV4aXN0aW5nIHRvZGF5L" +
        		"CBhbGwgdGhlIHR5cmFubmllcyBvZiB0aGUgcGFzdCB3ZXJlIGhhbGYtaGVhcnRlZCBhbmQgaW5lZmZpY2llbnQuIFRoZSBydW" +
        		"xpbmcgZ3JvdXBzIHdlcmUgYWx3YXlzIGluZmVjdGVkIHRvIHNvbWUgZXh0ZW50IGJ5IGxpYmVyYWwgaWRlYXMsIGFuZCB3ZXJ" +
        		"lIGNvbnRlbnQgdG8gbGVhdmUgbG9vc2UgZW5kcyBldmVyeXdoZXJlLCB0byByZWdhcmQgb25seSB0aGUgb3ZlcnQgYWN0IGFu" +
        		"ZCB0byBiZSB1bmludGVyZXN0ZWQgaW4gd2hhdCB0aGVpciBzdWJqZWN0cyB3ZXJlIHRoaW5raW5nLiBFdmVuIHRoZSBDYXRob" +
        		"2xpYyBDaHVyY2ggb2YgdGhlIE1pZGRsZSBBZ2VzIHdhcyB0b2xlcmFudCBieSBtb2Rlcm4gc3RhbmRhcmRzLiBQYXJ0IG9mIH" +
        		"RoZSByZWFzb24gZm9yIHRoaXMgd2FzIHRoYXQgaW4gdGhlIHBhc3Qgbm8gZ292ZXJubWVudCBoYWQgdGhlIHBvd2VyIHRvIGt" +
        		"lZXAgaXRzIGNpdGl6ZW5zIHVuZGVyIGNvbnN0YW50IHN1cnZlaWxsYW5jZS4gVGhlIGludmVudGlvbiBvZiBwcmludCwgaG93" +
        		"ZXZlciwgbWFkZSBpdCBlYXNpZXIgdG8gbWFuaXB1bGF0ZSBwdWJsaWMgb3BpbmlvbiwgYW5kIHRoZSBmaWxtIGFuZCB0aGUgc" +
        		"mFkaW8gY2FycmllZCB0aGUgcHJvY2VzcyBmdXJ0aGVyLiBXaXRoIHRoZSBkZXZlbG9wbWVudCBvZiB0ZWxldmlzaW9uLCBhbm" +
        		"QgdGhlIHRlY2huaWNhbCBhZHZhbmNlIHdoaWNoIG1hZGUgaXQgcG9zc2libGUgdG8gcmVjZWl2ZSBhbmQgdHJhbnNtaXQgc2l" +
        		"tdWx0YW5lb3VzbHkgb24gdGhlIHNhbWUgaW5zdHJ1bWVudCwgcHJpdmF0ZSBsaWZlIGNhbWUgdG8gYW4gZW5kLiBFdmVyeSBj" +
        		"aXRpemVuLCBvciBhdCBsZWFzdCBldmVyeSBjaXRpemVuIGltcG9ydGFudCBlbm91Z2ggdG8gYmUgd29ydGggd2F0Y2hpbmcsI" +
        		"GNvdWxkIGJlIGtlcHQgZm9yIHR3ZW50eS1mb3VyIGhvdXJzIGEgZGF5IHVuZGVyIHRoZSBleWVzIG9mIHRoZSBwb2xpY2UgYW" +
        		"5kIGluIHRoZSBzb3VuZCBvZiBvZmZpY2lhbCBwcm9wYWdhbmRhLCB3aXRoIGFsbCBvdGhlciBjaGFubmVscyBvZiBjb21tdW5" +
        		"pY2F0aW9uIGNsb3NlZC4gVGhlIHBvc3NpYmlsaXR5IG9mIGVuZm9yY2luZyBub3Qgb25seSBjb21wbGV0ZSBvYmVkaWVuY2Ug" +
        		"dG8gdGhlIHdpbGwgb2YgdGhlIFN0YXRlLCBidXQgY29tcGxldGUgdW5pZm9ybWl0eSBvZiBvcGluaW9uIG9uIGFsbCBzdWJqZ" +
        		"WN0cywgbm93IGV4aXN0ZWQgZm9yIHRoZSBmaXJzdCB0aW1lLgoKQWZ0ZXIgdGhlIHJldm9sdXRpb25hcnkgcGVyaW9kIG9mIH" +
        		"RoZSBmaWZ0aWVzIGFuZCBzaXh0aWVzLCBzb2NpZXR5IHJlZ3JvdXBlZCBpdHNlbGYsIGFzIGFsd2F5cywgaW50byBIaWdoLCB" +
        		"NaWRkbGUsIGFuZCBMb3cuIEJ1dCB0aGUgbmV3IEhpZ2ggZ3JvdXAsIHVubGlrZSBhbGwgaXRzIGZvcmVydW5uZXJzLCBkaWQg" +
        		"bm90IGFjdCB1cG9uIGluc3RpbmN0IGJ1dCBrbmV3IHdoYXQgd2FzIG5lZWRlZCB0byBzYWZlZ3VhcmQgaXRzIHBvc2l0aW9uL" +
        		"iBJdCBoYWQgbG9uZyBiZWVuIHJlYWxpemVkIHRoYXQgdGhlIG9ubHkgc2VjdXJlIGJhc2lzIGZvciBvbGlnYXJjaHkgaXMgY2" +
        		"9sbGVjdGl2aXNtLiBXZWFsdGggYW5kIHByaXZpbGVnZSBhcmUgbW9zdCBlYXNpbHkgZGVmZW5kZWQgd2hlbiB0aGV5IGFyZSB" +
        		"wb3NzZXNzZWQgam9pbnRseS4gVGhlIHNvLWNhbGxlZCAnYWJvbGl0aW9uIG9mIHByaXZhdGUgcHJvcGVydHknIHdoaWNoIHR" +
        		"vb2sgcGxhY2UgaW4gdGhlIG1pZGRsZSB5ZWFycyBvZiB0aGUgY2VudHVyeSBtZWFudCwgaW4gZWZmZWN0LCB0aGUgY29uY2Vu" +
        		"dHJhdGlvbiBvZiBwcm9wZXJ0eSBpbiBmYXIgZmV3ZXIgaGFuZHMgdGhhbiBiZWZvcmU6IGJ1dCB3aXRoIHRoaXMgZGlmZmVyZW" +
        		"5jZSwgdGhhdCB0aGUgbmV3IG93bmVycyB3ZXJlIGEgZ3JvdXAgaW5zdGVhZCBvZiBhIG1hc3Mgb2YgaW5kaXZpZHVhbHMuIElu" +
        		"ZGl2aWR1YWxseSwgbm8gbWVtYmVyIG9mIHRoZSBQYXJ0eSBvd25zIGFueXRoaW5nLCBleGNlcHQgcGV0dHkgcGVyc29uYWwgYm" +
        		"Vsb25naW5ncy4gQ29sbGVjdGl2ZWx5LCB0aGUgUGFydHkgb3ducyBldmVyeXRoaW5nIGluIE9jZWFuaWEsIGJlY2F1c2UgaXQg" +
        		"Y29udHJvbHMgZXZlcnl0aGluZywgYW5kIGRpc3Bvc2VzIG9mIHRoZSBwcm9kdWN0cyBhcyBpdCB0aGlua3MgZml0LiBJbiB0aGU" +
        		"geWVhcnMgZm9sbG93aW5nIHRoZSBSZXZvbHV0aW9uIGl0IHdhcyBhYmxlIHRvIHN0ZXAgaW50byB0aGlzIGNvbW1hbmRpbmcgcG" +
        		"9zaXRpb24gYWxtb3N0IHVub3Bwb3NlZCwgYmVjYXVzZSB0aGUgd2hvbGUgcHJvY2VzcyB3YXMgcmVwcmVzZW50ZWQgYXMgYW4gY" +
        		"WN0IG9mIGNvbGxlY3Rpdml6YXRpb24uIEl0IGhhZCBhbHdheXMgYmVlbiBhc3N1bWVkIHRoYXQgaWYgdGhlIGNhcGl0YWxpc3Qg" +
        		"Y2xhc3Mgd2VyZSBleHByb3ByaWF0ZWQsIFNvY2lhbGlzbSBtdXN0IGZvbGxvdzogYW5kIHVucXVlc3Rpb25hYmx5IHRoZSBjYXB" +
        		"pdGFsaXN0cyBoYWQgYmVlbiBleHByb3ByaWF0ZWQuIEZhY3RvcmllcywgbWluZXMsIGxhbmQsIGhvdXNlcywgdHJhbnNwb3J0LS" +
        		"1ldmVyeXRoaW5nIGhhZCBiZWVuIHRha2VuIGF3YXkgZnJvbSB0aGVtOiBhbmQgc2luY2UgdGhlc2UgdGhpbmdzIHdlcmUgbm8gb" +
        		"G9uZ2VyIHByaXZhdGUgcHJvcGVydHksIGl0IGZvbGxvd2VkIHRoYXQgdGhleSBtdXN0IGJlIHB1YmxpYyBwcm9wZXJ0eS4gSW5n" +
        		"c29jLCB3aGljaCBncmV3IG91dCBvZiB0aGUgZWFybGllciBTb2NpYWxpc3QgbW92ZW1lbnQgYW5kIGluaGVyaXRlZCBpdHMgcGh" +
        		"yYXNlb2xvZ3ksIGhhcyBpbiBmYWN0IGNhcnJpZWQgb3V0IHRoZSBtYWluIGl0ZW0gaW4gdGhlIFNvY2lhbGlzdCBwcm9ncmFtbW" +
        		"U7IHdpdGggdGhlIHJlc3VsdCwgZm9yZXNlZW4gYW5kIGludGVuZGVkIGJlZm9yZWhhbmQsIHRoYXQgZWNvbm9taWMgaW5lcXVhb" +
        		"Gl0eSBoYXMgYmVlbiBtYWRlIHBlcm1hbmVudC4KCkJ1dCB0aGUgcHJvYmxlbXMgb2YgcGVycGV0dWF0aW5nIGEgaGllcmFyY2hp" +
        		"Y2FsIHNvY2lldHkgZ28gZGVlcGVyIHRoYW4gdGhpcy4gVGhlcmUgYXJlIG9ubHkgZm91ciB3YXlzIGluIHdoaWNoIGEgcnVsaW5" +
        		"nIGdyb3VwIGNhbiBmYWxsIGZyb20gcG93ZXIuIEVpdGhlciBpdCBpcyBjb25xdWVyZWQgZnJvbSB3aXRob3V0LCBvciBpdCBnb3" +
        		"Zlcm5zIHNvIGluZWZmaWNpZW50bHkgdGhhdCB0aGUgbWFzc2VzIGFyZSBzdGlycmVkIHRvIHJldm9sdCwgb3IgaXQgYWxsb3dzI" +
        		"GEgc3Ryb25nIGFuZCBkaXNjb250ZW50ZWQgTWlkZGxlIGdyb3VwIHRvIGNvbWUgaW50byBiZWluZywgb3IgaXQgbG9zZXMgaXRz" +
        		"IG93biBzZWxmLWNvbmZpZGVuY2UgYW5kIHdpbGxpbmduZXNzIHRvIGdvdmVybi4gVGhlc2UgY2F1c2VzIGRvIG5vdCBvcGVyYXR" +
        		"lIHNpbmdseSwgYW5kIGFzIGEgcnVsZSBhbGwgZm91ciBvZiB0aGVtIGFyZSBwcmVzZW50IGluIHNvbWUgZGVncmVlLiBBIHJ1bG" +
        		"luZyBjbGFzcyB3aGljaCBjb3VsZCBndWFyZCBhZ2FpbnN0IGFsbCBvZiB0aGVtIHdvdWxkIHJlbWFpbiBpbiBwb3dlciBwZXJtY" +
        		"W5lbnRseS4gVWx0aW1hdGVseSB0aGUgZGV0ZXJtaW5pbmcgZmFjdG9yIGlzIHRoZSBtZW50YWwgYXR0aXR1ZGUgb2YgdGhlIHJ1" +
        		"bGluZyBjbGFzcyBpdHNlbGYuCgpBZnRlciB0aGUgbWlkZGxlIG9mIHRoZSBwcmVzZW50IGNlbnR1cnksIHRoZSBmaXJzdCBkYW5" +
        		"nZXIgaGFkIGluIHJlYWxpdHkgZGlzYXBwZWFyZWQuIEVhY2ggb2YgdGhlIHRocmVlIHBvd2VycyB3aGljaCBub3cgZGl2aWRlIH" +
        		"RoZSB3b3JsZCBpcyBpbiBmYWN0IHVuY29ucXVlcmFibGUsIGFuZCBjb3VsZCBvbmx5IGJlY29tZSBjb25xdWVyYWJsZSB0aHJvd" +
        		"WdoIHNsb3cgZGVtb2dyYXBoaWMgY2hhbmdlcyB3aGljaCBhIGdvdmVybm1lbnQgd2l0aCB3aWRlIHBvd2VycyBjYW4gZWFzaWx5" +
        		"IGF2ZXJ0LiBUaGUgc2Vjb25kIGRhbmdlciwgYWxzbywgaXMgb25seSBhIHRoZW9yZXRpY2FsIG9uZS4gVGhlIG1hc3NlcyBuZXZ" +
        		"lciByZXZvbHQgb2YgdGhlaXIgb3duIGFjY29yZCwgYW5kIHRoZXkgbmV2ZXIgcmV2b2x0IG1lcmVseSBiZWNhdXNlIHRoZXkgYX" +
        		"JlIG9wcHJlc3NlZC4gSW5kZWVkLCBzbyBsb25nIGFzIHRoZXkgYXJlIG5vdCBwZXJtaXR0ZWQgdG8gaGF2ZSBzdGFuZGFyZHMgb" +
        		"2YgY29tcGFyaXNvbiwgdGhleSBuZXZlciBldmVuIGJlY29tZSBhd2FyZSB0aGF0IHRoZXkgYXJlIG9wcHJlc3NlZC4gVGhlIHJl" +
        		"Y3VycmVudCBlY29ub21pYyBjcmlzZXMgb2YgcGFzdCB0aW1lcyB3ZXJlIHRvdGFsbHkgdW5uZWNlc3NhcnkgYW5kIGFyZSBub3Q" +
        		"gbm93IHBlcm1pdHRlZCB0byBoYXBwZW4sIGJ1dCBvdGhlciBhbmQgZXF1YWxseSBsYXJnZSBkaXNsb2NhdGlvbnMgY2FuIGFuZC" +
        		"BkbyBoYXBwZW4gd2l0aG91dCBoYXZpbmcgcG9saXRpY2FsIHJlc3VsdHMsIGJlY2F1c2UgdGhlcmUgaXMgbm8gd2F5IGluIHdoa" +
        		"WNoIGRpc2NvbnRlbnQgY2FuIGJlY29tZSBhcnRpY3VsYXRlLiBBcyBmb3IgdGhlIHByb2JsZW0gb2Ygb3Zlci1wcm9kdWN0aW9u" +
        		"LCB3aGljaCBoYXMgYmVlbiBsYXRlbnQgaW4gb3VyIHNvY2lldHkgc2luY2UgdGhlIGRldmVsb3BtZW50IG9mIG1hY2hpbmUgdGV" +
        		"jaG5pcXVlLCBpdCBpcyBzb2x2ZWQgYnkgdGhlIGRldmljZSBvZiBjb250aW51b3VzIHdhcmZhcmUgKHNlZSBDaGFwdGVyIElJSS" +
        		"ksIHdoaWNoIGlzIGFsc28gdXNlZnVsIGluIGtleWluZyB1cCBwdWJsaWMgbW9yYWxlIHRvIHRoZSBuZWNlc3NhcnkgcGl0Y2guI" +
        		"EZyb20gdGhlIHBvaW50IG9mIHZpZXcgb2Ygb3VyIHByZXNlbnQgcnVsZXJzLCB0aGVyZWZvcmUsIHRoZSBvbmx5IGdlbnVpbmUg" +
        		"ZGFuZ2VycyBhcmUgdGhlIHNwbGl0dGluZy1vZmYgb2YgYSBuZXcgZ3JvdXAgb2YgYWJsZSwgdW5kZXItZW1wbG95ZWQsIHBvd2V" +
        		"yLWh1bmdyeSBwZW9wbGUsIGFuZCB0aGUgZ3Jvd3RoIG9mIGxpYmVyYWxpc20gYW5kIHNjZXB0aWNpc20gaW4gdGhlaXIgb3duIH" +
        		"JhbmtzLiBUaGUgcHJvYmxlbSwgdGhhdCBpcyB0byBzYXksIGlzIGVkdWNhdGlvbmFsLiBJdCBpcyBhIHByb2JsZW0gb2YgY29ud" +
        		"GludW91c2x5IG1vdWxkaW5nIHRoZSBjb25zY2lvdXNuZXNzIGJvdGggb2YgdGhlIGRpcmVjdGluZyBncm91cCBhbmQgb2YgdGhl" +
        		"IGxhcmdlciBleGVjdXRpdmUgZ3JvdXAgdGhhdCBsaWVzIGltbWVkaWF0ZWx5IGJlbG93IGl0LiBUaGUgY29uc2Npb3VzbmVzcyB" +
        		"vZiB0aGUgbWFzc2VzIG5lZWRzIG9ubHkgdG8gYmUgaW5mbHVlbmNlZCBpbiBhIG5lZ2F0aXZlIHdheS4KCkdpdmVuIHRoaXMgYm" +
        		"Fja2dyb3VuZCwgb25lIGNvdWxkIGluZmVyLCBpZiBvbmUgZGlkIG5vdCBrbm93IGl0IGFscmVhZHksIHRoZSBnZW5lcmFsIHN0c" +
        		"nVjdHVyZSBvZiBPY2VhbmljIHNvY2lldHkuIEF0IHRoZSBhcGV4IG9mIHRoZSBweXJhbWlkIGNvbWVzIEJpZyBCcm90aGVyLiBC" +
        		"aWcgQnJvdGhlciBpcyBpbmZhbGxpYmxlIGFuZCBhbGwtcG93ZXJmdWwuIEV2ZXJ5IHN1Y2Nlc3MsIGV2ZXJ5IGFjaGlldmVtZW5" +
        		"0LCBldmVyeSB2aWN0b3J5LCBldmVyeSBzY2llbnRpZmljIGRpc2NvdmVyeSwgYWxsIGtub3dsZWRnZSwgYWxsIHdpc2RvbSwgYW" +
        		"xsIGhhcHBpbmVzcywgYWxsIHZpcnR1ZSwgYXJlIGhlbGQgdG8gaXNzdWUgZGlyZWN0bHkgZnJvbSBoaXMgbGVhZGVyc2hpcCBhb" +
        		"mQgaW5zcGlyYXRpb24uIE5vYm9keSBoYXMgZXZlciBzZWVuIEJpZyBCcm90aGVyLiBIZSBpcyBhIGZhY2Ugb24gdGhlIGhvYXJk" +
        		"aW5ncywgYSB2b2ljZSBvbiB0aGUgdGVsZXNjcmVlbi4gV2UgbWF5IGJlIHJlYXNvbmFibHkgc3VyZSB0aGF0IGhlIHdpbGwgbmV" +
        		"2ZXIgZGllLCBhbmQgdGhlcmUgaXMgYWxyZWFkeSBjb25zaWRlcmFibGUgdW5jZXJ0YWludHkgYXMgdG8gd2hlbiBoZSB3YXMgYm" +
        		"9ybi4gQmlnIEJyb3RoZXIgaXMgdGhlIGd1aXNlIGluIHdoaWNoIHRoZSBQYXJ0eSBjaG9vc2VzIHRvIGV4aGliaXQgaXRzZWxmI" +
        		"HRvIHRoZSB3b3JsZC4gSGlzIGZ1bmN0aW9uIGlzIHRvIGFjdCBhcyBhIGZvY3VzaW5nIHBvaW50IGZvciBsb3ZlLCBmZWFyLCBh" +
        		"bmQgcmV2ZXJlbmNlLCBlbW90aW9ucyB3aGljaCBhcmUgbW9yZSBlYXNpbHkgZmVsdCB0b3dhcmRzIGFuIGluZGl2aWR1YWwgdGh" +
        		"hbiB0b3dhcmRzIGFuIG9yZ2FuaXphdGlvbi4gQmVsb3cgQmlnIEJyb3RoZXIgY29tZXMgdGhlIElubmVyIFBhcnR5LiBJdHMgbn" +
        		"VtYmVycyBsaW1pdGVkIHRvIHNpeCBtaWxsaW9ucywgb3Igc29tZXRoaW5nIGxlc3MgdGhhbiAyIHBlciBjZW50IG9mIHRoZSBwb" +
        		"3B1bGF0aW9uIG9mIE9jZWFuaWEuIEJlbG93IHRoZSBJbm5lciBQYXJ0eSBjb21lcyB0aGUgT3V0ZXIgUGFydHksIHdoaWNoLCBp" +
        		"ZiB0aGUgSW5uZXIgUGFydHkgaXMgZGVzY3JpYmVkIGFzIHRoZSBicmFpbiBvZiB0aGUgU3RhdGUsIG1heSBiZSBqdXN0bHkgbGl" +
        		"rZW5lZCB0byB0aGUgaGFuZHMuIEJlbG93IHRoYXQgY29tZSB0aGUgZHVtYiBtYXNzZXMgd2hvbSB3ZSBoYWJpdHVhbGx5IHJlZm" +
        		"VyIHRvIGFzICd0aGUgcHJvbGVzJywgbnVtYmVyaW5nIHBlcmhhcHMgODUgcGVyIGNlbnQgb2YgdGhlIHBvcHVsYXRpb24uIEluI" +
        		"HRoZSB0ZXJtcyBvZiBvdXIgZWFybGllciBjbGFzc2lmaWNhdGlvbiwgdGhlIHByb2xlcyBhcmUgdGhlIExvdzogZm9yIHRoZSBz" +
        		"bGF2ZSBwb3B1bGF0aW9uIG9mIHRoZSBlcXVhdG9yaWFsIGxhbmRzIHdobyBwYXNzIGNvbnN0YW50bHkgZnJvbSBjb25xdWVyb3I" +
        		"gdG8gY29ucXVlcm9yLCBhcmUgbm90IGEgcGVybWFuZW50IG9yIG5lY2Vzc2FyeSBwYXJ0IG9mIHRoZSBzdHJ1Y3R1cmUuCgpJbi" +
        		"BwcmluY2lwbGUsIG1lbWJlcnNoaXAgb2YgdGhlc2UgdGhyZWUgZ3JvdXBzIGlzIG5vdCBoZXJlZGl0YXJ5LiBUaGUgY2hpbGQgb" +
        		"2YgSW5uZXIgUGFydHkgcGFyZW50cyBpcyBpbiB0aGVvcnkgbm90IGJvcm4gaW50byB0aGUgSW5uZXIgUGFydHkuIEFkbWlzc2lv" +
        		"biB0byBlaXRoZXIgYnJhbmNoIG9mIHRoZSBQYXJ0eSBpcyBieSBleGFtaW5hdGlvbiwgdGFrZW4gYXQgdGhlIGFnZSBvZiBzaXh" +
        		"0ZWVuLiBOb3IgaXMgdGhlcmUgYW55IHJhY2lhbCBkaXNjcmltaW5hdGlvbiwgb3IgYW55IG1hcmtlZCBkb21pbmF0aW9uIG9mIG" +
        		"9uZSBwcm92aW5jZSBieSBhbm90aGVyLiBKZXdzLCBOZWdyb2VzLCBTb3V0aCBBbWVyaWNhbnMgb2YgcHVyZSBJbmRpYW4gYmxvb" +
        		"2QgYXJlIHRvIGJlIGZvdW5kIGluIHRoZSBoaWdoZXN0IHJhbmtzIG9mIHRoZSBQYXJ0eSwgYW5kIHRoZSBhZG1pbmlzdHJhdG9y" +
        		"cyBvZiBhbnkgYXJlYSBhcmUgYWx3YXlzIGRyYXduIGZyb20gdGhlIGluaGFiaXRhbnRzIG9mIHRoYXQgYXJlYS4gSW4gbm8gcGF" +
        		"ydCBvZiBPY2VhbmlhIGRvIHRoZSBpbmhhYml0YW50cyBoYXZlIHRoZSBmZWVsaW5nIHRoYXQgdGhleSBhcmUgYSBjb2xvbmlhbC" +
        		"Bwb3B1bGF0aW9uIHJ1bGVkIGZyb20gYSBkaXN0YW50IGNhcGl0YWwuIE9jZWFuaWEgaGFzIG5vIGNhcGl0YWwsIGFuZCBpdHMgd" +
        		"Gl0dWxhciBoZWFkIGlzIGEgcGVyc29uIHdob3NlIHdoZXJlYWJvdXRzIG5vYm9keSBrbm93cy4gRXhjZXB0IHRoYXQgRW5nbGlz" +
        		"aCBpcyBpdHMgY2hpZWYgTElOR1VBIEZSQU5DQSBhbmQgTmV3c3BlYWsgaXRzIG9mZmljaWFsIGxhbmd1YWdlLCBpdCBpcyBub3Q" +
        		"gY2VudHJhbGl6ZWQgaW4gYW55IHdheS4gSXRzIHJ1bGVycyBhcmUgbm90IGhlbGQgdG9nZXRoZXIgYnkgYmxvb2QtdGllcyBidX" +
        		"QgYnkgYWRoZXJlbmNlIHRvIGEgY29tbW9uIGRvY3RyaW5lLiBJdCBpcyB0cnVlIHRoYXQgb3VyIHNvY2lldHkgaXMgc3RyYXRpZ" +
        		"mllZCwgYW5kIHZlcnkgcmlnaWRseSBzdHJhdGlmaWVkLCBvbiB3aGF0IGF0IGZpcnN0IHNpZ2h0IGFwcGVhciB0byBiZSBoZXJl" +
        		"ZGl0YXJ5IGxpbmVzLiBUaGVyZSBpcyBmYXIgbGVzcyB0by1hbmQtZnJvIG1vdmVtZW50IGJldHdlZW4gdGhlIGRpZmZlcmVudCB" +
        		"ncm91cHMgdGhhbiBoYXBwZW5lZCB1bmRlciBjYXBpdGFsaXNtIG9yIGV2ZW4gaW4gdGhlIHByZS1pbmR1c3RyaWFsIGFnZS4gQm" +
        		"V0d2VlbiB0aGUgdHdvIGJyYW5jaGVzIG9mIHRoZSBQYXJ0eSB0aGVyZSBpcyBhIGNlcnRhaW4gYW1vdW50IG9mIGludGVyY2hhb" +
        		"mdlLCBidXQgb25seSBzbyBtdWNoIGFzIHdpbGwgZW5zdXJlIHRoYXQgd2Vha2xpbmdzIGFyZSBleGNsdWRlZCBmcm9tIHRoZSBJ" +
        		"bm5lciBQYXJ0eSBhbmQgdGhhdCBhbWJpdGlvdXMgbWVtYmVycyBvZiB0aGUgT3V0ZXIgUGFydHkgYXJlIG1hZGUgaGFybWxlc3M" +
        		"gYnkgYWxsb3dpbmcgdGhlbSB0byByaXNlLiBQcm9sZXRhcmlhbnMsIGluIHByYWN0aWNlLCBhcmUgbm90IGFsbG93ZWQgdG8gZ3" +
        		"JhZHVhdGUgaW50byB0aGUgUGFydHkuIFRoZSBtb3N0IGdpZnRlZCBhbW9uZyB0aGVtLCB3aG8gbWlnaHQgcG9zc2libHkgYmVjb" +
        		"21lIG51Y2xlaSBvZiBkaXNjb250ZW50LCBhcmUgc2ltcGx5IG1hcmtlZCBkb3duIGJ5IHRoZSBUaG91Z2h0IFBvbGljZSBhbmQg" +
        		"ZWxpbWluYXRlZC4gQnV0IHRoaXMgc3RhdGUgb2YgYWZmYWlycyBpcyBub3QgbmVjZXNzYXJpbHkgcGVybWFuZW50LCBub3IgaXM" +
        		"gaXQgYSBtYXR0ZXIgb2YgcHJpbmNpcGxlLiBUaGUgUGFydHkgaXMgbm90IGEgY2xhc3MgaW4gdGhlIG9sZCBzZW5zZSBvZiB0aG" +
        		"Ugd29yZC4gSXQgZG9lcyBub3QgYWltIGF0IHRyYW5zbWl0dGluZyBwb3dlciB0byBpdHMgb3duIGNoaWxkcmVuLCBhcyBzdWNoO" +
        		"yBhbmQgaWYgdGhlcmUgd2VyZSBubyBvdGhlciB3YXkgb2Yga2VlcGluZyB0aGUgYWJsZXN0IHBlb3BsZSBhdCB0aGUgdG9wLCBp" +
        		"dCB3b3VsZCBiZSBwZXJmZWN0bHkgcHJlcGFyZWQgdG8gcmVjcnVpdCBhbiBlbnRpcmUgbmV3IGdlbmVyYXRpb24gZnJvbSB0aGU" +
        		"gcmFua3Mgb2YgdGhlIHByb2xldGFyaWF0LiBJbiB0aGUgY3J1Y2lhbCB5ZWFycywgdGhlIGZhY3QgdGhhdCB0aGUgUGFydHkgd2" +
        		"FzIG5vdCBhIGhlcmVkaXRhcnkgYm9keSBkaWQgYSBncmVhdCBkZWFsIHRvIG5ldXRyYWxpemUgb3Bwb3NpdGlvbi4gVGhlIG9sZ" +
        		"GVyIGtpbmQgb2YgU29jaWFsaXN0LCB3aG8gaGFkIGJlZW4gdHJhaW5lZCB0byBmaWdodCBhZ2FpbnN0IHNvbWV0aGluZyBjYWxs" +
        		"ZWQgJ2NsYXNzIHByaXZpbGVnZScgYXNzdW1lZCB0aGF0IHdoYXQgaXMgbm90IGhlcmVkaXRhcnkgY2Fubm90IGJlIHBlcm1hbmV" +
        		"udC4gSGUgZGlkIG5vdCBzZWUgdGhhdCB0aGUgY29udGludWl0eSBvZiBhbiBvbGlnYXJjaHkgbmVlZCBub3QgYmUgcGh5c2ljYW" +
        		"wsIG5vciBkaWQgaGUgcGF1c2UgdG8gcmVmbGVjdCB0aGF0IGhlcmVkaXRhcnkgYXJpc3RvY3JhY2llcyBoYXZlIGFsd2F5cyBiZ" +
        		"WVuIHNob3J0bGl2ZWQsIHdoZXJlYXMgYWRvcHRpdmUgb3JnYW5pemF0aW9ucyBzdWNoIGFzIHRoZSBDYXRob2xpYyBDaHVyY2gg" +
        		"aGF2ZSBzb21ldGltZXMgbGFzdGVkIGZvciBodW5kcmVkcyBvciB0aG91c2FuZHMgb2YgeWVhcnMuIFRoZSBlc3NlbmNlIG9mIG9" +
        		"saWdhcmNoaWNhbCBydWxlIGlzIG5vdCBmYXRoZXItdG8tc29uIGluaGVyaXRhbmNlLCBidXQgdGhlIHBlcnNpc3RlbmNlIG9mIG" +
        		"EgY2VydGFpbiB3b3JsZC12aWV3IGFuZCBhIGNlcnRhaW4gd2F5IG9mIGxpZmUsIGltcG9zZWQgYnkgdGhlIGRlYWQgdXBvbiB0a" +
        		"GUgbGl2aW5nLiBBIHJ1bGluZyBncm91cCBpcyBhIHJ1bGluZyBncm91cCBzbyBsb25nIGFzIGl0IGNhbiBub21pbmF0ZSBpdHMg" +
        		"c3VjY2Vzc29ycy4gVGhlIFBhcnR5IGlzIG5vdCBjb25jZXJuZWQgd2l0aCBwZXJwZXR1YXRpbmcgaXRzIGJsb29kIGJ1dCB3aXR" +
        		"oIHBlcnBldHVhdGluZyBpdHNlbGYuIFdITyB3aWVsZHMgcG93ZXIgaXMgbm90IGltcG9ydGFudCwgcHJvdmlkZWQgdGhhdCB0aG" +
        		"UgaGllcmFyY2hpY2FsIHN0cnVjdHVyZSByZW1haW5zIGFsd2F5cyB0aGUgc2FtZS4KCkFsbCB0aGUgYmVsaWVmcywgaGFiaXRzL" +
        		"CB0YXN0ZXMsIGVtb3Rpb25zLCBtZW50YWwgYXR0aXR1ZGVzIHRoYXQgY2hhcmFjdGVyaXplIG91ciB0aW1lIGFyZSByZWFsbHkg" +
        		"ZGVzaWduZWQgdG8gc3VzdGFpbiB0aGUgbXlzdGlxdWUgb2YgdGhlIFBhcnR5IGFuZCBwcmV2ZW50IHRoZSB0cnVlIG5hdHVyZSB" +
        		"vZiBwcmVzZW50LWRheSBzb2NpZXR5IGZyb20gYmVpbmcgcGVyY2VpdmVkLiBQaHlzaWNhbCByZWJlbGxpb24sIG9yIGFueSBwcm" +
        		"VsaW1pbmFyeSBtb3ZlIHRvd2FyZHMgcmViZWxsaW9uLCBpcyBhdCBwcmVzZW50IG5vdCBwb3NzaWJsZS4gRnJvbSB0aGUgcHJvb" +
        		"GV0YXJpYW5zIG5vdGhpbmcgaXMgdG8gYmUgZmVhcmVkLiBMZWZ0IHRvIHRoZW1zZWx2ZXMsIHRoZXkgd2lsbCBjb250aW51ZSBm" +
        		"cm9tIGdlbmVyYXRpb24gdG8gZ2VuZXJhdGlvbiBhbmQgZnJvbSBjZW50dXJ5IHRvIGNlbnR1cnksIHdvcmtpbmcsIGJyZWVkaW5" +
        		"nLCBhbmQgZHlpbmcsIG5vdCBvbmx5IHdpdGhvdXQgYW55IGltcHVsc2UgdG8gcmViZWwsIGJ1dCB3aXRob3V0IHRoZSBwb3dlci" +
        		"BvZiBncmFzcGluZyB0aGF0IHRoZSB3b3JsZCBjb3VsZCBiZSBvdGhlciB0aGFuIGl0IGlzLiBUaGV5IGNvdWxkIG9ubHkgYmVjb" +
        		"21lIGRhbmdlcm91cyBpZiB0aGUgYWR2YW5jZSBvZiBpbmR1c3RyaWFsIHRlY2huaXF1ZSBtYWRlIGl0IG5lY2Vzc2FyeSB0byBl" +
        		"ZHVjYXRlIHRoZW0gbW9yZSBoaWdobHk7IGJ1dCwgc2luY2UgbWlsaXRhcnkgYW5kIGNvbW1lcmNpYWwgcml2YWxyeSBhcmUgbm8" +
        		"gbG9uZ2VyIGltcG9ydGFudCwgdGhlIGxldmVsIG9mIHBvcHVsYXIgZWR1Y2F0aW9uIGlzIGFjdHVhbGx5IGRlY2xpbmluZy4gV2" +
        		"hhdCBvcGluaW9ucyB0aGUgbWFzc2VzIGhvbGQsIG9yIGRvIG5vdCBob2xkLCBpcyBsb29rZWQgb24gYXMgYSBtYXR0ZXIgb2Yga" +
        		"W5kaWZmZXJlbmNlLiBUaGV5IGNhbiBiZSBncmFudGVkIGludGVsbGVjdHVhbCBsaWJlcnR5IGJlY2F1c2UgdGhleSBoYXZlIG5v" +
        		"IGludGVsbGVjdC4gSW4gYSBQYXJ0eSBtZW1iZXIsIG9uIHRoZSBvdGhlciBoYW5kLCBub3QgZXZlbiB0aGUgc21hbGxlc3QgZGV" +
        		"2aWF0aW9uIG9mIG9waW5pb24gb24gdGhlIG1vc3QgdW5pbXBvcnRhbnQgc3ViamVjdCBjYW4gYmUgdG9sZXJhdGVkLgoKQSBQYX" +
        		"J0eSBtZW1iZXIgbGl2ZXMgZnJvbSBiaXJ0aCB0byBkZWF0aCB1bmRlciB0aGUgZXllIG9mIHRoZSBUaG91Z2h0IFBvbGljZS4gR" +
        		"XZlbiB3aGVuIGhlIGlzIGFsb25lIGhlIGNhbiBuZXZlciBiZSBzdXJlIHRoYXQgaGUgaXMgYWxvbmUuIFdoZXJldmVyIGhlIG1h" +
        		"eSBiZSwgYXNsZWVwIG9yIGF3YWtlLCB3b3JraW5nIG9yIHJlc3RpbmcsIGluIGhpcyBiYXRoIG9yIGluIGJlZCwgaGUgY2FuIGJ" +
        		"lIGluc3BlY3RlZCB3aXRob3V0IHdhcm5pbmcgYW5kIHdpdGhvdXQga25vd2luZyB0aGF0IGhlIGlzIGJlaW5nIGluc3BlY3RlZC" +
        		"4gTm90aGluZyB0aGF0IGhlIGRvZXMgaXMgaW5kaWZmZXJlbnQuIEhpcyBmcmllbmRzaGlwcywgaGlzIHJlbGF4YXRpb25zLCBoa" +
        		"XMgYmVoYXZpb3VyIHRvd2FyZHMgaGlzIHdpZmUgYW5kIGNoaWxkcmVuLCB0aGUgZXhwcmVzc2lvbiBvZiBoaXMgZmFjZSB3aGVu" +
        		"IGhlIGlzIGFsb25lLCB0aGUgd29yZHMgaGUgbXV0dGVycyBpbiBzbGVlcCwgZXZlbiB0aGUgY2hhcmFjdGVyaXN0aWMgbW92ZW1" +
        		"lbnRzIG9mIGhpcyBib2R5LCBhcmUgYWxsIGplYWxvdXNseSBzY3J1dGluaXplZC4gTm90IG9ubHkgYW55IGFjdHVhbCBtaXNkZW" +
        		"1lYW5vdXIsIGJ1dCBhbnkgZWNjZW50cmljaXR5LCBob3dldmVyIHNtYWxsLCBhbnkgY2hhbmdlIG9mIGhhYml0cywgYW55IG5lc" +
        		"nZvdXMgbWFubmVyaXNtIHRoYXQgY291bGQgcG9zc2libHkgYmUgdGhlIHN5bXB0b20gb2YgYW4gaW5uZXIgc3RydWdnbGUsIGlz" +
        		"IGNlcnRhaW4gdG8gYmUgZGV0ZWN0ZWQuIEhlIGhhcyBubyBmcmVlZG9tIG9mIGNob2ljZSBpbiBhbnkgZGlyZWN0aW9uIHdoYXR" +
        		"ldmVyLiBPbiB0aGUgb3RoZXIgaGFuZCBoaXMgYWN0aW9ucyBhcmUgbm90IHJlZ3VsYXRlZCBieSBsYXcgb3IgYnkgYW55IGNsZW" +
        		"FybHkgZm9ybXVsYXRlZCBjb2RlIG9mIGJlaGF2aW91ci4gSW4gT2NlYW5pYSB0aGVyZSBpcyBubyBsYXcuIFRob3VnaHRzIGFuZ" +
        		"CBhY3Rpb25zIHdoaWNoLCB3aGVuIGRldGVjdGVkLCBtZWFuIGNlcnRhaW4gZGVhdGggYXJlIG5vdCBmb3JtYWxseSBmb3JiaWRk" +
        		"ZW4sIGFuZCB0aGUgZW5kbGVzcyBwdXJnZXMsIGFycmVzdHMsIHRvcnR1cmVzLCBpbXByaXNvbm1lbnRzLCBhbmQgdmFwb3JpemF" +
        		"0aW9ucyBhcmUgbm90IGluZmxpY3RlZCBhcyBwdW5pc2htZW50IGZvciBjcmltZXMgd2hpY2ggaGF2ZSBhY3R1YWxseSBiZWVuIG" +
        		"NvbW1pdHRlZCwgYnV0IGFyZSBtZXJlbHkgdGhlIHdpcGluZy1vdXQgb2YgcGVyc29ucyB3aG8gbWlnaHQgcGVyaGFwcyBjb21ta" +
        		"XQgYSBjcmltZSBhdCBzb21lIHRpbWUgaW4gdGhlIGZ1dHVyZS4gQSBQYXJ0eSBtZW1iZXIgaXMgcmVxdWlyZWQgdG8gaGF2ZSBu" +
        		"b3Qgb25seSB0aGUgcmlnaHQgb3BpbmlvbnMsIGJ1dCB0aGUgcmlnaHQgaW5zdGluY3RzLiBNYW55IG9mIHRoZSBiZWxpZWZzIGF" +
        		"uZCBhdHRpdHVkZXMgZGVtYW5kZWQgb2YgaGltIGFyZSBuZXZlciBwbGFpbmx5IHN0YXRlZCwgYW5kIGNvdWxkIG5vdCBiZSBzdG" +
        		"F0ZWQgd2l0aG91dCBsYXlpbmcgYmFyZSB0aGUgY29udHJhZGljdGlvbnMgaW5oZXJlbnQgaW4gSW5nc29jLiBJZiBoZSBpcyBhI" +
        		"HBlcnNvbiBuYXR1cmFsbHkgb3J0aG9kb3ggKGluIE5ld3NwZWFrIGEgR09PRFRISU5LRVIpLCBoZSB3aWxsIGluIGFsbCBjaXJj" +
        		"dW1zdGFuY2VzIGtub3csIHdpdGhvdXQgdGFraW5nIHRob3VnaHQsIHdoYXQgaXMgdGhlIHRydWUgYmVsaWVmIG9yIHRoZSBkZXN" +
        		"pcmFibGUgZW1vdGlvbi4gQnV0IGluIGFueSBjYXNlIGFuIGVsYWJvcmF0ZSBtZW50YWwgdHJhaW5pbmcsIHVuZGVyZ29uZSBpbi" +
        		"BjaGlsZGhvb2QgYW5kIGdyb3VwaW5nIGl0c2VsZiByb3VuZCB0aGUgTmV3c3BlYWsgd29yZHMgQ1JJTUVTVE9QLCBCTEFDS1dIS" +
        		"VRFLCBhbmQgRE9VQkxFVEhJTkssIG1ha2VzIGhpbSB1bndpbGxpbmcgYW5kIHVuYWJsZSB0byB0aGluayB0b28gZGVlcGx5IG9u" +
        		"IGFueSBzdWJqZWN0IHdoYXRldmVyLgoKQSBQYXJ0eSBtZW1iZXIgaXMgZXhwZWN0ZWQgdG8gaGF2ZSBubyBwcml2YXRlIGVtb3R" +
        		"pb25zIGFuZCBubyByZXNwaXRlcyBmcm9tIGVudGh1c2lhc20uIEhlIGlzIHN1cHBvc2VkIHRvIGxpdmUgaW4gYSBjb250aW51b3" +
        		"VzIGZyZW56eSBvZiBoYXRyZWQgb2YgZm9yZWlnbiBlbmVtaWVzIGFuZCBpbnRlcm5hbCB0cmFpdG9ycywgdHJpdW1waCBvdmVyI" +
        		"HZpY3RvcmllcywgYW5kIHNlbGYtYWJhc2VtZW50IGJlZm9yZSB0aGUgcG93ZXIgYW5kIHdpc2RvbSBvZiB0aGUgUGFydHkuIFRo" +
        		"ZSBkaXNjb250ZW50cyBwcm9kdWNlZCBieSBoaXMgYmFyZSwgdW5zYXRpc2Z5aW5nIGxpZmUgYXJlIGRlbGliZXJhdGVseSB0dXJ" +
        		"uZWQgb3V0d2FyZHMgYW5kIGRpc3NpcGF0ZWQgYnkgc3VjaCBkZXZpY2VzIGFzIHRoZSBUd28gTWludXRlcyBIYXRlLCBhbmQgdG" +
        		"hlIHNwZWN1bGF0aW9ucyB3aGljaCBtaWdodCBwb3NzaWJseSBpbmR1Y2UgYSBzY2VwdGljYWwgb3IgcmViZWxsaW91cyBhdHRpd" +
        		"HVkZSBhcmUga2lsbGVkIGluIGFkdmFuY2UgYnkgaGlzIGVhcmx5IGFjcXVpcmVkIGlubmVyIGRpc2NpcGxpbmUuIFRoZSBmaXJz" +
        		"dCBhbmQgc2ltcGxlc3Qgc3RhZ2UgaW4gdGhlIGRpc2NpcGxpbmUsIHdoaWNoIGNhbiBiZSB0YXVnaHQgZXZlbiB0byB5b3VuZyB" +
        		"jaGlsZHJlbiwgaXMgY2FsbGVkLCBpbiBOZXdzcGVhaywgQ1JJTUVTVE9QLiBDUklNRVNUT1AgbWVhbnMgdGhlIGZhY3VsdHkgb2" +
        		"Ygc3RvcHBpbmcgc2hvcnQsIGFzIHRob3VnaCBieSBpbnN0aW5jdCwgYXQgdGhlIHRocmVzaG9sZCBvZiBhbnkgZGFuZ2Vyb3VzI" +
        		"HRob3VnaHQuIEl0IGluY2x1ZGVzIHRoZSBwb3dlciBvZiBub3QgZ3Jhc3BpbmcgYW5hbG9naWVzLCBvZiBmYWlsaW5nIHRvIHBl" +
        		"cmNlaXZlIGxvZ2ljYWwgZXJyb3JzLCBvZiBtaXN1bmRlcnN0YW5kaW5nIHRoZSBzaW1wbGVzdCBhcmd1bWVudHMgaWYgdGhleSB" +
        		"hcmUgaW5pbWljYWwgdG8gSW5nc29jLCBhbmQgb2YgYmVpbmcgYm9yZWQgb3IgcmVwZWxsZWQgYnkgYW55IHRyYWluIG9mIHRob3" +
        		"VnaHQgd2hpY2ggaXMgY2FwYWJsZSBvZiBsZWFkaW5nIGluIGEgaGVyZXRpY2FsIGRpcmVjdGlvbi4gQ1JJTUVTVE9QLCBpbiBza" +
        		"G9ydCwgbWVhbnMgcHJvdGVjdGl2ZSBzdHVwaWRpdHkuIEJ1dCBzdHVwaWRpdHkgaXMgbm90IGVub3VnaC4gT24gdGhlIGNvbnRy" +
        		"YXJ5LCBvcnRob2RveHkgaW4gdGhlIGZ1bGwgc2Vuc2UgZGVtYW5kcyBhIGNvbnRyb2wgb3ZlciBvbmUncyBvd24gbWVudGFsIHB" +
        		"yb2Nlc3NlcyBhcyBjb21wbGV0ZSBhcyB0aGF0IG9mIGEgY29udG9ydGlvbmlzdCBvdmVyIGhpcyBib2R5LiBPY2VhbmljIHNvY2" +
        		"lldHkgcmVzdHMgdWx0aW1hdGVseSBvbiB0aGUgYmVsaWVmIHRoYXQgQmlnIEJyb3RoZXIgaXMgb21uaXBvdGVudCBhbmQgdGhhd" +
        		"CB0aGUgUGFydHkgaXMgaW5mYWxsaWJsZS4gQnV0IHNpbmNlIGluIHJlYWxpdHkgQmlnIEJyb3RoZXIgaXMgbm90IG9tbmlwb3Rl" +
        		"bnQgYW5kIHRoZSBwYXJ0eSBpcyBub3QgaW5mYWxsaWJsZSwgdGhlcmUgaXMgbmVlZCBmb3IgYW4gdW53ZWFyeWluZywgbW9tZW5" +
        		"0LXRvLW1vbWVudCBmbGV4aWJpbGl0eSBpbiB0aGUgdHJlYXRtZW50IG9mIGZhY3RzLiBUaGUga2V5d29yZCBoZXJlIGlzIEJMQU" +
        		"NLV0hJVEUuIExpa2Ugc28gbWFueSBOZXdzcGVhayB3b3JkcywgdGhpcyB3b3JkIGhhcyB0d28gbXV0dWFsbHkgY29udHJhZGljd" +
        		"G9yeSBtZWFuaW5ncy4gQXBwbGllZCB0byBhbiBvcHBvbmVudCwgaXQgbWVhbnMgdGhlIGhhYml0IG9mIGltcHVkZW50bHkgY2xh" +
        		"aW1pbmcgdGhhdCBibGFjayBpcyB3aGl0ZSwgaW4gY29udHJhZGljdGlvbiBvZiB0aGUgcGxhaW4gZmFjdHMuIEFwcGxpZWQgdG8" +
        		"gYSBQYXJ0eSBtZW1iZXIsIGl0IG1lYW5zIGEgbG95YWwgd2lsbGluZ25lc3MgdG8gc2F5IHRoYXQgYmxhY2sgaXMgd2hpdGUgd2" +
        		"hlbiBQYXJ0eSBkaXNjaXBsaW5lIGRlbWFuZHMgdGhpcy4gQnV0IGl0IG1lYW5zIGFsc28gdGhlIGFiaWxpdHkgdG8gQkVMSUVWR" +
        		"SB0aGF0IGJsYWNrIGlzIHdoaXRlLCBhbmQgbW9yZSwgdG8gS05PVyB0aGF0IGJsYWNrIGlzIHdoaXRlLCBhbmQgdG8gZm9yZ2V0" +
        		"IHRoYXQgb25lIGhhcyBldmVyIGJlbGlldmVkIHRoZSBjb250cmFyeS4gVGhpcyBkZW1hbmRzIGEgY29udGludW91cyBhbHRlcmF" +
        		"0aW9uIG9mIHRoZSBwYXN0LCBtYWRlIHBvc3NpYmxlIGJ5IHRoZSBzeXN0ZW0gb2YgdGhvdWdodCB3aGljaCByZWFsbHkgZW1icm" +
        		"FjZXMgYWxsIHRoZSByZXN0LCBhbmQgd2hpY2ggaXMga25vd24gaW4gTmV3c3BlYWsgYXMgRE9VQkxFVEhJTksuCgpUaGUgYWx0Z" +
        		"XJhdGlvbiBvZiB0aGUgcGFzdCBpcyBuZWNlc3NhcnkgZm9yIHR3byByZWFzb25zLCBvbmUgb2Ygd2hpY2ggaXMgc3Vic2lkaWFy" +
        		"eSBhbmQsIHNvIHRvIHNwZWFrLCBwcmVjYXV0aW9uYXJ5LiBUaGUgc3Vic2lkaWFyeSByZWFzb24gaXMgdGhhdCB0aGUgUGFydHk" +
        		"gbWVtYmVyLCBsaWtlIHRoZSBwcm9sZXRhcmlhbiwgdG9sZXJhdGVzIHByZXNlbnQtZGF5IGNvbmRpdGlvbnMgcGFydGx5IGJlY2" +
        		"F1c2UgaGUgaGFzIG5vIHN0YW5kYXJkcyBvZiBjb21wYXJpc29uLiBIZSBtdXN0IGJlIGN1dCBvZmYgZnJvbSB0aGUgcGFzdCwga" +
        		"nVzdCBhcyBoZSBtdXN0IGJlIGN1dCBvZmYgZnJvbSBmb3JlaWduIGNvdW50cmllcywgYmVjYXVzZSBpdCBpcyBuZWNlc3Nhcnkg" +
        		"Zm9yIGhpbSB0byBiZWxpZXZlIHRoYXQgaGUgaXMgYmV0dGVyIG9mZiB0aGFuIGhpcyBhbmNlc3RvcnMgYW5kIHRoYXQgdGhlIGF" +
        		"2ZXJhZ2UgbGV2ZWwgb2YgbWF0ZXJpYWwgY29tZm9ydCBpcyBjb25zdGFudGx5IHJpc2luZy4gQnV0IGJ5IGZhciB0aGUgbW9yZS" +
        		"BpbXBvcnRhbnQgcmVhc29uIGZvciB0aGUgcmVhZGp1c3RtZW50IG9mIHRoZSBwYXN0IGlzIHRoZSBuZWVkIHRvIHNhZmVndWFyZ" +
        		"CB0aGUgaW5mYWxsaWJpbGl0eSBvZiB0aGUgUGFydHkuIEl0IGlzIG5vdCBtZXJlbHkgdGhhdCBzcGVlY2hlcywgc3RhdGlzdGlj" +
        		"cywgYW5kIHJlY29yZHMgb2YgZXZlcnkga2luZCBtdXN0IGJlIGNvbnN0YW50bHkgYnJvdWdodCB1cCB0byBkYXRlIGluIG9yZGV" +
        		"yIHRvIHNob3cgdGhhdCB0aGUgcHJlZGljdGlvbnMgb2YgdGhlIFBhcnR5IHdlcmUgaW4gYWxsIGNhc2VzIHJpZ2h0LiBJdCBpcy" +
        		"BhbHNvIHRoYXQgbm8gY2hhbmdlIGluIGRvY3RyaW5lIG9yIGluIHBvbGl0aWNhbCBhbGlnbm1lbnQgY2FuIGV2ZXIgYmUgYWRta" +
        		"XR0ZWQuIEZvciB0byBjaGFuZ2Ugb25lJ3MgbWluZCwgb3IgZXZlbiBvbmUncyBwb2xpY3ksIGlzIGEgY29uZmVzc2lvbiBvZiB3" +
        		"ZWFrbmVzcy4gSWYsIGZvciBleGFtcGxlLCBFdXJhc2lhIG9yIEVhc3Rhc2lhICh3aGljaGV2ZXIgaXQgbWF5IGJlKSBpcyB0aGU" +
        		"gZW5lbXkgdG9kYXksIHRoZW4gdGhhdCBjb3VudHJ5IG11c3QgYWx3YXlzIGhhdmUgYmVlbiB0aGUgZW5lbXkuIEFuZCBpZiB0aG" +
        		"UgZmFjdHMgc2F5IG90aGVyd2lzZSB0aGVuIHRoZSBmYWN0cyBtdXN0IGJlIGFsdGVyZWQuIFRodXMgaGlzdG9yeSBpcyBjb250a" +
        		"W51b3VzbHkgcmV3cml0dGVuLiBUaGlzIGRheS10by1kYXkgZmFsc2lmaWNhdGlvbiBvZiB0aGUgcGFzdCwgY2FycmllZCBvdXQg" +
        		"YnkgdGhlIE1pbmlzdHJ5IG9mIFRydXRoLCBpcyBhcyBuZWNlc3NhcnkgdG8gdGhlIHN0YWJpbGl0eSBvZiB0aGUgcmVnaW1lIGF" +
        		"zIHRoZSB3b3JrIG9mIHJlcHJlc3Npb24gYW5kIGVzcGlvbmFnZSBjYXJyaWVkIG91dCBieSB0aGUgTWluaXN0cnkgb2YgTG92ZS" +
        		"4KClRoZSBtdXRhYmlsaXR5IG9mIHRoZSBwYXN0IGlzIHRoZSBjZW50cmFsIHRlbmV0IG9mIEluZ3NvYy4gUGFzdCBldmVudHMsI" +
        		"Gl0IGlzIGFyZ3VlZCwgaGF2ZSBubyBvYmplY3RpdmUgZXhpc3RlbmNlLCBidXQgc3Vydml2ZSBvbmx5IGluIHdyaXR0ZW4gcmVj" +
        		"b3JkcyBhbmQgaW4gaHVtYW4gbWVtb3JpZXMuIFRoZSBwYXN0IGlzIHdoYXRldmVyIHRoZSByZWNvcmRzIGFuZCB0aGUgbWVtb3J" +
        		"pZXMgYWdyZWUgdXBvbi4gQW5kIHNpbmNlIHRoZSBQYXJ0eSBpcyBpbiBmdWxsIGNvbnRyb2wgb2YgYWxsIHJlY29yZHMgYW5kIG" +
        		"luIGVxdWFsbHkgZnVsbCBjb250cm9sIG9mIHRoZSBtaW5kcyBvZiBpdHMgbWVtYmVycywgaXQgZm9sbG93cyB0aGF0IHRoZSBwY" +
        		"XN0IGlzIHdoYXRldmVyIHRoZSBQYXJ0eSBjaG9vc2VzIHRvIG1ha2UgaXQuIEl0IGFsc28gZm9sbG93cyB0aGF0IHRob3VnaCB0" +
        		"aGUgcGFzdCBpcyBhbHRlcmFibGUsIGl0IG5ldmVyIGhhcyBiZWVuIGFsdGVyZWQgaW4gYW55IHNwZWNpZmljIGluc3RhbmNlLiB" +
        		"Gb3Igd2hlbiBpdCBoYXMgYmVlbiByZWNyZWF0ZWQgaW4gd2hhdGV2ZXIgc2hhcGUgaXMgbmVlZGVkIGF0IHRoZSBtb21lbnQsIH" +
        		"RoZW4gdGhpcyBuZXcgdmVyc2lvbiBJUyB0aGUgcGFzdCwgYW5kIG5vIGRpZmZlcmVudCBwYXN0IGNhbiBldmVyIGhhdmUgZXhpc" +
        		"3RlZC4gVGhpcyBob2xkcyBnb29kIGV2ZW4gd2hlbiwgYXMgb2Z0ZW4gaGFwcGVucywgdGhlIHNhbWUgZXZlbnQgaGFzIHRvIGJl" +
        		"IGFsdGVyZWQgb3V0IG9mIHJlY29nbml0aW9uIHNldmVyYWwgdGltZXMgaW4gdGhlIGNvdXJzZSBvZiBhIHllYXIuIEF0IGFsbCB" +
        		"0aW1lcyB0aGUgUGFydHkgaXMgaW4gcG9zc2Vzc2lvbiBvZiBhYnNvbHV0ZSB0cnV0aCwgYW5kIGNsZWFybHkgdGhlIGFic29sdX" +
        		"RlIGNhbiBuZXZlciBoYXZlIGJlZW4gZGlmZmVyZW50IGZyb20gd2hhdCBpdCBpcyBub3cuIEl0IHdpbGwgYmUgc2VlbiB0aGF0I" +
        		"HRoZSBjb250cm9sIG9mIHRoZSBwYXN0IGRlcGVuZHMgYWJvdmUgYWxsIG9uIHRoZSB0cmFpbmluZyBvZiBtZW1vcnkuIFRvIG1h" +
        		"a2Ugc3VyZSB0aGF0IGFsbCB3cml0dGVuIHJlY29yZHMgYWdyZWUgd2l0aCB0aGUgb3J0aG9kb3h5IG9mIHRoZSBtb21lbnQgaXM" +
        		"gbWVyZWx5IGEgbWVjaGFuaWNhbCBhY3QuIEJ1dCBpdCBpcyBhbHNvIG5lY2Vzc2FyeSB0byBSRU1FTUJFUiB0aGF0IGV2ZW50cy" +
        		"BoYXBwZW5lZCBpbiB0aGUgZGVzaXJlZCBtYW5uZXIuIEFuZCBpZiBpdCBpcyBuZWNlc3NhcnkgdG8gcmVhcnJhbmdlIG9uZSdzI" +
        		"G1lbW9yaWVzIG9yIHRvIHRhbXBlciB3aXRoIHdyaXR0ZW4gcmVjb3JkcywgdGhlbiBpdCBpcyBuZWNlc3NhcnkgdG8gRk9SR0VU" +
        		"IHRoYXQgb25lIGhhcyBkb25lIHNvLiBUaGUgdHJpY2sgb2YgZG9pbmcgdGhpcyBjYW4gYmUgbGVhcm5lZCBsaWtlIGFueSBvdGh" +
        		"lciBtZW50YWwgdGVjaG5pcXVlLiBJdCBpcyBsZWFybmVkIGJ5IHRoZSBtYWpvcml0eSBvZiBQYXJ0eSBtZW1iZXJzLCBhbmQgY2" +
        		"VydGFpbmx5IGJ5IGFsbCB3aG8gYXJlIGludGVsbGlnZW50IGFzIHdlbGwgYXMgb3J0aG9kb3guIEluIE9sZHNwZWFrIGl0IGlzI" +
        		"GNhbGxlZCwgcXVpdGUgZnJhbmtseSwgJ3JlYWxpdHkgY29udHJvbCcuIEluIE5ld3NwZWFrIGl0IGlzIGNhbGxlZCBET1VCTEVU" +
        		"SElOSywgdGhvdWdoIERPVUJMRVRISU5LIGNvbXByaXNlcyBtdWNoIGVsc2UgYXMgd2VsbC4KCkRPVUJMRVRISU5LIG1lYW5zIHR" +
        		"oZSBwb3dlciBvZiBob2xkaW5nIHR3byBjb250cmFkaWN0b3J5IGJlbGllZnMgaW4gb25lJ3MgbWluZCBzaW11bHRhbmVvdXNseS" +
        		"wgYW5kIGFjY2VwdGluZyBib3RoIG9mIHRoZW0uIFRoZSBQYXJ0eSBpbnRlbGxlY3R1YWwga25vd3MgaW4gd2hpY2ggZGlyZWN0a" +
        		"W9uIGhpcyBtZW1vcmllcyBtdXN0IGJlIGFsdGVyZWQ7IGhlIHRoZXJlZm9yZSBrbm93cyB0aGF0IGhlIGlzIHBsYXlpbmcgdHJp" +
        		"Y2tzIHdpdGggcmVhbGl0eTsgYnV0IGJ5IHRoZSBleGVyY2lzZSBvZiBET1VCTEVUSElOSyBoZSBhbHNvIHNhdGlzZmllcyBoaW1" +
        		"zZWxmIHRoYXQgcmVhbGl0eSBpcyBub3QgdmlvbGF0ZWQuIFRoZSBwcm9jZXNzIGhhcyB0byBiZSBjb25zY2lvdXMsIG9yIGl0IH" +
        		"dvdWxkIG5vdCBiZSBjYXJyaWVkIG91dCB3aXRoIHN1ZmZpY2llbnQgcHJlY2lzaW9uLCBidXQgaXQgYWxzbyBoYXMgdG8gYmUgd" +
        		"W5jb25zY2lvdXMsIG9yIGl0IHdvdWxkIGJyaW5nIHdpdGggaXQgYSBmZWVsaW5nIG9mIGZhbHNpdHkgYW5kIGhlbmNlIG9mIGd1" +
        		"aWx0LiBET1VCTEVUSElOSyBsaWVzIGF0IHRoZSB2ZXJ5IGhlYXJ0IG9mIEluZ3NvYywgc2luY2UgdGhlIGVzc2VudGlhbCBhY3Q" +
        		"gb2YgdGhlIFBhcnR5IGlzIHRvIHVzZSBjb25zY2lvdXMgZGVjZXB0aW9uIHdoaWxlIHJldGFpbmluZyB0aGUgZmlybW5lc3Mgb2" +
        		"YgcHVycG9zZSB0aGF0IGdvZXMgd2l0aCBjb21wbGV0ZSBob25lc3R5LiBUbyB0ZWxsIGRlbGliZXJhdGUgbGllcyB3aGlsZSBnZ" +
        		"W51aW5lbHkgYmVsaWV2aW5nIGluIHRoZW0sIHRvIGZvcmdldCBhbnkgZmFjdCB0aGF0IGhhcyBiZWNvbWUgaW5jb252ZW5pZW50" +
        		"LCBhbmQgdGhlbiwgd2hlbiBpdCBiZWNvbWVzIG5lY2Vzc2FyeSBhZ2FpbiwgdG8gZHJhdyBpdCBiYWNrIGZyb20gb2JsaXZpb24" +
        		"gZm9yIGp1c3Qgc28gbG9uZyBhcyBpdCBpcyBuZWVkZWQsIHRvIGRlbnkgdGhlIGV4aXN0ZW5jZSBvZiBvYmplY3RpdmUgcmVhbG" +
        		"l0eSBhbmQgYWxsIHRoZSB3aGlsZSB0byB0YWtlIGFjY291bnQgb2YgdGhlIHJlYWxpdHkgd2hpY2ggb25lIGRlbmllcy0tYWxsI" +
        		"HRoaXMgaXMgaW5kaXNwZW5zYWJseSBuZWNlc3NhcnkuIEV2ZW4gaW4gdXNpbmcgdGhlIHdvcmQgRE9VQkxFVEhJTksgaXQgaXMg" +
        		"bmVjZXNzYXJ5IHRvIGV4ZXJjaXNlIERPVUJMRVRISU5LLiBGb3IgYnkgdXNpbmcgdGhlIHdvcmQgb25lIGFkbWl0cyB0aGF0IG9" +
        		"uZSBpcyB0YW1wZXJpbmcgd2l0aCByZWFsaXR5OyBieSBhIGZyZXNoIGFjdCBvZiBET1VCTEVUSElOSyBvbmUgZXJhc2VzIHRoaX" +
        		"Mga25vd2xlZGdlOyBhbmQgc28gb24gaW5kZWZpbml0ZWx5LCB3aXRoIHRoZSBsaWUgYWx3YXlzIG9uZSBsZWFwIGFoZWFkIG9mI" +
        		"HRoZSB0cnV0aC4gVWx0aW1hdGVseSBpdCBpcyBieSBtZWFucyBvZiBET1VCTEVUSElOSyB0aGF0IHRoZSBQYXJ0eSBoYXMgYmVl" +
        		"biBhYmxlLS1hbmQgbWF5LCBmb3IgYWxsIHdlIGtub3csIGNvbnRpbnVlIHRvIGJlIGFibGUgZm9yIHRob3VzYW5kcyBvZiB5ZWF" +
        		"ycy0tdG8gYXJyZXN0IHRoZSBjb3Vyc2Ugb2YgaGlzdG9yeS4KCkFsbCBwYXN0IG9saWdhcmNoaWVzIGhhdmUgZmFsbGVuIGZyb2" +
        		"0gcG93ZXIgZWl0aGVyIGJlY2F1c2UgdGhleSBvc3NpZmllZCBvciBiZWNhdXNlIHRoZXkgZ3JldyBzb2Z0LiBFaXRoZXIgdGhle" +
        		"SBiZWNhbWUgc3R1cGlkIGFuZCBhcnJvZ2FudCwgZmFpbGVkIHRvIGFkanVzdCB0aGVtc2VsdmVzIHRvIGNoYW5naW5nIGNpcmN1" +
        		"bXN0YW5jZXMsIGFuZCB3ZXJlIG92ZXJ0aHJvd247IG9yIHRoZXkgYmVjYW1lIGxpYmVyYWwgYW5kIGNvd2FyZGx5LCBtYWRlIGN" +
        		"vbmNlc3Npb25zIHdoZW4gdGhleSBzaG91bGQgaGF2ZSB1c2VkIGZvcmNlLCBhbmQgb25jZSBhZ2FpbiB3ZXJlIG92ZXJ0aHJvd2" +
        		"4uIFRoZXkgZmVsbCwgdGhhdCBpcyB0byBzYXksIGVpdGhlciB0aHJvdWdoIGNvbnNjaW91c25lc3Mgb3IgdGhyb3VnaCB1bmNvb" +
        		"nNjaW91c25lc3MuIEl0IGlzIHRoZSBhY2hpZXZlbWVudCBvZiB0aGUgUGFydHkgdG8gaGF2ZSBwcm9kdWNlZCBhIHN5c3RlbSBv" +
        		"ZiB0aG91Z2h0IGluIHdoaWNoIGJvdGggY29uZGl0aW9ucyBjYW4gZXhpc3Qgc2ltdWx0YW5lb3VzbHkuIEFuZCB1cG9uIG5vIG9" +
        		"0aGVyIGludGVsbGVjdHVhbCBiYXNpcyBjb3VsZCB0aGUgZG9taW5pb24gb2YgdGhlIFBhcnR5IGJlIG1hZGUgcGVybWFuZW50Li" +
        		"BJZiBvbmUgaXMgdG8gcnVsZSwgYW5kIHRvIGNvbnRpbnVlIHJ1bGluZywgb25lIG11c3QgYmUgYWJsZSB0byBkaXNsb2NhdGUgd" +
        		"GhlIHNlbnNlIG9mIHJlYWxpdHkuIEZvciB0aGUgc2VjcmV0IG9mIHJ1bGVyc2hpcCBpcyB0byBjb21iaW5lIGEgYmVsaWVmIGlu" +
        		"IG9uZSdzIG93biBpbmZhbGxpYmlsaXR5IHdpdGggdGhlIFBvd2VyIHRvIGxlYXJuIGZyb20gcGFzdCBtaXN0YWtlcy4KCkl0IG5" +
        		"lZWQgaGFyZGx5IGJlIHNhaWQgdGhhdCB0aGUgc3VidGxlc3QgcHJhY3RpdGlvbmVycyBvZiBET1VCTEVUSElOSyBhcmUgdGhvc2" +
        		"Ugd2hvIGludmVudGVkIERPVUJMRVRISU5LIGFuZCBrbm93IHRoYXQgaXQgaXMgYSB2YXN0IHN5c3RlbSBvZiBtZW50YWwgY2hlY" +
        		"XRpbmcuIEluIG91ciBzb2NpZXR5LCB0aG9zZSB3aG8gaGF2ZSB0aGUgYmVzdCBrbm93bGVkZ2Ugb2Ygd2hhdCBpcyBoYXBwZW5p" +
        		"bmcgYXJlIGFsc28gdGhvc2Ugd2hvIGFyZSBmdXJ0aGVzdCBmcm9tIHNlZWluZyB0aGUgd29ybGQgYXMgaXQgaXMuIEluIGdlbmV" +
        		"yYWwsIHRoZSBncmVhdGVyIHRoZSB1bmRlcnN0YW5kaW5nLCB0aGUgZ3JlYXRlciB0aGUgZGVsdXNpb247IHRoZSBtb3JlIGludG" +
        		"VsbGlnZW50LCB0aGUgbGVzcyBzYW5lLiBPbmUgY2xlYXIgaWxsdXN0cmF0aW9uIG9mIHRoaXMgaXMgdGhlIGZhY3QgdGhhdCB3Y" +
        		"XIgaHlzdGVyaWEgaW5jcmVhc2VzIGluIGludGVuc2l0eSBhcyBvbmUgcmlzZXMgaW4gdGhlIHNvY2lhbCBzY2FsZS4gVGhvc2Ug" +
        		"d2hvc2UgYXR0aXR1ZGUgdG93YXJkcyB0aGUgd2FyIGlzIG1vc3QgbmVhcmx5IHJhdGlvbmFsIGFyZSB0aGUgc3ViamVjdCBwZW9" +
        		"wbGVzIG9mIHRoZSBkaXNwdXRlZCB0ZXJyaXRvcmllcy4gVG8gdGhlc2UgcGVvcGxlIHRoZSB3YXIgaXMgc2ltcGx5IGEgY29udG" +
        		"ludW91cyBjYWxhbWl0eSB3aGljaCBzd2VlcHMgdG8gYW5kIGZybyBvdmVyIHRoZWlyIGJvZGllcyBsaWtlIGEgdGlkYWwgd2F2Z" +
        		"S4gV2hpY2ggc2lkZSBpcyB3aW5uaW5nIGlzIGEgbWF0dGVyIG9mIGNvbXBsZXRlIGluZGlmZmVyZW5jZSB0byB0aGVtLiBUaGV5" +
        		"IGFyZSBhd2FyZSB0aGF0IGEgY2hhbmdlIG9mIG92ZXJsb3Jkc2hpcCBtZWFucyBzaW1wbHkgdGhhdCB0aGV5IHdpbGwgYmUgZG9" +
        		"pbmcgdGhlIHNhbWUgd29yayBhcyBiZWZvcmUgZm9yIG5ldyBtYXN0ZXJzIHdobyB0cmVhdCB0aGVtIGluIHRoZSBzYW1lIG1hbm" +
        		"5lciBhcyB0aGUgb2xkIG9uZXMuIFRoZSBzbGlnaHRseSBtb3JlIGZhdm91cmVkIHdvcmtlcnMgd2hvbSB3ZSBjYWxsICd0aGUgc" +
        		"HJvbGVzJyBhcmUgb25seSBpbnRlcm1pdHRlbnRseSBjb25zY2lvdXMgb2YgdGhlIHdhci4gV2hlbiBpdCBpcyBuZWNlc3Nhcnkg" +
        		"dGhleSBjYW4gYmUgcHJvZGRlZCBpbnRvIGZyZW56aWVzIG9mIGZlYXIgYW5kIGhhdHJlZCwgYnV0IHdoZW4gbGVmdCB0byB0aGV" +
        		"tc2VsdmVzIHRoZXkgYXJlIGNhcGFibGUgb2YgZm9yZ2V0dGluZyBmb3IgbG9uZyBwZXJpb2RzIHRoYXQgdGhlIHdhciBpcyBoYX" +
        		"BwZW5pbmcuIEl0IGlzIGluIHRoZSByYW5rcyBvZiB0aGUgUGFydHksIGFuZCBhYm92ZSBhbGwgb2YgdGhlIElubmVyIFBhcnR5L" +
        		"CB0aGF0IHRoZSB0cnVlIHdhciBlbnRodXNpYXNtIGlzIGZvdW5kLiBXb3JsZC1jb25xdWVzdCBpcyBiZWxpZXZlZCBpbiBtb3N0" +
        		"IGZpcm1seSBieSB0aG9zZSB3aG8ga25vdyBpdCB0byBiZSBpbXBvc3NpYmxlLiBUaGlzIHBlY3VsaWFyIGxpbmtpbmctdG9nZXR" +
        		"oZXIgb2Ygb3Bwb3NpdGVzLS1rbm93bGVkZ2Ugd2l0aCBpZ25vcmFuY2UsIGN5bmljaXNtIHdpdGggZmFuYXRpY2lzbS0taXMgb2" +
        		"5lIG9mIHRoZSBjaGllZiBkaXN0aW5ndWlzaGluZyBtYXJrcyBvZiBPY2VhbmljIHNvY2lldHkuIFRoZSBvZmZpY2lhbCBpZGVvb" +
        		"G9neSBhYm91bmRzIHdpdGggY29udHJhZGljdGlvbnMgZXZlbiB3aGVuIHRoZXJlIGlzIG5vIHByYWN0aWNhbCByZWFzb24gZm9y" +
        		"IHRoZW0uIFRodXMsIHRoZSBQYXJ0eSByZWplY3RzIGFuZCB2aWxpZmllcyBldmVyeSBwcmluY2lwbGUgZm9yIHdoaWNoIHRoZSB" +
        		"Tb2NpYWxpc3QgbW92ZW1lbnQgb3JpZ2luYWxseSBzdG9vZCwgYW5kIGl0IGNob29zZXMgdG8gZG8gdGhpcyBpbiB0aGUgbmFtZS" +
        		"BvZiBTb2NpYWxpc20uIEl0IHByZWFjaGVzIGEgY29udGVtcHQgZm9yIHRoZSB3b3JraW5nIGNsYXNzIHVuZXhhbXBsZWQgZm9yI" +
        		"GNlbnR1cmllcyBwYXN0LCBhbmQgaXQgZHJlc3NlcyBpdHMgbWVtYmVycyBpbiBhIHVuaWZvcm0gd2hpY2ggd2FzIGF0IG9uZSB0" +
        		"aW1lIHBlY3VsaWFyIHRvIG1hbnVhbCB3b3JrZXJzIGFuZCB3YXMgYWRvcHRlZCBmb3IgdGhhdCByZWFzb24uIEl0IHN5c3RlbWF" +
        		"0aWNhbGx5IHVuZGVybWluZXMgdGhlIHNvbGlkYXJpdHkgb2YgdGhlIGZhbWlseSwgYW5kIGl0IGNhbGxzIGl0cyBsZWFkZXIgYn" +
        		"kgYSBuYW1lIHdoaWNoIGlzIGEgZGlyZWN0IGFwcGVhbCB0byB0aGUgc2VudGltZW50IG9mIGZhbWlseSBsb3lhbHR5LiBFdmVuI" +
        		"HRoZSBuYW1lcyBvZiB0aGUgZm91ciBNaW5pc3RyaWVzIGJ5IHdoaWNoIHdlIGFyZSBnb3Zlcm5lZCBleGhpYml0IGEgc29ydCBv" +
        		"ZiBpbXB1ZGVuY2UgaW4gdGhlaXIgZGVsaWJlcmF0ZSByZXZlcnNhbCBvZiB0aGUgZmFjdHMuIFRoZSBNaW5pc3RyeSBvZiBQZWF" +
        		"jZSBjb25jZXJucyBpdHNlbGYgd2l0aCB3YXIsIHRoZSBNaW5pc3RyeSBvZiBUcnV0aCB3aXRoIGxpZXMsIHRoZSBNaW5pc3RyeS" +
        		"BvZiBMb3ZlIHdpdGggdG9ydHVyZSBhbmQgdGhlIE1pbmlzdHJ5IG9mIFBsZW50eSB3aXRoIHN0YXJ2YXRpb24uIFRoZXNlIGNvb" +
        		"nRyYWRpY3Rpb25zIGFyZSBub3QgYWNjaWRlbnRhbCwgbm9yIGRvIHRoZXkgcmVzdWx0IGZyb20gb3JkaW5hcnkgaHlwb2NyaXN5" +
        		"OyB0aGV5IGFyZSBkZWxpYmVyYXRlIGV4ZXJjaXNlcyBpbiBET1VCTEVUSElOSy4gRm9yIGl0IGlzIG9ubHkgYnkgcmVjb25jaWx" +
        		"pbmcgY29udHJhZGljdGlvbnMgdGhhdCBwb3dlciBjYW4gYmUgcmV0YWluZWQgaW5kZWZpbml0ZWx5LiBJbiBubyBvdGhlciB3YX" +
        		"kgY291bGQgdGhlIGFuY2llbnQgY3ljbGUgYmUgYnJva2VuLiBJZiBodW1hbiBlcXVhbGl0eSBpcyB0byBiZSBmb3IgZXZlciBhd" +
        		"mVydGVkLS1pZiB0aGUgSGlnaCwgYXMgd2UgaGF2ZSBjYWxsZWQgdGhlbSwgYXJlIHRvIGtlZXAgdGhlaXIgcGxhY2VzIHBlcm1h" +
        		"bmVudGx5LS10aGVuIHRoZSBwcmV2YWlsaW5nIG1lbnRhbCBjb25kaXRpb24gbXVzdCBiZSBjb250cm9sbGVkIGluc2FuaXR5Lgo" +
        		"KQnV0IHRoZXJlIGlzIG9uZSBxdWVzdGlvbiB3aGljaCB1bnRpbCB0aGlzIG1vbWVudCB3ZSBoYXZlIGFsbW9zdCBpZ25vcmVkLi" +
        		"BJdCBpczsgV0hZIHNob3VsZCBodW1hbiBlcXVhbGl0eSBiZSBhdmVydGVkPyBTdXBwb3NpbmcgdGhhdCB0aGUgbWVjaGFuaWNzI" +
        		"G9mIHRoZSBwcm9jZXNzIGhhdmUgYmVlbiByaWdodGx5IGRlc2NyaWJlZCwgd2hhdCBpcyB0aGUgbW90aXZlIGZvciB0aGlzIGh1" +
        		"Z2UsIGFjY3VyYXRlbHkgcGxhbm5lZCBlZmZvcnQgdG8gZnJlZXplIGhpc3RvcnkgYXQgYSBwYXJ0aWN1bGFyIG1vbWVudCBvZiB" +
        		"0aW1lPwoKSGVyZSB3ZSByZWFjaCB0aGUgY2VudHJhbCBzZWNyZXQuIEFzIHdlIGhhdmUgc2Vlbi4gdGhlIG15c3RpcXVlIG9mIH" +
        		"RoZSBQYXJ0eSwgYW5kIGFib3ZlIGFsbCBvZiB0aGUgSW5uZXIgUGFydHksIGRlcGVuZHMgdXBvbiBET1VCTEVUSElOSyBCdXQgZ" +
        		"GVlcGVyIHRoYW4gdGhpcyBsaWVzIHRoZSBvcmlnaW5hbCBtb3RpdmUsIHRoZSBuZXZlci1xdWVzdGlvbmVkIGluc3RpbmN0IHRo" +
        		"YXQgZmlyc3QgbGVkIHRvIHRoZSBzZWl6dXJlIG9mIHBvd2VyIGFuZCBicm91Z2h0IERPVUJMRVRISU5LLCB0aGUgVGhvdWdodCB" +
        		"Qb2xpY2UsIGNvbnRpbnVvdXMgd2FyZmFyZSwgYW5kIGFsbCB0aGUgb3RoZXIgbmVjZXNzYXJ5IHBhcmFwaGVybmFsaWEgaW50by" +
        		"BleGlzdGVuY2UgYWZ0ZXJ3YXJkcy4gVGhpcyBtb3RpdmUgcmVhbGx5IGNvbnNpc3RzLi4uCg==</batchFile>" +
        		"    <fileName>test/resources/Control/instructions.txt</fileName>" +
        		"</batchRequest>\n";
        
        BatchRequestType req = BatchRequestType.Factory.parse(StAXUtils.createXMLStreamReader(new ByteArrayInputStream(str.getBytes())));
        
        assertEquals("abc123", req.getIdentityToken());
        assertEquals("mdiponio", req.getRequestor());
        assertEquals("test/resources/Control/instructions.txt", req.getFileName());
    }
    
    @Test
    public void testSerialize() throws Exception
    {
        BatchRequestType batch = new BatchRequestType();
        batch.setIdentityToken("abc123");
        batch.setRequestor("mdiponio");
        FileDataSource source = new FileDataSource(new File("test/resources/Control/instructions.txt"));
        DataHandler hand = new DataHandler(source);
        batch.setBatchFile(hand);
        batch.setFileName("test/resources/Control/instructions.txt");
        
        OMElement ele = batch.getOMElement(new QName("", "batchRequest"), OMAbstractFactory.getOMFactory());
        String str = ele.toStringWithConsume();
        assertTrue(str.contains("<fileName>test/resources/Control/instructions.txt</fileName>"));
        assertTrue(str.contains("<identityToken>abc123</identityToken>"));
        assertTrue(str.contains("<requestor>mdiponio</requestor>"));
    }
}
