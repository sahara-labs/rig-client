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
 * @date 23rd October 2009
 *
 * Changelog:
 * - 23/10/2009 - mdiponio - Initial file creation.
 */
package au.edu.uts.eng.remotelabs.rigclient.rig.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Abstract class for batch control. Batch control in Sahara is run by 
 * invoking a system executable which does the batch control and
 * (presumably) generates a results file. The following methods
 * should be overridden for rig specific batch control.
 * <ul>
 *     <li><code>testFile()</code> - Should provide a sanity check
 *     on the provided batch instruction file.</li>
 *     <li>invoke() - Should start the batch control executable.</li>
 *     <li>sync() - This is run after the completion of batch
 *     control and should do synchronisation of the generated results 
 *     files to a persistent store.</li>
 * </ul>
 */
public abstract class AbstractBatchRunner implements Runnable
{
    /** Batch process. */
    private Process batchProc;
    
    /** Command line argument to invoke. */
    private String command;
    
    /** Command line argument to invoke. */ 
    private List<String> commandArgs;
    
    /** File name of instruction file. */
    protected String fileName;
    
    /** Processes working directory. */
    private String workingDir;
    
    /** Enviroment variables for the batch process. */
    private Map<String, String> env;
    
    
    /** Logger. */
    protected ILogger logger;
    
    public AbstractBatchRunner(String file)
    {
        this.logger = LoggerFactory.getLoggerInstance();
        this.logger.debug("Creating a Batch Runner.");
        
        this.fileName = file;
        this.commandArgs = new ArrayList<String>();
        this.env = new HashMap<String, String>();
    }
    
    public void run()
    {
        
    }
    
    /**
     * Sets the required batch control process variables. The variables that
     * should be set in this method are:
     * 
     * <ul>
     *     <li><strong><code>command</code></strong> - The batch command name
     *     executable name.</li>
     *     <li><strong><code>commandArgs</code></strong> - The command line 
     *     arguments to provide for the </li>
     *     <li></li> 
     * </ul>
     * 
     * The arguments are provided to command line program in the order they
     * are inserted into the <code>commandArgs</code> array. <strong>NOTE: </strong>
     * You must add the <code>fileName</code> parameter at its appropriate 
     * location in the argument sequence.
     * 
     * @return true if successful, false otherwise
     */
    protected abstract boolean init();
    
    /**
     * Provides a sanity check on the provided batch instruction file.
     * For example, it should check the file extension, file size and/or
     * file magic number.
     * 
     * It may also check the file extension and apply a transform. For
     * example, if the extension is <code>.gz</code>, it may used 
     * <code>GZIPInputStream</code> to uncompress the file. 
     * 
     * @return true if the file passed the sanity check, false otherwise
     */
    protected abstract boolean testFile();
    
    
    
    /**
     * Invokes the batch control executable providing the instruction files
     * as a command line parameter. The function should return after the 
     * invocation of the executable and not wait for it to be completed.
     * 
     * @return true if the executable was successfully invoked
     */
    protected boolean invoke()
    {
        
        
        
        return false;
    }
    
    /**
     * This method is run after completion of the batch control executable 
     * and should do cleanup, if needed and file synchronisation to some 
     * persistent user accessible store, again if needed.
     * 
     * This is provided as a utility with a guarantee that it is run
     * <strong>only</strong> after the successful completion of the 
     * batch control executable.
     * 
     * @return true if successfully completed
     */
    protected abstract boolean sync();
}
