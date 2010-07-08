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
 * @date 26th January 2010
 *
 * Changelog:
 * - 26/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.action.test.AbstractTestAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Tests the AbstractTestAction class.
 */
@Deprecated
public class AbstractTestActionTester extends TestCase
{
    /** Object of class under test. */
    private AbstractTestAction test;
    
    /** Mock configuration. */
    private IConfig mockConfig;
    
    @Override
    @Before
    public void setUp() throws Exception
    {
        this.mockConfig = createMock(IConfig.class);
        expect(this.mockConfig.getProperty("Logger_Type"))
                .andReturn("SystemErr");
        expect(this.mockConfig.getProperty("Log_Level"))
                .andReturn("DEBUG");
        expect(this.mockConfig.getProperty("Default_Log_Format", "[__LEVEL__] - [__ISO8601__] - __MESSAGE__"))
                .andReturn("[__LEVEL__] - [__ISO8601__] - __MESSAGE__");
        expect(this.mockConfig.getProperty("FATAL_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("PRIORITY_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("ERROR_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("WARN_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("INFO_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("DEBUG_Log_Format")).andReturn(null);
        replay(this.mockConfig);
        
        ConfigFactory.getInstance();
        Field f = ConfigFactory.class.getDeclaredField("instance");
        f.setAccessible(true);
        f.set(null, this.mockConfig);
        
        LoggerFactory.getLoggerInstance(); 
        this.test = new MockTestAction();
    }

    @Test
    public void testLoadConfigLight() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Test_Light_Time", "09:00")).andReturn("10:30");
        replay(this.mockConfig);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("loadLightDarkConfig", boolean.class, int[].class);
        me.setAccessible(true);
        me.invoke(this.test, true, new int[] {9, 0});
        
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        int time[] = (int[])f.get(this.test);
        assertEquals(10, time[0]);
        assertEquals(30, time[1]);
    }
    
    @Test
    public void testLoadConfigDark() throws Exception
    {
        reset(this.mockConfig);
        expect(this.mockConfig.getProperty("Test_Dark_Time", "18:00")).andReturn("18:30");
        replay(this.mockConfig);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("loadLightDarkConfig", boolean.class, int[].class);
        me.setAccessible(true);
        me.invoke(this.test, false, new int[] {18, 0});
        
        Field f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        int time[] = (int[])f.get(this.test);
        assertEquals(18, time[0]);
        assertEquals(30, time[1]);
    }
    
    @Test
    public void testIsLightTimeBeforeLight() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 4);
        cal.set(Calendar.MINUTE, 30);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertFalse(is);
    }
    
    @Test
    public void testIsLightTimeBeforeLight2() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 29);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertFalse(is);
    }
    
    @Test
    public void testIsLightTimeBeforeDark() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 31);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertTrue(is);
    }
    
    @Test
    public void testIsLightTimeBeforeDark2() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 00);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertTrue(is);
    }
    
    @Test
    public void testIsLightTimeBeforeDark3() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 29);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertTrue(is);
    }
  
    @Test
    public void testIsLightTimeBeforeDark4() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 30);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertTrue(is);
    }
    
    @Test
    public void testIsLightTimeAfterDark() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 30);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertFalse(is);
    }
    
    @Test
    public void testIsLightTimeAfterDark2() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 31);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertFalse(is);
    }
    
    @Test
    public void testIsLightTimeAfterDark3() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 30);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertFalse(is);
    }
    
    @Test
    public void testIsLightTimeAfterDark4() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertFalse(is);
    }
    
    @Test
    public void testIsLightTimeBeforeDarkInvert() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 4);
        cal.set(Calendar.MINUTE, 23);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertTrue(is);
    }
    
    @Test
    public void testIsLightTimeBeforeLightInvert() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 23);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertFalse(is);
    }
    
    @Test
    public void testIsLightTimeAfterLightInvert() throws Exception
    {
        Field f = AbstractTestAction.class.getDeclaredField("darkTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {9, 30});
        f = AbstractTestAction.class.getDeclaredField("lightTime");
        f.setAccessible(true);
        f.set(this.test, new int[] {18, 30});
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 23);
        
        Method me = AbstractTestAction.class.getDeclaredMethod("isLightTime", Calendar.class);
        me.setAccessible(true);
        boolean is = (Boolean)me.invoke(this.test, cal);
        assertTrue(is);
    }
}
