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
 * @date 12th December 2009
 *
 * Changelog:
 * - 29/12/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.util.tests;

import java.util.Calendar;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.util.LogFormatter;

/**
 * Tests the log format class.
 */
public class LogFormatterTester extends TestCase
{
    /** Object of class under test. */
    private LogFormatter formatter;
    
    @Override
    @Before
    public void setUp()
    {
        this.formatter = new LogFormatter();
    }

    /**
     * Test method for {@link au.edu.uts.eng.remotelabs.rigclient.util.LogFormatter#formatLog(java.lang.String, java.lang.String, java.lang.String, int)}.
     */
    @Test
    public void testFormatLog()
    {
        String fmt = "[__LEVEL__] - <__ISO8601__) - __MESSAGE__";
        String mess = "Test message";
        String lvl = "INFO";
        
        String log = this.formatter.formatLog(fmt, mess, lvl, 1);
        
        assertTrue(log.startsWith("[" + lvl + "]"));
        assertTrue(log.endsWith(mess));
        
        String p[] = log.split("<", 2);
        assertEquals(2, p.length);
        p = p[1].split("\\)");
        assertEquals(2, p.length);

        String iso = p[0];
        assertTrue(iso.contains("T"));
        p = iso.split("T");
        assertEquals(2, p.length);
        
        String d = p[0];
        String t = p[1];
        
        p = d.split("-");
        assertEquals(3, p.length);
        Calendar cal = Calendar.getInstance();
        assertEquals(Integer.parseInt(p[0]), cal.get(Calendar.YEAR));
        assertEquals(Integer.parseInt(p[1]), cal.get(Calendar.MONTH) + 1);
        assertEquals(Integer.parseInt(p[2]), cal.get(Calendar.DAY_OF_MONTH));
        
        p = t.split(":");
        assertEquals(3, p.length);
        assertEquals(Integer.parseInt(p[0]), cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(Integer.parseInt(p[1]), cal.get(Calendar.MINUTE));
    }
    
    public void testFormatLogStack()
    {
        String fmt = "[__LEVEL__] - (__DATE__) - <__TID__-__CLASS__-__METHOD__-__SOURCE__-__LINE_NUM__> __MESSAGE__";
        String mess = "Test message";
        String lvl = "INFO";
        
        String log = this.formatter.formatLog(fmt, mess, lvl, 2);

        assertTrue(log.startsWith("[" + lvl + "]"));
        assertTrue(log.endsWith(mess));
        
        String p[] = log.split("\\(", 2);
        assertEquals(2, p.length);
        p = p[1].split("\\)");
        assertEquals(2, p.length);
        
        String d = p[0];
        
        p = d.split("-");
        assertEquals(3, p.length);
        Calendar cal = Calendar.getInstance();
        assertEquals(Integer.parseInt(p[2]), cal.get(Calendar.YEAR));
        assertEquals(Integer.parseInt(p[1]), cal.get(Calendar.MONTH) + 1);
        assertEquals(Integer.parseInt(p[0]), cal.get(Calendar.DAY_OF_MONTH));
        
        p = log.split("<", 2);
        assertEquals(2, p.length);
        p = p[1].split(">", 2);
        assertEquals(2, p.length);
        
        p = p[0].split("-");
        assertEquals(5, p.length);
        assertEquals(Thread.currentThread().getId(), Integer.parseInt(p[0]));
        assertEquals(this.getClass().getName(), p[1]);
        assertEquals("testFormatLogStack", p[2]);
        assertEquals("LogFormatterTester.java", p[3]);
        assertEquals(115, Integer.parseInt(p[4]));
    }

    @Test
    public void testFormatLogTime()
    {
        String fmt = "[__LEVEL__] - <__TIME__) - __MESSAGE__";
        String mess = "Test message";
        String lvl = "INFO";
        
        String log = this.formatter.formatLog(fmt, mess, lvl, 1);
        
        assertTrue(log.startsWith("[" + lvl + "]"));
        assertTrue(log.endsWith(mess));
        
        String p[] = log.split("<", 2);
        assertEquals(2, p.length);
        p = p[1].split("\\)");
        assertEquals(2, p.length);
        String t = p[0];
        
        p = t.split(":");
        assertEquals(3, p.length);
        Calendar cal = Calendar.getInstance();
        assertEquals(Integer.parseInt(p[0]), cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(Integer.parseInt(p[1]), cal.get(Calendar.MINUTE));
        assertEquals(2, p[0].length());
        assertEquals(2, p[1].length());
        assertEquals(2, p[2].length());
    }
}
