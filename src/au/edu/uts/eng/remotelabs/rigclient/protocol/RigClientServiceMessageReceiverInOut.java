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
 * @date 4th December 2009
 *
 * Changelog:
 * - 04/12/2009 - mdiponio - Initial file creation.
 */

/**
 * RigClientServiceMessageReceiverInOut.java This file was auto-generated from
 * WSDL by the Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:39
 * LKT)
 */
package au.edu.uts.eng.remotelabs.rigclient.protocol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.receivers.AbstractInOutMessageReceiver;

import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse;

/**
 * RigClientServiceMessageReceiverInOut message receiver.
 */
@SuppressWarnings("unchecked")
public class RigClientServiceMessageReceiverInOut extends AbstractInOutMessageReceiver
{
    private Object fromOM(OMElement param, Class type, Map extraNamespaces) throws AxisFault
    {

        try
        {

            if (PerformPrimitiveControl.class.equals(type))
                return PerformPrimitiveControl.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (PerformPrimitiveControlResponse.class.equals(type))
                return PerformPrimitiveControlResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (GetBatchControlStatus.class.equals(type))
                return GetBatchControlStatus.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (GetBatchControlStatusResponse.class.equals(type))
                return GetBatchControlStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (GetAttribute.class.equals(type))
                return GetAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (GetAttributeResponse.class.equals(type))
                return GetAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (SlaveRelease.class.equals(type))
                return SlaveRelease.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (SlaveReleaseResponse.class.equals(type))
                return SlaveReleaseResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (Release.class.equals(type)) return Release.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (ReleaseResponse.class.equals(type))
                return ReleaseResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (Notify.class.equals(type)) return Notify.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (NotifyResponse.class.equals(type))
                return NotifyResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (SetTestInterval.class.equals(type))
                return SetTestInterval.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (SetTestIntervalResponse.class.equals(type))
                return SetTestIntervalResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (SetMaintenance.class.equals(type))
                return SetMaintenance.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (SetMaintenanceResponse.class.equals(type))
                return SetMaintenanceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (PerformBatchControl.class.equals(type))
                return PerformBatchControl.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (PerformBatchControlResponse.class.equals(type))
                return PerformBatchControlResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (GetStatus.class.equals(type)) return GetStatus.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (GetStatusResponse.class.equals(type))
                return GetStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (Allocate.class.equals(type)) return Allocate.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (AllocateResponse.class.equals(type))
                return AllocateResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (SlaveAllocate.class.equals(type))
                return SlaveAllocate.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (SlaveAllocateResponse.class.equals(type))
                return SlaveAllocateResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (AbortBatchControl.class.equals(type))
                return AbortBatchControl.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (AbortBatchControlResponse.class.equals(type))
                return AbortBatchControlResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

        }
        catch (java.lang.Exception e)
        {
            throw AxisFault.makeFault(e);
        }
        return null;
    }

    /**
     * A utility method that copies the name spaces from the SOAPEnvelope.
     */
    private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env)
    {
        Map returnMap = new HashMap();
        Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext())
        {
            OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
        }
        return returnMap;
    }

    @Override
    public void invokeBusinessLogic(MessageContext msgContext, MessageContext newMsgContext) throws AxisFault
    {
        try
        {
            // get the implementation class for the Web Service
            Object obj = this.getTheImplementationObject(msgContext);
            RigClientServiceSkeletonInterface skel = (RigClientServiceSkeletonInterface) obj;
            
            // Out Envelop
            org.apache.axiom.soap.SOAPEnvelope envelope = null;
            // Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
            if (op == null)
                throw new org.apache.axis2.AxisFault(
                        "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");

            java.lang.String methodName;
            if (op.getName() != null
                    && (methodName = org.apache.axis2.util.JavaUtils.xmlNameToJava(op.getName().getLocalPart())) != null)
            {

                if ("performPrimitiveControl".equals(methodName))
                {

                    PerformPrimitiveControlResponse performPrimitiveControlResponse27 = null;
                    PerformPrimitiveControl wrappedParam = (PerformPrimitiveControl) this.fromOM(msgContext
                            .getEnvelope().getBody().getFirstElement(), PerformPrimitiveControl.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    performPrimitiveControlResponse27 =

                    skel.performPrimitiveControl(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), performPrimitiveControlResponse27,
                            false);
                }
                else

                if ("getBatchControlStatus".equals(methodName))
                {

                    GetBatchControlStatusResponse getBatchControlStatusResponse29 = null;
                    GetBatchControlStatus wrappedParam = (GetBatchControlStatus) this.fromOM(msgContext.getEnvelope()
                            .getBody().getFirstElement(), GetBatchControlStatus.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getBatchControlStatusResponse29 =

                    skel.getBatchControlStatus(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), getBatchControlStatusResponse29, false);
                }
                else

                if ("getAttribute".equals(methodName))
                {

                    GetAttributeResponse getAttributeResponse31 = null;
                    GetAttribute wrappedParam = (GetAttribute) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), GetAttribute.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getAttributeResponse31 =

                    skel.getAttribute(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), getAttributeResponse31, false);
                }
                else

                if ("slaveRelease".equals(methodName))
                {

                    SlaveReleaseResponse slaveReleaseResponse33 = null;
                    SlaveRelease wrappedParam = (SlaveRelease) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), SlaveRelease.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    slaveReleaseResponse33 =

                    skel.slaveRelease(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), slaveReleaseResponse33, false);
                }
                else

                if ("release".equals(methodName))
                {

                    ReleaseResponse releaseResponse35 = null;
                    Release wrappedParam = (Release) this.fromOM(msgContext.getEnvelope().getBody().getFirstElement(),
                            Release.class, this.getEnvelopeNamespaces(msgContext.getEnvelope()));

                    releaseResponse35 =

                    skel.release(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), releaseResponse35, false);
                }
                else

                if ("notify".equals(methodName))
                {

                    NotifyResponse notifyResponse37 = null;
                    Notify wrappedParam = (Notify) this.fromOM(msgContext.getEnvelope().getBody().getFirstElement(),
                            Notify.class, this.getEnvelopeNamespaces(msgContext.getEnvelope()));

                    notifyResponse37 =

                    skel.notify(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), notifyResponse37, false);
                }
                else

                if ("setTestInterval".equals(methodName))
                {

                    SetTestIntervalResponse setTestIntervalResponse39 = null;
                    SetTestInterval wrappedParam = (SetTestInterval) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), SetTestInterval.class, this.getEnvelopeNamespaces(msgContext
                            .getEnvelope()));

                    setTestIntervalResponse39 = skel.setTestInterval(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), setTestIntervalResponse39, false);
                }
                else

                if ("setMaintenance".equals(methodName))
                {

                    SetMaintenanceResponse setMaintenanceResponse41 = null;
                    SetMaintenance wrappedParam = (SetMaintenance) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), SetMaintenance.class, this.getEnvelopeNamespaces(msgContext
                            .getEnvelope()));

                    setMaintenanceResponse41 =

                    skel.setMaintenance(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), setMaintenanceResponse41, false);
                }
                else

                if ("performBatchControl".equals(methodName))
                {

                    PerformBatchControlResponse performBatchControlResponse43 = null;
                    PerformBatchControl wrappedParam = (PerformBatchControl) this.fromOM(msgContext.getEnvelope()
                            .getBody().getFirstElement(), PerformBatchControl.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    performBatchControlResponse43 =

                    skel.performBatchControl(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), performBatchControlResponse43, false);
                }
                else

                if ("getStatus".equals(methodName))
                {

                    GetStatusResponse getStatusResponse45 = null;
                    GetStatus wrappedParam = (GetStatus) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), GetStatus.class, this.getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getStatusResponse45 =

                    skel.getStatus(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), getStatusResponse45, false);
                }
                else

                if ("allocate".equals(methodName))
                {

                    AllocateResponse allocateResponse47 = null;
                    Allocate wrappedParam = (Allocate) this.fromOM(
                            msgContext.getEnvelope().getBody().getFirstElement(), Allocate.class, this
                                    .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    allocateResponse47 =

                    skel.allocate(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), allocateResponse47, false);
                }
                else

                if ("slaveAllocate".equals(methodName))
                {

                    SlaveAllocateResponse slaveAllocateResponse49 = null;
                    SlaveAllocate wrappedParam = (SlaveAllocate) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), SlaveAllocate.class, this.getEnvelopeNamespaces(msgContext
                            .getEnvelope()));

                    slaveAllocateResponse49 =

                    skel.slaveAllocate(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), slaveAllocateResponse49, false);
                }
                else

                if ("abortBatchControl".equals(methodName))
                {

                    AbortBatchControlResponse abortBatchControlResponse51 = null;
                    AbortBatchControl wrappedParam = (AbortBatchControl) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), AbortBatchControl.class, this.getEnvelopeNamespaces(msgContext
                            .getEnvelope()));

                    abortBatchControlResponse51 =

                    skel.abortBatchControl(wrappedParam);

                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), abortBatchControlResponse51, false);

                }
                else
                    throw new java.lang.RuntimeException("method not found");

                newMsgContext.setEnvelope(envelope);
            }
        }
        catch (java.lang.Exception e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            AbortBatchControlResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(AbortBatchControlResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            AllocateResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(AllocateResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            GetAttributeResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(GetAttributeResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            GetBatchControlStatusResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(GetBatchControlStatusResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            GetStatusResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(GetStatusResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            NotifyResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(NotifyResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            PerformBatchControlResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(PerformBatchControlResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            PerformPrimitiveControlResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(PerformPrimitiveControlResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            ReleaseResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(ReleaseResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            SetMaintenanceResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(SetMaintenanceResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            SetTestIntervalResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(SetTestIntervalResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            SlaveAllocateResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(SlaveAllocateResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
            SlaveReleaseResponse param, boolean optimizeContent) throws org.apache.axis2.AxisFault
    {
        try
        {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(SlaveReleaseResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (org.apache.axis2.databinding.ADBException e)
        {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

}// end of class
