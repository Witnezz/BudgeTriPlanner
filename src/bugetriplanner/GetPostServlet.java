package bugetriplanner;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/GetPostServlet")
public class GetPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	response.setContentType("application/json");
    	PrintWriter out = response.getWriter();
    	Connection conn = null;
    	PreparedStatement ps1 = null;
    	PreparedStatement ps2 = null;
    	PreparedStatement ps3 = null;
    	try {
    		// Connect to database
    		Class.forName("com.mysql.jdbc.Driver");
    		conn = DriverManager.getConnection("jdbc:mysql://google/finalproject?cloudSqlInstance=stellar-shard-255523:us-central1:wang&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=root");
    		System.out.println("Connected!");
    		
    		String username = request.getParameter("username");
    		System.out.println(username);

    		// Get tripID from likes
    		String s1 = "SELECT * FROM likes WHERE username = ?";
    		ps1 = (PreparedStatement) conn.prepareStatement(s1);
    		ps1.setString(1, username);
    		ResultSet rs1 = ps1.executeQuery();
    		System.out.println("HERE");
    	
    		// MYTRIPS
    		String s2 = "SELECT * FROM tripInfo WHERE username = ?";
    		ps2 = (PreparedStatement) conn.prepareStatement(s2);
    		ps2.setString(1, username);
    		ResultSet rs2 = ps2.executeQuery();
    		
    		// Get trip information from tripInfo
    		String s3 = "SELECT * FROM tripInfo WHERE tripID = ?";
    		ps3 = (PreparedStatement) conn.prepareStatement(s3);
    		ResultSet rs3 = null;
    		
    		// Get all trips from tripID
 		    String s4 = "SELECT * FROM tripInfo";
 		    PreparedStatement ps4 = (PreparedStatement) conn.prepareStatement(s4);
 		    ResultSet rs5 = ps4.executeQuery();
    
    		// Variables of trip information
    		int tripID = -1;
    		String title = "";
    		String author = "";
    		int budget = 0;
    		int days = 0;
    		String start = "";
    		String destination = "";
    		String startDate = "";
    		String endDate = "";
    		String description = "";
    		
    		// JSON
    		JsonArrayBuilder trips = Json.createArrayBuilder();
    		JsonArrayBuilder mytrips = Json.createArrayBuilder();
    		JsonArrayBuilder likedtrips = Json.createArrayBuilder();
    		JsonArrayBuilder alltrips = Json.createArrayBuilder();
    		
    		// ALLTRIPS 
    		while (rs5.next()) {
    			tripID = rs5.getInt("tripID");
 		        title = rs5.getString("tripName");
 		        author = rs5.getString("username");
 		        budget = rs5.getInt("budget");
 		        days = rs5.getInt("days");
 		        start = rs5.getString("startAirport");
 		        destination = rs5.getString("endAirport");
 		        startDate = rs5.getString("startDate");
 		        endDate = rs5.getString("endDate");
 		        description = rs5.getString("description");
 		        
 		        ps3.setInt(1, tripID);
   			    ResultSet rs4 = ps3.executeQuery();
   			
   			    int count = 0;
   			    while(rs4.next()) {
   				   count++;
   			    }
 		       
 		        JsonObjectBuilder curr = getTrip(tripID, title, author, budget, days, start, destination, startDate, endDate, description, count);
 		        alltrips.add(curr);
    		}
    		// MYTRIPS
    		while (rs2.next()) {
    			tripID = rs2.getInt("tripID");
    			title = rs2.getString("tripName");
    			author = rs2.getString("username");
    			budget = rs2.getInt("budget");
    			days = rs2.getInt("days");
    			start = rs2.getString("startAirport");
    			destination = rs2.getString("endAirport");
    			startDate = rs2.getString("startDate");
    			endDate = rs2.getString("endDate");
    			description = rs2.getString("description");
    			
    			ps3.setInt(1, tripID);
    			ResultSet rs4 = ps3.executeQuery();
    			
    			int count = 0;
    			while(rs4.next()) {
    				count++;
    			}
    			
    			JsonObjectBuilder curr = getTrip(tripID, title, author, budget, days, start, destination, startDate, endDate, description, count);
    			mytrips.add(curr);
    		}
    		// LIKEDTRIPS
    		while (rs1.next()) {
    			int tid = rs1.getInt("tripID");
    			ps3.setInt(1, tid);
    			rs3 = ps3.executeQuery();
    			
    			tripID = rs3.getInt("tripID");
    			title = rs3.getString("tripName");
    			author = rs3.getString("username");
    			budget = rs3.getInt("budget");
    			days = rs3.getInt("days");
    			start = rs3.getString("startAirport");
    			destination = rs3.getString("endAirport");
    			startDate = rs3.getString("startDate");
    			endDate = rs3.getString("endDate");
    			description = rs3.getString("description");
    			
    			ps3.setInt(1, tripID);
    			ResultSet rs4 = ps3.executeQuery();
    			
    			int count = 0;
    			while(rs4.next()) {
    				count++;
    			}
    			
    			JsonObjectBuilder curr = getTrip(tripID, title, author, budget, days, start, destination, startDate, endDate, description, count);
    			likedtrips.add(curr);
    		}
    		JsonArray mytripsArr = mytrips.build();
    		JsonArray likedtripsArr = likedtrips.build();
    		JsonArray alltripsArr = alltrips.build();
    		trips.add(mytripsArr);
    		trips.add(likedtripsArr);
    		trips.add(alltripsArr);
    		JsonArray tripsArr = trips.build();
    		
    		String result = tripsArr.toString();
    		System.out.println(result);
    		out.print(result);
    		
    	} catch (SQLException sqle) {
    		sqle.printStackTrace();
    		
    	} catch (ClassNotFoundException e) {
    		
    	} finally {
    		try {
    			if (out != null) {
    				out.close();
    			}
        		if (conn != null) {
        			conn.close();
        		}
        		if (ps1 != null) {
        			ps1.close();
        		}
        		if (ps2 != null) {
        			ps2.close();
        		}
        		if (ps3 != null) {
        			ps3.close();
        		}
    		} catch(SQLException sqle) {
    			
    		}
    	}
    }
    protected JsonObjectBuilder getTrip(int tripID, String title, String author, int budget, int days, String start, String destination, String startDate, String endDate, String description, int count) {
    	JsonObjectBuilder currTrip = Json.createObjectBuilder();
    	currTrip.add("tripID", tripID);
    	currTrip.add("title", title);
    	currTrip.add("author", author);
    	currTrip.add("budget", budget);
    	currTrip.add("days", days);
    	currTrip.add("start", start);
    	currTrip.add("destination", destination);
    	currTrip.add("startDate", startDate);
    	currTrip.add("endDate", endDate);
    	currTrip.add("description", description);
    	currTrip.add("count", count);
    	return currTrip;
    }
}
