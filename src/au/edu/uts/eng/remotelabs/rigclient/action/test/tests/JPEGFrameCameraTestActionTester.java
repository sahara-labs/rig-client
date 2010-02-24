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
 * @date 25th January 2010
 *
 * Changelog:
 * - 25/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.edu.uts.eng.remotelabs.rigclient.action.test.AbstractTestAction;
import au.edu.uts.eng.remotelabs.rigclient.action.test.JPEGFrameCameraTestAction;
import au.edu.uts.eng.remotelabs.rigclient.action.test.JPEGFrameCameraTestAction.Camera;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Tests the {@link JPEGFrameCameraTestAction} class.
 * 
 * DODGY: This is a test case that should working using the UTS experiment camera set up
 *        as of 27th January 2009. 
 */
public class JPEGFrameCameraTestActionTester extends TestCase
{
    /** Cameras - these are currently public at UTS:FEIT Remote Laboratory. */
    public static final String GOOD_CAM = "http://civilmonitor1.eng.uts.edu.au:7070/stream1.jpg";
    public static final String GOOD_CAM_2 = "http://civilmonitor2.eng.uts.edu.au:7070/stream2.jpg";
    public static final String NOT_EXIST_CAM = "http://hostname_does_not_exist/stream1.jpg";
    public static final String LOCKED_CAM = "http://cv1.eng.uts.edu.au:7070/stream1.jpg";
    public static final String WRONG_CODE_CAM = "http://civilmonitor1.eng.uts.edu.au:7070/stream_voodoo.jpg";
    public static final String WRONG_SOI_CAM = "http://remotelabs.eng.uts.edu.au/images/wrong_soi.jpg";
    
    /** Object of class under test. */
    private JPEGFrameCameraTestAction test;

    /** Mock configuration. */
    private IConfig mockConfig;

    @Override
    @Before
    public void setUp() throws Exception
    {
        this.mockConfig = createMock(IConfig.class);
        
        expect(this.mockConfig.getProperty("Logger_Type")).andReturn("SystemErr");
        expect(this.mockConfig.getProperty("Log_Level")).andReturn("DEBUG");
        expect(this.mockConfig.getProperty("Default_Log_Format", "[__LEVEL__] - [__ISO8601__] - __MESSAGE__"))
                .andReturn("[__LEVEL__] - [__ISO8601__] - __MESSAGE__");
        expect(this.mockConfig.getProperty("FATAL_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("PRIORITY_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("ERROR_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("WARN_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("INFO_Log_Format")).andReturn(null);
        expect(this.mockConfig.getProperty("DEBUG_Log_Format")).andReturn(null);
        
        expect(this.mockConfig.getProperty("Camera_Test_URL_1"))
                .andReturn(JPEGFrameCameraTestActionTester.GOOD_CAM);
        expect(this.mockConfig.getProperty("Camera_Test_URL_2")).andReturn(null);
        expect(this.mockConfig.getProperty("Camera_Test_Fail_Threshold", "3")).andReturn("1");
        expect(this.mockConfig.getProperty("Camera_Test_Timeout", "5")).andReturn("3");
        expect(this.mockConfig.getProperty("Camera_Test_Image_Min_Size", "10")).andReturn("50");
        expect(this.mockConfig.getProperty("Camera_Test_Interval", "30")).andReturn("10");
        expect(this.mockConfig.getProperty("Camera_Test_Enable_Uniqueness_Test", "false")).andReturn("true");
        expect(this.mockConfig.getProperty("Camera_Test_Max_Num_Unique_Frames", "10")).andReturn("3");
        
        
        replay(this.mockConfig);

        ConfigFactory.getInstance();
        Field f = ConfigFactory.class.getDeclaredField("instance");
        f.setAccessible(true);
        f.set(null, this.mockConfig);

        LoggerFactory.getLoggerInstance();
        this.test = new JPEGFrameCameraTestAction();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSetup() throws Exception
    {
        this.test.setUp();
        
        Field f = JPEGFrameCameraTestAction.class.getDeclaredField("cameraUrls");
        f.setAccessible(true);
        Map<String, Camera> cams = (Map<String, Camera>)f.get(this.test);
        assertNotNull(cams);
        assertEquals(1, cams.size());
        assertTrue(cams.containsKey(JPEGFrameCameraTestActionTester.GOOD_CAM));
        Camera c = cams.get(JPEGFrameCameraTestActionTester.GOOD_CAM);
        assertNotNull(c);
        assertEquals(0, c.getFails());
        URL url = c.getCamUrl();
        assertTrue(url.openConnection() instanceof HttpURLConnection);
        assertEquals(7070, url.getPort());
        assertEquals("civilmonitor1.eng.uts.edu.au", url.getHost());
        assertEquals("http", url.getProtocol());
        assertEquals("/stream1.jpg", url.getFile());
        assertEquals("/stream1.jpg", url.getPath());
        
        f = JPEGFrameCameraTestAction.class.getDeclaredField("failThreshold");
        f.setAccessible(true);
        assertEquals(1, f.getInt(this.test));
        
        f = JPEGFrameCameraTestAction.class.getDeclaredField("timeOut");
        f.setAccessible(true);
        assertEquals(3, f.getInt(this.test));
        
        f = JPEGFrameCameraTestAction.class.getDeclaredField("minImageSize");
        f.setAccessible(true);
        assertEquals(50 * 1024, f.getInt(this.test));
        
        f = AbstractTestAction.class.getDeclaredField("runInterval");
        f.setAccessible(true);
        assertEquals(10, f.getInt(this.test));
        
        f = JPEGFrameCameraTestAction.class.getDeclaredField("checkUniqueness");
        f.setAccessible(true);
        assertTrue(f.getBoolean(this.test));
        
        f = JPEGFrameCameraTestAction.class.getDeclaredField("maxUniqFrames");
        f.setAccessible(true);
        assertEquals(4, f.getInt(this.test));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTest() throws Exception
    {
        reset(this.mockConfig);
        
        /* This should be a camera that works!. */
        expect(this.mockConfig.getProperty("Camera_Test_URL_1"))
                .andReturn(JPEGFrameCameraTestActionTester.GOOD_CAM);
        expect(this.mockConfig.getProperty("Camera_Test_URL_2")).andReturn(null);
        expect(this.mockConfig.getProperty("Camera_Test_Fail_Threshold", "3")).andReturn("1");
        expect(this.mockConfig.getProperty("Camera_Test_Timeout", "5")).andReturn("3");
        expect(this.mockConfig.getProperty("Camera_Test_Image_Min_Size", "10")).andReturn("50");
        expect(this.mockConfig.getProperty("Camera_Test_Interval", "30")).andReturn("10");
        expect(this.mockConfig.getProperty("Camera_Test_Enable_Uniqueness_Test", "false")).andReturn("true");
        expect(this.mockConfig.getProperty("Camera_Test_Max_Num_Unique_Frames", "10")).andReturn("3");
        replay(this.mockConfig);
        
        
        this.test.setUp();
        this.test.startTest();
        
        int tests = 5;
        for (int i = 0; i < tests; i++)
        {
            this.test.doTest();
            Thread.sleep(5000);
        }
        
        assertTrue(this.test.getStatus());
        assertNull(this.test.getReason());

        Field f = JPEGFrameCameraTestAction.class.getDeclaredField("cameraUrls");
        f.setAccessible(true);
        Map<String, Camera> cams = (Map<String, Camera>)f.get(this.test);
        assertNotNull(cams);
        assertTrue(cams.containsKey(JPEGFrameCameraTestActionTester.GOOD_CAM));
        
        Camera cam = cams.get(JPEGFrameCameraTestActionTester.GOOD_CAM);
        assertNotNull(cam);
        assertEquals(0, cam.getFails());
        
        byte[][] hashes = cam.getFrameHashes();
        assertEquals(4, hashes.length);
        
        /* Check all hashes are populated. */
        for (int i = 0; i < hashes.length; i++)
        {
            boolean sequential = true;
            for (int k = 0; k < hashes[i].length; k++)
            {
                if (hashes[i][k] != 0x00)
                {
                    sequential = false;
                }
            }
            assertFalse(sequential);
        }
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestTwoCams() throws Exception
    {
        reset(this.mockConfig);
        
        /* These should be a camera that works!. */
        expect(this.mockConfig.getProperty("Camera_Test_URL_1"))
                .andReturn(JPEGFrameCameraTestActionTester.GOOD_CAM);
        expect(this.mockConfig.getProperty("Camera_Test_URL_2"))
                .andReturn(JPEGFrameCameraTestActionTester.GOOD_CAM_2);
        expect(this.mockConfig.getProperty("Camera_Test_URL_3")).andReturn(null);
        expect(this.mockConfig.getProperty("Camera_Test_Fail_Threshold", "3")).andReturn("1");
        expect(this.mockConfig.getProperty("Camera_Test_Timeout", "5")).andReturn("3");
        expect(this.mockConfig.getProperty("Camera_Test_Image_Min_Size", "10")).andReturn("50");
        expect(this.mockConfig.getProperty("Camera_Test_Interval", "30")).andReturn("10");
        expect(this.mockConfig.getProperty("Camera_Test_Enable_Uniqueness_Test", "false")).andReturn("false");
        expect(this.mockConfig.getProperty("Camera_Test_Max_Num_Unique_Frames", "10")).andReturn("3");
        replay(this.mockConfig);
        
        
        this.test.setUp();
        this.test.startTest();
        
        int tests = 5;
        for (int i = 0; i < tests; i++)
        {
            this.test.doTest();
            Thread.sleep(5000);
        }
        
        assertTrue(this.test.getStatus());
        assertNull(this.test.getReason());

        Field f = JPEGFrameCameraTestAction.class.getDeclaredField("cameraUrls");
        f.setAccessible(true);
        Map<String, Camera> cams = (Map<String, Camera>)f.get(this.test);
        assertNotNull(cams);
        
        assertTrue(cams.containsKey(JPEGFrameCameraTestActionTester.GOOD_CAM));
        Camera cam = cams.get(JPEGFrameCameraTestActionTester.GOOD_CAM);
        assertNotNull(cam);
        assertEquals(0, cam.getFails());
        
        
        
        byte[][] hashes = cam.getFrameHashes();
        assertEquals(4, hashes.length);
        
        /* Check all hashes are not populated. */
        for (int i = 0; i < hashes.length; i++)
        {
            boolean sequential = true;
            for (int k = 0; k < hashes[i].length; k++)
            {
                if (hashes[i][k] != 0x00)
                {
                    sequential = false;
                }
            }
            assertTrue(sequential);
        }
        
        assertTrue(cams.containsKey(JPEGFrameCameraTestActionTester.GOOD_CAM_2));
        cam = cams.get(JPEGFrameCameraTestActionTester.GOOD_CAM_2);
        assertNotNull(cam);
        assertEquals(0, cam.getFails());
        
        hashes = cam.getFrameHashes();
        assertEquals(4, hashes.length);
        
        /* Check all hashes are not populated. */
        for (int i = 0; i < hashes.length; i++)
        {
            boolean sequential = true;
            for (int k = 0; k < hashes[i].length; k++)
            {
                if (hashes[i][k] != 0x00)
                {
                    sequential = false;
                }
            }
            assertTrue(sequential);
        }
    }
    
    /* DODGY This test is disabled because its rare a camera is locked up with this condition.
     * Really this test should only work if Michael is lazy and hasn't fixed up the broken
     * cameras. */
//    @Test
//    @SuppressWarnings("unchecked")
//    public void testDoTestLocked() throws Exception
//    {
//        reset(this.mockConfig);
//        
//        /* This should be a camera that works!. */
//        expect(this.mockConfig.getProperty("Camera_Test_URL_1"))
//                .andReturn(JPEGFrameCameraTestActionTester.LOCKED_CAM);
//        expect(this.mockConfig.getProperty("Camera_Test_URL_2")).andReturn(null);
//        expect(this.mockConfig.getProperty("Camera_Test_Fail_Threshold", "3")).andReturn("1");
//        expect(this.mockConfig.getProperty("Camera_Test_Timeout", "5")).andReturn("3");
//        expect(this.mockConfig.getProperty("Camera_Test_Image_Min_Size", "10")).andReturn("5");
//        expect(this.mockConfig.getProperty("Camera_Test_Interval", "30")).andReturn("10");
//        expect(this.mockConfig.getProperty("Camera_Test_Enable_Uniqueness_Test", "false")).andReturn("true");
//        expect(this.mockConfig.getProperty("Camera_Test_Max_Num_Unique_Frames", "10")).andReturn("10");
//        replay(this.mockConfig);
//        
//        
//        this.test.setUp();
//        this.test.startTest();
//        
//        int tests = 15;
//        for (int i = 0; i < tests; i++)
//        {
//            this.test.doTest();
//            Thread.sleep(1000);
//        }
//        
//        assertFalse(this.test.getStatus());
//        assertNotNull(this.test.getReason());
//
//        Field f = JPEGFrameCameraTestAction.class.getDeclaredField("cameraUrls");
//        f.setAccessible(true);
//        Map<String, Camera> cams = (Map<String, Camera>)f.get(this.test);
//        assertNotNull(cams);
//        assertTrue(cams.containsKey(JPEGFrameCameraTestActionTester.LOCKED_CAM));
//        
//        Camera cam = cams.get(JPEGFrameCameraTestActionTester.LOCKED_CAM);
//        assertNotNull(cam);
//        assertEquals(0, cam.getFails());
//        
//        byte[][] hashes = cam.getFrameHashes();
//        assertEquals(11, hashes.length);
//    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestWrongResponseCode() throws Exception
    {
        reset(this.mockConfig);
        
        /* This should be a camera that works!. */
        expect(this.mockConfig.getProperty("Camera_Test_URL_1"))
                .andReturn(JPEGFrameCameraTestActionTester.WRONG_CODE_CAM);
        expect(this.mockConfig.getProperty("Camera_Test_URL_2")).andReturn(null);
        expect(this.mockConfig.getProperty("Camera_Test_Fail_Threshold", "3")).andReturn("1");
        expect(this.mockConfig.getProperty("Camera_Test_Timeout", "5")).andReturn("3");
        expect(this.mockConfig.getProperty("Camera_Test_Image_Min_Size", "10")).andReturn("50");
        expect(this.mockConfig.getProperty("Camera_Test_Interval", "30")).andReturn("10");
        expect(this.mockConfig.getProperty("Camera_Test_Enable_Uniqueness_Test", "false")).andReturn("true");
        expect(this.mockConfig.getProperty("Camera_Test_Max_Num_Unique_Frames", "10")).andReturn("3");
        replay(this.mockConfig);
        
        
        this.test.setUp();
        this.test.startTest();
        
        int tests = 5;
        for (int i = 0; i < tests; i++)
        {
            this.test.doTest();
            Thread.sleep(1000);
        }
        
        assertFalse(this.test.getStatus());
        assertNotNull(this.test.getReason());

        Field f = JPEGFrameCameraTestAction.class.getDeclaredField("cameraUrls");
        f.setAccessible(true);
        Map<String, Camera> cams = (Map<String, Camera>)f.get(this.test);
        assertNotNull(cams);
        assertTrue(cams.containsKey(JPEGFrameCameraTestActionTester.WRONG_CODE_CAM));
        
        Camera cam = cams.get(JPEGFrameCameraTestActionTester.WRONG_CODE_CAM);
        assertNotNull(cam);
        assertEquals(5, cam.getFails());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestWrongSOI() throws Exception
    {
        reset(this.mockConfig);
        
        /* This should be a camera that works!. */
        expect(this.mockConfig.getProperty("Camera_Test_URL_1"))
                .andReturn(JPEGFrameCameraTestActionTester.WRONG_SOI_CAM);
        expect(this.mockConfig.getProperty("Camera_Test_URL_2")).andReturn(null);
        expect(this.mockConfig.getProperty("Camera_Test_Fail_Threshold", "3")).andReturn("1");
        expect(this.mockConfig.getProperty("Camera_Test_Timeout", "5")).andReturn("3");
        expect(this.mockConfig.getProperty("Camera_Test_Image_Min_Size", "10")).andReturn("2");
        expect(this.mockConfig.getProperty("Camera_Test_Interval", "30")).andReturn("100");
        expect(this.mockConfig.getProperty("Camera_Test_Enable_Uniqueness_Test", "false")).andReturn("false");
        expect(this.mockConfig.getProperty("Camera_Test_Max_Num_Unique_Frames", "10")).andReturn("3");
        replay(this.mockConfig);
        
        
        this.test.setUp();
        this.test.startTest();
        
        int tests = 5;
        for (int i = 0; i < tests; i++)
        {
            this.test.doTest();
            Thread.sleep(3000);
        }
        
        assertFalse(this.test.getStatus());
        assertNotNull(this.test.getReason());

        Field f = JPEGFrameCameraTestAction.class.getDeclaredField("cameraUrls");
        f.setAccessible(true);
        Map<String, Camera> cams = (Map<String, Camera>)f.get(this.test);
        assertNotNull(cams);
        assertTrue(cams.containsKey(JPEGFrameCameraTestActionTester.WRONG_SOI_CAM));
        
        Camera cam = cams.get(JPEGFrameCameraTestActionTester.WRONG_SOI_CAM);
        assertNotNull(cam);
        assertEquals(5, cam.getFails());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestWrongSize() throws Exception
    {
        reset(this.mockConfig);
        
        /* This should be a camera that works!. */
        expect(this.mockConfig.getProperty("Camera_Test_URL_1"))
                .andReturn(JPEGFrameCameraTestActionTester.GOOD_CAM);
        expect(this.mockConfig.getProperty("Camera_Test_URL_2")).andReturn(null);
        expect(this.mockConfig.getProperty("Camera_Test_Fail_Threshold", "3")).andReturn("1");
        expect(this.mockConfig.getProperty("Camera_Test_Timeout", "5")).andReturn("3");
        expect(this.mockConfig.getProperty("Camera_Test_Image_Min_Size", "10")).andReturn("100");
        expect(this.mockConfig.getProperty("Camera_Test_Interval", "30")).andReturn("10");
        expect(this.mockConfig.getProperty("Camera_Test_Enable_Uniqueness_Test", "false")).andReturn("true");
        expect(this.mockConfig.getProperty("Camera_Test_Max_Num_Unique_Frames", "10")).andReturn("3");
        replay(this.mockConfig);
        
        
        this.test.setUp();
        this.test.startTest();
        
        int tests = 5;
        for (int i = 0; i < tests; i++)
        {
            this.test.doTest();
            Thread.sleep(5000);
        }
        
        assertFalse(this.test.getStatus());
        assertNotNull(this.test.getReason());

        Field f = JPEGFrameCameraTestAction.class.getDeclaredField("cameraUrls");
        f.setAccessible(true);
        Map<String, Camera> cams = (Map<String, Camera>)f.get(this.test);
        assertNotNull(cams);
        assertTrue(cams.containsKey(JPEGFrameCameraTestActionTester.GOOD_CAM));
        
        Camera cam = cams.get(JPEGFrameCameraTestActionTester.GOOD_CAM);
        assertNotNull(cam);
        assertEquals(5, cam.getFails());
        
        byte[][] hashes = cam.getFrameHashes();
        assertEquals(4, hashes.length);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testDoTestWrongCam() throws Exception
    {
        reset(this.mockConfig);
        
        /* This should be a camera that works!. */
        expect(this.mockConfig.getProperty("Camera_Test_URL_1"))
                .andReturn(JPEGFrameCameraTestActionTester.GOOD_CAM);
        expect(this.mockConfig.getProperty("Camera_Test_URL_2"))
                .andReturn(JPEGFrameCameraTestActionTester.NOT_EXIST_CAM);
        expect(this.mockConfig.getProperty("Camera_Test_URL_3")).andReturn(null);
        expect(this.mockConfig.getProperty("Camera_Test_Fail_Threshold", "3")).andReturn("1");
        expect(this.mockConfig.getProperty("Camera_Test_Timeout", "5")).andReturn("3");
        expect(this.mockConfig.getProperty("Camera_Test_Image_Min_Size", "10")).andReturn("5");
        expect(this.mockConfig.getProperty("Camera_Test_Interval", "30")).andReturn("10");
        expect(this.mockConfig.getProperty("Camera_Test_Enable_Uniqueness_Test", "false")).andReturn("true");
        expect(this.mockConfig.getProperty("Camera_Test_Max_Num_Unique_Frames", "10")).andReturn("10");
        replay(this.mockConfig);
        
        
        this.test.setUp();
        this.test.startTest();
        
        int tests = 5;
        for (int i = 0; i < tests; i++)
        {
            this.test.doTest();
            Thread.sleep(3000);
        }
        
        assertFalse(this.test.getStatus());
        assertNotNull(this.test.getReason());

        Field f = JPEGFrameCameraTestAction.class.getDeclaredField("cameraUrls");
        f.setAccessible(true);
        Map<String, Camera> cams = (Map<String, Camera>)f.get(this.test);
        assertNotNull(cams);
        assertTrue(cams.containsKey(JPEGFrameCameraTestActionTester.NOT_EXIST_CAM));
        
        Camera cam = cams.get(JPEGFrameCameraTestActionTester.NOT_EXIST_CAM);
        assertNotNull(cam);
        assertEquals(5, cam.getFails());
        
        byte[][] hashes = cam.getFrameHashes();
        assertEquals(11, hashes.length);
    }
}
