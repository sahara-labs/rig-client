/**
 * UTS Remote Labs Wave Tank Rig Client. 
 *
 * @author Michael Diponio (mdiponio)
 * @date 9th Janurary 2012
 */

package au.edu.uts.eng.remotelabs.rigclient.collaboration.primitive;

import java.io.IOException;
import java.util.List;

import au.edu.uts.eng.remotelabs.rigclient.collaboration.CollaborationEngine;
import au.edu.uts.eng.remotelabs.rigclient.collaboration.CommunicationDirectory;
import au.edu.uts.eng.remotelabs.rigclient.collaboration.types.Message;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveResponse;
import au.edu.uts.eng.remotelabs.rigclient.rig.primitive.IPrimitiveController;


/**
 * Controller for a Primitive Collaborative Rig
 */
public class CollaborationController implements IPrimitiveController
{
	@Override
	public boolean initController() {
		return true;
	}

	@Override
	public boolean preRoute() {
		return true;
	}
	
    /**
     * Updates response with User data
     * 
     * @param request 
     * @return response
     */
    public PrimitiveResponse dataAction(PrimitiveRequest request)
    {
    	PrimitiveResponse response = new PrimitiveResponse();
    	response.setSuccessful(true);

        response.addResult("master", CollaborationEngine.getMasterUser());
        response.addResult("users", CollaborationEngine.getUserList());
        response.addResult("requestor", request.getRequestor());
        response.addResult("hascontrol", String.valueOf(CollaborationEngine.hasControl(request.getRequestor())));
        response.addResult("mode", CollaborationEngine.getMode());
        CommunicationDirectory dir = CollaborationEngine.getDirectory();
        if (!dir.isUpToDate(request.getRequestor()))
        		response.addResult("messages", htmlStringMessages(dir.getRemainingMessages(request.getRequestor())));
        return response;
    }
    
    /**
     * Toggles experiment control for the passed in user
     * 
     * @param request 
     * @return response
     */
    public PrimitiveResponse toggleControlAction(PrimitiveRequest request) throws IOException
    {
    	if (request.getRequestor().equals(CollaborationEngine.getMasterUser())){
    		String user = request.getParameters().get("user");
    		if (!CollaborationEngine.hasControl(user))
    			CollaborationEngine.assignControlToUser(user);
    		else
    			CollaborationEngine.removeControlFromUser(user);
    	}
    	else if(CollaborationEngine.getMode().equals("Baton Pass") && CollaborationEngine.hasControl(request.getRequestor()))
    	{
    		CollaborationEngine.assignControlToUser(request.getParameters().get("user"));
    	}
    	return dataAction(request);
    }
    
    public PrimitiveResponse setModeAction(PrimitiveRequest request) throws IOException
    {
    	if (!request.getRequestor().equals(CollaborationEngine.getMasterUser()))
    		return dataAction(request);
    	
    	String mode = request.getParameters().get("mode");
    	if (mode.equals("Free For All")){
    		CollaborationEngine.setModeFFA();
    	}
    	else if (mode.equals("Master/Slave")){
    		CollaborationEngine.setModeMS();
    	}
    	else if (mode.equals("Baton Pass")){
    		CollaborationEngine.setModeBP();
    	}
    	return dataAction(request);
    }
    
    /**
     * Pushes a message to the Communication Directory
     * 
     * @param request 
     * @return response
     */
	public PrimitiveResponse pushMessageAction(PrimitiveRequest request) throws IOException
	{
		CollaborationEngine.getDirectory().addMessage(request.getRequestor(), request.getParameters().get("message"));
		return dataAction(request);
	}
	
	public boolean hasControl(PrimitiveRequest request){
		return CollaborationEngine.hasControl(request.getRequestor());
	}

	@Override
	public boolean postRoute() {
		return true;
	}

	@Override
	public void cleanup() {
		
	}
	
    /**
     * Converts a list of messages to a HTML string to display in a chat box interface
     * 
     * @param List of messages
     * @return String of messages
     */
	private String htmlStringMessages(List<Message> messages){
		String ret = "";
		for (Message m : messages){
			ret = ret + m.toString()+"<br/>";
		}
		
		return ret;
	}

}