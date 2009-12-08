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
package au.edu.uts.eng.remotelabs.rigclient.protocol.tests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.AllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.ErrorType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotificationRequestType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.NotifyResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.OperationResponseType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.ParamType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControlResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PrimitiveControlRequestType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.PrimitiveControlResponseType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.ReleaseResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocateResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveReleaseResponse;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveUserType;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.TypeSlaveUser;
import au.edu.uts.eng.remotelabs.rigclient.protocol.types.UserType;
import au.edu.uts.eng.remotelabs.rigclient.rig.ConfiguredRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session;
import au.edu.uts.eng.remotelabs.rigclient.type.RigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
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
        
        
        this.rig = RigFactory.getRigInstance();
        this.service = new RigClientService();
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#allocate(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate)}.
     */
    @Test
    public void testAllocate()
    {
        Allocate alloc = new Allocate();
        UserType user = new UserType();
        user.setUser("mdiponio");
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#allocate(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate)}.
     */
    @Test
    public void testAllocateInSession()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        Allocate alloc = new Allocate();
        UserType user = new UserType();
        user.setUser("mdiponio");
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#allocate(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Allocate)}.
     */
    @Test
    public void testAllocateInMain()
    {
        assertTrue(this.rig.setMaintenance(true, "The cylons", true));
        
        Allocate alloc = new Allocate();
        UserType user = new UserType();
        user.setUser("mdiponio");
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#release(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release)}.
     */
    @Test
    public void testRelease()
    {
        assertTrue(this.rig.assign("mdiponio"));
        assertTrue(this.rig.isSessionActive());
        
        Release rel = new Release();
        UserType user = new UserType();
        user.setUser("mdiponio");
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#release(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release)}.
     */
    @Test
    public void testReleaseNoSession()
    {               
        Release rel = new Release();
        UserType user = new UserType();
        user.setUser("mdiponio");
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#release(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Release)}.
     */
    @Test
    public void testReleaseDifferentUser()
    {
        assertTrue(this.rig.assign("tmachet"));
        Release rel = new Release();
        UserType user = new UserType();
        user.setUser("mdiponio");
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocate()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); /* Always the slave... */
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocatePassive()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); /* Always the slave... */
        slave.setType(new TypeSlaveUser("Passive"));
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocateWrongType()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); 
        slave.setType(new TypeSlaveUser("God_Mode")); /* Not the slave no more. */
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
        assertEquals("Invalid slave type parameter.", err.getReason());
        
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_ACTIVE));
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_PASSIVE));
        assertEquals(Session.NOT_IN, this.rig.isInSession("mdiponio"));	         
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocateNoSession()
    {
        
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); 
        slave.setType(new TypeSlaveUser("Active"));
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#slaveAllocate(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveAllocate)}.
     */
    @Test
    public void testSlaveAllocateMaster()
    {
        assertTrue(this.rig.assign("mdiponio"));
        SlaveAllocate request = new SlaveAllocate();
        SlaveUserType slave = new SlaveUserType();
        slave.setUser("mdiponio"); 
        slave.setType(new TypeSlaveUser("Active")); /* Trying to demote me... */
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#slaveRelease(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease)}.
     */
    @Test
    public void testSlaveRelease()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", false));
        
        SlaveRelease request = new SlaveRelease();
        UserType slave = new UserType();
        slave.setUser("mdiponio");
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#slaveRelease(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease)}.
     */
    @Test
    public void testSlaveReleasePassive()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", true));
        
        SlaveRelease request = new SlaveRelease();
        UserType slave = new UserType();
        slave.setUser("mdiponio");
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
        assertFalse(this.rig.hasPermission("mdiponio", Session.SLAVE_PASSIVE));
        assertTrue(this.rig.hasPermission("tmachet", Session.MASTER));
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#slaveRelease(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SlaveRelease)}.
     */
    @Test
    public void testSlaveReleaseNotIn()
    {
        assertTrue(this.rig.assign("tmachet"));
        
        SlaveRelease request = new SlaveRelease();
        UserType slave = new UserType();
        slave.setUser("mdiponio");
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#notify(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify)}.
     */
    @Test
    public void testNotify()
    {
        assertTrue(this.rig.assign("mdiponio"));
        
        Notify notify = new Notify();
        NotificationRequestType request = new NotificationRequestType();
        notify.setNotify(request);
        request.setMessage("This is a very important message");
        
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#notify(au.edu.uts.eng.remotelabs.rigclient.protocol.types.Notify)}.
     */
    @Test
    public void testNotifyNoSession()
    {
        Notify notify = new Notify();
        NotificationRequestType request = new NotificationRequestType();
        notify.setNotify(request);
        request.setMessage("This is a very important message");
        
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#performBatchControl(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformBatchControl)}.
     */
    @Test
    public void testPerformBatchControl()
    {
        fail("Not yet implemented"); // TODO
    }
 
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#abortBatchControl(au.edu.uts.eng.remotelabs.rigclient.protocol.types.AbortBatchControl)}.
     */
    @Test
    public void testAbortBatchControl()
    {
        assertTrue(this.rig.assign("mdiponio"));
        assertTrue(this.rig instanceof IRigControl);
        
       
        fail("Not yet implemented.");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#getBatchControlStatus(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetBatchControlStatus)}.
     */
    @Test
    public void testGetBatchControlStatus()
    {
        fail("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl)}.
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl)}.
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControlSlavePassive()
    {
        assertTrue(this.rig.assign("tmachet"));
        assertTrue(this.rig.addSlave("mdiponio", true));
        
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
        assertEquals(3, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        assertEquals("Invalid permission.", err.getReason());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl)}.
     */
    @Test
    public void testPerformPrimitiveControlWrongUser()
    {
        assertTrue(this.rig.assign("tmachet"));
        
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
        assertEquals(3, err.getCode());
        assertNotNull(err.getReason());
        assertNotNull(err.getOperation());
        assertEquals("Invalid permission.", err.getReason());
    }
    
     /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#performPrimitiveControl(au.edu.uts.eng.remotelabs.rigclient.protocol.types.PerformPrimitiveControl)}.
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
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#getAttribute(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetAttribute)}.
     */
    @Test
    public void testGetAttribute()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#getStatus(au.edu.uts.eng.remotelabs.rigclient.protocol.types.GetStatus)}.
     */
    @Test
    public void testGetStatus()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#setMaintenance(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetMaintenance)}.
     */
    @Test
    public void testSetMaintenance()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#setTestInterval(au.edu.uts.eng.remotelabs.rigclient.protocol.types.SetTestInterval)}.
     */
    @Test
    public void testSetTestInterval()
    {
        fail("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.protocol.RigClientService#isActivityDetectable(au.edu.uts.eng.remotelabs.rigclient.protocol.types.IsActivityDetectable)}.
     */
    public void testIsActivityDetectable()
    {
        fail("Not yet implemented"); // TODO
    }

}
