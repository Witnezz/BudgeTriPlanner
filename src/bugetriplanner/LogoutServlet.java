package bugetriplanner;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		@SuppressWarnings("resource")
		Socket socket = new Socket("localhost",6789);
		ClientRequest loggoutRequest = new ClientRequest("Logout User");
		String username = (String) session.getAttribute("name");
		loggoutRequest.setUsername(username);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		try {
			oos.writeObject(loggoutRequest);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.invalidate();
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/Home.jsp");
		dispatcher.forward(request, response);
		
	}

}
