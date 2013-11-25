package au.edu.uts.eng.remotelabs.rigclient.collaboration.types;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	private String user;
	private String message;
	private Date timestamp;
	
	public Message(String user, String message){
		this.user = user;
		this.message = message;
		this.timestamp = new Date();
	}
	
	public String toString(){
		return (user + "[" + (new SimpleDateFormat("hh:mm:ss a")).format(timestamp).toString() + "]: " + message); 
	}
}
