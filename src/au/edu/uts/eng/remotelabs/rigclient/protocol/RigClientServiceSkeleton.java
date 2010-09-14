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
 * RigClientServiceSkeleton.java This file was auto-generated from WSDL by the
 * Apache Axis2 version: 1.4.1 Built on : Aug 19, 2008 (10:13:39 LKT)
 */
package au.edu.uts.eng.remotelabs.rigclient.protocol;

import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AttributeRequestType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.BatchRequestType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttributeResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatusResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetConfig;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetConfigResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatusResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectableResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.MaintenanceRequestType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PrimitiveControlRequestType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetConfig;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetConfigResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenanceResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestIntervalResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveUserType;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * RigClientServiceSkeleton dummy implementation.
 */
public class RigClientServiceSkeleton implements RigClientServiceSkeletonInterface
{
    /** Logger. */
    private final ILogger logger;
    
    /**
     * Logger.
     */
    public RigClientServiceSkeleton()
    {
        this.logger = LoggerFactory.getLoggerInstance();
    }
    
    @Override
    public AllocateResponse allocate(final Allocate all)
    {
        this.logger.info("Allocate operation called with params: User=" + all.getAllocate().getUser() + ".");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName() + "#allocate.");
    }
    
    @Override
    public ReleaseResponse release(final Release rel)
    {
        this.logger.info("Release operation called.");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName() + "#release.");
    }
    
    @Override
    public SlaveAllocateResponse slaveAllocate(final SlaveAllocate slaveAlloc)
    {
        final SlaveUserType slave = slaveAlloc.getSlaveAllocate();
        this.logger.info("Slave allocation operation called with params: User=" + 
                slave.getUser() + ", Type=" + slave.getType().getValue() + ".");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName() 
                + "#slaveAllocate.");
    }

    @Override
    public SlaveReleaseResponse slaveRelease(final SlaveRelease slaveRel)
    {
        this.logger.info("Slave release operation called with params: User=" + 
                slaveRel.getSlaveRelease().getUser() + ".");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName() 
                + "#slaveRelease.");
    }
    
    @Override
    public NotifyResponse notify(final Notify notify)
    {
        this.logger.info("Notify operation called with params: Message=" + notify.getNotify().getMessage() + ".");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName() + "#notify.");
    }
    
    @Override
    public PerformBatchControlResponse performBatchControl(final PerformBatchControl perfBatch)
    {
        final BatchRequestType request = perfBatch.getPerformBatchControl();
        this.logger.info("Perform batch control operation called with params: User=" + request.getRequestor() + 
                ", Identify=" + request.getIdentityToken() + ",FileData=" + request.getBatchFile().toString() + ".");
        throw new UnsupportedOperationException("Skeletopn implementation of " + this.getClass().getName() +
                "#performBatchControl");
    }

    @Override
    public AbortBatchControlResponse abortBatchControl(final AbortBatchControl abc)
    {
        this.logger.info("Abort batch control operation called with params: User=" + 
                abc.getAbortBatchControl().getRequestor() + 
                ", Identity=" + abc.getAbortBatchControl().getIdentityToken() + ".");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName() + 
                "#abortBatchControl");
    }
    
    @Override
    public GetBatchControlStatusResponse getBatchControlStatus(final GetBatchControlStatus batchStatus)
    {
        this.logger.info("Get batch control status operation called with params: User=" + 
                batchStatus.getGetBatchControlStatus().getRequestor() + 
                ", Identity=" + batchStatus.getGetBatchControlStatus().getIdentityToken() + ".");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName() +
                "#getBatchControlStatus");
    }

    @Override
    public PerformPrimitiveControlResponse performPrimitiveControl(final PerformPrimitiveControl perfPrim)
    {
        final PrimitiveControlRequestType request = perfPrim.getPerformPrimitiveControl();
        final StringBuilder builder = new StringBuilder(50);
        builder.append("Perform primitive control operation called with params: User=");
        builder.append(request.getIdentityToken());
        builder.append(", Identity=");
        builder.append(request.getIdentityToken());
        builder.append(", Controller=");
        builder.append(request.getController());
        builder.append(", Action=");
        builder.append(request.getAction());
        builder.append(", Params=(");
        for (ParamType param : request.getParam())
        {
            builder.append(param.getName());
            builder.append('=');
            builder.append(param.getValue());
            builder.append(',');
        }
        builder.append(").");
        
        this.logger.info(builder.toString());
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName()
                + "#performPrimitiveControl");
    }

    @Override
    public GetAttributeResponse getAttribute(final GetAttribute attr)
    {
        final AttributeRequestType request = attr.getGetAttribute();
        this.logger.info("Get attribute operation called with params: User=" + request.getRequestor() +
                ", Identity=" + request.getIdentityToken() + ", Attribute=" +
                request.getAttribute() + ".");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName()
                + "#getAttribute");
    }
    
    @Override 
    public GetStatusResponse getStatus(final GetStatus status)
    {
        this.logger.info("Get status attribute operation called.");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName()
                + "#getStatus");
    }

    @Override
    public SetMaintenanceResponse setMaintenance(final SetMaintenance set)
    {
        final MaintenanceRequestType request = set.getSetMaintenance();
        this.logger.info("Set maintenance operation called with params: Offline=" +
                request.getPutOffline() + ", RunTests=" + request.getRunTests() + 
                ", Identity=" + request.getIdentityToken() + ".");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName()
                + "#setMaintenance");
    }

    @Override
    public SetTestIntervalResponse setTestInterval(final SetTestInterval set)
    {
        this.logger.info("Set test interval operation called with params: Test interval=" + 
                set.getSetTestInterval().getInterval() + ", Identity=" + 
                set.getSetTestInterval().getIdentityToken() + ".");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName()
                + "#setTestInterval");
    }

    @Override
    public IsActivityDetectableResponse isActivityDetectable(IsActivityDetectable isActivityDetectable)
    {
        this.logger.info("Activity detection operation called.");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName()
                + "#isActivityDectectable");
    }

    @Override
    public GetConfigResponse getConfig(GetConfig configRequest)
    {
        this.logger.info("Get configuration operation called.");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName() + 
                "#getConfig");
    }

    @Override
    public SetConfigResponse setConfig(SetConfig configRequest)
    {
        this.logger.info("Set configuration operation called");
        throw new UnsupportedOperationException("Skeleton implementation of " + this.getClass().getName() +
                "#setConfig");
    }
}
