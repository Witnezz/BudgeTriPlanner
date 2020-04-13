package bugetriplanner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Calendar;

public class ServerThread extends Thread {	
	private ObjectInputStream ois;
	private boolean exit = false;
	private Socket socket;


	public ServerThread(Server server, Socket socket) {
		this.socket = socket;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	public ClientRequest getRequest() {
		try {
			// get request from client
			ClientRequest request = (ClientRequest)ois.readObject();
			return request;
		} catch (IOException ioe) {
			Thread.yield();
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		}
		return null;
	}

	public void run() {
		while(!exit) {
			ClientRequest request = getRequest();
			if(request != null && request.getRequest()!=null) {
				System.out.println(request.getRequest());
				switch(request.getRequest()) {
				case "Login User":
					printStatus("user <"+request.getUsername()+"> session created.");
					break;
				case "Add Post":
					Server.addRemovePost(request,true);
					printStatus("user <"+request.getUsername()+"> added new post named <"+request.getTripName()+">.");
					break;
				case "Remove Post":
					Server.addRemovePost(request,false);
					printStatus("user <"+request.getUsername()+"> removed post id: <"+request.getTripID()+">.");
					break;
				case "Like":
					Server.like(request,true);
					printStatus("user <"+request.getUsername()+"> liked post id: <"+request.getTripID()+">.");
					break;
				case "Dislike":
					Server.like(request,false);
					printStatus("user <"+request.getUsername()+"> disliked post id: <"+request.getTripID()+">.");
					break;
				case "Add Comment":
					Server.addComment(request);
					printStatus("user <"+request.getUsername()+"> added comment to post id: <"+request.getTripID()+">.");
					break;
				case "Logout User":
					printStatus("user <"+request.getUsername()+"> session invalidated.");
					exit = true;
					break;
				}
				exit = true;
				try {
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void printStatus(String status) {
		// get time stamp
		Calendar cal = Calendar.getInstance();
		String time = "" + cal.get(Calendar.HOUR_OF_DAY);
		time += ":" + cal.get(Calendar.MINUTE);
		time += ":" + cal.get(Calendar.SECOND);
		time += "." + cal.get(Calendar.MILLISECOND);
		// add space in front if time stamp is short
		// nicer alignment
		while(time.length() != 12) {
			time = " " + time;
		}
		// print status with time stamp in front
		System.out.println(time + " " + status);
	}

}


