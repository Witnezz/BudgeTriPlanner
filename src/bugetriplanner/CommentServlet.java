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
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CommentServlet() {
        super();
    }

    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClientRequest commentRequest = new ClientRequest("Add Comment");
		
		String username = (String) request.getSession().getAttribute("name");
		commentRequest.setUsername(username);
		
		String tripID = request.getParameter("tripID");
		int tripIDInt = Integer.valueOf(tripID);
		System.out.println(tripIDInt);
		commentRequest.setTripID(tripIDInt);
		
		String comment = request.getParameter("comment");
		System.out.println(comment);
		commentRequest.setComment(comment);
		
		@SuppressWarnings("resource")
		Socket socket = new Socket("localhost",6789);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		try {
			oos.writeObject(commentRequest);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
