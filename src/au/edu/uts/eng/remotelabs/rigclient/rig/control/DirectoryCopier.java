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
package au.edu.uts.eng.remotelabs.rigclient.rig.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Moves the contents into a new directory.
 */
public class DirectoryCopier
{
    /** File content buffer. */
    private final byte buf[];
    
    /** Logger object. */
    private final ILogger logger;
    
    /**
     * Constructor.
     */
    public DirectoryCopier()
    {
        this.logger = LoggerFactory.getLoggerInstance();
        
        this.buf = new byte[4096];
    }
    
    /**
     * Copies a directories contents to another directory. If the 
     * supplied destination directory does not exist, it is created before
     * proceeding with copying the contents to it.
     * 
     * @param fromDir location of files to copy
     * @param toDir destination of files
     * @return true if successful
     */
    public boolean copyDirectory(final String fromDir, final String toDir)
    {
        try
        {
            this.recursiveCopy(fromDir, toDir);
            return true;
        }
        catch (IOException e)
        {
            this.logger.error("Failed to copy directory contents with error " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Recursively copies a directories contents to another directory. If the 
     * supplied destination directory does not exist, it is created before
     * proceeding with copying the contents to it.
     * 
     * @param fromDir location of files to copy
     * @param toDir destination of files
     * @throws IOException error copying directories
     */
    public void recursiveCopy(final String fromDir, final String toDir) throws IOException
    {
        this.logger.debug("Copying the contents of " + fromDir + " to " + toDir + ".");
        
        final File fromFile = new File(fromDir);
        final File toFile = new File(toDir);
        
        if (!fromFile.isDirectory())
        {
            this.logger.warn(fromDir + " is not a directory. Directory copy failed.");
            throw new IOException(fromDir + " is not a directory.");
        }
       
        if (!toFile.exists() && !toFile.mkdirs())
        {
            this.logger.warn("Failed to create directory " + toDir + ". Directory copy failed.");
            throw new IOException("Failed to create directory " + toDir + ".");
        }
        
        for (File f : fromFile.listFiles())
        {
            if (f.isDirectory())
            {
                this.copyDirectory(f.getAbsolutePath(), toDir + File.separatorChar + f.getName());
            }
            else
            {
                this.copyFile(f, toFile);
            }
        }
    }
    
    /**
     * Copies the provided file into the destination directory.
     * 
     * @param fromFile file to copy
     * @param destination directory to copy file into
     * @throws IOException error copying file
     */
    public void copyFile(final File fromFile, final File destination) throws IOException
    {
        this.logger.debug("Copying file " + fromFile.getName() + " in " + fromFile.getParent() + " to " + destination + ".");
        int len;
        
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try
        {
            final File toFile = new File(destination, fromFile.getName());
            fis = new FileInputStream(fromFile);
            fos = new FileOutputStream(toFile);
            
            while ((len = fis.read(this.buf)) > 0)
            {
                fos.write(this.buf, 0, len);
            }
        }
        finally
        {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
    }
}
