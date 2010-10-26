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
 * @date 26th October 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.util;

import java.io.File;
import java.io.IOException;

/**
 * Cleans Apache Axis generated temp files. These are located in the temporary 
 * directory and have the prefix <tt>axis2-tmp-</tt>
 */
public class AxisCleaner implements Runnable
{
    /** The prefix of Axis2 temp files. */
    public static String TEMP_PREFIX = "axis2-tmp-";
    
    /** The number of hours between running the cleaner. */
    public static int RUN_INTERVAL = 6;
    
    /** Axis cleaner. */
    private static AxisCleaner cleaner;
    
    /** Logger. */
    private ILogger logger;
    
    public AxisCleaner()
    {
        this.logger = LoggerFactory.getLoggerInstance();
    }
    
    /**
     * Starts and runs the cleaner. Only one cleaner will ever run.
     */
    public static void startCleaner()
    {
        if (AxisCleaner.cleaner != null) return;
        
        AxisCleaner.cleaner = new AxisCleaner();
        Thread thr = new Thread(AxisCleaner.cleaner);
        thr.setDaemon(true);
        thr.setName("Axis Cleaner");
        thr.start();
    }
    
    /**
     * Forces a clean run.
     */
    public static void forceCleanRun()
    {
        if (AxisCleaner.cleaner == null)
        {
            AxisCleaner ac = new AxisCleaner();
            ac.runClean();
        }
        else
        {
            AxisCleaner.cleaner.runClean();
        }
    }

    @Override
    public void run()
    {
        this.logger.debug("Starting the Axis temp file cleaner.");
        
        try
        {
            while (!Thread.interrupted())
            {
                Thread.sleep(AxisCleaner.RUN_INTERVAL * 60 * 60 * 1000);
                this.runClean();
            }
        }
        catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Runs clean.
     */
    public void runClean()
    {
        String temp = System.getProperty("java.io.tmpdir");
        this.logger.debug("Running Axis temp file cleanup in directory '" + temp + "'.");
        
        File tempDir  = new File(temp);
        if (!tempDir.isDirectory())
        {
            this.logger.warn("Default temporary file directory path '" + temp + "' does not exist, cannot " +
                    "run cleanup.");
            return;
        }
        
        for (File file : tempDir.listFiles())
        {
            if (!file.getName().startsWith(AxisCleaner.TEMP_PREFIX)) continue;
            
            if (file.isDirectory())
            {
                try
                {
                    this.logger.debug("Deleting Axis2 temp directory '" + file.getAbsolutePath() + "'.");
                    this.recusiveDelete(file);
                }
                catch (IOException e)
                {
                    this.logger.warn("Failed to delete Axis2 temp directory '" + file.getAbsolutePath() + "'.");
                }
            }
            else
            {
                this.logger.debug("Deleting Axis2 temp file '" + file.getAbsolutePath() + "'.");
                file.delete();
            }
        }
    }
    
     /**
     * Recursively delete a directory. The directory and all its child files 
     * and elements will be deleted.
     *  
     * @param file directory or file to delete
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
                    this.logger.debug("Deleting Axis2 temp directory '" + file.getAbsolutePath() + "'.");
                    this.recusiveDelete(f);
                }
                else
                {
                    this.logger.debug("Deleting Axis2 temp file '" + f.getAbsolutePath() + "'.");
                    f.delete();
                }
            }
        }
        
        file.delete();
    }
}
