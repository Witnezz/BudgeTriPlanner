package bugetriplanner;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FlightSearchServlet
 */
@WebServlet("/SearchOfferServlet")
public class SearchOfferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Random randi = new Random();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchOfferServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String origin = request.getParameter("origin");
		String destination = request.getParameter("destination");

		Vector<Object> originCoordinates = getCoordinates(origin);
		if(originCoordinates == null) {
			WriteErrorResponse("origin",response);
			return;
		}

		Vector<Object> destinationCoordinates = getCoordinates(destination);
		if(destinationCoordinates == null) {
			WriteErrorResponse("destination",response);
			return;
		}

		double distance = calculateDisance(originCoordinates,destinationCoordinates);

		String originAirport = (String) originCoordinates.get(2);
		System.out.println(originAirport);
		String destinationAirport = (String) destinationCoordinates.get(2);
		System.out.println(destinationAirport);

		String checkIn = request.getParameter("checkIn");
		checkIn = formulateDate(checkIn);
		String checkOut = request.getParameter("checkOut");
		checkOut = formulateDate(checkOut);

		Vector<FlightOffer> flightOffers = generateFlightOffers(distance,checkIn,checkOut);
		for(FlightOffer offer : flightOffers) {
			System.out.println("ID: "+offer.getAirlineID());
			System.out.println(offer.getPriceString());
			System.out.println("Flight time: "+offer.getFlightTime());
			System.out.println(offer.getOriginDepartureTime());
			System.out.println(offer.getDestinationDepartureTime());
		}

		int numOfNights = getNumberOfNights(checkIn,checkOut);
		System.out.println("nights:" + numOfNights);
		double budgetLeft = Double.valueOf(request.getParameter("budget"));
		double flightTicket = flightOffers.lastElement().getPrice();
		double spendOnFlight = flightTicket/numOfNights;
		budgetLeft -= spendOnFlight;

		System.out.println("Budget left1:" + budgetLeft);
		String rental = request.getParameter("rental");
		Vector<RentalOffer> rentalOffers = null;
		if(rental.equals("true")) {
			if(Double.compare(budgetLeft, 115) > 0) {
				budgetLeft -= 50;
				rentalOffers = generateRentalOffers();
				for(RentalOffer offer : rentalOffers) {
					System.out.println(offer.getPriceString());
				}
			}
		}
		System.out.println("Budget left2:" + budgetLeft);

		Vector<HotelOffer> hotelOffers = null;
		if(numOfNights != 0) {
			if(Double.compare(budgetLeft, 65) < 0) {
				WriteErrorResponse("budget",response);
				return;
			}
			else {
				hotelOffers = generateHotelOffers(destination,budgetLeft);
				for(HotelOffer offer : hotelOffers) {
					System.out.println(offer.getHotelName());
					System.out.println(offer.getRating());
					System.out.println(offer.getPriceString());
					System.out.println(offer.getPictureID());
					System.out.println(offer.getAddress());
				}
			}
		}

		WriteJsonResponse(originAirport,destinationAirport,checkIn,checkOut,numOfNights,
				rental,flightOffers,rentalOffers,hotelOffers,response);

	}

	private void WriteJsonResponse(String originAirport, String destinationAirport, String checkIn, String checkOut,
			int numOfNights, String rental, Vector<FlightOffer> flightOffers, Vector<RentalOffer> rentalOffers,
			Vector<HotelOffer> hotelOffers, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		// start building a JSON tree.// start building a JSON tree.
		JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
		JsonObjectBuilder nodeBuilder = Json.createObjectBuilder();
		
		rootBuilder.add("status", "ok");
		rootBuilder.add("originAirport", originAirport);
		rootBuilder.add("destinationAirport", destinationAirport);
		rootBuilder.add("checkIn", checkIn);
		rootBuilder.add("checkOut", checkOut);
		rootBuilder.add("numOfNights", numOfNights);
		
		
		JsonArrayBuilder flightBuilder = Json.createArrayBuilder();
		for(FlightOffer flightOffer : flightOffers) {
			nodeBuilder.add("airlineID", flightOffer.getAirlineID());
			nodeBuilder.add("flightTime", flightOffer.getFlightTime());
			nodeBuilder.add("priceString", flightOffer.getPriceString());
			nodeBuilder.add("price", flightOffer.getPrice());
			nodeBuilder.add("originDepartureTime", flightOffer.getOriginDepartureTime());
			nodeBuilder.add("destinationDepartureTime", flightOffer.getDestinationDepartureTime());
			JsonObject offer = nodeBuilder.build();
			flightBuilder.add(offer);
		}
		rootBuilder.add("flightOffers", flightBuilder);
		
		if(!rental.equals("true"))
			rootBuilder.add("rentalStatus", "not requested");
		else {
			if(rentalOffers == null) {
				rootBuilder.add("rentalStatus", "error");
			}
			else {
				rootBuilder.add("rentalStatus", "ok");
				JsonArrayBuilder rentalBuilder = Json.createArrayBuilder();
				for(RentalOffer rentalOffer : rentalOffers) {
					nodeBuilder.add("priceString", rentalOffer.getPriceString());
					nodeBuilder.add("price", rentalOffer.getPrice());
					JsonObject offer = nodeBuilder.build();
					rentalBuilder.add(offer);
				}
				rootBuilder.add("rentalOffers", rentalBuilder);
			}
		}
		
		JsonArrayBuilder hotelBuilder = Json.createArrayBuilder();
		for(HotelOffer hotelOffer : hotelOffers) {
			nodeBuilder.add("name", hotelOffer.getHotelName());
			nodeBuilder.add("address", hotelOffer.getAddress());
			nodeBuilder.add("rating", hotelOffer.getRating());
			nodeBuilder.add("priceString", hotelOffer.getPriceString());
			nodeBuilder.add("price", hotelOffer.getPrice());
			nodeBuilder.add("pictureID", hotelOffer.getPictureID());
			JsonObject offer = nodeBuilder.build();
			hotelBuilder.add(offer);
		}
		rootBuilder.add("hotelOffers", hotelBuilder);
		
		JsonObject root = rootBuilder.build();
		writer.print(root);
		writer.flush();
		writer.close();
	}

	private void WriteErrorResponse(String error, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		// start building a JSON tree.// start building a JSON tree.
		JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
		rootBuilder.add("status", "error");
		rootBuilder.add("error", error);
		JsonObject root = rootBuilder.build();
		writer.print(root);
		writer.flush();
		writer.close();
	}

	private Vector<RentalOffer> generateRentalOffers() {
		Vector<RentalOffer> offers = new Vector<RentalOffer>();
		for(int i=5; i>0; i--) {
			RentalOffer offer = new RentalOffer();
			offer.setPrice(50-i+randi.nextDouble());
			offers.add(offer);
		}
		return offers;
	}

	public class HotelOfferComparator implements Comparator<HotelOffer> {
		// over writes compare function
		@Override
		public int compare(HotelOffer offer1, HotelOffer offer2) {
			if (offer1.getRating() < offer2.getRating()) return -1;
			if (offer1.getRating() > offer2.getRating()) return 1;
			return 0;
		}
	}

	private Vector<HotelOffer> generateHotelOffers(String destination, double budgetLeft) throws IOException {
		String searchFor = "hotels+in+"+destination.replaceAll(" ", "+");
		String key = "&key=AIzaSyCiJGWKe7T3q31gMHueaf4IT9mFgvKZaws";

		URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + searchFor + key);  
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.connect();
		JsonReader reader = Json.createReader(conn.getInputStream());
		JsonObject jsonObject = reader.readObject();
		reader.close();

		if(conn.getResponseCode() == 200) {
			JsonArray resultsArray = jsonObject.getJsonArray("results");
			Vector<HotelOffer> offers = new Vector<HotelOffer>();
			for(int i=0; i<resultsArray.size(); i++) {
				if(i==5)
					break;
				HotelOffer offer = new HotelOffer();
				offer.setHotelName(resultsArray.getJsonObject(i).getString("name"));
				offer.setRating(Double.valueOf(resultsArray.getJsonObject(i).get("rating").toString()));
				offer.setAddress(resultsArray.getJsonObject(i).getString("formatted_address"));
				offers.add(offer);
			}
			Collections.sort(offers, new HotelOfferComparator());
			double basePrice = 65;
			double priceJump = (300 - basePrice)/5;
			if(Double.compare(budgetLeft, 300) < 0)
				priceJump = (budgetLeft - basePrice)/5;
			int indexA = 1;
			for(int i=0; i<offers.size(); i++) {
				offers.get(i).setPrice(basePrice-1+randi.nextDouble());
				if(Double.compare(offers.get(i).getPrice(), 200) < 0) {
					offers.get(i).setPictureID("B"+String.valueOf(i+1));
				}
				else {
					offers.get(i).setPictureID("A"+String.valueOf(indexA));
					indexA += 1;
				}
				basePrice += priceJump;
			}
			return offers;
		} 
		else
			return null;
	}
	private String formulateDate(String date) {
		String[] dateArray = date.split(",");
		String month = dateArray[1];
		if(month.length()==1) {
			month = "0" + month;
		}
		String day = dateArray[2];
		if(day.length()==1) {
			day = "0" + day;
		}
		String prettyDate = String.join("-",dateArray[0],month,day);
		return prettyDate;
	}
	private int getNumberOfNights(String checkIn, String checkOut) {
		//Parsing the date
		LocalDate dateBefore = LocalDate.parse(checkIn);
		LocalDate dateAfter = LocalDate.parse(checkOut);

		//calculating number of days in between
		long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		int nights = (int) noOfDaysBetween;

		return nights;
	}

	private Vector<FlightOffer> generateFlightOffers(double distance, String checkIn, String checkOut) {

		Vector<FlightOffer> offers = new Vector<FlightOffer>();

		double basePrice = 0.11 * distance * 2;

		String[] checkInDate = checkIn.split("-");
		int month = Integer.valueOf(checkInDate[1]);
		if(month >= 5 && month <= 10) {
			basePrice += 25;
		}
		int day = Integer.valueOf(checkInDate[2]);
		if(day >= 10 && day <= 20) {
			basePrice += 25;
		}

		String[] checkOutDate = checkOut.split("-");
		month = Integer.valueOf(checkOutDate[1]);
		if(month >= 5 && month <= 10) {
			basePrice += 25;
		}
		day = Integer.valueOf(checkOutDate[2]);
		if(day >= 10 && day <= 20) {
			basePrice += 25;
		}

		int flightMinutes = (int) (distance/9+45);
		Vector<Integer> airlineIDs = generateAirlineIDs();
		int randomFee = 0;
		for(int i=0; i<5; i++) {
			FlightOffer offer = new FlightOffer();
			int id = airlineIDs.remove(0);
			offer.setAirlineID(id);
			randomFee += randi.nextInt(10);
			offer.setPrice(basePrice+i*25+randomFee);
			offer.setFlightTime(flightMinutes);
			offer.setOriginDepartureTime();
			offer.setDestinationDepartureTime();
			offers.add(offer);
		}

		return offers;
	}

	private Vector<Integer> generateAirlineIDs() {
		Vector<Integer> airlineIDs = new Vector<Integer>();
		for(int i=1; i<=5; i++) {
			airlineIDs.add(i);
		}
		Collections.shuffle(airlineIDs);
		return airlineIDs;
	}

	private double calculateDisance(Vector<Object> originCoordinates, Vector<Object> destinationCoordinates) {
		double lat1 = (double) originCoordinates.get(0);
		double lon1 = (double) originCoordinates.get(1);
		double lat2 = (double) destinationCoordinates.get(0);
		double lon2 = (double) destinationCoordinates.get(1);

		double theta = lon1 - lon2;
		double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515;
		return dist;
	}

	private Vector<Object> getCoordinates(String airport) throws IOException {
		Vector<Object> vector = new Vector<Object>();
		String searchFor = "airport+in+"+airport.replaceAll(" ", "+");
		String key = "&key=AIzaSyCiJGWKe7T3q31gMHueaf4IT9mFgvKZaws";

		URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + searchFor + key);  
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.connect();

		JsonReader reader = Json.createReader(conn.getInputStream());
		JsonObject jsonObject = reader.readObject();
		reader.close();
		if(conn.getResponseCode() == 200) {
			if(jsonObject.getString("status").equals("ZERO_RESULTS")) {
				return null;
			}
			else {
				JsonArray resultsArray = jsonObject.getJsonArray("results");
				double lat = Double.valueOf(resultsArray.getJsonObject(0).getJsonObject("geometry").getJsonObject("location").get("lat").toString());
				vector.add(lat);
				double lon = Double.valueOf(resultsArray.getJsonObject(0).getJsonObject("geometry").getJsonObject("location").get("lng").toString());
				vector.add(lon);
				vector.add(new String(resultsArray.getJsonObject(0).getString("name")));
				return vector;
			}
		} 
		else
			return null;
	}

	private final DecimalFormat df = new DecimalFormat("0.00");

	private class FlightOffer {
		private int airlineID;
		private double price;
		private String flightTime;
		private String originDepartureTime;
		private String destinationDepartureTime;

		private void setFlightTime(int flightMinutes) {
			flightMinutes += randi.nextInt(20);
			String hours = String.valueOf(flightMinutes/60);
			String minutes = String.valueOf(flightMinutes%60);
			this.flightTime = hours+"h"+minutes+"m";
		}

		private String getRandomTime() {
			String hour = String.valueOf(randi.nextInt(12)+1);
			if(hour.length()==1) {
				hour = " "+hour;
			}
			String minute = String.valueOf(randi.nextInt(60));
			if(minute.length()==1) {
				minute = "0"+minute;
			}
			String time = hour + ":" + minute;
			int randomInt = randi.nextInt();
			if(randomInt%2 == 0) {
				time += "AM";
			}
			else {
				time += "PM";
			}
			return time;
		}

		public void setOriginDepartureTime() {
			this.originDepartureTime = getRandomTime();
		}

		public void setDestinationDepartureTime() {
			this.destinationDepartureTime = getRandomTime();
		}

		public String getPriceString() {
			return ("$"+df.format(price));
		}

		public double getPrice() {
			return Double.valueOf(df.format(price));
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public int getAirlineID() {
			return airlineID;
		}

		public void setAirlineID(int airlineID) {
			this.airlineID = airlineID;
		}

		public String getFlightTime() {
			return flightTime;
		}

		public String getOriginDepartureTime() {
			return originDepartureTime;
		}

		public String getDestinationDepartureTime() {
			return destinationDepartureTime;
		}
	}

	private class HotelOffer {
		private String hotelName;
		private double price;
		private double rating;
		private String pictureID;
		private String address;

		public String getHotelName() {
			return hotelName;
		}

		public void setHotelName(String hotelName) {
			this.hotelName = hotelName;
		}

		public String getPriceString() {
			return ("$"+df.format(price));
		}

		public double getPrice() {
			return Double.valueOf(df.format(price));
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public double getRating() {

			return rating;
		}

		public void setRating(double rating) {
			this.rating = rating;
		}

		public String getPictureID() {
			return pictureID;
		}
		public void setPictureID(String pictureID) {
			this.pictureID = pictureID;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	}
	private class RentalOffer {
		private double price;
		public String getPriceString() {
			return ("$"+df.format(price));
		}

		public double getPrice() {
			return Double.valueOf(df.format(price));
		}

		public void setPrice(double price) {
			this.price = price;
		}
	}

}








