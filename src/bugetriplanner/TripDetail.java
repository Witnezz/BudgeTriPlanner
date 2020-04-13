package bugetriplanner;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.sql.*;
import java.util.Vector;

/**
 * Servlet implementation class TripDetail
 */
@WebServlet("/TripDetail")
public class TripDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TripDetail() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in");
		
		String username = request.getParameter("username");
		String t = request.getParameter("tripID");
		int tripID = Integer.parseInt(t);	
		
		//general status variable
		boolean login = username=="" ? true : false;  //front end set: logout is username =""
		boolean likeByUser = false;  //true: current post is owned by current user
		boolean owner = false;    //true: current post is owned by current user
		
		//connect to db
		Connection conn = null;
		ResultSet triprs = null ;
		ResultSet likers = null;
		ResultSet likeListrs = null;
		ResultSet commentrs = null;
		PreparedStatement ps = null;
		String dbConnection = "jdbc:mysql://google/finalproject?cloudSqlInstance"
				+ "=stellar-shard-255523:us-central1:wang&socketFactory=com.google.cloud.sql.mysql.SocketFactory"
				+ "&useSSL=false&user=root&password=root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbConnection);
		}
		catch(ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		}
		catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		
		//variable list
		String tripName ="";
		int budget = 0;
		int days = 0;
		String description = "";
		String startDate="";
		String endDate="";
		String startAirport="";
		String endAirport="";
		String hotelName="";
		String hotelRating="";
		String airline="";
		int car=0;
		String ownerName="";
		Vector<String> likeList = new Vector<String>();
		Vector<Vector<String>> commentList = new Vector<Vector<String>>();;
		
		try {
			ps=conn.prepareStatement("SELECT * FROM tripInfo WHERE tripID = ?");
			ps.setInt(1, tripID);
			triprs=ps.executeQuery();
			
			if(triprs.next()) {
				if(triprs.getString("username").equals(username)){
					owner = true;
				}
				else {
					owner = false;
				}
				
				tripName = triprs.getString("tripName");
				budget = triprs.getInt("budget");
				days = triprs.getInt("days");
				description = triprs.getString("description");
				startDate = triprs.getString("startDate");
				endDate = triprs.getString("endDate");
				startAirport = triprs.getString("startAirport");
				endAirport = triprs.getString("endAirport");
				hotelName = triprs.getString("hotelName");
				hotelRating = triprs.getString("hotelRating");
				airline = triprs.getString("airline");
				car = triprs.getInt("car");
				ownerName = triprs.getString("username");
			}
		}
		catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		
		try {
			ps=conn.prepareStatement("SELECT * FROM likes WHERE username = ? AND tripID = ?");
			ps.setString(1, username);
			ps.setInt(2, tripID);
			likers=ps.executeQuery();
			
			if(likers.next()) {
				//like by current user
				likeByUser = true;
			}
			else {
				likeByUser = false;
			}
		}
		catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		
		try {
			ps=conn.prepareStatement("SELECT * FROM likes WHERE tripID = ?");
			ps.setInt(1, tripID);
			likeListrs=ps.executeQuery();
			
			while(likeListrs.next()) {
				likeList.add(likeListrs.getString("username"));
			}
		}
		catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		
		try {
			ps=conn.prepareStatement("SELECT * FROM comments WHERE tripID = ?");
			ps.setInt(1, tripID);
			commentrs=ps.executeQuery();
			
			while(commentrs.next()) {
				Vector<String> temp = new Vector<String>();
				temp.add(commentrs.getString("username"));
				temp.add(commentrs.getString("content"));
				commentList.add(temp);
			}
		}
		catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		
		 WriteJsonResponse(tripName, budget, days, description, startDate, endDate,
			startAirport, endAirport, hotelName, hotelRating, airline, car, ownerName,
			likeList, likeByUser, owner, login, commentList, response);
	}
	
	private void WriteJsonResponse(String tripName, int budget, int days, String description, String startDate, String endDate,
			String startAirport, String endAirport, String hotelName, String hotelRating, String airline, int car, String ownerName,
			Vector<String> likeList, Boolean likeByUser, Boolean owner, Boolean login, Vector<Vector<String>> commentList, HttpServletResponse response) throws IOException {
		
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		// start building a JSON tree.// start building a JSON tree.
		JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
		JsonObjectBuilder nodeBuilder = Json.createObjectBuilder();
		
		rootBuilder.add("tripName", tripName);
		rootBuilder.add("budget", budget);
		rootBuilder.add("days", days);
		rootBuilder.add("description", description);
		rootBuilder.add("startDate", startDate);
		rootBuilder.add("endDate", endDate);
		rootBuilder.add("startAirport", startAirport);
		rootBuilder.add("endAirport", endAirport);
		rootBuilder.add("hotelName", hotelName);
		rootBuilder.add("hotelRating", hotelRating);
		rootBuilder.add("airline", airline);
		rootBuilder.add("car", car);  //0: no; 1: yes
		rootBuilder.add("owner", ownerName);
		rootBuilder.add("likeFlag", likeByUser);
		rootBuilder.add("ownerFlag", owner);
		rootBuilder.add("loginFlag", login);
		
		JsonArrayBuilder likeListBuilder = Json.createArrayBuilder();
		for(int i=0; i<likeList.size();++i) {
			nodeBuilder.add("username", likeList.get(i));
			JsonObject offer = nodeBuilder.build();
			likeListBuilder.add(offer);
		}
		rootBuilder.add("likeList", likeListBuilder);
		
		JsonArrayBuilder commentListBuilder = Json.createArrayBuilder();
		for(int i=0; i<commentList.size();++i) {
			nodeBuilder.add("username", commentList.get(i).get(0));
			nodeBuilder.add("content", commentList.get(i).get(1));
			JsonObject offer = nodeBuilder.build();
			commentListBuilder.add(offer);
		}
		rootBuilder.add("commentList", commentListBuilder);
		
		JsonObject root = rootBuilder.build();
		writer.print(root);
		writer.flush();
		writer.close();
	}

}
