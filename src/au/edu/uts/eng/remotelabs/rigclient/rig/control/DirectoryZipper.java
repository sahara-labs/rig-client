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
 * @date 7th November 2009
 *
 * Changelog:
 * - 07/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Zips a directory. 
 */
public class DirectoryZipper
{
    /** Zip output stream. */
    private ZipOutputStream zipOutput;
    
    /** Destination directory base. */
    private String destBase;
    
    /** Compression level. */
    private int level;
    
    /** Logger. */
    private final ILogger logger;
    
    /**
     * Constructor. The compression level may be either DEFAULT, BEST or 
     * FASTEST. If the provided level is none of these values or
     * <code>null</code>, the DEFAULT level is used.
     * 
     * @param compLevel compression level either DEFAULT, BEST or FASTEST
     */
    public DirectoryZipper(final String compLevel)
    {
        this.logger = LoggerFactory.getLoggerInstance();
        
        if (compLevel == null || compLevel.equalsIgnoreCase("DEFAULT"))
        {
            this.level = Deflater.DEFAULT_COMPRESSION;
        }
        else if (compLevel.equalsIgnoreCase("BEST"))
        {
            this.level = Deflater.BEST_COMPRESSION;
        }
        else if (compLevel.equalsIgnoreCase("FASTEST"))
        {
            this.level = Deflater.BEST_SPEED;
        }
        else
        {
            this.level = Deflater.DEFAULT_COMPRESSION;
        }
    }
    
    
    /**
     * Compresses a directory into a Zip file. If the <code>destFile</code>
     * directory does not end in <code>.zip</code> extension it will have 
     * this extension appended to it.
     * 
     * @param fromDirectory absolute path to directory to compress
     * @param destFile absolute path to destination file
     * @return true if successful, false otherwise
     */
    public boolean compressDirectory(final String fromDirectory, final String destFile)
    {
        this.logger.debug("Compressing directroy " + fromDirectory + " to file " + destFile);
        
        /* Check source. */
        final File from = new File(fromDirectory);
        if (!from.isDirectory())
        {
            this.logger.warn("Provided path " + fromDirectory + " is not a directory, failing directory compression.");
            return false;
        }
        this.destBase = from.getParent();
        
        /* Check destination. */
        final File dest = new File(destFile.endsWith(".zip") ? destFile : destFile + ".zip");
        if (dest.isFile())
        {
            this.logger.warn("Destination zip file " + destFile + " already exists, not overwriting. " +
            		"Directory compression failed.");
            return false;
        }
        
        if (!dest.getParentFile().canWrite())
        {
            this.logger.warn("Unable to write to destination directory " + dest.getParent() + ". " +
            		"Directory compression failed.");
            return false;
        }
        
        try
        {
            this.zipOutput = new ZipOutputStream(new FileOutputStream(dest));
            this.zipOutput.setLevel(this.level);
            this.recusiveCompress(from);
        }
        catch (IOException e)
        {
            this.logger.error("Failed compressing directory with exception " + e.getClass().getName() + " and message "
                    + e.getMessage() + ".");
            return false;
        }
        finally
        {
            try
            {
                this.zipOutput.close();
            }
            catch (IOException e)
            {
               this.logger.warn("Failed closing zip file output stream with message " + e.getMessage() + ".");
            }
        }
        return true;
    }
    
    /**
     * Recursively compress a directory.
     * 
     * @param dir directory to compress
     * @throws IOException error compressing files
     */
    private void recusiveCompress(final File dir) throws IOException
    {
        final byte buf[] = new byte[4096];
        int read = 0;
        
        for (File file : dir.listFiles())
        {
            if (file.isDirectory())
            {
                this.recusiveCompress(file);
                continue;
            }
            
            final FileInputStream input = new FileInputStream(file);
            /* Remove the absolute directory base, to set the destination name
             * as the zip file root directory. */
            final ZipEntry entry = new ZipEntry(file.getAbsolutePath().substring(this.destBase.length()));
            this.zipOutput.putNextEntry(entry);
            
            while ((read = input.read(buf)) > 0)
            {
                this.zipOutput.write(buf, 0, read);
            }
            input.close();
        }
    }
}
