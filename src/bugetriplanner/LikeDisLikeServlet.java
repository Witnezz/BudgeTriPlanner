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
 * Servlet implementation class LikeDisLikeServlet
 */
@WebServlet("/LikeDisLikeServlet")
public class LikeDisLikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LikeDisLikeServlet() {
        super();
    }
    
    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		ClientRequest likeRequest = null;
		if(action.equals("like")) {
			likeRequest = new ClientRequest("Like");
		} 
		else {
			likeRequest = new ClientRequest("Dislike");
		}
		
		String username = (String) request.getSession().getAttribute("name");
		likeRequest.setUsername(username);
		
		String tripID = request.getParameter("tripID");
		
		int tripIDInt = Integer.valueOf(tripID);

		likeRequest.setTripID(tripIDInt);
		
		@SuppressWarnings("resource")
		Socket socket = new Socket("localhost",6789);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		try {
			oos.writeObject(likeRequest);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}
