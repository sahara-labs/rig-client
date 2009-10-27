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

import java.io.File;
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
 *     <li>init() - Should setup the command, arguments, working directory and 
 *     execution environment.</li>
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
    
    /** Batch process working directory base (base has a new folder added to
     *  be set as the batch processes CWD. */
    private String workingDirBase;
    
    /** Batch process working directory. */
    private String workingDir;
    
    /** Enviroment variables for the batch process. */
    private Map<String, String> envMap;
    
    /** Flag to specify if batch execution is running. */
    private boolean isRunning;
    
    /** Logger. */
    protected ILogger logger;
    
    public AbstractBatchRunner(String file)
    {
        this.logger = LoggerFactory.getLoggerInstance();
        this.logger.debug("Creating a Batch Runner.");
        
        this.fileName = file;
        this.commandArgs = new ArrayList<String>();
        this.envMap = new HashMap<String, String>();
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
     *     arguments to provide for the command. The arguments are provided
     *     in the same order as they are entered into the
     *     <code>commandArgs</code> list. <strong>NOTE:</strong> You should
     *     add the <code>fileName</code> field as a parameter for the command
     *     since this contains an absolute reference to the uploaded 
     *     instruction file.</li>
     *     <li><strong><code>workingDirBase</code></strong> - The working
     *     directory base for the batch command process. If not set, the default
     *     base is the system temporary directory. The base is appended with
     *     a new folder to contain the results files from the batch executable
     *     (<em>assuming</em> it puts the files in its current working 
     *     directory.)</li>
     *     <li><strong><code>envMap</code></strong> - Environment variable map
     *     for the batch process enviroment. Additions to this will be appended
     *     to the default enviroment variables.</li>
     * </ul>
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
     * as a command line parameter. Returns are invocation and does not
     * wait for completeion.
     * 
     * @return true if the executable was successfully invoked
     */
    public boolean invoke()
    {
        List<String> invocation = new ArrayList<String>();
        invocation.add(this.command);
        invocation.addAll(this.commandArgs);
        this.logger.info("Batch command that will be invoked is " + this.command);
        this.logger.info("Batch command arguments are " + this.commandArgs.toString());
        
        ProcessBuilder build = new ProcessBuilder(invocation);
        
        /* Working directory. */
        if (this.workingDirBase != null)
        {
            this.logger.info("User set batch working directory is " + this.workingDirBase);
            build.directory(new File(this.workingDirBase));
        }
        else
        {
            /* Default is a directory in the system directory. */
            this.workingDirBase = System.getProperty("java.io.tmpdir");
            if (this.workingDirBase == null)
            {
                this.logger.warn("The temporary directory system property (java.io.tmpdir) did not find the system" +
                		"temporary directory. Going to use the current Rig Client working directory.");
                this.workingDirBase = System.getProperty("user.dir");
                this.logger.warn("Using Rig Client working directory " + this.workingDirBase + " as batch control working " +
                		"directory base.");
            }
        }
        /* Add a new folder with a timestamp as the name. */
        
        
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
    
    /**
     * Returns <code>true</code> if a batch process has been started and is
     * currently running. 
     * 
     * @return true if batch is running, false otherwise
     */
    public boolean isRunning()
    {
        return this.isRunning;
    }
}
