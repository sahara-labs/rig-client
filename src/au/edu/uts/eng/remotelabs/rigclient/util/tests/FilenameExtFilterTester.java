/**
 * SAHARA Rig Client
 * 
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 *
 * @license See LICENSE in the top level directory for complete license terms.
 *
 * Copyright (c) 2010, University of Technology, Sydney
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
 * @date 25th August 2010 
 */
package au.edu.uts.eng.remotelabs.rigclient.util.tests;

import java.io.File;

import junit.framework.TestCase;
import au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.PropertiesIntersectionConfig.FilenameExtFiler;

/**
 * Tests the {@link PropertiesIntersectionConfig.FilenameExtFilter} class.
 */
public class FilenameExtFilterTester extends TestCase
{
    /** Object of class under test. */
    private FilenameExtFiler filter;
    
    @Override
    public void setUp()
    {
        this.filter = (new PropertiesIntersectionConfig()).new FilenameExtFiler("props", "rc");
    }
    
    public void testFilesAccept()
    {
        File f = new File("/foo/bar/baz");
        assertFalse(this.filter.accept(f, "foo.properties"));
        assertFalse(this.filter.accept(f, "foo.car"));
    }
    
    public void testFilesRefuse()
    {
        File f = new File("/foo/bar/baz");
        assertFalse(this.filter.accept(f, "foo.properties"));
        assertFalse(this.filter.accept(f, "foo.car"));
    }
}
