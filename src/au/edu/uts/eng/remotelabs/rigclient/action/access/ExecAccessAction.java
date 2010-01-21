/**
 * SAHARA Rig Client
 * Software abstraction of physical rig to provide rig session control
 * and rig device control. Automatically tests rig hardware and reports
 * the rig status to ensure rig goodness.
 * 
 * @license See LICENSE in the top level directory for complete license terms.
 *          Copyright (c) 2009, University of Technology, Sydney
 *          All rights reserved.
 *          Redistribution and use in source and binary forms, with or without
 *          modification, are permitted provided that the following conditions are met:
 *          * Redistributions of source code must retain the above copyright notice,
 *          this list of conditions and the following disclaimer.
 *          * Redistributions in binary form must reproduce the above copyright
 *          notice, this list of conditions and the following disclaimer in the
 *          documentation and/or other materials provided with the distribution.
 *          * Neither the name of the University of Technology, Sydney nor the names
 *          of its contributors may be used to endorse or promote products derived from
 *          this software without specific prior written permission.
 *          THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *          AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *          IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *          DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 *          FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *          DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *          SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *          CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *          OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *          OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * @author Tania Machet (tmachet)
 * @date 14th January 2010
 */
package au.edu.uts.eng.remotelabs.rigclient.action.access;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Abstract class for command line execution of an access
 * action. 
 * <p>
 * The following methods should be overridden for rig 
 * specific access control.
 * <ul>
 *     <li><code>setupAccessAction()</code> - Should setup the 
 *     command, arguments,working directory and execution environment.</li>
 *     <li><code>verifyAccessAction()</code> - Should verify that the
 *     action has been completed successfully.</li>
 * </ul>
 */

public abstract class ExecAccessAction implements IAccessAction 
{
    
    /** Arguments for access command */
    protected final List<String> commandArguments;

    /** Access Action process. */
    private Process accessActionProcess;
    
    /** Command to execute */
    protected String command;
    
    /** Environment variables for access command */
    protected final Map<String, String> environmentVariables;

    /** Access Action process exit code. */
    protected int exitCode;

    /** Command output string */
    protected String outputString;

    /** Error string */
    protected String errorString;

    /** Working directory for access command */
    protected String workingDirectory;

    /** Logger. */
    protected ILogger logger;    

    /**
     * Constructor, initialises the argument and environment variable lists 
     */
    public ExecAccessAction() 
    {
        
        this.logger = LoggerFactory.getLoggerInstance();
        this.logger.debug("Creating a new ExecAccessAction instance");
        
        this.commandArguments = new ArrayList<String>();
        this.environmentVariables = new HashMap<String, String>();
        
        this.outputString = new String();
        this.errorString = new String();
        
    }
    
    /**
     * Executes the access action specified using the command,
     * its arguments, environment variables and directory
     * specified
     * 
     * @return true if operation successful, false otherwise
     */
    protected boolean executeAccessAction() 
    {
        if (this.command == null)
        {
            this.logger.warn("No command file has been specified for the access action.  Access action failed.");
            return false;
        }
        
        final List<String> processCommand = new ArrayList<String>();
        processCommand.add(this.command);
        
        if (this.commandArguments == null)
        {
            this.logger.info("No arguments have been specified for the access action command " + this.command + 
                    ". This is unusual, please check.");
        }
        else
        {
            processCommand.addAll(this.commandArguments);
            this.logger.info("Access action command arguments are " + this.commandArguments.toString());
        }

        final ProcessBuilder builder = new ProcessBuilder(processCommand);
        
        if (this.workingDirectory == null)
        {
            this.workingDirectory = System.getProperty("java.io.tmpdir");
            this.logger.warn("No working directory set-up, using tmp directory " + this.workingDirectory
                    + " as default.");
        }
        else
        {
            this.logger.info("User set action access command working directory is " + this.workingDirectory);
        }
        final File workingDir = new File(this.workingDirectory);
        builder.directory(workingDir);
        
        final Map<String, String> env = builder.environment();
        for (Entry<String, String> e : this.environmentVariables.entrySet())
        {
            this.logger.info("Adding access action environment variable " + e.getKey() + " with value " + e.getValue());
            env.put(e.getKey(), e.getValue());
        }
        this.logger.info("Access action environment variables: " + env.toString());

        try
        {
            this.accessActionProcess = builder.start();
            this.logger.info("Invoked batch command at " + this.getTimeStamp('/', ' ', ':'));
            this.exitCode = this.accessActionProcess.waitFor();
            this.outputString = this.getAccessOutputString();
            this.errorString = this.getAccessErrorString();
            
            return true;
            
        }
        catch (Exception ex)
        {
            this.logger.warn("Access Action failed with exception of type " + ex.getClass().getName() + " and with " +
                    "message " + ex.getMessage());
            return false;
            
        }
        finally
        {
            /* Cleanup. */
            this.cleanup();
        }
       
    }

    /**
     * Cleans up access action invocation
     */
    private void cleanup()
    {
        this.logger.debug("Cleaning a access action control invocation.");
        try
        {
            if (this.accessActionProcess.getInputStream() != null) 
            {
                this.accessActionProcess.getInputStream().close();
            }
            
            if (this.accessActionProcess.getErrorStream() != null) 
            {
                this.accessActionProcess.getErrorStream().close();
            }
        }
        catch (IOException ex)
        {
            this.logger.warn("Failed to clean a batch control invocation because of error " + ex.getMessage());
        }
        

        
    }

    /**
     * Sets the required access command variables. The variables that
     * should be set in this method are:
     * 
     * <ul>
     *     <li><strong><code>command</code></strong> - The executable command
     *     name.</li>
     *     <li><strong><code>commandArguments</code></strong> - The command line 
     *     arguments for the access command in the order they are entered. 
     *     They are provided in the order they are entered into the
     *     <code>commandArgumentss</code> list. 
     *     <li><strong><code>workingDirectory</code></strong> - The working
     *     directory access command. 
     *     <li><strong><code>environmentVariables</code></strong> - Environment variable
     *     list for the access command. These will be appended to the default
     *     environment variables.</li>
     * </ul>
     * 
     * @return true if successful, false otherwise
     */
    protected abstract void setupAccessAction();

    
    /**
     * Verifies that the access command was executed as expected.
     * 
     * @return true if successful, false otherwise
     */
    protected abstract boolean verifyAccessAction();

    /**
     * Gets a formatted time stamp with day, month, year, hour, minute
     * and second components separated with the <code>glue</code> parameter.
     * 
     * @param dateGlue character to append date components with
     * @param join character to join date and time with
     * @param timeGlue character to append time with
     * @return String formatted time stamp
     */
    protected String getTimeStamp(final char dateGlue, final char join, final char timeGlue)
    {
        final Calendar cal = Calendar.getInstance();
        final StringBuffer buf = new StringBuffer();
        buf.append(cal.get(Calendar.DATE));
        buf.append(dateGlue);
        buf.append(cal.get(Calendar.MONTH) + 1);
        buf.append(dateGlue);
        buf.append(cal.get(Calendar.YEAR));
        buf.append(join);
        buf.append(cal.get(Calendar.HOUR_OF_DAY));
        buf.append(timeGlue);
        buf.append(cal.get(Calendar.MINUTE));
        buf.append(timeGlue);
        buf.append(cal.get(Calendar.SECOND));
        return buf.toString();
    }
    
    /**
     * Get input string of access action process, the the output from the 
     * access action 
     * 
     * @return String input string of subprocess
     */
    protected String getAccessOutputString()
    {
        InputStream is = this.accessActionProcess.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String line = null;
        final StringBuilder buf = new StringBuilder();
        
        try
        {
            while ( (line = br.readLine()) != null )
            {
                buf.append(line);
                buf.append(System.getProperty("line.separator"));
            }
        }
        catch (IOException e)
        {
            this.logger.warn("Reading access action input stream failed with error " + e.getMessage() + ".");
            return null;
        }

        return buf.toString();
        
    }
    

    /**
     * Get error string of access action process.
     * 
     * @return String error string of subprocess
     */
    protected String getAccessErrorString()
    {
        InputStream is = this.accessActionProcess.getErrorStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String line = null;
        final StringBuilder buf = new StringBuilder();

        try
        {
            while ((line = br.readLine()) != null )
            {
                buf.append(line);
                buf.append(System.getProperty("line.separator"));
            }
        }
        catch (IOException e)
        {
            this.logger.warn("Reading access action input stream failed with error " + e.getMessage() + ".");
            return null;
        }

        return buf.toString();
        
    }
    
    /**
     * Gets the exit code of the access action process. If the access action process has not
     * completed, the returned exit code is 0.
     * 
     * @return exit code
     */
    public int getExitCode()
    {
        return this.exitCode;
    }

 }
