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
 * @date 1st December 2009
 *
 * Changelog:
 * - 01/12/2009 - mdiponio - Initial file creation.
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

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.receivers.AbstractInOutMessageReceiver;
import org.apache.axis2.util.JavaUtils;

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
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse;
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
public class RigClientServiceMessageReceiverInOut extends AbstractInOutMessageReceiver
{
    @Override
    public void invokeBusinessLogic(MessageContext msgContext, MessageContext newMsgContext) throws AxisFault
    {
        try
        {
            /* Get the implementation class for the Web Service. */
            final Object obj = this.getTheImplementationObject(msgContext);
            final RigClientServiceSkeletonInterface serviceImpl = (RigClientServiceSkeletonInterface) obj;

            /* Out envelope. */
            SOAPEnvelope envelope = null;

            final AxisOperation op = msgContext.getOperationContext().getAxisOperation();
            if (op == null)
            {
                throw new AxisFault("Operation is not located, if this is doclit style "
                        + "the SOAP-ACTION should specified  via the SOAP Action to use the RawXMLProvider");
            }

            String methodName;
            if (op.getName() != null && (methodName = JavaUtils.xmlNameToJava(op.getName().getLocalPart())) != null)
            {
                if ("performPrimitiveControl".equals(methodName))
                {
                    PerformPrimitiveControlResponse primResponse = null;
                    final PerformPrimitiveControl wrappedParam = (PerformPrimitiveControl) this.fromOM(msgContext
                            .getEnvelope().getBody().getFirstElement(), PerformPrimitiveControl.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    primResponse = serviceImpl.performPrimitiveControl(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), primResponse, false);
                }
                else if ("getBatchControlStatus".equals(methodName))
                {
                    GetBatchControlStatusResponse batchStatus = null;
                    final GetBatchControlStatus wrappedParam = (GetBatchControlStatus) this.fromOM(msgContext
                            .getEnvelope().getBody().getFirstElement(), GetBatchControlStatus.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));
                    
                    batchStatus = serviceImpl.getBatchControlStatus(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), batchStatus, false);
                }
                else if ("isActivityDetectable".equals(methodName))
                {
                    IsActivityDetectableResponse actDetectable = null;
                    final IsActivityDetectable wrappedParam = (IsActivityDetectable) this.fromOM(msgContext
                            .getEnvelope().getBody().getFirstElement(), IsActivityDetectable.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    actDetectable = serviceImpl.isActivityDetectable(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), actDetectable, false);
                }
                else if ("getAttribute".equals(methodName))
                {
                    GetAttributeResponse attrResponse = null;
                    final GetAttribute wrappedParam = (GetAttribute) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), GetAttribute.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    attrResponse = serviceImpl.getAttribute(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), attrResponse, false);
                }
                else if ("slaveRelease".equals(methodName))
                {
                    SlaveReleaseResponse slaveRelResponse = null;
                    final SlaveRelease wrappedParam = (SlaveRelease) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), SlaveRelease.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    slaveRelResponse = serviceImpl.slaveRelease(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), slaveRelResponse, false);
                }
                else if ("release".equals(methodName))
                {
                    ReleaseResponse relResponse = null;
                    final Release wrappedParam = (Release) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), Release.class, this.getEnvelopeNamespaces(msgContext.getEnvelope()));

                    relResponse = serviceImpl.release(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), relResponse, false);
                }
                else if ("notify".equals(methodName))
                {
                    NotifyResponse notfResponse = null;
                    final Notify wrappedParam = (Notify) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), Notify.class, this.getEnvelopeNamespaces(msgContext.getEnvelope()));

                    notfResponse = serviceImpl.notify(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), notfResponse, false);
                }
                else if ("setTestInterval".equals(methodName))
                {
                    SetTestIntervalResponse setIntResponse = null;
                    final SetTestInterval wrappedParam = (SetTestInterval) this.fromOM(msgContext.getEnvelope()
                            .getBody().getFirstElement(), SetTestInterval.class, this.getEnvelopeNamespaces(msgContext
                            .getEnvelope()));

                    setIntResponse = serviceImpl.setTestInterval(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), setIntResponse, false);
                }
                else if ("setMaintenance".equals(methodName))
                {
                    SetMaintenanceResponse maintenResponse = null;
                    final SetMaintenance wrappedParam = (SetMaintenance) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), SetMaintenance.class, this.getEnvelopeNamespaces(msgContext
                            .getEnvelope()));

                    maintenResponse = serviceImpl.setMaintenance(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), maintenResponse, false);
                }
                else if ("performBatchControl".equals(methodName))
                {
                    PerformBatchControlResponse batchResponse = null;
                    final PerformBatchControl wrappedParam = (PerformBatchControl) this.fromOM(msgContext.getEnvelope()
                            .getBody().getFirstElement(), PerformBatchControl.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    batchResponse = serviceImpl.performBatchControl(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), batchResponse, false);
                }
                else if ("getStatus".equals(methodName))
                {
                    GetStatusResponse statResponse = null;
                    final GetStatus wrappedParam = (GetStatus) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), GetStatus.class, this.getEnvelopeNamespaces(msgContext.getEnvelope()));

                    statResponse = serviceImpl.getStatus(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), statResponse, false);
                }
                else if ("allocate".equals(methodName))
                {
                    AllocateResponse allocResponse = null;
                    final Allocate wrappedParam = (Allocate) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), Allocate.class, this.getEnvelopeNamespaces(msgContext.getEnvelope()));

                    allocResponse = serviceImpl.allocate(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), allocResponse, false);
                }
                else if ("slaveAllocate".equals(methodName))
                {
                    SlaveAllocateResponse slaAllocResponse = null;
                    final SlaveAllocate wrappedParam = (SlaveAllocate) this.fromOM(msgContext.getEnvelope().getBody()
                            .getFirstElement(), SlaveAllocate.class, this.getEnvelopeNamespaces(msgContext
                            .getEnvelope()));

                    slaAllocResponse = serviceImpl.slaveAllocate(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), slaAllocResponse, false);
                }
                else if ("abortBatchControl".equals(methodName))
                {
                    AbortBatchControlResponse abortBatchResponse = null;
                    final AbortBatchControl wrappedParam = (AbortBatchControl) this.fromOM(msgContext.getEnvelope()
                            .getBody().getFirstElement(), AbortBatchControl.class, this
                            .getEnvelopeNamespaces(msgContext.getEnvelope()));

                    abortBatchResponse = serviceImpl.abortBatchControl(wrappedParam);
                    envelope = this.toEnvelope(this.getSOAPFactory(msgContext), abortBatchResponse, false);
                }
                else
                {
                    throw new RuntimeException("method not found");
                }
                newMsgContext.setEnvelope(envelope);
            }
        }
        catch (final Exception e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(PerformPrimitiveControl param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(PerformPrimitiveControl.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(PerformPrimitiveControlResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(PerformPrimitiveControlResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(GetBatchControlStatus param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(GetBatchControlStatus.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(GetBatchControlStatusResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(GetBatchControlStatusResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(IsActivityDetectable param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(IsActivityDetectable.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(IsActivityDetectableResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(IsActivityDetectableResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(GetAttribute param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(GetAttribute.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(GetAttributeResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(GetAttributeResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(SlaveRelease param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(SlaveRelease.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(SlaveReleaseResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(SlaveReleaseResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(Release param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(Release.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(ReleaseResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(ReleaseResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(Notify param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(Notify.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(NotifyResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(NotifyResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(SetTestInterval param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(SetTestInterval.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(SetTestIntervalResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(SetTestIntervalResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(SetMaintenance param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(SetMaintenance.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(SetMaintenanceResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(SetMaintenanceResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(PerformBatchControl param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(PerformBatchControl.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(PerformBatchControlResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(PerformBatchControlResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(GetStatus param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(GetStatus.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(GetStatusResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(GetStatusResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(Allocate param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(Allocate.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(AllocateResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(AllocateResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(SlaveAllocate param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(SlaveAllocate.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(SlaveAllocateResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(SlaveAllocateResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(AbortBatchControl param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(AbortBatchControl.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private OMElement toOM(AbortBatchControlResponse param, boolean optimizeContent) throws AxisFault
    {
        try
        {
            return param.getOMElement(AbortBatchControlResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, PerformPrimitiveControlResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(PerformPrimitiveControlResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private PerformPrimitiveControlResponse wrapperformPrimitiveControl()
    {
        final PerformPrimitiveControlResponse wrappedElement = new PerformPrimitiveControlResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, GetBatchControlStatusResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(GetBatchControlStatusResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private GetBatchControlStatusResponse wrapgetBatchControlStatus()
    {
        final GetBatchControlStatusResponse wrappedElement = new GetBatchControlStatusResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, IsActivityDetectableResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(IsActivityDetectableResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private IsActivityDetectableResponse wrapisActivityDetectable()
    {
        final IsActivityDetectableResponse wrappedElement = new IsActivityDetectableResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, GetAttributeResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(GetAttributeResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private GetAttributeResponse wrapgetAttribute()
    {
        final GetAttributeResponse wrappedElement = new GetAttributeResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, SlaveReleaseResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(SlaveReleaseResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private SlaveReleaseResponse wrapslaveRelease()
    {
        final SlaveReleaseResponse wrappedElement = new SlaveReleaseResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, ReleaseResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(ReleaseResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private ReleaseResponse wraprelease()
    {
        final ReleaseResponse wrappedElement = new ReleaseResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, NotifyResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(NotifyResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private NotifyResponse wrapnotify()
    {
        final NotifyResponse wrappedElement = new NotifyResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, SetTestIntervalResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(SetTestIntervalResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private SetTestIntervalResponse wrapsetTestInterval()
    {
        final SetTestIntervalResponse wrappedElement = new SetTestIntervalResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, SetMaintenanceResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(SetMaintenanceResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private SetMaintenanceResponse wrapsetMaintenance()
    {
        final SetMaintenanceResponse wrappedElement = new SetMaintenanceResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, PerformBatchControlResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(PerformBatchControlResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private PerformBatchControlResponse wrapperformBatchControl()
    {
        final PerformBatchControlResponse wrappedElement = new PerformBatchControlResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, GetStatusResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
            emptyEnvelope.getBody().addChild(param.getOMElement(GetStatusResponse.MY_QNAME, factory));
            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private GetStatusResponse wrapgetStatus()
    {
        final GetStatusResponse wrappedElement = new GetStatusResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, AllocateResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(AllocateResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private AllocateResponse wrapallocate()
    {
        final AllocateResponse wrappedElement = new AllocateResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, SlaveAllocateResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody().addChild(param.getOMElement(SlaveAllocateResponse.MY_QNAME, factory));

            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private SlaveAllocateResponse wrapslaveAllocate()
    {
        final SlaveAllocateResponse wrappedElement = new SlaveAllocateResponse();
        return wrappedElement;
    }

    private SOAPEnvelope toEnvelope(SOAPFactory factory, AbortBatchControlResponse param, boolean optimizeContent)
            throws AxisFault
    {
        try
        {
            final SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
            emptyEnvelope.getBody().addChild(param.getOMElement(AbortBatchControlResponse.MY_QNAME, factory));
            return emptyEnvelope;
        }
        catch (final ADBException e)
        {
            throw AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private AbortBatchControlResponse wrapabortBatchControl()
    {
        final AbortBatchControlResponse wrappedElement = new AbortBatchControlResponse();
        return wrappedElement;
    }

    @SuppressWarnings("unused")
    private SOAPEnvelope toEnvelope(SOAPFactory factory)
    {
        return factory.getDefaultEnvelope();
    }

    @SuppressWarnings("unchecked")
    private Object fromOM(OMElement param, Class type, Map<String, String> extraNamespaces) throws AxisFault
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

            if (IsActivityDetectable.class.equals(type))
                return IsActivityDetectable.Factory.parse(param.getXMLStreamReaderWithoutCaching());

            if (IsActivityDetectableResponse.class.equals(type))
                return IsActivityDetectableResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

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
        catch (final Exception e)
        {
            throw AxisFault.makeFault(e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getEnvelopeNamespaces(SOAPEnvelope env)
    {
        final Map<String, String> returnMap = new HashMap<String, String>();
        final Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext())
        {
            final OMNamespace ns = (OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
        }
        return returnMap;
    }

    @SuppressWarnings("unused")
    private AxisFault createAxisFault(Exception e)
    {
        AxisFault f;
        final Throwable cause = e.getCause();
        if (cause != null)
        {
            f = new AxisFault(e.getMessage(), cause);
        }
        else
        {
            f = new AxisFault(e.getMessage());
        }
        return f;
    }
}
