package quicktests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveRequest;
import au.edu.uts.eng.remotelabs.rigclient.rig.IRigControl.PrimitiveResponse;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import au.edu.uts.eng.remotelabs.rigclient.collaboration.types.Message;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*ArrayList<Message> messages = new ArrayList<Message>();
		
		try{
			messages.get(0);
		}
		catch (IndexOutOfBoundsException e){
			System.out.println("Invalid index");
		}*/
		
		TreeSet<String> ts = new TreeSet<String>();
		
		ts.add("a");
		
		System.out.println(ts.contains("a"));
		
		
		//System.out.println(CollaborationController.class.toString());
	}

}
