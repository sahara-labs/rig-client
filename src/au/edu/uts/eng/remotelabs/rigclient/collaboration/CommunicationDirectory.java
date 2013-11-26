package au.edu.uts.eng.remotelabs.rigclient.collaboration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import au.edu.uts.eng.remotelabs.rigclient.type.RigFactory;
import au.edu.uts.eng.remotelabs.rigclient.util.ILogger;
import au.edu.uts.eng.remotelabs.rigclient.util.LoggerFactory;

import au.edu.uts.eng.remotelabs.rigclient.collaboration.types.Message;

public class CommunicationDirectory {

	private ILogger logger;
	private ArrayList<Message> messages;
	private Map<String, Integer> lastReceived;
	
	public CommunicationDirectory()
	{
		logger = LoggerFactory.getLoggerInstance();
		messages = new ArrayList<Message>();
		lastReceived = new TreeMap<String, Integer>();

        logger.debug("Communication Directory re-initialized.");
	}
	
	public synchronized boolean addMessage(String user, String message)
	{
		messages.add(new Message(user, message));
		return true;
	}
	
	public Message getMessage(int id)
	{
		try
		{
			return messages.get(id);
		}
		catch(IndexOutOfBoundsException e)
		{
			logger.warn("Invalid message index in Communication Directory.");
			return null;
		}
	}
	
	public boolean isUpToDate(String user)
	{
		if (!lastReceived.containsKey(user))
		{
			lastReceived.put(user, -1);
		}
		return (lastReceived.get(user).intValue() == (messages.size()-1));
	}
	
	public List<Message> getMessages(int startId)
	{
		return messages.subList(startId, messages.size());
	}
	
	public List<Message> getRemainingMessages(String user)
	{
		if (!lastReceived.containsKey(user))
		{
			lastReceived.put(user, -1);
		}
		int s = messages.size();
		List<Message> ret = messages.subList(lastReceived.get(user)+1, s);
		lastReceived.put(user, s-1);
		return ret;
		
	}
	
}
