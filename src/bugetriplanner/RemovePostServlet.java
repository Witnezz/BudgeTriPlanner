package bugetriplanner;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemovePostServlet
 */
@WebServlet("/RemovePostServlet")
public class RemovePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemovePostServlet() {
        super();
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in");
		ClientRequest removeRequest = new ClientRequest("Remove Post");
		String username = (String) request.getSession().getAttribute("name");
		removeRequest.setUsername(username);
		
		
		String tripID = request.getParameter("tripID");
		int tripIDInt = Integer.valueOf(tripID);
		System.out.println(tripIDInt);
		removeRequest.setTripID(tripIDInt);
		
		@SuppressWarnings("resource")
		Socket socket = new Socket("localhost",6789);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		try {
			oos.writeObject(removeRequest);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
