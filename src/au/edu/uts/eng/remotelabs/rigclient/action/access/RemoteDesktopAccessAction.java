/**
 * SAHARA Rig Client
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
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the University of Technology, Sydney nor the names
 *     of its contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
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
 * @author Tania Machet (tmachet)
 * @date 19th February 2010
 * 
 * Changelog:
 *   - 19/02/2010 - tmachet - start again with Remote Desktop
 */
package au.edu.uts.eng.remotelabs.rigclient.action.access;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Windows Terminal Services access action. Performs the access action by
 * adding and removing users from the configurable Remote Desktop Users group
 * which controls who may remote login to a Windows console using RDP.
 * <p>
 * This action only works for Windows, on other platforms a {@link IllegalStateException} will be thrown on
 * construction.
 * <p>
 * The configuration properties for RemoteDesktopAccessAction are:
 * <ul>
 * <li><tt>Remote_Desktop_Windows_Domain</tt> - specifies the Windows/ Samba domain the user is part of (i.e their name
 * is '\\&lt;Windows_Domain&gt;\&lt;name&gt;') Access is granted with the <code>assign</code> method that adds users to
 * the user group if they do not exist there yet. Access is revoked with the <code>revoke</code> method that removes the
 * user from the group
 */
public class RemoteDesktopAccessAction implements IAccessAction
{

    /** Default user group for remote desktop access. */
    public static final String DEFAULT_GROUPNAME = "\"Remote Desktop Users\"";

    /** Default command for changing user groups for windows */
    public static final String DEFAULT_COMMAND = "net";

    /** Default command for changing user groups for windows */
    public static final String DEFAULT_LOCALGROUP = "localgroup";

    /** Domain name. */
    private final String domainName;

    /** Default user group for remote desktop access. */
    private String failureReason;

    /** Logger. */
    protected ILogger logger;

    /**
     * Constructor.
     */
    public RemoteDesktopAccessAction()
    {
        final String os = System.getProperty("os.name");
        this.logger = LoggerFactory.getLoggerInstance();

        /* RDP access only valid for Windows - check that the OS is windows */
        if (os.startsWith("Windows"))
        {
            /* Get domain if it is configured */
            this.domainName = ConfigFactory.getInstance().getProperty(
                    "Remote_Desktop_Windows_Domain");
        }
        else
        {
            throw new IllegalStateException(
                    "Remote Desktop Action is only valid for WINDOWS platforms not "
                            + os);
        }
    }

    /*
     * (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction#assign(java.lang.String)
     */
    @Override
    public boolean assign(final String name)
    {
        synchronized (this)
        {
            /*
             * --------------------------------------------------------------------
             * 1. Check whether user is in RDP user group
             * a. sets up net process
             * b. executes process
             * c. check output to see whether user is in the group
             * --------------------------------------------------------------------
             */
            final List<String> command = new ArrayList<String>();

            command.add(RemoteDesktopAccessAction.DEFAULT_COMMAND);
            command.add(RemoteDesktopAccessAction.DEFAULT_LOCALGROUP);
            command.add(ConfigFactory.getInstance().getProperty(
                    "Remote_Desktop_Groupname",
                    RemoteDesktopAccessAction.DEFAULT_GROUPNAME));

            try
            {
                Process proc = this.executeCommand(command);
                if (!this.isUserInGroup(proc, name))
                {
                    this.cleanup(proc);
                    /*--------------------------------------------------------------------
                     * 2. If not, add user to group
                     * a. sets up net add process
                     * b. executes process
                     * -------------------------------------------------------------------- */

                    command.add("/ADD");
                    if (this.domainName != null)
                    {
                        command.add(this.domainName + "\\");
                    }
                    else
                    {
                        command.add(name);
                    }
                    this.logger.debug("Command is " + command.toString());

                    proc = this.executeCommand(command);
                    this.cleanup(proc);

                    /* --------------------------------------------------------------------
                     * 3. Check whether user is in RDP user group
                     * a. sets up net process
                     * b. executes process
                     * c. check output to see whether user is in the group
                     * --------------------------------------------------------------------*/
                    command.remove(name);
                    if (this.domainName != null)
                    {
                        command.remove(this.domainName + "\\" + name);
                    }
                    else
                    {
                        command.remove(name);
                    }
                    command.remove("/ADD");
                    proc = this.executeCommand(command);
                    if (!this.isUserInGroup(proc, name))
                    {
                        this.cleanup(proc);
                        this.logger
                                .info("User could not be successfully added to the group "
                                        + command.toString());
                        return false;
                    }
                }
                this.cleanup(proc);
            }
            catch (final Exception e)
            {
                this.logger.info("Executing command " + command.toString()
                        + " failed with error " + e.getMessage());
                return false;
            }
            return true;
        }
    }

    /*
     * (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction#revoke(java.lang.String)
     */
    @Override
    public boolean revoke(final String name)
    {
        synchronized (this)
        {
            /*--------------------------------------------------------------------
             * 1. End users sessions
             * a. Set up process for qwinsta
             * b. Execute process
             * -------------------------------------------------------------------*/
            final List<String> command = new ArrayList<String>();

            command.add("qwinsta");
            command.add(name);

            Process proc;
            try
            {
                proc = this.executeCommand(command);
                final InputStream is = proc.getInputStream();
                final BufferedReader br = new BufferedReader(
                        new InputStreamReader(is));
                String line = null;

                /* --------------------------------------------------------------------
                 * c. Check user session exists using qwinsta output
                 * d. for each session, set up process for log-off
                 * e. execute log-off process
                 * --------------------------------------------------------------------*/
                while (br.ready() && (line = br.readLine()) != null)
                {
                    if (line.contains(name))
                    {
                        final String qwinstaSplit[] = line.split("\\s");

                        final List<String> logoffCommand = new ArrayList<String>();
                        logoffCommand.add("logoff");
                        logoffCommand.add(qwinstaSplit[2]);

                        Process procLogoff;
                        procLogoff = this.executeCommand(logoffCommand);
                        if (procLogoff.exitValue() != 0)
                        {
                            this.logger.warn("Log off for user " + name
                                    + " returned an unexpected result.");
                            return false;
                        }
                        this.cleanup(procLogoff);
                    }
                }
                this.cleanup(proc);

                /*--------------------------------------------------------------------
                 * 2. Check whether user is in RDP user group
                 * a. sets up net process
                 * b. executes process
                 * c. check output to see whether user is in the group
                 * --------------------------------------------------------------------*/

                final List<String> checkCommand = new ArrayList<String>();
                checkCommand.add(RemoteDesktopAccessAction.DEFAULT_COMMAND);
                checkCommand.add(RemoteDesktopAccessAction.DEFAULT_LOCALGROUP);
                checkCommand.add(ConfigFactory.getInstance().getProperty(
                        "Remote_Desktop_Groupname",
                        RemoteDesktopAccessAction.DEFAULT_GROUPNAME));

                Process procCheck = this.executeCommand(checkCommand);
                if (this.isUserInGroup(procCheck, name))
                {
                    this.cleanup(procCheck);
                    /* --------------------------------------------------------------------
                     * 3. If yes, remove user from group
                     * a. sets up net delete process
                     * b. executes process
                     * --------------------------------------------------------------------*/
                    checkCommand.add("/DELETE");
                    checkCommand.add(name);
                    procCheck = this.executeCommand(checkCommand);
                    if (procCheck.exitValue() != 0)
                    {
                        return false;
                    }
                }
            }
            catch (final Exception e)
            {
                this.logger.warn("Revoke action failed with exception of type "
                        + e.getClass().getName() + " and with " + "message "
                        + e.getMessage() + ".");
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAction#getActionType()
     */
    @Override
    public String getActionType()
    {
        return "Windows Remote Desktop Access";
    }

    /*
     * (non-Javadoc)
     * @see au.edu.uts.eng.remotelabs.rigclient.rig.IAction#getFailureReason()
     */
    @Override
    public String getFailureReason()
    {
        return this.failureReason;
    }

    /**
     * Executes the access action specified using the command and working directory
     * 
     * @param command
     * @return Process
     * @throws Exception
     */
    private Process executeCommand(final List<String> command) throws Exception
    {
        //*Check that the command was passed */
        if (command == null)
        {
            this.logger
                    .warn("No command file has been specified for the access action.  Access action failed.");
            throw new Exception("No command to exectue!");
        }
        this.logger.debug("Access action commands and arguments are "
                + command.toString());

        final ProcessBuilder builder = new ProcessBuilder(command);
        final File workingDir = new File(System.getProperty("java.io.tmpdir"));
        builder.directory(workingDir);
        builder.environment();

        Process accessProc = null;

        accessProc = builder.start();
        this.logger.info("Invoked access action command");
        accessProc.waitFor();

        return accessProc;
    }

    /**
     * Method to check that a user belongs to the user group.
     * 
     * @param proc
     *            proccess whose input stream should be searched
     * @param name
     *            of user to be checked
     * @return true if user is in group
     * @throws IOException
     */
    private boolean isUserInGroup(final Process proc, final String name)
            throws IOException
    {
        final InputStream is = proc.getInputStream();
        final BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        while (br.ready() && (line = br.readLine()) != null)
        {
            this.logger.debug(line);

            if (line.contains(name))
            {
                this.logger.debug("Name found");
                return true;
            }
        }
        return false;
    }

    /**
     * Clean up output streams from Process
     * 
     * @param proc
     *            Process to be cleaed up
     */
    private void cleanup(final Process proc)
    {
        this.logger.debug("Cleaning a access action control invocation.");
        try
        {
            if (proc.getInputStream() != null)
            {
                proc.getInputStream().close();
            }
            if (proc.getErrorStream() != null)
            {
                proc.getErrorStream().close();
            }
        }
        catch (final IOException ex)
        {
            this.logger.warn("Failed to clean a process because of error "
                    + ex.getMessage());
        }
    }
}
