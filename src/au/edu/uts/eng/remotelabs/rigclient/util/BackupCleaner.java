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
 * @date 11th October 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Cleans up rig client created backups.
 */
public class BackupCleaner
{
    /** Logger. */
    private final ILogger logger;
    
    public BackupCleaner()
    {
        this.logger = LoggerFactory.getLoggerInstance();
    }
    
    /** 
     * Cleans up backup files.
     */
    public void clean()
    {
        this.configClean();
        this.cleanLogBackups();
    }
    
    /**
     * Cleans up configuration backup files in the following locations:
     * <ul>
     *  <li>config</li>
     *  <li>conf</li>
     *  <li>conf/conf.d</li>
     *  <li>prop.extension.dir</li>
     *  <li>prop.file</li>
     * </ul>
     */
    public void configClean()
    {
        List<String> dirs = new ArrayList<String>(5);
        dirs.add("config");
        dirs.add("conf");
        dirs.add("conf/conf.d");
        
        String tmp = System.getProperty("prop.extension.dir");
        if (tmp != null) dirs.add(tmp);
        tmp = System.getProperty("prop.file");
        if (tmp != null)
        {
            File f =  new File(tmp);
            dirs.add(f.getParent());
        }
        
        for (String d : dirs)
        {
            File dir = new File(d);
            if (!dir.isDirectory()) continue;
            for (File f : dir.listFiles())
            {
                if (f.getName().endsWith(".backup"))
                {
                    this.logger.info("Deleting configuration backup file '" + f.getName() + "' in path '" + 
                            dir.getName() + "'.");
                    f.delete();
                }
            }
        }
    }
    
    /** 
     * Cleans up log backup files.
     */
    public void cleanLogBackups()
    {
        if (!(this.logger instanceof RolledFileLogger))
        {
            this.logger.debug("Not cleaning any log file backups because the logger type is not 'RolledFileLogger'.");
            return;
        }
        
        String log = ConfigFactory.getInstance().getProperty("Log_File_Name");
        if (log == null)
        {
            this.logger.debug("Not cleaning any log files because the log file name is not configured.");
            return;
        }
        
        File logFile = new File(log);
        if (!logFile.exists())
        {
            logFile = new File("./" + log);
        }
        logFile = logFile.getAbsoluteFile();
        File logDir = logFile.getParentFile();
        String logName = logFile.getName();
        for (File f : logDir.listFiles())
        {
            String nm = f.getName();
            
            /* If the file name doesn't start with the log file name, it is 
             * not a backup. */
            if (!nm.startsWith(logName)) continue;
            
            /* If the last character is not a number, it is not a backup. */
            char c = nm.charAt(nm.length() - 1);
            if (c <= 48 || c >=57) continue;
            
            this.logger.info("Deleting log file backup '" + nm + "'.");
            f.delete();
        }
    }
}
