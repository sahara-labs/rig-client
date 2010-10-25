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
 * @date 16th October 2009
 *
 * Changelog:
 * - 16/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.tester;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import au.edu.uts.eng.remotelabs.rigclient.intf.tests.RigClientServiceTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.AbortBatchControlResponseTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.AbortBatchControlTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.ActivityDetectableTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.AllocateResponseTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.AllocateTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.AttributeRequestTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.AttributeResponseTypeChoiceTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.AttributeResponseTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.BatchRequestTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.BatchStateTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.BatchStatusResponseTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.ErrorTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.ErrorTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.ExtensionMapperTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.GetAttributeResponseTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.GetAttributeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.GetBatchControlStatusResponseTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.GetBatchControlStatusTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.GetStatusResponseTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.GetStatusTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.IsActivityDetectableResponseTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.IsActivityDetectableTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.MaintenanceRequestTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.NotificationRequestTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.NotifyResponseTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.NotifyTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.OperationResponseTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.ReleaseResponseTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.ReleaseTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.SlaveUserTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.StatusResponseTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.TestIntervalRequestTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.TypeSlaveUserTester;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.tests.UserTypeTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.control.tests.AbstractBatchRunnerTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.control.tests.ConfiguredBatchRunnerTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.internal.tests.DirectoryCopierTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.internal.tests.DirectoryZipperTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.internal.tests.BatchMacroSubstituerTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.PrimitiveCacheTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.PrimitiveFrontTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.tests.AbstractControlledRigTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.tests.AbstractRigTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.tests.ConfiguredControlledRigTester;
import au.edu.uts.eng.remotelabs.rigclient.rig.tests.ConfiguredRigTester;
import au.edu.uts.eng.remotelabs.rigclient.type.tests.RigFactoryTester;
import au.edu.uts.eng.remotelabs.rigclient.util.tests.LogFormatterTester;
import au.edu.uts.eng.remotelabs.rigclient.util.tests.PropertiesConfigTester;

/**
 * Runs all the Rig Client unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    LogFormatterTester.class,
    TestIntervalRequestTypeTester.class,
    TypeSlaveUserTester.class,
    SlaveUserTypeTester.class,
    StatusResponseTypeTester.class,
    ReleaseTester.class,
    ReleaseResponseTester.class,
    OperationResponseTypeTester.class,
    NotifyTester.class,
    NotifyResponseTester.class,
    NotificationRequestTypeTester.class,
    MaintenanceRequestTypeTester.class,
    IsActivityDetectableTester.class,
    IsActivityDetectableResponseTester.class,
    GetAttributeResponseTester.class,
    GetAttributeTester.class,
    GetBatchControlStatusResponseTester.class,
    GetBatchControlStatusTester.class,
    GetStatusResponseTester.class,
    GetStatusTester.class,
    ExtensionMapperTester.class,
    ErrorTypeTester.class,
    ErrorTester.class,
    BatchStatusResponseTypeTester.class,
    BatchStateTester.class,
    BatchRequestTypeTester.class,
    AttributeResponseTypeChoiceTester.class,
    AttributeResponseTypeTester.class,
    AttributeRequestTypeTester.class,
    ActivityDetectableTypeTester.class,
    AbortBatchControlResponseTester.class,
    AbortBatchControlTester.class,
    AllocateResponseTester.class,
    AllocateTester.class,
    UserTypeTester.class,
    RigClientServiceTester.class,
    RigFactoryTester.class,
    ConfiguredRigTester.class,
    ConfiguredControlledRigTester.class,
    ConfiguredBatchRunnerTester.class,
    PrimitiveFrontTester.class,
    PrimitiveCacheTester.class,
    DirectoryCopierTester.class,
    DirectoryZipperTester.class,
    BatchMacroSubstituerTester.class,
    AbstractBatchRunnerTester.class,
    AbstractControlledRigTester.class,
    AbstractRigTester.class,
    ConfiguredBatchRunnerTester.class,
    PropertiesConfigTester.class
})
public class RigClientTestSuite
{
    // Does nothing...
}
