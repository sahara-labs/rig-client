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
 * @author <First name> <Last name> (mdiponio)
 * @date <day> <month> 2009
 *
 * Changelog:
 * - 29/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.IPrimitiveController;
import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveCache;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;

/**
 * Tests the <code>PrimitiveCache</code> class.
 */
public class PrimitiveCacheTester extends TestCase
{
    /** Object of class under test. */
    private PrimitiveCache cache;
    
    /** Configuration. */
    private IConfig mockConfig;

    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception
    {   
        this.mockConfig = createMock(IConfig.class);
        expect(this.mockConfig.getProperty("Logger_Type"))
            .andReturn("SystemErr");
        expect(this.mockConfig.getProperty("Log_Level"))
            .andReturn("DEBUG");
        expect(this.mockConfig.getProperty("Package_Prefixes"))
            .andReturn("au.edu.uts.eng.remotelabs;au.edu.uts.eng.remotelabs.rigclient.rig.primitive.tests");
        replay(this.mockConfig);
        
        ConfigFactory.getInstance();
        Field field = ConfigFactory.class.getDeclaredField("instance");        
        field.setAccessible(true);
        field.set(null, this.mockConfig);
        
        this.cache = new PrimitiveCache();
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveCache#getInstance(java.lang.String)}.
     */
    @Test
    public void testGetInstance()
    {
       IPrimitiveController controller = this.cache.getInstance("au.edu.uts.eng.remotelabs.rigclient.rig." +
       		"primitive.tests.MockController");
       assertNotNull(controller);
       
       assertTrue(controller instanceof MockController);
       MockController mock = (MockController)controller;
       assertTrue(mock.isInitialised());
       assertFalse(mock.isCleanedUp());
       assertEquals(1, mock.callCount());
       assertEquals(2, mock.callCount());
       
       /* Getting the same class should get the same instance. */
       IPrimitiveController sameCont = this.cache.getInstance("au.edu.uts.eng.remotelabs.rigclient.rig." +
       		"primitive.tests.MockController");
       assertNotNull(sameCont);
       assertEquals(controller, sameCont);
       
       assertTrue(sameCont instanceof MockController);
       MockController sameMock = (MockController)sameCont;
       assertEquals(sameMock, mock);
       assertTrue(sameMock.isInitialised());
       assertFalse(sameMock.isCleanedUp());
       assertEquals(3, sameMock.callCount());
       
       /* Getting the same class should get the same instance - consistently. */
       IPrimitiveController sameContAgain = this.cache.getInstance("au.edu.uts.eng.remotelabs.rigclient.rig." +
       		"primitive.tests.MockController");
       assertNotNull(sameContAgain);
       assertEquals(sameContAgain, controller);
       assertEquals(sameContAgain, sameCont);
       
       assertTrue(sameContAgain instanceof MockController);
       MockController sameMockAgain = (MockController)sameContAgain;
       assertEquals(mock, sameMockAgain);
       assertEquals(sameMock, sameMockAgain);
       assertTrue(sameMockAgain.isInitialised());
       assertFalse(sameMockAgain.isCleanedUp());
       assertEquals(4, sameMockAgain.callCount());
       assertEquals(5, sameMockAgain.callCount());
       
       this.cache.expungeCache();
       assertTrue(mock.isCleanedUp());
       
       /* This should be new instance after a cache cleanup. */
       IPrimitiveController newCont = this.cache.getInstance("au.edu.uts.eng.remotelabs.rigclient.rig." +
       "primitive.tests.MockController");
       assertNotNull(sameCont);
       assertFalse(controller.equals(newCont));
       
       assertTrue(newCont instanceof MockController);
       MockController newMock = (MockController)newCont;
       assertFalse(mock.equals(newMock));
       assertTrue(newMock.isInitialised());
       assertFalse(newMock.isCleanedUp());
       assertEquals(1, newMock.callCount());
       assertEquals(2, newMock.callCount());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveCache#getInstance()}.
     */
    @Test
    public void testGetInstanceNotQualified()
    {
        IPrimitiveController controller = this.cache.getInstance("MockController");
       assertNotNull(controller);
       
       assertTrue(controller instanceof MockController);
       MockController mock = (MockController)controller;
       assertTrue(mock.isInitialised());
       assertFalse(mock.isCleanedUp());
       assertEquals(1, mock.callCount());
       assertEquals(2, mock.callCount());
       
       /* Getting the same class should get the same instance. */
       IPrimitiveController sameCont = this.cache.getInstance("MockController");
       assertNotNull(sameCont);
       assertEquals(controller, sameCont);
       
       assertTrue(sameCont instanceof MockController);
       MockController sameMock = (MockController)sameCont;
       assertEquals(sameMock, mock);
       assertTrue(sameMock.isInitialised());
       assertFalse(sameMock.isCleanedUp());
       assertEquals(3, sameMock.callCount());
       
       /* Getting the same class should get the same instance - consistently. */
       IPrimitiveController sameContAgain = this.cache.getInstance("MockController");
       assertNotNull(sameContAgain);
       assertEquals(sameContAgain, controller);
       assertEquals(sameContAgain, sameCont);
       
       assertTrue(sameContAgain instanceof MockController);
       MockController sameMockAgain = (MockController)sameContAgain;
       assertEquals(mock, sameMockAgain);
       assertEquals(sameMock, sameMockAgain);
       assertTrue(sameMockAgain.isInitialised());
       assertFalse(sameMockAgain.isCleanedUp());
       assertEquals(4, sameMockAgain.callCount());
       assertEquals(5, sameMockAgain.callCount());
       
       this.cache.expungeCache();
       assertTrue(mock.isCleanedUp());
       
       /* This should be new instance after a cache cleanup. */
       IPrimitiveController newCont = this.cache.getInstance("MockController");
       assertNotNull(sameCont);
       assertFalse(controller.equals(newCont));
       
       assertTrue(newCont instanceof MockController);
       MockController newMock = (MockController)newCont;
       assertFalse(mock.equals(newMock));
       assertTrue(newMock.isInitialised());
       assertFalse(newMock.isCleanedUp());
       assertEquals(1, newMock.callCount());
       assertEquals(2, newMock.callCount());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveCache#getInstance()}.
     */
    public void testGetInstanceDifferent()
    {
        IPrimitiveController controller = this.cache.getInstance("MockController");
        assertNotNull(controller);
        
        IPrimitiveController derived = this.cache.getInstance("DerivedMockController");
        assertNotNull(derived);
        
        assertFalse(controller == derived);
        assertFalse(derived.equals(controller));
        assertTrue(controller instanceof MockController);
        assertTrue(derived instanceof DerivedMockController);
        
        MockController mock = (MockController)controller;
        DerivedMockController deMock = (DerivedMockController)derived;
        
        assertEquals(1, mock.callCount());
        assertEquals(2, mock.callCount());
        assertEquals(3, mock.callCount());
        assertEquals(1, deMock.callCount());
        assertEquals(2, deMock.callCount());
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveCache#removeCachedInstance(String)}.
     */
    public void testRemoveCachedInstance()
    {
        IPrimitiveController controller = this.cache.getInstance("MockController");
        assertNotNull(controller);
        
        IPrimitiveController derived = this.cache.getInstance("DerivedMockController");
        assertNotNull(derived);
        
        assertFalse(controller == derived);
        assertFalse(derived.equals(controller));
        assertTrue(controller instanceof MockController);
        assertTrue(derived instanceof DerivedMockController);
        
        MockController mock = (MockController)controller;
        DerivedMockController deMock = (DerivedMockController)derived;
        
        assertEquals(1, mock.callCount());
        assertEquals(2, mock.callCount());
        assertEquals(3, mock.callCount());
        assertEquals(1, deMock.callCount());
        assertEquals(2, deMock.callCount());
        
        this.cache.removeCachedInstance("MockController");
        IPrimitiveController newController = this.cache.getInstance("MockController");
        assertNotNull(newController);
        assertFalse(newController.equals(controller));
        assertFalse(newController == controller);
        
        mock = (MockController)newController;
        assertEquals(1, mock.callCount());
        assertEquals(2, mock.callCount());
        
        IPrimitiveController sameDerived = this.cache.getInstance("DerivedMockController");
        assertNotNull(derived);
        assertTrue(sameDerived.equals(derived));
        assertTrue(sameDerived == derived);
        
        deMock = (DerivedMockController)sameDerived;
        assertEquals(3, deMock.callCount());
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.primitive.PrimitiveCache#getInstance()}.
     */
    @Test
    public void testGetInstanceNonExistent()
    {
       IPrimitiveController controller = this.cache.getInstance("FooController");
       assertNull(controller);
       
       controller = this.cache.getInstance("FooController");
       assertNull(controller);
    }
}
