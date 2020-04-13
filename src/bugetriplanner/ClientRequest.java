package bugetriplanner;

import java.io.ObjectOutputStream;
import java.io.Serializable;

// class that carries all the parameters required
// for client to communicate with server
// contains only getters and setters
// for serialization purpose
public class ClientRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String request;
	private String username;
	private String tripName;
	private int budget;
	private String description;
	private String startDate;
	private String endDate;
	private String startAirport;
	private String endAirport;
	private String hotelName;
	private double hotelRating;
	private String airline;
	private int car;
	private int tripID;
	private String comment;
	private ObjectOutputStream oos;
	
	public ClientRequest(String request) {
		this.request = request;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartAirport() {
		return startAirport;
	}

	public void setStartAirport(String startAirport) {
		this.startAirport = startAirport;
	}

	public String getEndAirport() {
		return endAirport;
	}

	public void setEndAirport(String endAirport) {
		this.endAirport = endAirport;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public double getHotelRating() {
		return hotelRating;
	}

	public void setHotelRating(double hotelRating) {
		this.hotelRating = hotelRating;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public int getCar() {
		return car;
	}

	public void setCar(int car) {
		this.car = car;
	}

	public int getTripID() {
		return tripID;
	}

	public void setTripID(int tripID) {
		this.tripID = tripID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	
}
