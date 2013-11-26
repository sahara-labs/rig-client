/**
 * SAHARA iRobot Rig Client
 *
 * @author Michael Diponio (mdiponio)
 * @date 30th Janurary 2012
 */
package au.edu.labshare.rigclient.action.detect;

import au.edu.uts.eng.remotelabs.rigclient.rig.IAccessAction;
import au.edu.uts.eng.remotelabs.rigclient.rig.IActivityDetectorAction;
import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;

/**
 * Provides activity detection by allowing other Rig Client code to tickle
 * this activity detector to indicate activity has occurred. This is useful
 * for rigs which use the Rig Client as the control proxy. This class can be 
 * optionally configured with the property:
 * <ul>
 *  <li>Tickle_Timeout - Specifies the timeout for determining when there is
 *  no activity if no tickling has been called. The default of 30 second is
 *  most appropriate for most situations.</li>
 * </ul>
 */
public class TickleActivityDetector implements IAccessAction, IActivityDetectorAction
{
    /** Default no activity timeout. */
    public static final long DEFAULT_TIMEOUT = 30;
    
    /** Timeout in use. */
    private final long timeout;
    
    /** Last tickle time stamp. */
    private static long tickle;
    
    public TickleActivityDetector()
    {
        long to;
        try
        {
            to = Integer.parseInt(ConfigFactory.getInstance().getProperty("Tickle_Timeout"));
        }
        catch (NumberFormatException ex)
        {
            to = DEFAULT_TIMEOUT;
        }
        this.timeout = to * 1000;
    }
    
    public static void tickle()
    {
        tickle = System.currentTimeMillis();
    }
    
    @Override
    public boolean assign(String name)
    {
        /* Priming tickle. */
        tickle();
        return true;
    }

    @Override
    public boolean revoke(String name)
    {
        /* Nothing to do. */
        return true;
    }
    
    @Override
    public boolean detectActivity()
    {
        return System.currentTimeMillis() - tickle < this.timeout;
    }

    @Override
    public String getActionType()
    {
        return "Tickle Activity Detector";
    }

    @Override
    public String getFailureReason()
    {
        return null;
    }
}
