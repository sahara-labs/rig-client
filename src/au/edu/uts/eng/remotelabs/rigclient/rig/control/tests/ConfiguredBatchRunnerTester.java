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
import static org.easymock.EasyMock.verify;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner;
import au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;

/**
 * Tests the <code>ConfiguredBatchRunner</code> class. Successful 
 * interpretation of this test case requires a working knowledge
 * of <em>HexSpeak</em>.
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
        
        this.runner = new ConfiguredBatchRunner("/var/lib/instructions", "tuser");
        Field field = ConfiguredBatchRunner.class.getDeclaredField("batchConfig");
        field.setAccessible(true);
        field.set(this.runner, this.mockConfig);
        
        field = AbstractBatchRunner.class.getDeclaredField("workingDir");
        field.setAccessible(true);
        field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/ResultsFiles");
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#init()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testInit()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Batch_Exec"))
            .andReturn("/bin/ls");
        expect(this.mockConfig.getProperty("Batch_Exec_Arg_1"))
            .andReturn("-l");
        expect(this.mockConfig.getProperty("Batch_Exec_Arg_2"))
            .andReturn("__YEAR__");
        expect(this.mockConfig.getProperty("Batch_Exec_Arg_3"))
            .andReturn(null);
        expect(this.mockConfig.getProperty("Batch_Env_1"))
            .andReturn("user:__USER__");
        expect(this.mockConfig.getProperty("Batch_Env_2"))
            .andReturn("foo:bar");
        expect(this.mockConfig.getProperty("Batch_Env_3"))
            .andReturn(null);
        replay(this.mockConfig);
        
        try
        {
            Method meth = AbstractBatchRunner.class.getDeclaredMethod("init");
            meth.setAccessible(true);
            assertTrue((Boolean)meth.invoke(this.runner));
            
            Field field = AbstractBatchRunner.class.getDeclaredField("command");
            field.setAccessible(true);
            assertEquals("/bin/ls", (String)field.get(this.runner));
            
            field = AbstractBatchRunner.class.getDeclaredField("commandArgs");
            field.setAccessible(true);
            List<String> args = (List<String>)field.get(this.runner);
            assertEquals(2, args.size());
            assertTrue(args.contains("-l"));
            assertTrue(args.contains(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
            
            field = AbstractBatchRunner.class.getDeclaredField("envMap");
            field.setAccessible(true);
            Map<String, String> env = (Map<String, String>)field.get(this.runner);
            assertEquals(2, env.size());
            assertTrue(env.containsKey("user"));
            assertEquals("tuser", env.get("user"));
            assertTrue(env.containsKey("foo"));
            assertEquals("bar", env.get("foo"));
        }
        catch (Exception e)
        {
            fail("Exception: " + e.getClass().getName() + ", message: " + e.getMessage());
        }        
        verify(this.mockConfig);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#fileSizeTest()}.
     */
    @Test
    public void testFileSizeTest()
    {
        try
        {
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("Batch_Max_File_Size"))
                .andReturn("50");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("fileSizeTest");
            meth.setAccessible(true);
            assertTrue((Boolean)meth.invoke(this.runner));
            
            verify(this.mockConfig);
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#fileSizeTest()}.
     */
    @Test
    public void testFileSizeTestFail()
    {
        try
        {
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("Batch_Max_File_Size"))
                .andReturn("10");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("fileSizeTest");
            meth.setAccessible(true);
            assertFalse((Boolean)meth.invoke(this.runner));
            
            verify(this.mockConfig);
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
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
            expect(this.mockConfig.getProperty("Batch_File_Extension"))
                .andReturn("class");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("fileExtensionTest");
            meth.setAccessible(true);
            assertTrue((Boolean)meth.invoke(this.runner));
            
            verify(this.mockConfig);
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#fileExtensionTest()}.
     */
    @Test
    public void testFileExtensionTestFailed()
    {
        try
        {
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("Batch_File_Extension"))
                .andReturn("properties");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("fileExtensionTest");
            meth.setAccessible(true);
            assertFalse((Boolean)meth.invoke(this.runner));
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
        try
        {
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("Batch_File_Magic_Number"))
                .andReturn("CAFEBABE");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            /* Java class files have a cool magic number - CAFEBABE. */
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("magicNumberTest");
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
    public void testMagicNumverTestPrefixed()
    {
        try
        {
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("Batch_File_Magic_Number"))
                .andReturn("0xCAFEBABE");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            /* Java class files have a cool magic number - CAFEBABE. */
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("magicNumberTest");
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
    public void testMagicNumverTestFailed()
    {
        try
        {
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("Batch_File_Magic_Number"))
                .andReturn("0xDEADBEEF"); /* Another awesome magic number. */
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            /* Java class files have a cool magic number - CAFEBABE. */
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("magicNumberTest");
            meth.setAccessible(true);
            assertFalse((Boolean)meth.invoke(this.runner));
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#checkFile()}.
     */
    @Test
    public void testCheckFile()
    {
        try
        {
            reset(this.mockConfig);
            /* Run file size test. */
            expect(this.mockConfig.getProperty("Batch_Test_Max_File_Size", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_Max_File_Size"))
                .andReturn("100");
            expect(this.mockConfig.getProperty("Batch_Test_Magic_Number", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_File_Magic_Number"))
                .andReturn("0xcafebabe");
            expect(this.mockConfig.getProperty("Batch_Test_File_Extension", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_File_Extension"))
                .andReturn(".class");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            /* Java class files have a cool magic number - 0xCAFEBABE. */
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("checkFile");
            meth.setAccessible(true);
            assertTrue((Boolean)meth.invoke(this.runner));
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#checkFile()}.
     */
    @Test
    public void testCheckFileFailMaxSize()
    {
        try
        {
            reset(this.mockConfig);
            /* Run file size test. */
            expect(this.mockConfig.getProperty("Batch_Test_Max_File_Size", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_Max_File_Size"))
                .andReturn("1");
            expect(this.mockConfig.getProperty("Batch_Test_Magic_Number", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_File_Magic_Number"))
                .andReturn("0xcafebabe");
            expect(this.mockConfig.getProperty("Batch_Test_File_Extension", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_File_Extension"))
                .andReturn(".class");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            /* Java class files have a cool magic number - 0xCAFEBABE. */
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("checkFile");
            meth.setAccessible(true);
            assertFalse((Boolean)meth.invoke(this.runner));
            assertEquals(11, this.runner.getErrorCode());
            assertEquals("Instruction file is too large. Maximum allowable file size is 1kB.", this.runner.getErrorReason());
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#checkFile()}.
     */
    @Test
    public void testCheckFileFailMagic()
    {
        try
        {
            reset(this.mockConfig);
            /* Run file size test. */
            expect(this.mockConfig.getProperty("Batch_Test_Max_File_Size", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_Max_File_Size"))
                .andReturn("100");
            expect(this.mockConfig.getProperty("Batch_Test_Magic_Number", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_File_Magic_Number"))
                .andReturn("0xABADBABE"); // That's a bad babe!
            expect(this.mockConfig.getProperty("Batch_Test_File_Extension", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_File_Extension"))
                .andReturn(".class");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            /* Java class files have a cool magic number - 0xCAFEBABE. */
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("checkFile");
            meth.setAccessible(true);
            assertFalse((Boolean)meth.invoke(this.runner));
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#checkFile()}.
     */
    @Test
    public void testCheckFileFailExtension()
    {
        try
        {
            reset(this.mockConfig);
            /* Run file size test. */
            expect(this.mockConfig.getProperty("Batch_Test_Max_File_Size", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_Max_File_Size"))
                .andReturn("100");
            expect(this.mockConfig.getProperty("Batch_Test_Magic_Number", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_File_Magic_Number"))
                .andReturn("0xcafebabe");
            expect(this.mockConfig.getProperty("Batch_Test_File_Extension", "false"))
                .andReturn("true");
            expect(this.mockConfig.getProperty("Batch_File_Extension"))
                .andReturn(".java");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            /* Java class files have a cool magic number - 0xCAFEBABE. */
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("checkFile");
            meth.setAccessible(true);
            assertFalse((Boolean)meth.invoke(this.runner));
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#checkFile()}.
     */
    @Test
    public void testCheckFileNoTestFailAll()
    {
        try
        {
            reset(this.mockConfig);
            /* Run file size test. */
            expect(this.mockConfig.getProperty("Batch_Test_Max_File_Size", "false"))
                .andReturn("false");
            expect(this.mockConfig.getProperty("Batch_Max_File_Size"))
                .andReturn("1");
            expect(this.mockConfig.getProperty("Batch_Test_Magic_Number", "false"))
                .andReturn("false");
            expect(this.mockConfig.getProperty("Batch_File_Magic_Number"))
                .andReturn("0xCAFED00D"); // Bring on those beans cafe dude!
            expect(this.mockConfig.getProperty("Batch_Test_File_Extension", "false"))
                .andReturn("false");
            expect(this.mockConfig.getProperty("Batch_File_Extension"))
                .andReturn(".java");
            replay(this.mockConfig);
            
            Field field = AbstractBatchRunner.class.getDeclaredField("fileName");
            field.setAccessible(true);
            /* Java class files have a cool magic number - 0xCAFEBABE. */
            field.set(this.runner, System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
            
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("checkFile");
            meth.setAccessible(true);
            assertTrue((Boolean)meth.invoke(this.runner));
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#sync()}.
     */
    @Test
    public void testSync()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Batch_Sync_Results_Dir", "false"))
            .andReturn("true");
        expect(this.mockConfig.getProperty("Batch_Sync_Dir_Destination"))
        .andReturn(System.getProperty("user.dir") + "/test/resources/BatchRunner/tmp/");
        expect(this.mockConfig.getProperty("Batch_Sync_Dir_Name"))
            .andReturn("__USER__/__ISO8601__");
        expect(this.mockConfig.getProperty("Batch_Compress_Dir", "false"))
            .andReturn("false");
        replay(this.mockConfig);
        
        try
        {   
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("sync");
            meth.setAccessible(true);
            assertTrue((Boolean)meth.invoke(this.runner));
            
            File file = new File(System.getProperty("user.dir") + "/test/resources/BatchRunner/tmp");
            assertTrue(file.isDirectory());
            assertTrue(file.exists());
            
            this.recusiveDelete(file);
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
        
        verify(this.mockConfig);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#sync()}.
     */
    @Test
    public void testSyncCompress()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Batch_Sync_Results_Dir", "false"))
            .andReturn("true");
        expect(this.mockConfig.getProperty("Batch_Sync_Dir_Destination"))
        .andReturn(System.getProperty("user.dir") + "/test/resources/BatchRunner/");
        expect(this.mockConfig.getProperty("Batch_Sync_Dir_Name"))
            .andReturn("__USER__");
        expect(this.mockConfig.getProperty("Batch_Compress_Dir", "false"))
            .andReturn("true");
        expect(this.mockConfig.getProperty("Batch_Compression_Level", "DEFAULT"))
            .andReturn("FASTEST");
        replay(this.mockConfig);
        
        try
        {   
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("sync");
            meth.setAccessible(true);
            assertTrue((Boolean)meth.invoke(this.runner));
            
            File file = new File(System.getProperty("user.dir") + "/test/resources/BatchRunner/tuser.zip");
            assertTrue(file.isFile());
            assertTrue(file.exists());
            file.delete();
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
        
        verify(this.mockConfig);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.ConfiguredBatchRunner#sync()}.
     */
    @Test
    public void testSyncPassNoSync()
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Batch_Sync_Results_Dir", "false"))
            .andReturn("false");
        replay(this.mockConfig);
        
        try
        {
            Method meth = ConfiguredBatchRunner.class.getDeclaredMethod("sync");
            meth.setAccessible(true);
            assertTrue((Boolean)meth.invoke(this.runner));
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + " message " + e.getMessage());
        }
        
        verify(this.mockConfig);
    }
    
    /**
     * Recursively delete directory and contents.
     * 
     * @param file directory to delete
     * @throws IOException
     */
    private void recusiveDelete(final File file) throws IOException
    {   
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
