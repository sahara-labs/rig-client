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
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveUserType;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * RigClientServiceSkeleton dummy implementation.
 */
public class RigClientServiceSkeleton implements RigClientServiceSkeletonInterface
{
    /** Logger. */
    private ILogger logger;
    
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
        SlaveUserType slave = slaveAlloc.getSlaveAllocate();
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
    public AbortBatchControlResponse abortBatchControl(final AbortBatchControl abc)
    {
        this.logger.info("Abort batch control called with params:" + abc.getAbortBatchControl().getUser() + ".");
        throw new UnsupportedOperationException("Please implement " + this.getClass().getName() + "#abortBatchControl");
    }

    
    /**
     * Auto generated method signature
     * 
     * @param getAttribute4
     */

    public GetAttributeResponse getAttribute(
            GetAttribute getAttribute4)
    {
        // TODO : fill this with the necessary business logic
        throw new UnsupportedOperationException("Please implement " + this.getClass().getName()
                + "#getAttribute");
    }

    /**
     * Auto generated method signature
     * 
     * @param getBatchControlStatus2
     */

    public GetBatchControlStatusResponse getBatchControlStatus(
            GetBatchControlStatus getBatchControlStatus2)
    {
        // TODO : fill this with the necessary business logic
        throw new UnsupportedOperationException("Please implement " + this.getClass().getName()
                + "#getBatchControlStatus");
    }

    /**
     * Auto generated method signature
     * 
     * @param getStatus18
     */

    public GetStatusResponse getStatus(
            GetStatus getStatus18)
    {
        // TODO : fill this with the necessary business logic
        throw new UnsupportedOperationException("Please implement " + this.getClass().getName()
                + "#getStatus");
    }

   

    /**
     * Auto generated method signature
     * 
     * @param performBatchControl16
     */

    public PerformBatchControlResponse performBatchControl(
            PerformBatchControl performBatchControl16)
    {
        // TODO : fill this with the necessary business logic
        throw new UnsupportedOperationException("Please implement " + this.getClass().getName()
                + "#performBatchControl");
    }

    /**
     * Auto generated method signature
     * 
     * @param performPrimitiveControl0
     */

    public PerformPrimitiveControlResponse performPrimitiveControl(
            PerformPrimitiveControl performPrimitiveControl0)
    {
        // TODO : fill this with the necessary business logic
        throw new UnsupportedOperationException("Please implement " + this.getClass().getName()
                + "#performPrimitiveControl");
    }
    
    

    /**
     * Auto generated method signature
     * 
     * @param setMaintenance14
     */

    public SetMaintenanceResponse setMaintenance(
            SetMaintenance setMaintenance14)
    {
        // TODO : fill this with the necessary business logic
        throw new UnsupportedOperationException("Please implement " + this.getClass().getName()
                + "#setMaintenance");
    }

    /**
     * Auto generated method signature
     * 
     * @param setTestInterval12
     */

    public SetTestIntervalResponse setTestInterval(
            SetTestInterval setTestInterval12)
    {
        // TODO : fill this with the necessary business logic
        throw new UnsupportedOperationException("Please implement " + this.getClass().getName()
                + "#setTestInterval");
    }
}
