/**
 * UTS Remote Labs Wave Tank Rig Client. 
 *
 * @author Dominic Argente (dargente)
 * @date 30th April 2013
 */
package au.edu.uts.eng.remotelabs.wavetank;

import java.io.IOException;

import au.edu.uts.eng.remotelabs.rigclient.util.ConfigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.IConfig;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

/**
 * Handles the communication requests between the CRIO and the
 * Rig Client. Implements a singleton pattern.
 */

public class WaveTankInterface implements Runnable
{

	// Singleton Instance
	private static WaveTankInterface instance = null;
	private static WaveTank rig;
	
	public static int acquireCount = 0;
	
	private ILogger logger;
	
	private static Thread tankThread;
	
	/* Private Constructor for Singleton */
	private WaveTankInterface() {
		
	}
	
	/**
	 * Initialisation of the CRIOHandler thread.
	 * Loads config files, opens comms with the CRIO, begins the CRIOHandler thread and
	 * zeros the outputs to the CRIO. 
	 * @return
	 */
	private boolean init() {
		
		/* Create connection with CRIO */
		rig = new WaveTank();
		tankThread = new Thread(this);
		tankThread.start();

		return true;
	}
	
    /** Simulates stuff happening */
	@Override
	public void run() 
	{
		try
		{
			while (!Thread.interrupted())
			{
				rig.bufferData();
                Thread.sleep(500);
            }
		}
	        catch (IOException e)
	        {
	        	this.logger.warn("Failed communicating with Wave Tank, exception: " + 
	                             e.getClass().getSimpleName() + ", message: " + e.getMessage());
	        }
	        catch (InterruptedException e)
	        {
	                      /* Shutting down. */
	        }
	}
	
	/**
	 * When acquiring an instance of CRIOTcp, ensure that the CRIOHandler thread is active.
	 * @return crioTCP
	 */
	public static synchronized WaveTank acquire()
	{
		if(acquireCount == 0)
		{
			instance = new WaveTankInterface();
			instance.init();
		}
		acquireCount++;
		return rig;
	}
	
	/**
	 * When all controllers are finished using the CRIO, shutdown the CRIOhandler thread.
	 */
	public static synchronized void lease()
	{
		if(acquireCount > 0)
		{
			acquireCount--;
			
			if(acquireCount == 0)
			{
				tankThread.interrupt();
			}
		}

	}
	
}