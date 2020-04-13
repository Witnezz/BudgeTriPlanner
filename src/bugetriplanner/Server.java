package bugetriplanner;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Server {

	private static HashMap<String,ObjectOutputStream> loggedInUsers = new HashMap<String,ObjectOutputStream>();

	public Server() {
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(6789);
			System.out.println("connected");
			while (true) {
				Socket s = ss.accept();
				ServerThread st = new ServerThread(this,s);
				st.start();
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	public static void main(String [] args) {
		new Server();
	}

	public static void sendRequest(String username, ClientRequest request) {
		for(int i=0; i<loggedInUsers.size(); i++) {
			ObjectOutputStream oos = loggedInUsers.get(username);
			try {
				oos.writeObject(request);
				oos.flush();
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
	}

	public static void addUser(String userName, ObjectOutputStream oos) {
		loggedInUsers.put(userName, oos);
	}
	
	public static void addRemovePost(ClientRequest request, boolean add) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// establish SQL connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://google/finalproject?cloudSqlInstance=stellar-shard-255523:us-central1:wang&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=root");
			if(add) {
				String newEntyString = "INSERT INTO tripInfo (username, tripName, budget, days, description,"
						+ " startDate, endDate, startAirport, endAirport, hotelName, hotelRating, airline, car)"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				ps = conn.prepareStatement(newEntyString);
				ps.setString(1, request.getUsername());
				ps.setString(2, request.getTripName());
				ps.setInt(3, request.getBudget());
				ps.setInt(4, request.getBudget());
				ps.setString(5, request.getDescription());
				ps.setString(6, request.getStartDate());
				ps.setString(7, request.getEndDate());
				ps.setString(8, request.getStartAirport());
				ps.setString(9, request.getEndAirport());
				ps.setString(10, request.getHotelName());
				ps.setDouble(11, request.getHotelRating());
				ps.setInt(12, request.getBudget());
				ps.setInt(13, request.getCar());
				ps.executeUpdate();
			}
			else {
				ps = conn.prepareStatement("DELETE FROM likes WHERE tripID = ?;");
				ps.setInt(1, request.getTripID());
				ps.executeUpdate();
				ps = conn.prepareStatement("DELETE FROM comments WHERE tripID = ?;");
				ps.setInt(1, request.getTripID());
				ps.executeUpdate();
				ps = conn.prepareStatement("DELETE FROM tripInfo WHERE tripID = ?;");
				ps.setInt(1, request.getTripID());
				ps.executeUpdate();
			}
		} catch (SQLException sqle) {
			// SQL exception return false
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			// CNF exception return false
			System.out.println(cnfe.getMessage());
		} finally {
			try {
				// close prepare statement
				if (ps != null) {
					ps.close();
				}
				// close connection
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void like(ClientRequest request, boolean like) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// establish SQL connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://google/finalproject?cloudSqlInstance=stellar-shard-255523:us-central1:wang&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=root");
			if(like) {
				String newEntyString = "INSERT INTO likes (username, tripID) VALUES (?, ?);";
				ps = conn.prepareStatement(newEntyString);
			}
			else {
				ps = conn.prepareStatement("DELETE FROM likes WHERE username = ? AND tripID = ?;");
			}
			ps.setString(1, request.getUsername());
			ps.setInt(2, request.getTripID());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			// SQL exception return false
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			// CNF exception return false
			System.out.println(cnfe.getMessage());
		} finally {
			try {
				// close prepare statement
				if (ps != null) {
					ps.close();
				}
				// close connection
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addComment(ClientRequest request) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// establish SQL connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://google/finalproject?cloudSqlInstance=stellar-shard-255523:us-central1:wang&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=root");
			ps = conn.prepareStatement("INSERT INTO comments (username, tripID, content) VALUES (?, ?, ?);");
			ps.setString(1, request.getUsername());
			ps.setInt(2, request.getTripID());
			ps.setString(3, request.getComment());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			// SQL exception return false
			System.out.println(sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			// CNF exception return false
			System.out.println(cnfe.getMessage());
		} finally {
			try {
				// close prepare statement
				if (ps != null) {
					ps.close();
				}
				// close connection
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
