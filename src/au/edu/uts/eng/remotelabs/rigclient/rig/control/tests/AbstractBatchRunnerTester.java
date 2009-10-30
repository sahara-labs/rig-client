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
 * - 29/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.control.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;

/**
 * Tests the abstract batch runner.  Batch runner is intended to be run in 
 * its own thread, but for the purposes of testing it, runs in the same
 * thread as the test case.
 */
public class AbstractBatchRunnerTester extends TestCase
{
    /** Object of class under test. */
    private AbstractBatchRunner runner;
    
    /** Mock configuration class. */
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
        
        Field configField = ConfigFactory.class.getDeclaredField("instance");
        configField.setAccessible(true);
        configField.set(null, this.mockConfig);
        
        this.runner = new MockBatchRunner(null);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#invoke()}.
     */
    @Test
    public void testInvoke()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Clean_Up_Batch"));
        
        
        // TODO
        fail();
    }

    

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#getBatchStandardOut()}.
     */
    @Test
    public void testGetBatchStandardOut()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#getBatchStandardError()}.
     */
    @Test
    public void testGetBatchStandardError()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#isStarted()}.
     */
    @Test
    public void testIsStarted()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#isRunning()}.
     */
    @Test
    public void testIsRunning()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#isFailed()}.
     */
    @Test
    public void testIsFailed()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#getResultsFiles()}.
     */
    @Test
    public void testGetResultsFiles()
    {
        
        try
        {
            String wdBase = System.getProperty("user.dir") + "/test/resources/BatchRunner";
            String wd = wdBase + "/ResultsFiles";
            
            Field wdBaseField = AbstractBatchRunner.class.getDeclaredField("workingDirBase");
            wdBaseField.setAccessible(true);
            wdBaseField.set(this.runner, wdBase);
            
            Field wdField = AbstractBatchRunner.class.getDeclaredField("workingDir");
            wdField.setAccessible(true);
            wdField.set(this.runner, wd);
            
            Method meth = AbstractBatchRunner.class.getDeclaredMethod("detectResultsFiles");
            meth.setAccessible(true);
            meth.invoke(this.runner);
            
            List<String> results = this.runner.getResultsFiles();
            assertTrue(results.size() > 3); // There is at least a .svn directory also in this directory
            assertTrue(results.contains("a.txt"));
            assertTrue(results.contains("b.txt"));
            assertTrue(results.contains("c.txt"));
        }
        catch (Exception e)
        {
            fail(e.getClass().getName() + " - " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#cleanup()}.
     * This is a highly risky test due to the risky nature of the tested 
     * operation (i.e. don't fraking symlink to somewhere dangerous). 
     */
    @Test
    public void testCleanup()
    {
        try
        {
            final String wdBase = System.getProperty("user.dir") + "/test/resources/BatchRunner";
            final String wd = wdBase + "/To_Be_Deleted";
            final String tDir = "/testdirectory";
            final String tFile = "/testfile";
            
            /* Create directory to be deleted. */
            assertTrue(new File(wd).mkdir());
            
            /* Create directory in directory to be deleted. */
            assertTrue(new File(wd + tDir).mkdir());
            
            /* Create files in directories to be deleted. */
            for (int i = 1; i <= 10; i++)
            {
                assertTrue(new File(wd + tFile + i).createNewFile());
                assertTrue(new File(wd + tDir + tFile + i).createNewFile());
            }
            
            Field wdBaseField = AbstractBatchRunner.class.getDeclaredField("workingDirBase");
            wdBaseField.setAccessible(true);
            wdBaseField.set(this.runner, wdBase);
            
            Field wdField = AbstractBatchRunner.class.getDeclaredField("workingDir");
            wdField.setAccessible(true);
            wdField.set(this.runner, wd);
            
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("Batch_Clean_Up", "false"))
                .andReturn("true"); // True will run recursive delete
            replay(this.mockConfig);
            
            Method meth = AbstractBatchRunner.class.getDeclaredMethod("cleanup");
            meth.setAccessible(true);
            meth.invoke(this.runner); 
            
            assertFalse(new File(wd).exists());
            assertFalse(new File(wd + tDir).exists());
            for (int i = 1; i <= 10; i++)
            {
                assertFalse(new File(wd + tFile + i).exists());
                assertFalse(new File(wd + tDir + tFile + i).exists());
            }
            
            verify(this.mockConfig); 
        }
        catch (Exception e)
        {
            fail(e.getClass().getName() + " - " + e.getMessage());
        }
    }

    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#cleanup()}.
     * This is a highly risky test due to the risky nature of the tested 
     * operation (i.e. don't fraking symlink to somewhere dangerous). 
     */
    @Test
    public void testCleanupConfigOff()
    {
        try
        {
            final String wdBase = System.getProperty("user.dir") + "/test/resources/BatchRunner";
            final String wd = wdBase + "/To_Not_Be_Deleted";
       
            /* Create directory to be deleted. */
            assertTrue(new File(wd).mkdir());
            
            Field wdBaseField = AbstractBatchRunner.class.getDeclaredField("workingDirBase");
            wdBaseField.setAccessible(true);
            wdBaseField.set(this.runner, wdBase);
            
            Field wdField = AbstractBatchRunner.class.getDeclaredField("workingDir");
            wdField.setAccessible(true);
            wdField.set(this.runner, wd);
            
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("Batch_Clean_Up", "false"))
                .andReturn("false");
            replay(this.mockConfig);
            
            Method meth = AbstractBatchRunner.class.getDeclaredMethod("cleanup");
            meth.setAccessible(true);
            meth.invoke(this.runner); 
            
            assertTrue(new File(wd).exists());
            assertTrue(new File(wd).delete());
            
            verify(this.mockConfig); 
        }
        catch (Exception e)
        {
            fail(e.getClass().getName() + " - " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#getTimeStamp(char, char, char)}.
     */
    @Test
    public void testGetTimeStamp()
    {
        try
        {
            Method meth = AbstractBatchRunner.class.getDeclaredMethod("getTimeStamp", char.class, char.class, char.class);
            meth.setAccessible(true);
            String ts = (String) meth.invoke(this.runner, '-', '-', '-');
            String[] parts = ts.split("-");
            
            /* Format should be day - month - year - hour - minute - second. */
            Calendar cal = Calendar.getInstance();
            assertEquals(cal.get(Calendar.DAY_OF_MONTH), Integer.parseInt(parts[0]));
            assertEquals(cal.get(Calendar.MONTH) + 1, Integer.parseInt(parts[1]));
            assertEquals(cal.get(Calendar.YEAR), Integer.parseInt(parts[2]));
            assertEquals(cal.get(Calendar.HOUR_OF_DAY), Integer.parseInt(parts[3]));
            assertEquals(cal.get(Calendar.MINUTE), Integer.parseInt(parts[4]));
            assertTrue(Integer.parseInt(parts[5]) >= 0 && Integer.parseInt(parts[5]) < 60);
        }
        catch (Exception e)
        {
            fail(e.getClass().getName() + " - " + e.getMessage());
        }
        
    }

}
