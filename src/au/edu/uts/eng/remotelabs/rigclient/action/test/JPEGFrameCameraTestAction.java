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
 * @date 20th January 2010
 *
 * Changelog:
 * - 20/01/2010 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.action.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Tests if a camera stream is providing JPEG images over HTTP as video frames.
 * The following tests are performed on the returned images to ensure the 
 * camera and video streaming are functioning correctly.
 * <ul>
 *  <li>Timeout: - If no response is returned within a set timeout period, it is 
 *  taken as a failure as the streaming server has died.</li>
 *  <li>HTTP response code: 200 OK - If the response code is not 200 OK (success
 *  response), it is taken as a failure as either the URL does not point to a
 *  valid camera.</li>
 *  <li>Content-Type: image/jpeg - If the content type is not a JPEG image, it
 *  is taken as a failure as the response is not a JPEG image.</li>
 *  <li>SOI Marker: FFD8 - If the start of image marker is not FFD8, it is taken
 *  as a failure as the response is not a JPEG image.</li>
 *  <li>Image Size: - If the file size is less than a set file size, it is taken 
 *  as a failure because the image is probably not valid. Empirical evidence
 *  with the UTS:FEIT Remote Laboratory iSight camera, using a patched VLC
 *  server to stream single a JPEG frames at 320x240 provides frames of 
 *  suggests sizes of at least 60kB are nominal.</li>
 *  <li>Image Uniqueness - If a set number of sequential images (one image read
 *  at each test run) are identical (having the same hash), it is taken as the
 *  streaming server has locked up. This has occurred erratically in the UTS:FEIT 
 *  Remote Laboratory when a FireWire camera has failed causing VLC to provide
 *  a cached image. The returned image is always the same irrespective of any
 *  changes of the target. The uniqueness test is slightly expensive, requiring
 *  hashes to be computer of each image, thus may be disabled. It is unlikely
 *  to returned false positives as a one bit difference is enough to determine
 *  image uniqueness.</li>
 * </ul>
 * The behavior of JPEG camera test is:
 * <ul>
 *  <li>Test run interval - the default is 30 seconds but may be configured
 *  by setting the property 'Camera_Test_Interval' to a value in seconds.</li>
 *  <li>Periodicity - is periodic.</li>
 *  <li>Set interval - ignored, not honoured.</li> </li>
 * </ul>
 * The camera test can be configured with the following properties:
 * <ul>
 *  <li><tt>Camera_Test_URL_&lt;n&gt;</tt> - The camera JPEG stream URLs for the
 *  camera test to verify for correct frames, where <tt>'n'</tt> is from 1 to 
 *  the nth host to ping test, in order. The <tt>'Camera_Test_URL_1'</tt> 
 *  property is mandatory and each subsequent property is optional.</li>
 *  <li><tt>Camera_Test_Fail_Threshold</tt> - The number of times a camera can
 *  fail before the camera test fails. The default is 3.</li>
 *  <li><tt>Camera_Test_Timeout</tt> - The amount of times in seconds to wait
 *  for the streaming server to respond. The default is 5 seconds.</li>
 *  <li><tt>Camera_Test_Image_Min_Size</tt> - The minimum size in kilobytes a
 *  image frame should be. The default is 5kB. The default is intentionally left
 *  implausibly small to reduce the possibly of false postives.</li>
 *  <li><tt>Camera_Test_Interval</tt> - The amount of time between camera test
 *  runs in seconds.</li>
 *  <li><tt>Camera_Test_Enable_Uniqueness_Test</tt> - Whether to perform the 
 *  uniqueness test. The default is not to perform this test.</li>
 *  <li><tt>Camera_Test_Max_Num_Unique_Frames</tt> - The maximum number of 
 *  unique frames which can be the same before failing the uniqueness 
 *  test. The default is 10.</li>
 * </ul>
 */
public class JPEGFrameCameraTestAction extends AbstractTestAction
{
    /** The initial size of the frame buffer. */
    public static final int INITIAL_FRAME_BUF_SIZE = 50 * 1024;
    
    /** The list of camera URLs and their failures. */
    private Map<String, Camera> cameraUrls;
    
    /** The amount of times a camera can fail before the camera test fails. */
    private int failThreshold;
    
    /** The time in seconds to wait for the camera stream server to respond. */
    private int timeOut;
    
    /** Minimum image size in bytes. */
    private int minImageSize;
    
    /** Whether to check for the frame uniqueness. */
    private boolean checkUniqueness;
    
    /** The maximum number of sequential frames which may be identical. */
    private int maxUniqFrames;
    
    public JPEGFrameCameraTestAction()
    {
        this.cameraUrls = new HashMap<String, Camera>();
        
        this.isPeriodic = true;
        this.runInterval = 30;
        this.isSetIntervalHonoured = false;
    }
    
    @Override
    public void setUp()
    {
        String cnf;
        
        cnf = this.config.getProperty("Camera_Test_Fail_Threshold", "3");
        try
        {
            this.failThreshold = Integer.parseInt(cnf);
            this.logger.debug("Camera test fail threshold is " + this.failThreshold + ".");
        }
        catch (NumberFormatException ex)
        {
            this.logger.warn("The configured value of the camera test fail threshold (Camera_Test_Fail_Threshold) " +
            		"is not a valid integer. Using the default fail threshold of 3.");
            this.failThreshold = 3;
        }
        
        cnf = this.config.getProperty("Camera_Test_Timeout", "5");
        try
        {
            this.timeOut = Integer.parseInt(cnf);
            this.logger.info("Camera test timeout is " + this.timeOut + " seconds.");
        }
        catch (NumberFormatException ex)
        {
            this.logger.warn("The configured value of the camera test timeout (Camera_Test_Timeout) is not a " +
            		"valid integer. Using the default timeout of 5 seconds.");
            this.timeOut = 5;
        }
        
        cnf = this.config.getProperty("Camera_Test_Image_Min_Size", "10");
        try
        {
            this.minImageSize = Integer.parseInt(cnf) * 1024;
            this.logger.info("Camera test minimum image size is " + cnf + "kB.");
        }
        catch (NumberFormatException ex)
        {
            this.logger.warn("The configured value of the camera test minimum image size (Camera_Test_Image_Min_Size)" +
            		" is not a valid integer. Using the default of 10kB.");
            this.minImageSize = 10;
        }
        
        cnf = this.config.getProperty("Camera_Test_Interval", "30");
        try
        {
            this.runInterval = Integer.parseInt(cnf);
            this.logger.info("Camera test run interval is every " + this.runInterval + " seconds.");
        }
        catch (NumberFormatException ex)
        {
            this.logger.warn("The configured camera test interval (Camera_Test_Interval) is not a valid number of " +
            		"seconds. Using the default of every 30 seconds.");
            this.runInterval = 30;
        }
        
        /* Camera uniqueness test. */
        if (this.checkUniqueness = Boolean.parseBoolean(this.config.getProperty(
                "Camera_Test_Enable_Uniqueness_Test", "false")))
        {
            this.logger.info("Going to test the uniqueness of the camera frames.");
        }
        
        cnf = this.config.getProperty("Camera_Test_Max_Num_Unique_Frames", "10");
        try
        {
            this.maxUniqFrames = Integer.parseInt(cnf);
            this.logger.info("The maximum number of unique frames is " + this.maxUniqFrames + ".");
            this.maxUniqFrames += 1;
        }
        catch (NumberFormatException ex)
        {
            this.logger.info("The configured camera test maximum number of unique frames " +
            		"(Camera_Test_Max_Num_Unique_Frames) is not a valid number of frames. Using the default of 10 " +
            		"frames.");
            this.maxUniqFrames = 11;
        }
        
        
        
        /* Load the camera URLs. */
        int c = 1;
        while ((cnf = this.config.getProperty("Camera_Test_URL_" + c)) != null)
        {
            this.logger.info("Going to test camera stream with URL " + cnf + ".");
            try
            {
                this.cameraUrls.put(cnf, new Camera(cnf));
            }
            catch (MalformedURLException e)
            {
                this.logger.error("Camera stream URL " + cnf + " is not a valid URL. It will not be tested.");
            }
            catch (NoSuchAlgorithmException e)
            {
                this.logger.error("BUG: Using an invalid hash type in camera test. Please fill a bug report.");
                throw new IllegalStateException("Unknown camera frame hash has type.");
            }
            ++c;
        }
    } 

    @Override
    public void doTest()
    {
        byte soi[] = new byte[2];
        byte buf[] = new byte[(int)(this.minImageSize * 1024 * 1.5)];
        int size;
        
        Iterator<Entry<String, Camera>> it = this.cameraUrls.entrySet().iterator();
        while (it.hasNext())
        {
            if (!this.runTest) break;
            
            Entry<String, Camera> camera = it.next();
            String url = camera.getKey();
            Camera cam = camera.getValue();
            try
            {
                HttpURLConnection conn = (HttpURLConnection) cam.getCamUrl().openConnection();
                conn.setConnectTimeout(this.timeOut * 1000);
                conn.setDoInput(true);
                conn.setReadTimeout(this.timeOut * 1000);
                conn.setRequestMethod("GET"); 
                conn.connect();
                
                /* Test the response code. */
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) // 200: OK
                {
                    this.logger.debug("Camera with URL " + url + " has failed because of the HTTP response code (" + 
                            conn.getResponseCode() + " " + conn.getResponseMessage() + ") is not 200 OK.");
                    cam.incrementFails();
                    conn.disconnect();
                    continue;
                }
                
                /* Test the response Content-Type header to match image/jpeg. */
                if (!conn.getContentType().equals("image/jpeg"))
                {
                    this.logger.debug("Camera with URL " + url + " has failed because the content type (" + 
                            conn.getContentType() + ") is not 'image/jpeg'.");
                    cam.incrementFails();
                    conn.disconnect();
                    continue;
                }
                
                /* Test the image size. The following two attempts are made to determine this:
                 *     1) Use the HTTP response Content-Length header if provided.
                 *     2) Read the stream and determine the number of bytes returned, up to
                 *        the set minimum image size. */
                if (conn.getContentLength() != -1 && conn.getContentLength() < (this.minImageSize * 1024))
                {
                    this.logger.debug("Camera with URL " + url + " has failed because the supplied content length (" +
                            (conn.getContentLength() / 1024) + "kB) is less the minimum size " + 
                            (this.minImageSize * 1024 ) + "kB.");
                    cam.incrementFails();
                    conn.disconnect();
                    continue;
                }
                
                InputStream stream = conn.getInputStream();
                Thread.sleep(100);
                if (stream.available() < 1)
                {
                    Thread.sleep(500);
                    if (stream.available() < 1)
                    {
                        this.logger.debug("Camera with URL " + url + " has failed because there isn't any response " +
                        		"to read even after waitng 1 second.");
                        cam.incrementFails();
                        stream.close();
                        conn.disconnect();
                        continue;
                    }
                }
                
                if (conn.getContentLength() == -1)
                {
                    stream.mark(this.minImageSize + 1024);
                    
                    /* Need to read stream to determine if it exceeds minimum size. */
                    size = 0;
                    while (stream.read() != -1 || size++ >= this.minImageSize);
                    if (size < this.minImageSize)
                    {
                        this.logger.debug("Camera with URL " + url + " has failed because the read image size (" +
                        		(size / 1024) + "kB) is less than the set image size (" + (this.minImageSize / 1024) +
                        		").");
                        cam.incrementFails();
                        stream.close();
                        conn.disconnect();
                        continue;
                    }
                    
                    stream.reset();
                }
                
                /* Test the JPEG SOI marker (FFD8). */
                stream.mark(1024);
                soi[0] = (byte)(stream.read() & 0xFF);
                soi[1] = (byte)(stream.read() & 0xFF);
                stream.reset();
                if (soi[0] != 0xFF && soi[1] != 0xD8)
                {
                    this.logger.debug("Camera with URL " + url + " has failed because the SOI marker (" +
                            Integer.toHexString(soi[0]) + Integer.toHexString(soi[1]) + " is not FFD8.");
                    cam.incrementFails();
                    stream.close();
                    conn.disconnect();
                    continue;
                }
                
                if (this.checkUniqueness)
                {
                    /* Read in the entire frame and compute its hash. */
                    int len = stream.read(buf);
                    while (len >= buf.length && stream.available() > 0)
                    {
                        buf = Arrays.copyOf(buf, (int)(buf.length * 1.5));
                        len += stream.read(buf, len, buf.length - len);
                    }
                    cam.determineHash(buf, len);
                }
                
                /* Success! */
                cam.clearFails();
                stream.close();
                conn.disconnect();
            }
            catch (DigestException e)
            {
                this.logger.error("Error calculating hash of image frames for uniqueness test. Error message is: " + 
                        e.getMessage() + ". Disabling uniqueness test.");
                this.checkUniqueness = false;
            }
            catch (ProtocolException e)
            {
                this.logger.error("Wrong protocol for confguired camera with URL " + url + ". This test is meant " +
                		"for cameras streaming JPEG frames over HTTP. This camera will NOT be tested.");
                it.remove();
            }
            catch (IOException e)
            {
                this.logger.error("Error connecting to camera stream with URL " + url + ". This is treated as a " +
                		"camera failure.");
                cam.incrementFails();
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void tearDown()
    {
        /* Does nothing. */
    }

    @Override
    public String getReason()
    {
        StringBuilder buf = new StringBuilder();
        for (Entry<String, Camera> cam : this.cameraUrls.entrySet())
        {
            if (cam.getValue().getFails() > this.failThreshold)
            {
                buf.append("Camera with URL " + cam.getKey() + " has failed.");
            }
            
            /* Check uniqueness. */
            if (this.checkUniqueness)
            {
                byte[][] hashes = cam.getValue().getFrameHashes();
                                
                boolean allSame = true;
                for (int i = 1; i < hashes.length; i++)
                {
                    if (!Arrays.equals(hashes[i - 1], hashes[i]))
                    {
                        allSame = false;
                        break;
                    }
                }
                
                if (allSame)
                {
                   buf.append("Camera with URL " + cam.getKey() + " has failed because it has returned " +
                   		" at least " + this.maxUniqFrames + " identical frames.");
                }
            }
        }
        
        if (buf.length() > 0)
        {
            return buf.toString();
        }
        
        return null;
    }

    @Override
    public boolean getStatus()
    {
        for (Entry<String, Camera> cam : this.cameraUrls.entrySet())
        {
            if (cam.getValue().getFails() > this.failThreshold)
            {
                return false;
            }
            
            /* Check uniqueness. */
            if (this.checkUniqueness)
            {
                byte[][] hashes = cam.getValue().getFrameHashes();
                                
                boolean allSame = true;
                for (int i = 1; i < hashes.length; i++)
                {
                    if (!Arrays.equals(hashes[i - 1], hashes[i]))
                    {
                        allSame = false;
                        break;
                    }
                }
                
                if (allSame)
                {
                    return false;
                }
            }
        }
        
        return true;
    }

    @Override
    public String getActionType()
    {
        return "JPEG camera test";
    }
    
    public final class Camera
    {
        /** Camera URL. */
        private URL camUrl;
        
        /** Number of times the camera has failed. */
        private int fails;
        
        /** Array of frame hashes. */
        private byte hashes[][];
        
        /** Frame hasher. */
        private MessageDigest hasher;
        
        public Camera(String url) throws MalformedURLException, NoSuchAlgorithmException
        {
            this.fails = 0;
            this.camUrl = new URL(url);
            
            this.hashes = new byte[JPEGFrameCameraTestAction.this.maxUniqFrames][16]; // MD5 hash length
            this.hasher = MessageDigest.getInstance("MD5");
        }

        /**
         * @return the camUrl
         */
        public URL getCamUrl()
        {
            return this.camUrl;
        }

        /**
         * @return the fails
         */
        public int getFails()
        {
            return this.fails;
        }
        
        /**
         * Clear any set fails.
         */
        public void clearFails()
        {
            this.fails = 0;
        }
        
        /**
         * Increment the failure count.
         */
        public void incrementFails()
        {
            this.fails++;
        }
        
        /**
         * Determines the hash of a image frame.
         * 
         * @param buf image buffer
         * @param len populated buffer length
         */
        public void determineHash(byte buf[], int len) throws DigestException
        {
            /* Slide the frame hashes to the left to keep sequential order. */
            int i;
            for (i = 1 ; i < this.hashes.length; i++)
            {
                this.hashes[i - 1] = this.hashes[i];
            }
            
            this.hasher.update(buf, 0, len);
            this.hashes[i] = this.hasher.digest();
            this.hasher.reset();
        }
        
        /**
         * @return the hashes
         */
        public byte[][] getFrameHashes()
        {
            return this.hashes;
        }
    }
}
