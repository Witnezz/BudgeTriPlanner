package bugetriplanner;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userNameInput = request.getParameter("username");
		String userPasswordInput = request.getParameter("password");
		findUser(userNameInput, userPasswordInput, request, response);
	}

	public static void findUser(String userNameInput, String userPasswordInput, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		userNameInput = userNameInput.trim().toLowerCase();


		String searchString = "SELECT * from users WHERE username = ?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://google/finalproject?cloudSqlInstance=stellar-shard-255523:us-central1:wang&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=root");
			// set prepare statement
			ps = conn.prepareStatement(searchString);
			// set prepare statement parameters
			ps.setString(1, userNameInput);
			// get result set
			rs = ps.executeQuery();
			// if no user has the same user name as input
			if (rs.next() == false) {
				// response user non existence
				out.print("This user does not exist.");
			} else {
				// if user name has found
				do {
					// get password from result set
					String userPassword  = rs.getString("password");
					// if password found matches with input
					if(userPassword.equals(userPasswordInput)) {
						
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
						
						
						// response success login message
						out.print("Successful login!");
					}
					// if password found does not match with input
					else {
						// response in correct password message
						out.print("Incorrect password.");
					}
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



}
