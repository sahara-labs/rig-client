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
 * @date 2nd November 2009
 *
 * Changelog:
 * - 02/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.control.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner;
import au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;

/**
 * Tests the <code>ConfiguredBatchRunner</code> class.
 */
public class ConfiguredBatchRunnerTester extends TestCase
{
    /** Object of class under test. */
    private ConfiguredBatchRunner runner;
    
    /** Mock configuration. */
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
        replay(this.mockConfig);
        
        this.runner = new ConfiguredBatchRunner(null, null);
        Field field = ConfiguredBatchRunner.class.getDeclaredField("batchConfig");
        field.setAccessible(true);
        field.set(this.runner, this.mockConfig);
       
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#init()}.
     */
    @Test
    public void testInit()
    {
        fail ("Not yet implemented"); // TODO
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#fileSizeTest()}.
     */
    @Test
    public void testFileSizeTest()
    {
        fail("Not yet implemented."); // TODO
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#fileExtensionTest()}.
     */
    @Test
    public void testFileExtensionTest()
    {
        try
        {
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("File_Extension"))
                .andReturn("class");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/IRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("fileExtensionTest");
            meth.setAccessible(true);
            assertTrue((Boolean)meth.invoke(this.runner));
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#magicNumberTest()}.
     */
    @Test
    public void testMagicNumverTest()
    {
        // TODO
        fail();
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#checkFile()}.
     */
    @Test
    public void testCheckFile()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#sync()}.
     */
    @Test
    public void testSync()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#run()}.
     */
    @Test
    public void testRun()
    {
        fail("Not yet implemented"); // TODO
    }

}
