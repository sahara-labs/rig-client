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
 * @date 8th November 2009
 *
 * Changelog:
 * - 08/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.control.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.control.DirectoryCopier;

/**
 * Tests the <code>DirectoryCopier</code> class.
 */
public class DirectoryCopierTester extends TestCase
{
    /** Object of class under test. */
    private DirectoryCopier copier;
    
    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp()
    {
        this.copier = new DirectoryCopier();
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.DirectoryCopier#copyDirectory(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testCopyDirectory() throws IOException
    {
        String from = System.getProperty("user.dir") + "/test/resources/BatchRunner/ResultsFiles";
        String to = System.getProperty("user.dir") + "/test/resources/BatchRunner/tmp";
        
        assertTrue(this.copier.copyDirectory(from, to));
        File toFile = new File(to);
        
        assertTrue(toFile.exists());
        assertTrue(toFile.isDirectory());
        
        File files[] = toFile.listFiles();
        assertTrue(files.length >= 3);
        this.recusiveDelete(toFile);
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.DirectoryCopier#recursiveCopy(String, String)}
     */
    @Test
    public void testRecursiveCopy()
    {
        String from = System.getProperty("user.dir") + "/test/resources/BatchRunner/ResultsFiles";
        String to = System.getProperty("user.dir") + "/test/resources/BatchRunner/tmp";
        
        try
        {
            this.copier.recursiveCopy(from, to);
            File toFile = new File(to);
            
            assertTrue(toFile.exists());
            assertTrue(toFile.isDirectory());
            
            File files[] = toFile.listFiles();
            assertTrue(files.length >= 3);
            this.recusiveDelete(toFile);
        }
        catch (IOException e)
        {
            fail("Exception: " + e.getClass().getName() + ", message: " + e.getMessage());
        }
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.control.DirectoryCopier#copyFile(java.io.File, java.io.File)}.
     */
    @Test
    public void testCopyFile()
    {
        File from = new File(System.getProperty("user.dir") + "/test/resources/BatchRunner/AbstractRig.class");
        File to = new File(System.getProperty("user.dir") + "/test/resources/");
        
        try
        {
            this.copier.copyFile(from, to);
            
            File copy = new File(to, from.getName());
            assertTrue(copy.exists());
            assertEquals(from.length(), copy.length());
            copy.delete();
        }
        catch (Exception e)
        {
            fail("Exception: " + e.getClass().getName() + ", message: " + e.getMessage());
        }
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
