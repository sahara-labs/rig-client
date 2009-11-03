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
 * @date 2nd November 2009
 *
 * Changelog:
 * - 02/11/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.PropertiesConfig;

/**
 * The <code>ConfiguredBatchRunner</code> is an implementation of a 
 * <code>AbstractBatchRunner</code> which allows batch control to be setup
 * with just configuration and not code implementation.
 * <br />
 * This uses a properties file (<code>config/batch.properties</code>) to 
 * store configuration.
 */
public class ConfiguredBatchRunner extends AbstractBatchRunner
{
    /** Batch properties configuration file location. */
    public static final String BATCH_PROPERTIES = "config/batch.properties";
    
    /** The maximum number of bytes comprising a file magic number. */
    /* DODGY This is sort of arbitrary based on no formal or even implied
     * conventions on the how long a magic number must be. Nevertheless,
     * even if the magic number exceeds this size, a reasonably accurate
     * file type finger print should be able to be determined. */
    public static final int MAGIC_NUMBER_LEN = 8;
    
    /** Batch configuration. */
    private IConfig batchConfig;
    
    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#init()
     */
    @Override
    protected boolean init()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * Default constructor.
     * 
     * @param file reference to uploaded instruction file
     * @param user name of user who invoked batch control
     */
    public ConfiguredBatchRunner(final String file, String user)
    {
        super(file, user);
        
        this.logger.debug("Creating a ConfiguredBatchRunner, using " + ConfiguredBatchRunner.BATCH_PROPERTIES +
                " as the batch configuration file.");
        this.batchConfig = new PropertiesConfig(ConfiguredBatchRunner.BATCH_PROPERTIES);
        this.logger.debug("Batch properites file information is " + this.batchConfig.getConfigurationInfomation());
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#checkFile()
     */
    @Override
    protected boolean checkFile()
    {
        /* Check file extension. */
        if (Boolean.parseBoolean(this.batchConfig.getProperty("Test_File_Extension", "false")) 
                && !this.fileExtensionTest())
        {
            return false;
        }

        /* Check file maximum size. */
        if (Boolean.parseBoolean(this.batchConfig.getProperty("Test_Max_File_Size", "false")) && !this.fileSizeTest())
        {
            return false;
        }

        /* Check file magic number. */
        if (Boolean.parseBoolean(this.batchConfig.getProperty("Test_Magic_Number", "false")) && !this.magicNumberTest())
        {
            return false;
        }

        return true;
    }

    /**
     * Tests if the uploaded instruction file magic number is the same as
     * the configured magic number give by the <code>File_Magic_Number</code>
     * property.
     * 
     * @return true if the magic numbers are the same
     */
    private boolean magicNumberTest()
    {
        String magicNumberStr = this.batchConfig.getProperty("File_Magic_Number");
        if (magicNumberStr == null)
        {
            this.logger.error("Incorrect configuration for file magic number test. The property 'File_Magic_Number'" +
            		" is missing. It should be present and populated with a hexadecimal value.");
            return false;
        }
        
        if (magicNumberStr.startsWith("0x")) // Remove the hexadecimal notation prefix 
        {
            magicNumberStr = magicNumberStr.substring(2, magicNumberStr.length());
        }
        
        final long magicNumber = Long.parseLong(magicNumberStr, 16);
        this.logger.debug("Testing instruction file for " + Long.toHexString(magicNumber) + " magic number.");
        final File file = new File(this.fileName);
        
        FileInputStream input = null;
        try
        {
            input = new FileInputStream(file);
            int i, c;
            byte fileBuf[] = new byte[ConfiguredBatchRunner.MAGIC_NUMBER_LEN];
            byte magicBuf[] = new byte[ConfiguredBatchRunner.MAGIC_NUMBER_LEN];
            
            /* Read the first 8 bytes of the file. */
            if (input.read(fileBuf, 0, ConfiguredBatchRunner.MAGIC_NUMBER_LEN) > 
                    ConfiguredBatchRunner.MAGIC_NUMBER_LEN)
            {
                this.logger.warn("Unable to read the first " + ConfiguredBatchRunner.MAGIC_NUMBER_LEN + " bytes " +
                		"of " + this.fileName + " failing magic number test.");
                return false;
            }
            
            /* Populate the array with the long bytes. */
            for (i = 0; i < magicBuf.length; i++) magicBuf[7 - i] = (byte) ((magicNumber >> (8 * i)) & 0xFF);
            
            /* Count left empty cells. */
            for (i = 0, c = 0; i < magicBuf.length; i++) 
            {
                if (magicBuf[i] == 0x00)
                {
                    c++;
                }
                else
                {
                    break;
                }
            }
            /* Shift cell left so the left cell is the first byte of the magic number. */
            for (i = c; i < magicBuf.length; i++)
            {
                magicBuf[i - c] = magicBuf[i];
                magicBuf[i] = 0x00;
            }
            
            /* Compare the cell values. */
            for (i = 0; i < magicBuf.length; i++)
            {
                if (magicBuf[i] != 0 && magicBuf[i] != fileBuf[i])
                {
                    this.logger.warn("File magic number comparison failed for " + this.fileName + ". It should" +
                            " be 0x" + Long.toHexString(magicNumber) + ". Actually read 0x" + 
                            Long.toHexString(ByteBuffer.wrap(fileBuf).getLong()) + ".");
                    return false;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            this.logger.warn("Unable to open instruction " + file.getAbsolutePath() + " as it apparently " +
            "doesn't exist.");
            return false;
        }
        catch (IOException e)
        {
            this.logger.warn("Unable to read instruction " + file.getAbsolutePath() + " because of error" +
                    e.getMessage() + ".");
            return false;
        }
        finally
        {
            try
            {
                if (input != null)  input.close();
            }
            catch (IOException e)
            {
                this.logger.debug("Failed to close file input stream with error " + e.getMessage());
            }
        }
        
        return true;
    }

    /**
     * Tests if the uploaded instruction file size is smaller that the 
     * configured maximum file size given by the <code>Max_File_Size</code>
     * property.
     *  
     * @return true if the uploaded instruction file is smaller than the \
     *         configured maximum file size
     */
    private boolean fileSizeTest()
    {
        final File file = new File(this.fileName);
        final long maxSize = Long.parseLong(this.batchConfig.getProperty("Max_File_Size"));
        this.logger.debug("Testing instruction file for less that maximum size " + maxSize + ".");
        if ((file.length() / 1024) > maxSize)
        {
            this.logger.warn("Instruction file " + this.fileName + " failed maximum size test (max " +
                    maxSize + "kB). The file size is " + (file.length() / 1024) + "kB.");
            return false;
        }
        return true;
    }

    /**
     * Tests if the uploaded instruction file extension matches the configured
     * extension given by the <code>File_Extension</code> property.
     * 
     * @return true if the file extension matches the configured extension
     */
    private boolean fileExtensionTest()
    {
        final String extension = this.batchConfig.getProperty("File_Extension");
        this.logger.debug("Testing instruction file for extension " + extension + ".");
        if (!this.fileName.endsWith(extension))
        {
            this.logger.warn("Instruction file " + this.fileName + " failed file extension test for the " +
            		"'" + extension + "' extension.");
            return false;
        }
        return true;
    }

    /* 
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner#sync()
     */
    @Override
    protected boolean sync()
    {
        // TODO Auto-generated method stub
        return false;
    }

}
