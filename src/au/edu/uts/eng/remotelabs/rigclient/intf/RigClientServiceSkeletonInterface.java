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
 * RigClientServiceSkeletonInterface.java This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:39 LKT)
 */
package au.edu.uts.eng.remotelabs.rigclient.intf;

import au.edu.uts.eng.remotelabs.rigclient.intf.types.AbortBatchControl;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AbortBatchControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.Allocate;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.GetAttribute;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.GetAttributeResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.GetBatchControlStatus;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.GetBatchControlStatusResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.GetConfig;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.GetConfigResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.GetStatus;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.GetStatusResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.IsActivityDetectable;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.IsActivityDetectableResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.Notify;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.NotifyResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformBatchControl;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformBatchControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.Release;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.ReleaseResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetConfig;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetConfigResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetMaintenance;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetMaintenanceResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetTestInterval;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetTestIntervalResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocate;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveRelease;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveReleaseResponse;

/**
 * RigClientServiceSkeletonInterface skeleton interface for the Axis service.
 */
public interface RigClientServiceSkeletonInterface
{    
    /**
     * Request to allocate a specified user to the rig.
     * 
     * @param allocRequest request parameters
     * @return operation response
     */
    public AllocateResponse allocate(Allocate allocRequest);
    
    /**
     * Request to remove a specified user from the rig.
     * 
     * @param relRequest request parameters
     * @return operation response
     */
    public ReleaseResponse release(Release relRequest);
    
    /**
     * Request to allocate a specified slave user to the rig. Allocate 
     * may be active slave or passive slave.
     * 
     * @param slaveAllocRequest request parameters
     * @return operation response
     */
    public SlaveAllocateResponse slaveAllocate(SlaveAllocate slaveAllocRequest);

    /**
     * Request to remove a specified slave user from the rig.
     * 
     * @param slaveRelRequest request parameters
     * @return operation response
     */
    public SlaveReleaseResponse slaveRelease(SlaveRelease slaveRelRequest);
    
     /**
     * Request to display a notification for the user to see.
     * 
     * @param notify request parameters
     * @return operation response
     */
    public NotifyResponse notify(Notify notify);
    
    /**
     * Request for the rig client to provide control of the hardware 
     * using a provided instruction file.
     * 
     * @param batchRequest request parameters
     * @return operation response
     */
    public PerformBatchControlResponse performBatchControl(PerformBatchControl batchRequest);

    /**
     * Request for the rig client to abort a previously started batch control
     * invocation.
     * 
     * @param abortRequest request parameters
     * @return operation response
     */
    public AbortBatchControlResponse abortBatchControl(AbortBatchControl abortRequest);

    /**
     * Request for the rig client to provide information about a previously 
     * started batch control invocation.
     * 
     * @param statusRequest request parameters
     * @return batch status response
     */
    public GetBatchControlStatusResponse getBatchControlStatus(GetBatchControlStatus statusRequest);
    
    /**
     * Request for the rig client to perform primitive control on a rig device.
     * 
     * @param orimRequest request parameters
     * @return primitive control response
     */
    public PerformPrimitiveControlResponse performPrimitiveControl(PerformPrimitiveControl orimRequest);
    
    /**
     * Request for the rig client to provide information about the rig.
     * 
     * @param attrRequest request parameters
     * @return attribute response
     */
    public GetAttributeResponse getAttribute(GetAttribute attrRequest);

    /**
     * Request for the rig client to provide status information about the rig 
     * client.
     * 
     * @param statusRequest request parameters
     * @return status response
     */
    public GetStatusResponse getStatus(GetStatus statusRequest);

    /**
     * Request to either put the rig client into maintenance mode or remove 
     * it from maintenance mode.
     * 
     * @param maintenRequest request parameters
     * @return operation response
     */
    public SetMaintenanceResponse setMaintenance(SetMaintenance maintenRequest);

    /**
     * Request to change the rig client interval
     * 
     * @param interRequest request parameters
     * @return operation response
     */
    public SetTestIntervalResponse setTestInterval(SetTestInterval interRequest);
    
    /**
     * Request to determine if their is activity detected from in session users.
     * 
     * @param isActivityDetectable request parameters
     * @return operation response
     */
    public IsActivityDetectableResponse isActivityDetectable(IsActivityDetectable isActivityDetectable);
    
    /**
     * Request to get all the configuration properties and their information
     * 
     * @param configRequest request parameters
     * @return configuration response
     */
    public GetConfigResponse getConfig(GetConfig configRequest);
    
    /**
     * Request to change the value of configuration properties.
     * 
     * @param configRequest request parameters
     * @return operation response
     */
    public SetConfigResponse setConfig(SetConfig configRequest);
}
