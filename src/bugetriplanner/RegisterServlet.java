package bugetriplanner;

import java.io.IOException;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}
	protected void service() {

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userNameInput = request.getParameter("username");
		String userPasswordInput = request.getParameter("password");
		makeNewUser(userNameInput,userPasswordInput,request,response);
	}

	@SuppressWarnings("resource")
	public static void makeNewUser(String username, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// print writer
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// trim user name input
		username = username.trim().toLowerCase();

		// search string
		String searchString = "SELECT * from users WHERE username = ?";
		// new entry string
		String newEntryString = "INSERT INTO users (username, password) values (?,?);";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://google/finalproject?cloudSqlInstance=stellar-shard-255523:us-central1:wang&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=root");
			// first make prepare statement with search string
			ps = conn.prepareStatement(searchString);
			// set prepare statement parameter to search
			ps.setString(1, username);
			// get result set
			rs = ps.executeQuery();
			// result set empty means the user name to register
			// is valid, it is not taken yet
			if (rs.next() == false) {
				// make new prepare statement to insert
				ps = conn.prepareStatement(newEntryString);
				// set prepare statement parameters with
				// user name and user password input
				ps.setString(1, username);
				ps.setString(2, password);
				// execute update
				ps.executeUpdate();
				// after inserted, login user
				loginUser(username, request,response);
				// response successfully login
				out.print("Successfully created a new account!");
			} else {
				// result set not empty case
				// user name is already taken then
				do {
					// response user name taken
					out.print("This username is already taken.");
				} while (rs.next());
			}
			out.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}


	}
	private static void loginUser(String userNameInput, HttpServletRequest request, HttpServletResponse response) throws UnknownHostException, IOException, ServletException {
		// search string
		String searchString = "SELECT * from users WHERE username = ?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://google/finalproject?cloudSqlInstance=stellar-shard-255523:us-central1:wang&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=root");
			// set prepare statement
			ps = conn.prepareStatement(searchString);
			// set prepare statement parameter with user name
			ps.setString(1, userNameInput);
			// get result set
			rs = ps.executeQuery();
			while (rs.next() ) {
				@SuppressWarnings("resource")
				Socket socket = new Socket("localhost",6789);
				
				HttpSession session = request.getSession();
				session.setAttribute("name",userNameInput);
	
				
				ClientRequest loginRequest = new ClientRequest("Login User");
				loginRequest.setUsername(userNameInput);
				
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				try {
					oos.writeObject(loginRequest);
					oos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}

}
