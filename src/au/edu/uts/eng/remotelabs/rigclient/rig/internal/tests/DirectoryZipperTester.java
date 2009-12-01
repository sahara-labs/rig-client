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
 * - 08/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.internal.tests;


import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.rig.internal.DirectoryZipper;

/**
 * Tests the <code>DirectoryZipper</code> class.
 */
public class DirectoryZipperTester extends TestCase
{
    /** Object of class under test. */
    private DirectoryZipper zipper;
    
    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception
    {
        this.zipper = new DirectoryZipper(null);
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.DirectoryZipper#compressDirectory(String, String)}
     */
    @Test
    public void testCompressDirectory()
    {
        String from = System.getProperty("user.dir") + "/test/resources/BatchRunner/ResultsFiles";
        String to = System.getProperty("user.dir") + "/test/resources/BatchRunner/results.zip";
        
        assertTrue(this.zipper.compressDirectory(from, to));
        
        File file = new File(to);
        assertTrue(file.isFile());
        assertTrue(file.exists());
        
        try
        {
            FileInputStream fis = new FileInputStream(file);
            byte magic[] = new byte[2];
            assertEquals(2, fis.read(magic));
            assertEquals(0x50, magic[0]);
            assertEquals(0x4b, magic[1]);
            
            ZipFile zip = new ZipFile(file);
            Enumeration<? extends ZipEntry> zipEntries = zip.entries();
            Map<String, String> entries = new HashMap<String, String>();
            ZipEntry z;
            File f;
            while (zipEntries.hasMoreElements())
            {
                z = zipEntries.nextElement();
                f = new File(z.getName());
                entries.put(f.getName(), f.getParent());                
            }
            assertTrue(entries.containsKey("a.txt"));
            assertTrue(entries.containsKey("b.txt"));
            assertTrue(entries.containsKey("c.txt"));
            file.delete();
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + ", message: " + e.getMessage());
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.rig.internal.DirectoryZipper#compressDirectory(String, String)}
     */
    @Test
    public void testCompressDirectoryNoZip()
    {
        DirectoryZipper zip = new DirectoryZipper("BEST");
        String from = System.getProperty("user.dir") + "/test/resources/BatchRunner/ResultsFiles";
        String to = System.getProperty("user.dir") + "/test/resources/BatchRunner/results2";
        
        assertTrue(zip.compressDirectory(from, to));
        
        File file = new File(to + ".zip");
        assertTrue(file.isFile());
        assertTrue(file.exists());
        
        try
        {
            FileInputStream fis = new FileInputStream(file);
            byte magic[] = new byte[2];
            assertEquals(2, fis.read(magic));
            assertEquals(0x50, magic[0]);
            assertEquals(0x4b, magic[1]);
            
            file.delete();
        }
        catch (Exception e)
        {
            fail("Exception " + e.getClass().getName() + ", message: " + e.getMessage());
        }
    }
}
