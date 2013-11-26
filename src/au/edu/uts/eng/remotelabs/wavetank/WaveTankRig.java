/**
 * UTS Remote Labs Wave Tank Rig Client. 
 *
 * @author Michael Diponio (mdiponio)
 * @date 9th Janurary 2012
 */
package au.edu.uts.eng.remotelabs.wavetank;

import au.edu.uts.eng.remotelabs.rigclient.rig.AbstractControlledRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveResponse;
import au.edu.uts.eng.remotelabs.rigclient.rig.control.AbstractBatchRunner;

import au.edu.labshare.rigclient.action.detect.TickleActivityDetector;

/**
 * WaveTank rig client.
 */
public class WaveTankRig extends AbstractControlledRig
{
    @Override
    protected void init()
    {
        /* --------------------------------------------------------------------
         *  1) Access actions.                                               --
         * --------------------------------------------------------------------*/
        /* --------------------------------------------------------------------
         *  2) Slave access actions.                                         --
         * --------------------------------------------------------------------*/
        /* None currently. */
        
        /* --------------------------------------------------------------------
         *  3) Reset actions                                                 --
         * --------------------------------------------------------------------*/
        /* None currently. */
        
        /* --------------------------------------------------------------------
         *  4) Notify actions.                                               --
         * --------------------------------------------------------------------*/
        /* None currently. */
        
        /* --------------------------------------------------------------------
         *  5) Test actions.                                                 --
         * --------------------------------------------------------------------*/
        /* None currently. */
        
        /* --------------------------------------------------------------------
         * -- 6) Detection actions.                                          --
         * --------------------------------------------------------------------*/
    }

    @Override
    protected AbstractBatchRunner instantiateBatchRunner(String fileName, String userName)
    {
        throw new IllegalStateException("Batch control not supported.");
    }
    
}