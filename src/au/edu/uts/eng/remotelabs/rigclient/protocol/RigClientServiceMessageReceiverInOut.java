
/**
 * RigClientServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:39 LKT)
 */
        package au.edu.uts.eng.remotelabs.rigclient.protocol;

        /**
        *  RigClientServiceMessageReceiverInOut message receiver
        */

        public class RigClientServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        RigClientServiceSkeletonInterface skel = (RigClientServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJava(op.getName().getLocalPart())) != null)){

        

            if("performPrimitiveControl".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse performPrimitiveControlResponse29 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               performPrimitiveControlResponse29 =
                                                   
                                                   
                                                         skel.performPrimitiveControl(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), performPrimitiveControlResponse29, false);
                                    } else 

            if("getBatchControlStatus".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse getBatchControlStatusResponse31 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getBatchControlStatusResponse31 =
                                                   
                                                   
                                                         skel.getBatchControlStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getBatchControlStatusResponse31, false);
                                    } else 

            if("isActivityDetectable".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse isActivityDetectableResponse33 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               isActivityDetectableResponse33 =
                                                   
                                                   
                                                         skel.isActivityDetectable(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), isActivityDetectableResponse33, false);
                                    } else 

            if("getAttribute".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse getAttributeResponse35 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getAttributeResponse35 =
                                                   
                                                   
                                                         skel.getAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getAttributeResponse35, false);
                                    } else 

            if("slaveRelease".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse slaveReleaseResponse37 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               slaveReleaseResponse37 =
                                                   
                                                   
                                                         skel.slaveRelease(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), slaveReleaseResponse37, false);
                                    } else 

            if("release".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse releaseResponse39 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               releaseResponse39 =
                                                   
                                                   
                                                         skel.release(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), releaseResponse39, false);
                                    } else 

            if("notify".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse notifyResponse41 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               notifyResponse41 =
                                                   
                                                   
                                                         skel.notify(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), notifyResponse41, false);
                                    } else 

            if("setTestInterval".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse setTestIntervalResponse43 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setTestIntervalResponse43 =
                                                   
                                                   
                                                         skel.setTestInterval(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setTestIntervalResponse43, false);
                                    } else 

            if("setMaintenance".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse setMaintenanceResponse45 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               setMaintenanceResponse45 =
                                                   
                                                   
                                                         skel.setMaintenance(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), setMaintenanceResponse45, false);
                                    } else 

            if("performBatchControl".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse performBatchControlResponse47 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               performBatchControlResponse47 =
                                                   
                                                   
                                                         skel.performBatchControl(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), performBatchControlResponse47, false);
                                    } else 

            if("getStatus".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse getStatusResponse49 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getStatusResponse49 =
                                                   
                                                   
                                                         skel.getStatus(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getStatusResponse49, false);
                                    } else 

            if("allocate".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse allocateResponse51 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               allocateResponse51 =
                                                   
                                                   
                                                         skel.allocate(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), allocateResponse51, false);
                                    } else 

            if("slaveAllocate".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse slaveAllocateResponse53 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               slaveAllocateResponse53 =
                                                   
                                                   
                                                         skel.slaveAllocate(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), slaveAllocateResponse53, false);
                                    } else 

            if("abortBatchControl".equals(methodName)){
                
                au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse abortBatchControlResponse55 = null;
	                        au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl wrappedParam =
                                                             (au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               abortBatchControlResponse55 =
                                                   
                                                   
                                                         skel.abortBatchControl(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), abortBatchControlResponse55, false);
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse wrapperformPrimitiveControl(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse wrapgetBatchControlStatus(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse wrapisActivityDetectable(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse wrapgetAttribute(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse wrapslaveRelease(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse wraprelease(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse wrapnotify(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse wrapsetTestInterval(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse wrapsetMaintenance(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse wrapperformBatchControl(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse wrapgetStatus(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse wrapallocate(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse wrapslaveAllocate(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse wrapabortBatchControl(){
                                au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse wrappedElement = new au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse.class.equals(type)){
                
                           return au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    