package au.edu.uts.eng.remotelabs.rigclient.collaboration;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

import au.edu.uts.eng.remotelabs.rigclient.collaboration.types.Message;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRig;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigSession.Session;
import au.edu.uts.eng.remotelabs.rigclient.type.RigFactory;

public class CollaborationEngine implements Runnable {
	
	/**Singleton instance*/
	private static CollaborationEngine instance = null;

    /** Logger. */
    private static ILogger logger;
	
    /** Interface to Rig */
    private static IRig rig;
 
	private static int numUsers = 0;
    
    /** Controls allowed for each non-master user in the current rig session*/
    private static volatile Map<String,Boolean> userControls;
    
    private static Set<String> userControlsSet;
    
    private static String master;
    
    private static String mode;
    
    private static volatile Set<String> registeredSlaveActions;
    
    private static CommunicationDirectory comDir;
    
    /** Thread for maintaining user data from the Rig interface **/
    private static Thread collaborationThread;

	private void init()
	{
        logger = LoggerFactory.getLoggerInstance();
        rig = RigFactory.getRigInstance();
        userControlsSet = new TreeSet<String>();
        comDir = new CommunicationDirectory();
        updateUserList();

        collaborationThread = new Thread(this);
        collaborationThread.start();
        setModeMS();
        
        registeredSlaveActions = new TreeSet<String>();
        registeredSlaveActions.add("dataAction");
        registeredSlaveActions.add("pushMessageAction");
        
        logger.debug("Collaboration Engine start sequence successful.");
	}
	
	public static synchronized void restart()
	{
		if(numUsers == 0)
		{
			instance = new CollaborationEngine();
			instance.init();
		}
	}
	
	public void run() 
	{
		try
		{
			while (!Thread.interrupted())
			{
				this.updateUserList();
                Thread.sleep(500);
                
                if (numUsers == 0)
                	collaborationThread.interrupt();
            }
		}
	        catch (InterruptedException e)
	        {
	                      /* Shutting down. */
	        }
	}
	
	public static String getMasterUser(){
        return master.toString();
	}
	
	public static boolean hasControl(String user){
		if (user.equals(master))
			return true;
		if (getMode().equals("Free For All"))
			return true;
		return userControlsSet.contains(user);
	}
	
	public static synchronized void assignControlToUser(String user){
		if (getMode().equals("Master/Slave"))
			userControlsSet.add(user);
		else if(getMode().equals("Baton Pass")){
			userControlsSet.clear();
			userControlsSet.add(user);
		}
	}
	
	public static synchronized void removeControlFromUser(String user){
		userControlsSet.remove(user);
	}
	
	private synchronized void updateUserList(){
		Map<String,Boolean> dirtyControls = new TreeMap<String, Boolean>();
		
        for (Entry<String, Session> e : this.rig.getSessionUsers().entrySet())
        {
            switch (e.getValue())
            {
                case MASTER:
                	this.master = e.getKey();
                    break;
                case SLAVE_ACTIVE:
                	if (userControlsSet.contains(e.getKey())) dirtyControls.put(e.getKey(), true);
                	else dirtyControls.put(e.getKey(), false);
                    break;
                case SLAVE_PASSIVE:
                	if (userControlsSet.contains(e.getKey())) dirtyControls.put(e.getKey(), true);
                	else dirtyControls.put(e.getKey(), false);
                    break;
            }
        }
        
        numUsers = rig.getSessionUsers().size();
        userControls = dirtyControls;
	}
	
	public static String getUserList(){
        return userControls.toString();
	}
	
	public static CommunicationDirectory getDirectory(){
		return comDir;
	}
	
	public static String getMode(){
		return mode;
	}
	
	public static synchronized void setModeFFA(){
		mode = "Free For All";
		userControlsSet.clear();
	}
	
	public static synchronized void setModeMS(){
		mode = "Master/Slave";
		userControlsSet.clear();
	}
	
	public static synchronized void setModeBP(){
		mode = "Baton Pass";
		userControlsSet.clear();
	}
	
	public static boolean checkSlaveAction(String action){
		if (registeredSlaveActions.contains(action))
			return true;
		return false;
	}
	
	public static synchronized void registerSlaveAction(String action){
		registeredSlaveActions.add(action);
	}
	
}
