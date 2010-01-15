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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //TODO - add throws exception
    
    /** Arguments for access command */
    protected final List<String> commandArguments;

    /** Command to execute */
    protected String command;
    
    /** Environment variables for access command */
    protected final Map<String, String> environmentVariables;

    /** Command input String */
    protected StringBuffer inputStringBuffer;

    /** Command output String */
    protected StringBuffer outputStringBuffer;

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
        
        this.inputStringBuffer = new StringBuffer();
        this.outputStringBuffer = new StringBuffer();
        

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
        return false;
        
        
        
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
    protected abstract boolean setupAccessAction();

    
    /**
     * Verifies that the access command was executed as expected.
     * 
     * @return true if successful, false otherwise
     */
    protected abstract boolean verifyAccessAction();
}
