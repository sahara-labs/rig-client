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
 * @date 7th December 2009
 *
 * Changelog:
 * - 07/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.intf.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AbortBatchControl;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AbortBatchControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.Allocate;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AttributeRequestType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AttributeResponseType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AttributeResponseTypeChoice;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.AuthRequiredRequestType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.BatchRequestType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.BatchState;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.BatchStatusResponseType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.ConfigPropertyType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.ConfigResponseType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.ErrorType;
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
import au.edu.uts.eng.remotelabs.rigclient.intf.types.MaintenanceRequestType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.NotificationRequestType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.Notify;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.NotifyResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.NullType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.OperationResponseType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.ParamType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformBatchControl;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformBatchControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PrimitiveControlRequestType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.PrimitiveControlResponseType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.Release;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.ReleaseResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetConfig;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetConfigRequestType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetConfigResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetMaintenance;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetMaintenanceResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetTestInterval;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SetTestIntervalResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocate;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveRelease;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveReleaseResponse;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveUserType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.StatusResponseType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.TestIntervalRequestType;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.TypeSlaveUser;
import au.edu.uts.eng.remotelabs.rigclient.intf.types.UserType;
import au.edu.uts.eng.remotelabs.rigclient.rig.AbstractRig.ActionType;
import au.edu.uts.eng.remotelabs.rigclient.rig.ConfiguredRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.BatchResults;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session;
import au.edu.uts.eng.remotelabs.rigclient.rig.tests.MockRig;
import au.edu.uts.eng.remotelabs.rigclient.status.StatusUpdater;
import au.edu.uts.eng.remotelabs.rigclient.type.RigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfigDescriptions;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfigDescriptions.Property;
import au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig;

/**
 * Tests the {@link RigClientService} class.
 */
public class RigClientServiceTester extends TestCase
{
    /** Object of class under test. */
    private RigClientService service;
    
    /** Rig. */
    private IRig rig;
    
    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    @SuppressWarnings("cast")
    public void setUp() throws Exception
    {
        IConfig config = new PropertiesConfig("test/resources/servicetest.properties");
        
        ConfigFactory.getInstance();
        Field f = ConfigFactory.class.getDeclaredField("instance");
        f.setAccessible(true);
        f.set(null, config);
        
        /* Freshen instance of rig. */
        RigFactory.getRigInstance();
        Method m = RigFactory.class.getDeclaredMethod("loadInstance");
        m.setAccessible(true);
        f = RigFactory.class.getDeclaredField("rig");
        f.setAccessible(true);
        f.set(null, (IRig)m.invoke(null));
        
        /* Set identity token to abc123. */
        f = StatusUpdater.class.getDeclaredField("identToks");
        f.setAccessible(true);
        String toks[] = (String[])f.get(null);
        toks[0] = "abc123";
        
        f = StatusUpdater.class.getDeclaredField("isRegistered");
        f.setAccessible(true);
        f.setBoolean(null, true);
        
        
        this.rig = RigFactory.getRigInstance();
        this.service = new RigClientService();
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#allocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.Allocate)}.
     */
    @Test
    public void testAllocate()
    {
        Allocate alloc = new Allocate();
        UserType user = new UserType();
        user.setUser("mdiponio");
        user.setIdentityToken("abc123");
        alloc.setAllocate(user);
        
        AllocateResponse resp = this.service.allocate(alloc);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getAllocateResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(0, error.getCode());
        assertNotNull(error.getOperation());
        assertNotNull(error.getReason());
        assertEquals("", error.getReason());
        
        assertTrue(this.rig.isSessionActive());
        assertEquals(Session.MASTER, this.rig.isInSession("mdiponio"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#allocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.Allocate)}.
     */
    @Test
    public void testAllocateAuthFail()
    {
        Allocate alloc = new Allocate();
        UserType user = new UserType();
        user.setUser("mdiponio");
        user.setIdentityToken("NOT_CORRECT");
        alloc.setAllocate(user);
        
        AllocateResponse resp = this.service.allocate(alloc);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getAllocateResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(3, error.getCode());
        assertNotNull(error.getOperation());
        assertNotNull(error.getReason());
        assertEquals("Not authorised to allocate a user.", error.getReason());
        
        assertFalse(this.rig.isSessionActive());
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#allocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.Allocate)}.
     */
    @Test
    public void testAllocateInSession()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        Allocate alloc = new Allocate();
        UserType user = new UserType();
        user.setUser("mdiponio");
        user.setIdentityToken("abc123");
        alloc.setAllocate(user);
        
        AllocateResponse resp = this.service.allocate(alloc);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getAllocateResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(4, error.getCode());
        assertNotNull(error.getOperation());
        assertNotNull(error.getReason());
        assertEquals("A session is already active.", error.getReason());
        
        assertTrue(this.rig.isSessionActive());
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));
        assertEquals(Session.MASTER, this.rig.isInSession("tmachet"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#allocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.Allocate)}.
     */
    @Test
    public void testAllocateInMain()
    {
        assertTrue(this.rig.setMaintenance(true, "The cylons", true));
        
        Allocate alloc = new Allocate();
        UserType user = new UserType();
        user.setUser("mdiponio");
        user.setIdentityToken("abc123");
        alloc.setAllocate(user);
        
        AllocateResponse resp = this.service.allocate(alloc);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getAllocateResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(7, error.getCode());
        assertNotNull(error.getOperation());
        assertNotNull(error.getReason());
        assertEquals("Rig not operable.", error.getReason());
        
        assertFalse(this.rig.isSessionActive());
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#allocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.Allocate)}.
     */
    @Test
    public void testAllocateActionFailure() throws Exception
    {
        MockRig mock = new MockRig();
        IAccessAction access = createMock(IAccessAction.class);
        expect(access.getActionType()).andReturn("Access action.").atLeastOnce();
        expect(access.assign("mdiponio")).andReturn(false);
        expect(access.getFailureReason()).andReturn("Expected failure.").atLeastOnce();
        replay(access);
        mock.register(access, ActionType.ACCESS);
        
        Field f = RigClientService.class.getDeclaredField("rig");
        f.setAccessible(true);
        f.set(this.service, mock);
                
        Allocate alloc = new Allocate();
        UserType user = new UserType();
        user.setUser("mdiponio");
        user.setIdentityToken("abc123");
        alloc.setAllocate(user);
        
        AllocateResponse resp = this.service.allocate(alloc);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getAllocateResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(16, error.getCode());
        assertNotNull(error.getOperation());
        
        String reason = error.getReason();
        assertNotNull(reason);
        assertTrue(reason.contains(access.getActionType()));
        assertTrue(reason.contains(access.getFailureReason()));
        
        assertFalse(this.rig.isSessionActive());
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#release(au.edu.uts.eng.remotelabs.rigclient.intf.types.Release)}.
     */
    @Test
    public void testRelease()
    {
        assertTrue(this.rig.assign("mdiponio"));
        assertTrue(this.rig.isSessionActive());
        
        Release rel = new Release();
        UserType user = new UserType();
        user.setUser("mdiponio");
        user.setIdentityToken("abc123");
        rel.setRelease(user);
        
        ReleaseResponse res = this.service.release(rel);
        assertNotNull(res);
        
        OperationResponseType op = res.getReleaseResponse();
        assertNotNull(res);
        assertTrue(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(0, error.getCode());
        assertNotNull(error.getOperation());
        assertNotNull(error.getReason());
        assertEquals("", error.getReason());
        
        assertFalse(this.rig.isSessionActive());
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#release(au.edu.uts.eng.remotelabs.rigclient.intf.types.Release)}.
     */
    @Test
    public void testReleaseAuthFailed()
    {
        assertTrue(this.rig.assign("mdiponio"));
        assertTrue(this.rig.isSessionActive());
        
        Release rel = new Release();
        UserType user = new UserType();
        user.setIdentityToken("NOT_CORRECT");
        user.setUser("mdiponio");
        rel.setRelease(user);
        
        ReleaseResponse res = this.service.release(rel);
        assertNotNull(res);
        
        OperationResponseType op = res.getReleaseResponse();
        assertNotNull(res);
        assertFalse(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(3, error.getCode());
        assertNotNull(error.getOperation());
        assertNotNull(error.getReason());
        assertEquals("Not authorised to release a user.", error.getReason());
        
        assertTrue(this.rig.isSessionActive());
        assertEquals(Session.MASTER, this.rig.isInSession("mdiponio"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#release(au.edu.uts.eng.remotelabs.rigclient.intf.types.Release)}.
     */
    @Test
    public void testReleaseNoSession()
    {               
        Release rel = new Release();
        UserType user = new UserType();
        user.setUser("mdiponio");
        user.setIdentityToken("abc123");
        rel.setRelease(user);
        
        ReleaseResponse res = this.service.release(rel);
        assertNotNull(res);
        
        OperationResponseType op = res.getReleaseResponse();
        assertNotNull(res);
        assertFalse(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(6, error.getCode());
        assertNotNull(error.getOperation());
        assertNotNull(error.getReason());
        assertEquals("Session not running.", error.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#release(au.edu.uts.eng.remotelabs.rigclient.intf.types.Release)}.
     */
    @Test
    public void testReleaseDifferentUser()
    {
        assertTrue(this.rig.assign("tmachet"));
        Release rel = new Release();
        UserType user = new UserType();
        user.setUser("mdiponio");
        user.setIdentityToken("abc123");        
        rel.setRelease(user);
        
        ReleaseResponse res = this.service.release(rel);
        assertNotNull(res);
        
        OperationResponseType op = res.getReleaseResponse();
        assertNotNull(res);
        assertFalse(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(5, error.getCode());
        assertNotNull(error.getOperation());
        assertNotNull(error.getReason());
        assertEquals("User is not a master user.", error.getReason());
        
        assertEquals(Session.MASTER, this.rig.isInSession("tmachet"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#allocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.Allocate)}.
     */
    @Test
    public void testReleaseActionFailure() throws Exception
    {
        MockRig mock = new MockRig();
        IAccessAction access = createMock(IAccessAction.class);
        expect(access.getActionType()).andReturn("Access action.").atLeastOnce();
        expect(access.assign("mdiponio")).andReturn(true);
        expect(access.revoke("mdiponio")).andReturn(false);
        expect(access.getFailureReason()).andReturn("Release failure.").atLeastOnce();
        replay(access);
        mock.register(access, ActionType.ACCESS);
        
        Field f = RigClientService.class.getDeclaredField("rig");
        f.setAccessible(true);
        f.set(this.service, mock);
        
        assertTrue(mock.assign("mdiponio"));
                
        Release rel = new Release();
        UserType user = new UserType();
        user.setUser("mdiponio");
        user.setIdentityToken("abc123");
        rel.setRelease(user);
        
        ReleaseResponse resp = this.service.release(rel);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getReleaseResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType error = op.getError();
        assertNotNull(error);
        assertEquals(16, error.getCode());
        assertNotNull(error.getOperation());
        
        String reason = error.getReason();
        assertNotNull(reason);
        assertTrue(reason.contains(access.getActionType()));
        assertTrue(reason.contains(access.getFailureReason()));
    }


    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocate()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); /* Always the slave... */
        slave.setType(new TypeSlaveUser("Active"));
        slave.setIdentityToken("abc123");
        request.setSlaveAllocate(slave);
        
        SlaveAllocateResponse resp = this.service.slaveAllocate(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveAllocateResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        
        assertTrue(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertEquals(Session.SLAVE_ACTIVE, this.rig.isInSession("mdiponio"));	         
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocateByMaster()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); /* Always the slave... */
        slave.setIdentityToken("NOT_CORRECT");
        slave.setRequestor("tmachet");
        slave.setType(new TypeSlaveUser("Active"));
        request.setSlaveAllocate(slave);
        
        SlaveAllocateResponse resp = this.service.slaveAllocate(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveAllocateResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        
        assertTrue(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertEquals(Session.SLAVE_ACTIVE, this.rig.isInSession("mdiponio"));	         
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocatePassive()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); /* Always the slave... */
        slave.setType(new TypeSlaveUser("Passive"));
        slave.setIdentityToken("abc123");
        request.setSlaveAllocate(slave);
        
        SlaveAllocateResponse resp = this.service.slaveAllocate(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveAllocateResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        
        assertTrue(this.rig.hasPermission("mdiponio", Session.SLAVE_PASSIVE));
        assertEquals(Session.SLAVE_PASSIVE, this.rig.isInSession("mdiponio"));	         
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocateWrongType()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); 
        slave.setType(new TypeSlaveUser("King_Maker")); /* Not the slave no more. */
        slave.setIdentityToken("abc123");
        request.setSlaveAllocate(slave);
        
        SlaveAllocateResponse resp = this.service.slaveAllocate(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveAllocateResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess()); /* Oh well, I can only dream... */
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(2, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        assertEquals("Invalid slave type parameter.", err.getReason());
        
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_PASSIVE));
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));	         
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocateWrongAuth()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); 
        slave.setIdentityToken("NOT_CORRECT");
        slave.setType(new TypeSlaveUser("Active")); /* Not the slave no more. */
        request.setSlaveAllocate(slave);
        
        SlaveAllocateResponse resp = this.service.slaveAllocate(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveAllocateResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(3, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        assertEquals("Not authorised to allocate a slave user.", err.getReason());
        
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_PASSIVE));
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));	         
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocateNoSession()
    {
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); 
        slave.setType(new TypeSlaveUser("Active"));
        slave.setIdentityToken("abc123");
        request.setSlaveAllocate(slave);
        
        SlaveAllocateResponse resp = this.service.slaveAllocate(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveAllocateResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(6, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        assertEquals("No session is currently running.", err.getReason());
        
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_PASSIVE));
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));	         
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocateMaster()
    {
        assertTrue(this.rig.assign("mdiponio"));
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); 
        slave.setType(new TypeSlaveUser("Active")); /* Trying to demote me... */
        slave.setIdentityToken("abc123");
        request.setSlaveAllocate(slave);
        
        SlaveAllocateResponse resp = this.service.slaveAllocate(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveAllocateResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(2, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        assertEquals("User mdiponio is already a master user.", err.getReason());
        
        assertEquals(Session.MASTER, this.rig.isInSession("mdiponio"));	         
    }


    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveRelease(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveRelease)}.
     */
    @Test
    public void testSlaveRelease()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        SlaveRelease request = new SlaveRelease();
        UserType slave = new UserType();
        slave.setUser("mdiponio");
        slave.setIdentityToken("abc123");
        request.setSlaveRelease(slave);
        
        SlaveReleaseResponse resp = this.service.slaveRelease(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveReleaseResponse();
        assertNotNull(resp);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertTrue(this.rig.hasPermission("tmachet", Session.MASTER));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveRelease(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveRelease)}.
     */
    @Test
    public void testSlaveReleaseByMaster()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        SlaveRelease request = new SlaveRelease();
        UserType slave = new UserType();
        slave.setUser("mdiponio");
        slave.setIdentityToken("Not correct");        
        slave.setRequestor("tmachet");
        request.setSlaveRelease(slave);
        
        SlaveReleaseResponse resp = this.service.slaveRelease(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveReleaseResponse();
        assertNotNull(resp);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertTrue(this.rig.hasPermission("tmachet", Session.MASTER));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveRelease(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveRelease)}.
     */
    @Test
    public void testSlaveReleaseBySlave()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        SlaveRelease request = new SlaveRelease();
        UserType slave = new UserType();
        slave.setUser("mdiponio");
        slave.setIdentityToken("Not correct");        
        slave.setRequestor("mdiponio");
        request.setSlaveRelease(slave);
        
        SlaveReleaseResponse resp = this.service.slaveRelease(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveReleaseResponse();
        assertNotNull(resp);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertTrue(this.rig.hasPermission("tmachet", Session.MASTER));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveRelease(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveRelease)}.
     */
    @Test
    public void testSlaveReleaseWrongAuth()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        SlaveRelease request = new SlaveRelease();
        UserType slave = new UserType();
        slave.setUser("mdiponio");
        slave.setIdentityToken("FOO BAR");
        request.setSlaveRelease(slave);
        
        SlaveReleaseResponse resp = this.service.slaveRelease(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveReleaseResponse();
        assertNotNull(resp);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(3, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        assertEquals("Not authorised to release slave user.", err.getReason());
        
        assertEquals(Session.SLAVE_ACTIVE, this.rig.isInSession("mdiponio"));
        assertTrue(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertTrue(this.rig.hasPermission("tmachet", Session.MASTER));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveRelease(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveRelease)}.
     */
    @Test
    public void testSlaveReleaseWrongMaster()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        SlaveRelease request = new SlaveRelease();
        UserType slave = new UserType();
        slave.setUser("mdiponio");
        slave.setIdentityToken("FOO BAR");
        slave.setRequestor("!tmachet");
        request.setSlaveRelease(slave);
        
        SlaveReleaseResponse resp = this.service.slaveRelease(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveReleaseResponse();
        assertNotNull(resp);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(3, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        assertEquals("Not authorised to release slave user.", err.getReason());
        
        assertEquals(Session.SLAVE_ACTIVE, this.rig.isInSession("mdiponio"));
        assertTrue(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertTrue(this.rig.hasPermission("tmachet", Session.MASTER));
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#slaveRelease(au.edu.uts.eng.remotelabs.rigclient.intf.types.SlaveRelease)}.
     */
    @Test
    public void testSlaveReleaseNotIn()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveRelease request = new SlaveRelease();
        UserType slave = new UserType();
        slave.setUser("mdiponio");
        slave.setIdentityToken("abc123");
        request.setSlaveRelease(slave);
        
        SlaveReleaseResponse resp = this.service.slaveRelease(request);
        assertNotNull(resp);
        
        OperationResponseType op = resp.getSlaveReleaseResponse();
        assertNotNull(resp);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(18, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        assertEquals("User mdiponio not a slave user.", err.getReason());
        
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_PASSIVE));
        assertTrue(this.rig.hasPermission("tmachet", Session.MASTER));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#notify(au.edu.uts.eng.remotelabs.rigclient.intf.types.Notify)}.
     */
    @Test
    public void testNotify()
    {
        assertTrue(this.rig.assign("mdiponio"));
        
        Notify notify = new Notify();
        NotificationRequestType request = new NotificationRequestType();
        notify.setNotify(request);
        request.setMessage("This is a very important message");
        request.setIdentityToken("abc123");
        
        NotifyResponse response = this.service.notify(notify);
        OperationResponseType op = response.getNotifyResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#notify(au.edu.uts.eng.remotelabs.rigclient.intf.types.Notify)}.
     */
    @Test
    public void testNotifyMaster()
    {
        assertTrue(this.rig.assign("mdiponio"));
        
        Notify notify = new Notify();
        NotificationRequestType request = new NotificationRequestType();
        notify.setNotify(request);
        request.setMessage("This is a very important message");
        request.setIdentityToken("Wrong...");
        request.setRequestor("mdiponio");
        
        NotifyResponse response = this.service.notify(notify);
        OperationResponseType op = response.getNotifyResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#notify(au.edu.uts.eng.remotelabs.rigclient.intf.types.Notify)}.
     */
    @Test
    public void testNotifySlaveActive()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        Notify notify = new Notify();
        NotificationRequestType request = new NotificationRequestType();
        notify.setNotify(request);
        request.setMessage("This is a very important message");
        request.setIdentityToken("Wrong...");
        request.setRequestor("mdiponio");
        
        NotifyResponse response = this.service.notify(notify);
        OperationResponseType op = response.getNotifyResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#notify(au.edu.uts.eng.remotelabs.rigclient.intf.types.Notify)}.
     */
    @Test
    public void testNotifySlavePassive()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", true));
        
        Notify notify = new Notify();
        NotificationRequestType request = new NotificationRequestType();
        notify.setNotify(request);
        request.setMessage("This is a very important message");
        request.setIdentityToken("Wrong...");
        request.setRequestor("mdiponio");
        
        NotifyResponse response = this.service.notify(notify);
        OperationResponseType op = response.getNotifyResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#notify(au.edu.uts.eng.remotelabs.rigclient.intf.types.Notify)}.
     */
    @Test
    public void testNotifyNoSession()
    {
        Notify notify = new Notify();
        NotificationRequestType request = new NotificationRequestType();
        notify.setNotify(request);
        request.setMessage("This is a very important message");
        request.setIdentityToken("abc123");
        
        NotifyResponse response = this.service.notify(notify);
        OperationResponseType op = response.getNotifyResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(6, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        assertEquals("Not in session.", err.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#notify(au.edu.uts.eng.remotelabs.rigclient.intf.types.Notify)}.
     */
    @Test
    public void testNotifyNoAuth()
    {
        Notify notify = new Notify();
        NotificationRequestType request = new NotificationRequestType();
        notify.setNotify(request);
        request.setMessage("This is a very important message");
        request.setIdentityToken("Wrong...");
        
        NotifyResponse response = this.service.notify(notify);
        OperationResponseType op = response.getNotifyResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(3, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        assertEquals("Invalid permission.", err.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#performBatchControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformBatchControl)}.
     */
    @Test
    public void testPerformBatchControl()
    {
        try
        {
            File f = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "test");
            f.mkdir();
            
            TestConfiguredBatchRunner.BATCH_PROPERTIES = "test/resources/servicebatch.properties";
            assertTrue(this.rig.assign("mdiponio"));
            assertTrue(this.rig instanceof IRigControl);
            
            PerformBatchControl perf = new PerformBatchControl();
            BatchRequestType req = new BatchRequestType();
            perf.setPerformBatchControl(req);
            
            req.setIdentityToken("abc123");
            req.setFileName("test/resources/Control/instructions.txt");
            FileDataSource src = new FileDataSource(new File("test/resources/Control/instructions.txt"));
            DataHandler hdl = new DataHandler(src);
            req.setBatchFile(hdl);
            
            TestConfiguredControlledRig cr = (TestConfiguredControlledRig)this.rig;
            assertFalse(cr.isBatchRunning());
            
            PerformBatchControlResponse res = this.service.performBatchControl(perf);
            assertNotNull(res);
            
            OperationResponseType op = res.getPerformBatchControlResponse();
            assertTrue(op.getSuccess());
            ErrorType err = op.getError();
            assertEquals(0, err.getCode());

            BatchResults trans = cr.getBatchResults();
            assertEquals(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.BatchState.IN_PROGRESS, trans.getState());
            while (cr.isBatchRunning())
            {
                Thread.sleep(1000);
            }
            
            /* Clean up. */
            this.recusiveDelete(f);
            
            assertFalse(cr.isBatchRunning());
            BatchResults br = cr.getBatchResults();
            assertNotNull(br);
            assertEquals(0, br.getErrorCode());
            assertEquals(0, br.getExitCode());
            assertNull(br.getErrorReason());
            assertEquals(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.BatchState.COMPLETE, br.getState());
            assertEquals(100, cr.getBatchProgress());
            assertFalse(br.getStandardOut().isEmpty());
            assertTrue(br.getStandardErr().isEmpty());
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + ", message " + e.getMessage() + '.');
        }
    }
 
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#abortBatchControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.AbortBatchControl)}.
     */
    @Test
    public void testAbortBatchControl()
    {
        try
        {
            File f = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "test");
            f.mkdir();
            
            TestConfiguredBatchRunner.BATCH_PROPERTIES = "test/resources/servicebatch.properties";
            assertTrue(this.rig.assign("mdiponio"));
            assertTrue(this.rig instanceof IRigControl);
            
            TestConfiguredControlledRig cr = (TestConfiguredControlledRig)this.rig;
            assertTrue(cr.performBatch(new File("test/resources/Control/instructions.txt").getCanonicalPath(), "mdiponio"));
            
            BatchResults trans = cr.getBatchResults();
            assertEquals(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.BatchState.IN_PROGRESS, trans.getState());
            Thread.sleep(500);
            
            AbortBatchControl abort = new AbortBatchControl();
            AuthRequiredRequestType auth = new AuthRequiredRequestType();
            auth.setRequestor("mdiponio");
            abort.setAbortBatchControl(auth);
            
            AbortBatchControlResponse resp = this.service.abortBatchControl(abort);
            OperationResponseType op = resp.getAbortBatchControlResponse();
            assertTrue(op.getSuccess());
            Thread.sleep(500);
            
            assertFalse(cr.isBatchRunning());
            
            /* Clean up. */
            this.recusiveDelete(f);
            
            BatchResults br = cr.getBatchResults();
            assertNotNull(br);
            assertEquals(0, br.getErrorCode());
            assertEquals(143, br.getExitCode());
            assertNull(br.getErrorReason());
            assertEquals(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.BatchState.ABORTED, br.getState());
            assertEquals(100, cr.getBatchProgress());
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + ", message " + e.getMessage() + '.');
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#getBatchControlStatus(au.edu.uts.eng.remotelabs.rigclient.intf.types.GetBatchControlStatus)}.
     */
    @Test
    public void testGetBatchControlStatus()
    {
        try
        {
            File f = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "test");
            f.mkdir();
            
            TestConfiguredBatchRunner.BATCH_PROPERTIES = "test/resources/servicebatch.properties";
            assertTrue(this.rig.assign("mdiponio"));
            assertTrue(this.rig instanceof IRigControl);
             
            TestConfiguredControlledRig cr = (TestConfiguredControlledRig)this.rig;
            assertTrue(cr.performBatch(new File("test/resources/Control/instructions.txt").getCanonicalPath(), "mdiponio"));
            BatchResults trans = cr.getBatchResults();
            assertEquals(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.BatchState.IN_PROGRESS, trans.getState());
            Thread.sleep(500);
            
            GetBatchControlStatus req = new GetBatchControlStatus();
            AuthRequiredRequestType auth = new AuthRequiredRequestType();
            auth.setIdentityToken("abc123");
            req.setGetBatchControlStatus(auth);
            
            GetBatchControlStatusResponse resp = this.service.getBatchControlStatus(req);
            BatchStatusResponseType status = resp.getGetBatchControlStatusResponse();
            assertEquals("-1", status.getProgress());
            assertEquals(BatchState.IN_PROGRESS, status.getState());
            
            while (cr.isBatchRunning())
            {
                Thread.sleep(1000);
            }
            
            resp = this.service.getBatchControlStatus(req);
            status = resp.getGetBatchControlStatusResponse();
            assertEquals(100, Integer.parseInt(status.getProgress()));
            assertEquals(0, status.getExitCode());
            assertEquals(BatchState.COMPLETE, status.getState());
            assertFalse(status.getStdout().isEmpty());
            assertTrue(status.getStderr().isEmpty());
            
            String[] res = status.getResultFilePath();
            assertNotNull(res);
            assertEquals(1, res.length);
            assertEquals("work-gods-damn-it.txt", res[0]);
            
            /* Clean up. */
            this.recusiveDelete(f);
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + ", message " + e.getMessage() + '.');
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControl()
    {
        assertTrue(this.rig.assign("mdiponio"));
        
        PerformPrimitiveControl performControl = new PerformPrimitiveControl();
        PrimitiveControlRequestType controlRequest = new PrimitiveControlRequestType();
        performControl.setPerformPrimitiveControl(controlRequest);
        
        controlRequest.setRequestor("mdiponio");
        controlRequest.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
        controlRequest.setAction("test");
        ParamType params[] = new ParamType[5];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = new ParamType();
            params[i].setName("param_" + i);
            params[i].setValue("Value_" + i);
        }
        controlRequest.setParam(params);
        
        PerformPrimitiveControlResponse response = this.service.performPrimitiveControl(performControl);
        PrimitiveControlResponseType controlResponse = response.getPerformPrimitiveControlResponse();
        assertNotNull(controlResponse);
        assertTrue(controlResponse.getSuccess());
        assertTrue(Boolean.valueOf(controlResponse.getWasSuccessful()));
        
        ErrorType err = controlResponse.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        
        ParamType resParams[] = controlResponse.getResult();
        assertEquals(5, resParams.length);
        Map<String, String> res = new HashMap<String, String>();
        for (ParamType p : resParams)
        {
            res.put(p.getName(), p.getValue());
        }
        
       for (int i = 0; i < params.length; i++)
       {
           assertTrue(res.containsKey(params[i].getName()));
           assertEquals(params[i].getValue(), res.get(params[i].getName()));
       }   
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControlSlaveActive()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        PerformPrimitiveControl performControl = new PerformPrimitiveControl();
        PrimitiveControlRequestType controlRequest = new PrimitiveControlRequestType();
        performControl.setPerformPrimitiveControl(controlRequest);
        
        controlRequest.setRequestor("mdiponio");
        controlRequest.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
        controlRequest.setAction("test");
        ParamType params[] = new ParamType[5];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = new ParamType();
            params[i].setName("param_" + i);
            params[i].setValue("Value_" + i);
        }
        controlRequest.setParam(params);
        
        PerformPrimitiveControlResponse response = this.service.performPrimitiveControl(performControl);
        PrimitiveControlResponseType controlResponse = response.getPerformPrimitiveControlResponse();
        assertNotNull(controlResponse);
        assertTrue(controlResponse.getSuccess());
        assertTrue(Boolean.valueOf(controlResponse.getWasSuccessful()));
        
        ErrorType err = controlResponse.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        
        ParamType resParams[] = controlResponse.getResult();
        assertEquals(5, resParams.length);
        Map<String, String> res = new HashMap<String, String>();
        for (ParamType p : resParams)
        {
            res.put(p.getName(), p.getValue());
        }
        
       for (int i = 0; i < params.length; i++)
       {
           assertTrue(res.containsKey(params[i].getName()));
           assertEquals(params[i].getValue(), res.get(params[i].getName()));
       }   
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControlAuth()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        PerformPrimitiveControl performControl = new PerformPrimitiveControl();
        PrimitiveControlRequestType controlRequest = new PrimitiveControlRequestType();
        performControl.setPerformPrimitiveControl(controlRequest);
        
        controlRequest.setRequestor("foobar");
        controlRequest.setIdentityToken("abc123");
        controlRequest.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
        controlRequest.setAction("test");
        ParamType params[] = new ParamType[5];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = new ParamType();
            params[i].setName("param_" + i);
            params[i].setValue("Value_" + i);
        }
        controlRequest.setParam(params);
        
        PerformPrimitiveControlResponse response = this.service.performPrimitiveControl(performControl);
        PrimitiveControlResponseType controlResponse = response.getPerformPrimitiveControlResponse();
        assertNotNull(controlResponse);
        assertTrue(controlResponse.getSuccess());
        assertTrue(Boolean.valueOf(controlResponse.getWasSuccessful()));
        
        ErrorType err = controlResponse.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        
        ParamType resParams[] = controlResponse.getResult();
        assertEquals(5, resParams.length);
        Map<String, String> res = new HashMap<String, String>();
        for (ParamType p : resParams)
        {
            res.put(p.getName(), p.getValue());
        }
        
       for (int i = 0; i < params.length; i++)
       {
           assertTrue(res.containsKey(params[i].getName()));
           assertEquals(params[i].getValue(), res.get(params[i].getName()));
       }   
    }
    
     /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControlSlavePassive()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", true));
        
        PerformPrimitiveControl performControl = new PerformPrimitiveControl();
        PrimitiveControlRequestType controlRequest = new PrimitiveControlRequestType();
        performControl.setPerformPrimitiveControl(controlRequest);
        controlRequest.setIdentityToken("Wrong...");
        controlRequest.setRequestor("mdiponio");
        controlRequest.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
        controlRequest.setAction("test");
        ParamType params[] = new ParamType[5];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = new ParamType();
            params[i].setName("param_" + i);
            params[i].setValue("Value_" + i);
        }
        controlRequest.setParam(params);
        
        PerformPrimitiveControlResponse response = this.service.performPrimitiveControl(performControl);
        PrimitiveControlResponseType cr = response.getPerformPrimitiveControlResponse();
        assertNotNull(cr);
        assertTrue(cr.getSuccess());
        assertTrue(Boolean.valueOf(cr.getWasSuccessful()));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControlWrongUser()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        PerformPrimitiveControl performControl = new PerformPrimitiveControl();
        PrimitiveControlRequestType controlRequest = new PrimitiveControlRequestType();
        performControl.setPerformPrimitiveControl(controlRequest);
        
        controlRequest.setRequestor("mdiponio");
        controlRequest.setIdentityToken("Wrong...");
        controlRequest.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
        controlRequest.setAction("test");
        ParamType params[] = new ParamType[5];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = new ParamType();
            params[i].setName("param_" + i);
            params[i].setValue("Value_" + i);
        }
        controlRequest.setParam(params);
        
        PerformPrimitiveControlResponse response = this.service.performPrimitiveControl(performControl);
        PrimitiveControlResponseType cr = response.getPerformPrimitiveControlResponse();
        assertNotNull(cr);
        assertFalse(cr.getSuccess());
        assertFalse(Boolean.valueOf(cr.getWasSuccessful()));
        
        ErrorType err = cr.getError();
        assertNotNull(err);
        assertEquals(3, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        assertEquals("Invalid permission.", err.getReason());
    }
    
     /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControlNotSupported() throws Exception
    {
        /* Set a rig type which is not controlled. */
        IRig noControlRig = new ConfiguredRig();
        Field f = RigClientService.class.getDeclaredField("rig");
        f.setAccessible(true);
        f.set(this.service, noControlRig);
     
        assertTrue(noControlRig.assign("mdiponio"));
        
        PerformPrimitiveControl performControl = new PerformPrimitiveControl();
        PrimitiveControlRequestType controlRequest = new PrimitiveControlRequestType();
        performControl.setPerformPrimitiveControl(controlRequest);
        
        controlRequest.setRequestor("mdiponio");
        controlRequest.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
        controlRequest.setAction("test");
        ParamType params[] = new ParamType[5];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = new ParamType();
            params[i].setName("param_" + i);
            params[i].setValue("Value_" + i);
        }
        controlRequest.setParam(params);
        
        PerformPrimitiveControlResponse response = this.service.performPrimitiveControl(performControl);
        PrimitiveControlResponseType cr = response.getPerformPrimitiveControlResponse();
        assertNotNull(cr);
        assertFalse(cr.getSuccess());
        assertFalse(Boolean.valueOf(cr.getWasSuccessful()));
        
        ErrorType err = cr.getError();
        assertNotNull(err);
        assertEquals(14, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        assertEquals("Primitive control not supported.", err.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControlNullKey()
    {
        assertTrue(this.rig.assign("mdiponio"));
        
        PerformPrimitiveControl performControl = new PerformPrimitiveControl();
        PrimitiveControlRequestType controlRequest = new PrimitiveControlRequestType();
        performControl.setPerformPrimitiveControl(controlRequest);
        
        controlRequest.setRequestor("mdiponio");
        controlRequest.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
        controlRequest.setAction("test");
        
        /* Normal params. */
        ParamType params[] = new ParamType[5];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = new ParamType();
            params[i].setName("param_" + i);
            params[i].setValue("Value_" + i);
        }
        controlRequest.setParam(params);
        
        /* Null key param. */
        ParamType np = new ParamType();
        np.setName(null);
        np.setValue("fooooooood");
        controlRequest.addParam(np);
        
        PerformPrimitiveControlResponse response = this.service.performPrimitiveControl(performControl);
        PrimitiveControlResponseType controlResponse = response.getPerformPrimitiveControlResponse();
        assertNotNull(controlResponse);
        assertTrue(controlResponse.getSuccess());
        assertTrue(Boolean.valueOf(controlResponse.getWasSuccessful()));
        
        ErrorType err = controlResponse.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        
        ParamType resParams[] = controlResponse.getResult();
        
        assertEquals(5, resParams.length);
        Map<String, String> res = new HashMap<String, String>();
        for (ParamType p : resParams)
        {
            res.put(p.getName(), p.getValue());
        }
        
       for (int i = 0; i < params.length; i++)
       {
           assertTrue(res.containsKey(params[i].getName()));
           assertEquals(params[i].getValue(), res.get(params[i].getName()));
       }   
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.intf.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControlNullValue()
    {
        assertTrue(this.rig.assign("mdiponio"));
        
        PerformPrimitiveControl performControl = new PerformPrimitiveControl();
        PrimitiveControlRequestType controlRequest = new PrimitiveControlRequestType();
        performControl.setPerformPrimitiveControl(controlRequest);
        
        controlRequest.setRequestor("mdiponio");
        controlRequest.setController("au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests.MockController");
        controlRequest.setAction("test");
        
        /* Normal params. */
        ParamType params[] = new ParamType[5];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = new ParamType();
            params[i].setName("param_" + i);
            params[i].setValue("Value_" + i);
        }
        controlRequest.setParam(params);
        
        /* Null key param. */
        ParamType np = new ParamType();
        np.setName("Its almost lunch time");
        np.setValue(null);
        controlRequest.addParam(np);
        
        PerformPrimitiveControlResponse response = this.service.performPrimitiveControl(performControl);
        PrimitiveControlResponseType controlResponse = response.getPerformPrimitiveControlResponse();
        assertNotNull(controlResponse);
        assertTrue(controlResponse.getSuccess());
        assertTrue(Boolean.valueOf(controlResponse.getWasSuccessful()));
        
        ErrorType err = controlResponse.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        
        ParamType resParams[] = controlResponse.getResult();
        
        assertEquals(5, resParams.length);
        Map<String, String> res = new HashMap<String, String>();
        for (ParamType p : resParams)
        {
            res.put(p.getName(), p.getValue());
        }
        
       for (int i = 0; i < params.length; i++)
       {
           assertTrue(res.containsKey(params[i].getName()));
           assertEquals(params[i].getValue(), res.get(params[i].getName()));
       }   
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#getAttribute(au.edu.uts.eng.remotelabs.rigclient.intf.types.GetAttribute)}.
     */
    @Test
    public void testGetAttribute()
    {
        GetAttribute attrRequest = new GetAttribute();
        AttributeRequestType request = new AttributeRequestType();
        attrRequest.setGetAttribute(request);
        request.setAttribute("Rig_Type");
        request.setIdentityToken("abc123");
                
        GetAttributeResponse attrResponse = this.service.getAttribute(attrRequest);
        assertNotNull(attrResponse);
        AttributeResponseType response = attrResponse.getGetAttributeResponse();
        assertNotNull(response);
       
        assertEquals("Rig_Type", request.getAttribute());
        AttributeResponseTypeChoice choice = response.getAttributeResponseTypeChoice();
        assertNotNull(choice);
        assertNull(choice.getError());
        assertNotNull(choice.getValue());
        assertEquals("fpga", choice.getValue());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#getAttribute(au.edu.uts.eng.remotelabs.rigclient.intf.types.GetAttribute)}.
     */
    @Test
    public void testGetAttributeMaster()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", true));
        
        GetAttribute attrRequest = new GetAttribute();
        AttributeRequestType request = new AttributeRequestType();
        attrRequest.setGetAttribute(request);
        request.setAttribute("Rig_Type");
        request.setIdentityToken("Wrong...");
        request.setRequestor("tmachet");
                
        GetAttributeResponse attrResponse = this.service.getAttribute(attrRequest);
        assertNotNull(attrResponse);
        AttributeResponseType response = attrResponse.getGetAttributeResponse();
        assertNotNull(response);
       
        assertEquals("Rig_Type", request.getAttribute());
        AttributeResponseTypeChoice choice = response.getAttributeResponseTypeChoice();
        assertNotNull(choice);
        assertNull(choice.getError());
        assertNotNull(choice.getValue());
        assertEquals("fpga", choice.getValue());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#getAttribute(au.edu.uts.eng.remotelabs.rigclient.intf.types.GetAttribute)}.
     */
    @Test
    public void testGetAttributeSlaveActive()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        GetAttribute attrRequest = new GetAttribute();
        AttributeRequestType request = new AttributeRequestType();
        attrRequest.setGetAttribute(request);
        request.setAttribute("Rig_Type");
        request.setIdentityToken("Wrong...");
        request.setRequestor("mdiponio");
                
        GetAttributeResponse attrResponse = this.service.getAttribute(attrRequest);
        assertNotNull(attrResponse);
        AttributeResponseType response = attrResponse.getGetAttributeResponse();
        assertNotNull(response);
       
        assertEquals("Rig_Type", request.getAttribute());
        AttributeResponseTypeChoice choice = response.getAttributeResponseTypeChoice();
        assertNotNull(choice);
        assertNull(choice.getError());
        assertNotNull(choice.getValue());
        assertEquals("fpga", choice.getValue());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#getAttribute(au.edu.uts.eng.remotelabs.rigclient.intf.types.GetAttribute)}.
     */
    @Test
    public void testGetAttributeSlavePassive()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", true));
        
        GetAttribute attrRequest = new GetAttribute();
        AttributeRequestType request = new AttributeRequestType();
        attrRequest.setGetAttribute(request);
        request.setAttribute("Rig_Type");
        request.setIdentityToken("Wrong...");
        request.setRequestor("mdiponio");
                
        GetAttributeResponse attrResponse = this.service.getAttribute(attrRequest);
        assertNotNull(attrResponse);
        AttributeResponseType response = attrResponse.getGetAttributeResponse();
        assertNotNull(response);
       
        assertEquals("Rig_Type", request.getAttribute());
        AttributeResponseTypeChoice choice = response.getAttributeResponseTypeChoice();
        assertNotNull(choice);
        assertNull(choice.getError());
        assertNotNull(choice.getValue());
        assertEquals("fpga", choice.getValue());
    }
    
        /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#getAttribute(au.edu.uts.eng.remotelabs.rigclient.intf.types.GetAttribute)}.
     */
    @Test
    public void testGetAttributeNoAuth()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", true));
        
        GetAttribute attrRequest = new GetAttribute();
        AttributeRequestType request = new AttributeRequestType();
        attrRequest.setGetAttribute(request);
        request.setAttribute("Rig_Type");

        GetAttributeResponse attrResponse = this.service.getAttribute(attrRequest);
        assertNotNull(attrResponse);
        AttributeResponseType response = attrResponse.getGetAttributeResponse();
        assertNotNull(response);
       
        assertEquals("Rig_Type", request.getAttribute());
        AttributeResponseTypeChoice choice = response.getAttributeResponseTypeChoice();
        assertNotNull(choice);
        assertNotNull(choice.getValue());

    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#getStatus(au.edu.uts.eng.remotelabs.rigclient.intf.types.GetStatus)}.
     */
    @Test
    public void testGetStatus()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        assertTrue(this.rig.addSlave("carlo", true));
        
        GetStatus request = new GetStatus();
        request.setGetStatus(new NullType());
        
        GetStatusResponse response = this.service.getStatus(request);
        assertNotNull(response);
        
        StatusResponseType status = response.getGetStatusResponse();
        assertTrue(status.getIsInSession());
        assertFalse(status.getIsInMaintenance());
        assertFalse(status.getIsMonitorFailed());
        assertNull(status.getMaintenanceReason());
        assertNull(status.getMonitorReason());
        assertEquals("tmachet", status.getSessionUser());
        
        String[] slavesArr = status.getSlaveUsers();
        List<String> slaves = Arrays.asList(slavesArr);
        assertEquals(2, slaves.size());
        assertTrue(slaves.contains("mdiponio"));
        assertTrue(slaves.contains("carlo"));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#getStatus(au.edu.uts.eng.remotelabs.rigclient.intf.types.GetStatus)}.
     */
    @Test
    public void testGetStatusMaintenance()
    {
        assertTrue(this.rig.setMaintenance(true, "Carlo is having a bludge!", true));
        
        GetStatus request = new GetStatus();
        request.setGetStatus(new NullType());
        
        GetStatusResponse response = this.service.getStatus(request);
        assertNotNull(response);
        
        StatusResponseType status = response.getGetStatusResponse();
        assertFalse(status.getIsInSession());
        assertTrue(status.getIsInMaintenance());
        assertTrue(status.getIsMonitorFailed());
        assertNotNull(status.getMaintenanceReason());
        assertEquals("Carlo is having a bludge!", status.getMaintenanceReason());
        assertNotNull(status.getMonitorReason());
        assertEquals("Carlo is having a bludge!", status.getMonitorReason());
        assertNull(status.getSessionUser());
        assertNull(status.getSlaveUsers());
    }

    /**
     * test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#setMaintenance(au.edu.uts.eng.remotelabs.rigclient.intf.types.SetMaintenance)}.
     */
    @Test
    public void testSetMaintenance()
    {
        SetMaintenance request = new SetMaintenance();
        MaintenanceRequestType main = new MaintenanceRequestType();
        request.setSetMaintenance(main);
        main.setIdentityToken("abc123");
        main.setPutOffline(true);
        main.setRunTests(true);
        
        SetMaintenanceResponse resp = this.service.setMaintenance(request);
        assertNotNull(resp);
        OperationResponseType op = resp.getSetMaintenanceResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertEquals(0, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        
    }
    
    /**
     * test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#setMaintenance(au.edu.uts.eng.remotelabs.rigclient.intf.types.SetMaintenance)}.
     */
    @Test
    public void testSetMaintenanceWrongAuth()
    {
        SetMaintenance request = new SetMaintenance();
        MaintenanceRequestType main = new MaintenanceRequestType();
        request.setSetMaintenance(main);
        main.setIdentityToken("wrong...");
        main.setPutOffline(true);
        main.setRunTests(true);
        
        SetMaintenanceResponse resp = this.service.setMaintenance(request);
        assertNotNull(resp);
        OperationResponseType op = resp.getSetMaintenanceResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertEquals(3, err.getCode());
        assertNotNull(err.getOperation());
        assertNotNull(err.getReason());
        assertEquals("Invalid permission.", err.getReason());
        
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#setTestInterval(au.edu.uts.eng.remotelabs.rigclient.intf.types.SetTestInterval)}.
     */
    @Test
    public void testSetTestInterval()
    {
        SetTestInterval request = new SetTestInterval();
        TestIntervalRequestType inter = new TestIntervalRequestType();
        inter.setIdentityToken("abc123");
        inter.setInterval(100);
        request.setSetTestInterval(inter);
        
        SetTestIntervalResponse resp = this.service.setTestInterval(request);
        assertNotNull(resp);
        OperationResponseType op = resp.getSetTestIntervalResponse();
        assertNotNull(op);
        assertTrue(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(0, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#setTestInterval(au.edu.uts.eng.remotelabs.rigclient.intf.types.SetTestInterval)}.
     */
    @Test
    public void testSetTestIntervalNoAuth()
    {
        SetTestInterval request = new SetTestInterval();
        TestIntervalRequestType inter = new TestIntervalRequestType();
        inter.setIdentityToken("wrong...");
        inter.setInterval(100);
        request.setSetTestInterval(inter);
        
        SetTestIntervalResponse resp = this.service.setTestInterval(request);
        assertNotNull(resp);
        OperationResponseType op = resp.getSetTestIntervalResponse();
        assertNotNull(op);
        assertFalse(op.getSuccess());
        
        ErrorType err = op.getError();
        assertNotNull(err);
        assertEquals(3, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        assertEquals("Invalid permission.", err.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#isActivityDetectable(au.edu.uts.eng.remotelabs.rigclient.intf.types.IsActivityDetectable)}.
     */
    public void testIsActivityDetectable()
    {
        assertTrue(this.rig.assign("mdiponoio"));
        IsActivityDetectable request = new IsActivityDetectable();
        request.setIsActivityDetectable(new NullType());
        
        IsActivityDetectableResponse resp = this.service.isActivityDetectable(request);
        assertNotNull(resp);
        assertTrue(resp.getIsActivityDetectableResponse().getActivity());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.intf.RigClientService#isActivityDetectable(au.edu.uts.eng.remotelabs.rigclient.intf.types.IsActivityDetectable)}.
     */
    public void testIsActivityDetectableNoSession()
    {
        IsActivityDetectable request = new IsActivityDetectable();
        request.setIsActivityDetectable(new NullType());
      
        IsActivityDetectableResponse resp = this.service.isActivityDetectable(request);
        assertNotNull(resp);
        assertFalse(resp.getIsActivityDetectableResponse().getActivity());
    }
    
    public void testGetConfig() throws Exception
    {
        Field f = ConfigFactory.class.getDeclaredField("descriptions");
        f.setAccessible(true);
        f.set(null, null);
        
        System.setProperty("prop.descriptions", "META-INF/config-descriptions.xml");
        
        GetConfig request = new GetConfig();
        NullType nll = new NullType();
        request.setGetConfig(nll);
        
        GetConfigResponse resp = this.service.getConfig(request);
        assertNotNull(resp);
        
        ConfigResponseType type = resp.getGetConfigResponse();
        assertNotNull(type);
        
        ConfigPropertyType conf[] = type.getConfig();
        assertNotNull(conf);
        assertTrue(conf.length > 0);
        
        boolean res = false, ty = false, form = false, ex = false, def = false, des = false, st = false;
        
        for (ConfigPropertyType c : conf)
        {
            assertNotNull(c.getKey());
            assertNotNull(c.getValue());
            
            if (c.getType() != null) ty = true;
            if (c.getRestart()) res = true;
            if (c.getDefault() != null) def = true;
            if (c.getDescription() != null) des = true;
            if (c.getExample() != null) ex = true;
            if (c.getFormat() != null) form = true;
            if (c.getStanza() != null) st = true;
        }
        
        assertTrue(res);
        assertTrue(ty);
        assertTrue(form);
        assertTrue(ex);
        assertTrue(def);
        assertTrue(des);
        assertTrue(st);
    }
    
    public void testGetConfigSpecificProps() throws Exception
    {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 1; i <= 9; i++)
        {
            map.put("Key" + i, "Value" + i);
        }
        
        IConfig conf = createMock(IConfig.class);
        expect(conf.getAllProperties()).andReturn(map);
        
        IConfigDescriptions des = createMock(IConfigDescriptions.class);
        for (int i = 1; i <= 5; i++)
        {
            String ty;
            if (i == 1) ty = "STRING";
            else if (i == 2) ty = "INTEGER";
            else if (i == 3) ty = "FLOAT";
            else if (i == 4) ty = "BOOLEAN";
            else ty = "CHAR";
            
            expect(des.getPropertyDescription("Key" + i)).andReturn(new Property("Key" + i, "stanza", true, 
                    ty, "foo,<string>,bar", "default", true, "example", "This is a core property to set..."));
        }
        
        for (int i = 6; i <= 9; i++)
        {
            expect(des.getPropertyDescription("Key" + i)).andReturn(null);
        }
        
        replay(conf);
        replay(des);
        
        Field f = RigClientService.class.getDeclaredField("config");
        f.setAccessible(true);
        f.set(this.service, conf);
        f = ConfigFactory.class.getDeclaredField("descriptions");
        f.setAccessible(true);
        f.set(null, des);
        
        GetConfig request = new GetConfig();
        request.setGetConfig(new NullType());
        
        GetConfigResponse response = this.service.getConfig(request);
        assertNotNull(response);
        
        ConfigResponseType type = response.getGetConfigResponse();
        assertNotNull(type);
        
        ConfigPropertyType config[] = type.getConfig();
        assertNotNull(config);
        assertEquals(9, config.length);
        
        /* Order the rig. */
        ConfigPropertyType ordered[] = new ConfigPropertyType[9];
        for (ConfigPropertyType c : config)
        {
            ordered[Integer.parseInt(c.getKey().substring(c.getKey().length() - 1)) - 1] = c;
        }
        
        for (int i = 1; i <= 5; i++)
        {
            ConfigPropertyType c = ordered[i - 1];
            assertEquals("Key" + i, c.getKey());
            assertEquals("Value" + i, c.getValue());
            assertEquals("stanza", c.getStanza());
            assertEquals("foo,<string>,bar", c.getFormat());
            assertEquals("default", c.getDefault());
            assertTrue(c.getRestart());
            assertEquals("example", c.getExample());
            assertEquals("This is a core property to set...", c.getDescription());
        }
        
        for (int i = 6; i <= 9; i++)
        {
            ConfigPropertyType c = ordered[i - 1];
            assertEquals("Key" + i, c.getKey());
            assertEquals("Value" + i, c.getValue());
            assertNull(c.getType());
            assertNull(c.getDefault());
            assertNull(c.getDescription());
            assertNull(c.getExample());
            assertNull(c.getStanza());
            assertNull(c.getFormat());
        }
        
        verify(conf);
        verify(des); 
    }
    
    public void testSetConfig() throws Exception
    {
        IConfig conf = createMock(IConfig.class);
        conf.setProperty("Rig_Name", "foobar");
        expectLastCall();
        conf.serialise();
        expectLastCall();
        replay(conf);
        
        Field f = RigClientService.class.getDeclaredField("config");
        f.setAccessible(true);
        f.set(this.service, conf);

        SetConfig request = new SetConfig();
        SetConfigRequestType req = new SetConfigRequestType();
        request.setSetConfig(req);
        req.setIdentityToken("abc123");
        ConfigPropertyType prop = new ConfigPropertyType();
        req.setConfig(prop);
        prop.setKey("Rig_Name");
        prop.setValue("foobar");
        
        SetConfigResponse resp = this.service.setConfig(request);
        assertNotNull(resp);
        OperationResponseType op = resp.getSetConfigResponse();
        assertNotNull(op);
        
        assertTrue(op.getSuccess());
        assertNull(op.getError());
       
        verify(conf);
    }
    
    private void recusiveDelete(final File file) throws IOException
    {   
        /* Try to detect symbolic links so some mischievous moron doesn't 
         * symlink to their  server root directory or some other 
         * inconvenient place and end up potentially deleting the rig client
         * server. */
        /* DODGY This apparently works according to Java Bug ID: 4313887. */
        if (!file.getCanonicalPath().equals(file.getAbsolutePath()))
        {
            return;
        }
        
        if (file.isDirectory())
        {
            /* Delete all the nested directories and files. */
            for (File f : file.listFiles())
            {
                if (f.isDirectory())
                {
                    this.recusiveDelete(f);
                }
                else
                {
                    f.delete();
                }
            }
        }

        file.delete();
    }
}
