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
 * Servlet implementation class AddPostServlet
 */
@WebServlet("/AddPostServlet")
public class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public AddPostServlet() {
        super();
    }
    
    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ClientRequest postRequest = new ClientRequest("Add Post");
    	
    	String username = (String) request.getSession().getAttribute("name");
    	postRequest.setUsername(username);
    	
    	String tripName = request.getParameter("tripName");
    	System.out.println(tripName);
    	postRequest.setTripName(tripName);
    	
    	String budget = request.getParameter("dailyCost");
    	System.out.println(budget);
    	double budgetDouble = Double.valueOf(budget);
    	int budgetInt = (int) budgetDouble;
    	System.out.println(budgetInt);
    	postRequest.setBudget(budgetInt);
    	
    	String days = request.getParameter("days");
    	System.out.println(days);
    	postRequest.setBudget(Integer.valueOf(days));
    	
    	String description = request.getParameter("description");
    	System.out.println(description);
    	postRequest.setDescription(description);
    	
    	String startDate = request.getParameter("startDate").replace(",", "-");
    	System.out.println(startDate);
    	postRequest.setStartDate(startDate);
    	
    	String endDate = request.getParameter("endDate").replace(",", "-");
    	System.out.println(endDate);
    	postRequest.setEndDate(endDate);
    	
    	String startAirport = request.getParameter("startAirport");
    	System.out.println(startAirport);
    	postRequest.setStartAirport(startAirport);
    	
    	String endAirport = request.getParameter("endAirport");
    	System.out.println(endAirport);
    	postRequest.setEndAirport(endAirport);
    	
    	String hotelName = request.getParameter("hotelName");
    	System.out.println(hotelName);
    	postRequest.setHotelName(hotelName);
    	
    	String hotelRating = request.getParameter("hotelRating");
    	double hotelRatingDouble = Double.valueOf(hotelRating);
    	System.out.println(hotelRatingDouble);
    	postRequest.setHotelRating(Double.valueOf(hotelRatingDouble));
    	
    	String airline = request.getParameter("airline");
    	System.out.println(airline);
    	postRequest.setAirline(airline);
    	
    	String car = request.getParameter("car");
    	int carInt = Integer.valueOf(car);
    	System.out.println(carInt);
    	postRequest.setCar(carInt);
    	
    	@SuppressWarnings("resource")
		Socket socket = new Socket("localhost",6789);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		try {
			oos.writeObject(postRequest);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
    


}
