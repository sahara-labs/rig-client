
package au.edu.uts.eng.remotelabs.rigclient.action.notify;

import au.edu.uts.eng.remotelabs.rigclient.rig.INotifyAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Notification action which provides an operating system
 * notification using the 'write' (1) command. 'write' prints a
 * message to a specific users terminal. Certain Linux 
 * desktop environments provide 'write' daemons to turn write
 * printed terminal messages to desktop environment dialogs.
 * Ensure the following are installed for the specified desktop
 * environment for the operating system dialog to present:
n* <ul>
 *  <li>KDE 3 - kwritedaemon</li>
 *  <li>KDE 4 - </li>
 *  <li>Gnome - </li>
 * </ul>
 * <strong>NOTE:</strong>The <em>Linux</em> write notification only runs on
 * the Linux operating system. If run on platform, an 
 * {@link IllegalStateException} exception thrown on construction.
 */
public class LinuxWriteNotifyAction implements INotifyAction
{
    /** Logger. */
    private final ILogger logger;
    
    /**
     * Constructor whichs the precondition that this action may only run on
     * Linux.
     */
    public LinuxWriteNotifyAction()
    {
	this.logger = LoggerFactory.getLoggerInstance();
	if (!System.getProperty("os.name").startsWith("Linux"))
	{
	    this.logger.error("The Linux 'write' notification action can " +
                    "only be used on a Linux Operating System. Detected " +
		    "operating system is " + System.getProperty("os.name") + '.');
	    throw new IllegalStateException("Wrong operating system for " +
                    "Linux 'write' notification action.");
	}
    }

    @Override
    public boolean notify(final String message, String users[])
    {
	this.logger.debug("Going to provide notification message " + message + '.');
	return false;
    }

    @Override
    public String getFailureReason()
    {
	// TODO failure reason for write notify action
	return null;
    }

    @Override

    public String getActionType()
    {
	return "Linux write notify action";
    }
}