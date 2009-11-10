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
 * @date 22nd October 2009
 *
 * Changelog:
 * - 22/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.BatchResults;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.BatchState;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;

/**
 * Tests the <code>AbstractControllerRigTester</code> class.
 */
public class AbstractControlledRigTester extends TestCase
{
    /** Object of class under test. */
    private MockControlledRig rig;
    
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
            .andReturn("WARN");
        expect(this.mockConfig.getProperty("Action_Failure_Threshold"))
            .andReturn("2");
        replay(this.mockConfig);

        Field configField = ConfigFactory.class.getDeclaredField("instance");
        configField.setAccessible(true);
        configField.set(null, this.mockConfig);

        this.rig = new MockControlledRig();
        
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#performPrimitive(au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest)}.
     */
    @Test
    public void testPerformPrimitive()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#performBatch(java.lang.String)}.
     */
    @Test
    public void testPerformBatch()
    {
        /* Tania, extra points if you can answer the last question asked in the instruction file. */
        final String worldDominiationInstr = System.getProperty("user.dir") + 
                "/test/resources/Control/instructions.txt";
        try
        {
            /* Set up AbstractRig. */
            String wdBase = System.getProperty("user.dir") + "/test/resources/Control/WorkDirBase";
            reset(this.mockConfig);
            expect(this.mockConfig.getProperty("Batch_Working_Dir"))
                .andReturn(wdBase);
            expect(this.mockConfig.getProperty("Batch_Create_Nested_Dir", "true"))
                .andReturn("false");
            expect(this.mockConfig.getProperty("Batch_Flush_Env", "false"))
                .andReturn("false");
            expect(this.mockConfig.getProperty("Batch_Clean_Up", "false"))
                .andReturn("false");
            expect(this.mockConfig.getProperty("Batch_Instruct_File_Delete", "true"))
                .andReturn("false");
            expect(this.mockConfig.getProperty("Batch_Timeout", "60"))
                .andReturn("180");
            replay(this.mockConfig);
            
            /* Set up batch runner. */
            this.rig.setComm(System.getProperty("user.dir") + "/test/resources/Control/slow-cat.sh");
            List<String> args = new ArrayList<String>();
            args.add(worldDominiationInstr);
            args.add("1");
            args.add("stdout");
            this.rig.setArgs(args);
            this.rig.setEnv(new HashMap<String, String>());
           
            /* Pre batch state. */
            assertTrue(this.rig.getBatchState() == BatchState.CLEAR);
            assertFalse(this.rig.isBatchRunning());
            assertEquals(0, this.rig.getBatchProgress());
            BatchResults results = this.rig.getBatchResults();
            assertEquals(BatchState.CLEAR, results.getState());
            
            /* Run the batch process. */
            assertTrue(this.rig.performBatch(worldDominiationInstr, "Inner_Party_Tania_Machet"));
            assertTrue(this.rig.isBatchRunning());
            assertTrue(this.rig.getBatchState() == BatchState.IN_PROGRESS);
            
            int progress = -1;
            while (this.rig.isBatchRunning())
            {
                Thread.sleep(3000);
                assertFalse(progress == this.rig.getBatchProgress()); // This should progess
                progress = this.rig.getBatchProgress();
                System.out.println("Progress " + progress);
              //  assertTrue(progress > 0 && progress <= 100);
            }
            
            assertFalse(this.rig.isBatchRunning());
            assertEquals(100, this.rig.getBatchProgress());
            assertTrue(BatchState.COMPLETE == this.rig.getBatchState());
            results = this.rig.getBatchResults();
            assertNotNull(results);
            assertTrue(BatchState.COMPLETE == results.getState());
            assertEquals(0, results.getResultsFiles().length);
            assertEquals(worldDominiationInstr, results.getInstructionFile());
            
            String stdout = results.getStandardOut();
            assertNotNull(stdout);
            String lines[] = stdout.split("\n");
            assertEquals(40, lines.length);
            assertEquals("Ignorance is Strength", lines[0].substring(2).trim()); // !!!
            
            assertEquals(0, results.getExitCode());
            
            verify(this.mockConfig);
        }
        catch (Exception e)
        {
            fail("Exception: " + e.getClass().getName() + ", message: " + e.getMessage() + ".");
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#isBatchRunning()}.
     */
    @Test
    public void testIsBatchRunning()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#abortBatch()}.
     */
    @Test
    public void testAbortBatch()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#expungePrimitiveControllerCache()}.
     */
    @Test
    public void testExpungePrimitiveControllerCache()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#getBatchProgress()}.
     */
    @Test
    public void testGetBatchProgress()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#getBatchResults()}.
     */
    @Test
    public void testGetBatchResults()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig#getBatchState()}.
     */
    @Test
    public void testGetBatchState()
    {
        fail("Not yet implemented"); // TODO
    }

}
