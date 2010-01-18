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
 * @author <First> <Last> (mdiponio)
 * @date <Day> <Month> 2010
 *
 * Changelog:
 * - 18/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.main.tests;

import junit.framework.TestCase;

import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.main.RigClientDefines;

/**
 * Tests the {@link RigClientDefines} class.
 */
public class RigClientDefinesTester extends TestCase
{
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.main.RigClientDefines#prependPackage(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testPrependPackage()
    {
        String pack = "au.edu.uts.eng.remotelabs";
        String clazz = ".rigclient.main.RigClient";
        
        String qualified = RigClientDefines.prependPackage(pack, clazz);
        assertNotNull(qualified);
        assertTrue(qualified.startsWith(pack));
        assertTrue(qualified.endsWith(clazz));
        try
        {
            Class<?> cl = Class.forName(qualified);
            assertNotNull(cl);
        }
        catch (ClassNotFoundException e)
        {
            fail("Class " + qualified + " should exist.");
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.main.RigClientDefines#prependPackage(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testPrependPackageSpace()
    {
        String pack = "  au.edu.uts.eng.remotelabs    ";
        String clazz = "  .rigclient.main.RigClient  ";
        
        String qualified = RigClientDefines.prependPackage(pack, clazz);
        System.out.println(qualified);
        assertNotNull(qualified);
        assertTrue(qualified.startsWith(pack.trim()));
        assertTrue(qualified.endsWith(clazz.trim()));
        
        try
        {
            Class<?> cl = Class.forName(qualified);
            assertNotNull(cl);
        }
        catch (ClassNotFoundException e)
        {
            fail("Class " + qualified + " should exist.");
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.main.RigClientDefines#prependPackage(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testPrependPackageDotMissing()
    {
        String pack = "au.edu.uts.eng.remotelabs";
        String clazz = "rigclient.main.RigClient";
        
        String qualified = RigClientDefines.prependPackage(pack, clazz);

        assertNotNull(qualified);
        assertTrue(qualified.startsWith(pack));
        assertTrue(qualified.endsWith(clazz));
        try
        {
            Class<?> cl = Class.forName(qualified);
            assertNotNull(cl);
        }
        catch (ClassNotFoundException e)
        {
            fail("Class " + qualified + " should exist.");
        }
    }
    
    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.main.RigClientDefines#prependPackage(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testPrependPackageTooManyDots()
    {
        String pack = "au.edu.uts.eng.remotelabs.";
        String clazz = ".rigclient.main.RigClient";
        
        String qualified = RigClientDefines.prependPackage(pack, clazz);

        assertNotNull(qualified);
        assertTrue(qualified.startsWith(pack));
        assertTrue(qualified.endsWith(clazz));
        try
        {
            Class<?> cl = Class.forName(qualified);
            assertNotNull(cl);
        }
        catch (ClassNotFoundException e)
        {
            fail("Class " + qualified + " should exist.");
        }
    }
}
