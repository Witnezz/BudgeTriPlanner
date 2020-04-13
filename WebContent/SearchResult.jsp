<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Results</title>
<style>
body {
	margin: 0;
}

#head-bar {
	background-color: lightyellow;
	height: 100px;
	z-index: 2;
	width: 100%;
}

#main-container {
	margin-top: 0;
	margin-bottom: 0;
	width: 1200px;
	margin-left: auto;
	margin-right: auto;
	background-color: pink;
}

#left-form {
	margin-top: 0;
	float: left;
	width: 300px;
	background-color: pink;
	padding: 25px;
}

input[type=text] {
	width: 250px;
	height: 20px;
	padding: 3px 40px 3px 3px;
	font-size: 15px;
}

#result {
	margin-top: 0;
	float: left;
	width: 800px;
	background-color: lightblue;
	padding: 25px;
}

.result-section {
	padding-bottom: 20px;
	padding-left: 20px;
	padding-right: 20px;
}

.float_clear {
	clear: both;
}

.curr_div {
	display: flex;
	margin: 5px 0;
}

.curr_div>div {
	margin-left: 10px;
}

.curr_div>button {
	background-color: red;
	margin-left: 20px;
	margin-top: 15px;
	height: 50px;
}

.bar {
	background-color: white;
	width: 100%;
	text-align: center;
}

.flight_image {
	
}

.hotel_image {
	height: 200px;
	width: auto;
}

.car_image {
	
}
</style>
<script>
	//general info
	var startAirport;
	var endAirport;
	var days;
	//global variable to store JSON object
	var all_info;
	//global variable to check button clicked
	var flight_bt = "no";
	var hotel_bt = "no";
	var rental_bt = "no";

	function getResult(origin, destination, checkIn, checkOut, budget, rental){
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				// good to go
				if (this.status == 200 && this.readyState == 4) {
					// parse the response text into Json object
					jSONobject = JSON.parse(this.responseText);
					all_info = jSONobject;
					// display on console -- leaved for debug purpose
					console.log(all_info);
					//document.body.innerHTML += this.responseText;
					if(jSONobject.status != "ok"){
						var error = jSONobject.error;
						var error_msg;
						if(error == "origin"){
							error_msg = "Invalid departure location.";
						}
						else if(error == "destination"){
							error_msg = "Invalid destination location.";
						}
						else{
							error_msg = "Please try a higher budget.";
						}
						alert(error_msg);
					   	history.go(-1);
					}
					
					//display
					displayResult(jSONobject);
				}
			}
		};
		xhttp.open("POST", "SearchOfferServlet", true);
		xhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		
		// make check request
		xhttp.send("origin="+origin+"&destination="+destination+"&checkIn="+checkIn+"&checkOut="+checkOut+"&budget="+budget+"&rental="+rental);
	}	
	
	function displayResult(JSONobject){
		for(var i = 0; i < JSONobject.flightOffers.length; i++){
			//button
			var curr_bt = document.createElement("button");
			curr_bt.innerHTML = "SELECT";
			
			//flight
			var curr_div = document.createElement("div");
			curr_div.className = "curr_div";
			curr_div.id = "1"+i;
			
			var curr_image1 = document.createElement("img");
			var curr_origin_time = document.createElement("p");
			var curr_dest_time = document.createElement("p");
			var curr_flight_time = document.createElement("p");
			var curr_price = document.createElement("p");
			var text_div = document.createElement("div");
			
			curr_image1.src = "airline/"+JSONobject.flightOffers[i].airlineID+".jpg";
			curr_image1.alt = "airline_image";
			curr_image1.className = "flight_image";
			
			curr_origin_time.innerHTML = '<strong> Departure Time: </strong>'+JSONobject.flightOffers[i].originDepartureTime;
			curr_dest_time.innerHTML = '<strong> Arrival Time: </strong>'+JSONobject.flightOffers[i].destinationDepartureTime;
			curr_flight_time.innerHTML = '<strong> Duration: </strong>'+JSONobject.flightOffers[i].flightTime;
			curr_price.innerHTML = '<strong> Flight Price: </strong>'+JSONobject.flightOffers[i].priceString;
			
			curr_div.appendChild(curr_image1);
			text_div.appendChild(curr_origin_time);
			text_div.appendChild(curr_dest_time);
			text_div.appendChild(curr_flight_time);
			text_div.appendChild(curr_price);
			curr_div.appendChild(text_div);
			curr_bt.id="1b"+i;
			curr_div.appendChild(curr_bt);
			curr_div.innerHTML += '<div class="float_clear"></div>';
			
			document.getElementById('flight').appendChild(curr_div);
			document.getElementById('flight').innerHTML += "<hr>";
			
			//hotel
			var curr_div2 = document.createElement("div");
			curr_div2.className = "curr_div";
			curr_div2.id = "2"+i;
			
			var curr_image2 = document.createElement("img");
			var curr_name = document.createElement("p");
			var curr_address = document.createElement("p");
			var curr_price_hotel = document.createElement("p");
			var curr_rating = document.createElement("p");
			var text_div2 = document.createElement("div");
			
			curr_image2.src = "hotel/"+JSONobject.hotelOffers[i].pictureID+".jpg";
			curr_image2.alt = "hotel_image";
			curr_image2.className = "hotel_image";
			
			curr_name.innerHTML = '<strong> Name: </strong>'+JSONobject.hotelOffers[i].name;
			curr_address.innerHTML = '<strong> Address : </strong>'+JSONobject.hotelOffers[i].address;
			curr_price_hotel.innerHTML = '<strong> Price: </strong>'+JSONobject.hotelOffers[i].priceString+' /day';
			curr_rating.innerHTML = '<strong> Rating: </strong>'+JSONobject.hotelOffers[i].rating;
			
			curr_div2.appendChild(curr_image2);
			text_div2.appendChild(curr_name);
			text_div2.appendChild(curr_address);
			text_div2.appendChild(curr_price_hotel);
			text_div2.appendChild(curr_rating);
			curr_div2.appendChild(text_div2);
			curr_bt.id="2b"+i;
			curr_div2.appendChild(curr_bt);
			curr_div2.innerHTML += '<div class="float_clear"></div>';
			
			document.getElementById('hotel').appendChild(curr_div2);
			document.getElementById('hotel').innerHTML += "<hr>";
			
			//rental
			if(JSONobject.rentalStatus == "ok"){
				var curr_div3 = document.createElement("div");
				curr_div3.className = "curr_div";
				curr_div3.id = "3"+i;
				
				var curr_image3 = document.createElement("img");
				var curr_price_car = document.createElement("p");
				var text_div3 = document.createElement("div");
				
				curr_image3.src = "rental/"+i+".jpg";
				curr_image3.alt = "car_image";
				curr_image3.className = "car_image";
				
				curr_price_car.innerHTML = '<strong> Price: </strong>'+JSONobject.rentalOffers[i].priceString+' /day';
				
				curr_div3.appendChild(curr_image3);
				text_div3.appendChild(curr_price_car);
				curr_div3.appendChild(text_div3);
				curr_bt.id="3b"+i;
				curr_div3.appendChild(curr_bt);
				curr_div3.innerHTML += '<div class="float_clear"></div>';
				
				document.getElementById('rental').appendChild(curr_div3);
				document.getElementById('rental').innerHTML += "<hr>";
			}
	
		}
		
		var a_buttons = document.querySelectorAll('#flight button');
		//console.log(a_buttons);
		for(var i = 0 ; i < a_buttons.length; i++) {
			a_buttons[i].onclick = function(){
				//console.log(this.parentNode.id);
				if(flight_bt != "no"){
					document.getElementById(flight_bt).style.border = "none";
				}
				if(flight_bt != "no" && flight_bt == this.parentNode.id){	
					flight_bt = "no";
				}
				else{
					document.getElementById(this.parentNode.id).style.border = "1px solid red";
					flight_bt = this.parentNode.id;
				}
						
			};
		}
		
		var b_buttons = document.querySelectorAll('#hotel button');
		//console.log(a_buttons);
		for(var i = 0 ; i < a_buttons.length; i++) {
			b_buttons[i].onclick = function(){
				//console.log(this.parentNode.id);
				if(hotel_bt != "no"){
					document.getElementById(hotel_bt).style.border = "none";
				}
				if(hotel_bt != "no" && hotel_bt == this.parentNode.id){			
					hotel_bt = "no";
				}
				else{
					document.getElementById(this.parentNode.id).style.border = "1px solid red";
					hotel_bt = this.parentNode.id;
				}
				
			};
		}
		if(JSONobject.rentalStatus == "ok"){
			var c_buttons = document.querySelectorAll('#rental button');
			//console.log(a_buttons);
			for(var i = 0 ; i < a_buttons.length; i++) {
				c_buttons[i].onclick = function(){
					//console.log(this.parentNode.id);
					if(rental_bt != "no"){
						document.getElementById(rental_bt).style.border = "none";
					}
					if(rental_bt != "no" && rental_bt == this.parentNode.id){
						rental_bt = "no";
					}
					else{
						document.getElementById(this.parentNode.id).style.border = "1px solid red";
						rental_bt = this.parentNode.id;
					}
				};
			}
		}
		
		if(JSONobject.rentalStatus != "ok"){
			document.getElementById('rental').innerHTML += 'You chose "No Car Renral Options needed".';
			document.getElementById('rental').innerHTML += "<hr>";
		}
		
		startAirport = all_info.originAirport;
		endAirport = all_info.destinationAirport;
		days = all_info.numOfNights;
		
		document.getElementById("startAirport").innerHTML = "Departure Airport: "+startAirport;
		document.getElementById("endAirport").innerHTML = "Destination Airport: "+endAirport;
		document.getElementById("numOfNights").innerHTML = "Number of Nights: "+days;
		//end of displayResult
	}
	
	function vvv(){
		//console.log("validate");
	    var origin = document.getElementById("origin").value;
	    var destination = document.getElementById("destination").value;
	    var budget = document.getElementById("budget").value;
	    var checkin =document.getElementById("checkin").value;
	    var checkout =document.getElementById("checkout").value;
	    var today = new Date();
	    var dd = String(today.getDate()).padStart(2, '0');
	    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
	    var yyyy = today.getFullYear();
	    today = yyyy+ mm  + dd;
	    var temp1 = checkin.replace("-","");
	    var temp2 = checkout.replace("-","");
	    var ci = Number(temp1.replace("-",""));
	    var co = Number(temp2.replace("-",""));
	    var td = Number(today);
	    var carrental =document.getElementById("car").value;
	    if(origin==""){
	    	document.getElementById("formerror").innerHTML = "Origin cannot be empty.";
	    	return false;
	    }
	  	if(destination==""){
	    	document.getElementById("formerror").innerHTML="Destination cannot be empty.";
	    	return false;
	    }
	    if(budget==""){
	    	document.getElementById("formerror").innerHTML="Budget cannot be empty.";
	    	return false;
	    }
	    if(budget.match(/^[0-9]+$/) == null){
	    	document.getElementById("formerror").innerHTML="Budget must be all digits.";
	    	return false;
	    }
	    if(checkin==""){
	    	document.getElementById("formerror").innerHTML="Please pick a checkIn date.";
	    	return false;
	    }
	    if(checkout==""){
	    	document.getElementById("formerror").innerHTML="Please pick a checkOut date.";
	    	return false;
	    }
	    if(ci<td){
	    	document.getElementById("formerror").innerHTML="Please pick a future checkIn date.";
	    	return false;
	    }
	    if(co<td){
	    	document.getElementById("formerror").innerHTML="Please pick a future checkOut date.";
	    	return false;
	    }
	    if(ci>=co){
	    	document.getElementById("formerror").innerHTML="CheckIn must be before CheckOut.";
	    	return false;
	    }
 		return true;
	}
	
	function saveTrip(){
		//if not logged in
		if(!loggedIn){
			alert("Please log in first.");
			return;
		}
		//if not all options are selected
		if(all_info.rentalStatus == "ok" && (flight_bt == "no" || hotel_bt == "no" || rental_bt == "no")){
			alert("Not all trip components are selected.");
			return;
		}
		else if(all_info.rentalStatus != "ok" && (flight_bt == "no" || hotel_bt == "no")){
			alert("Not all trip components are selected.");
			return;
		}
		else{
			if(all_info.rentalStatus == "ok"){
				var tripName = document.getElementById("tripName").value;
				var tripDescription = document.getElementById("tripDescription").value;
				if(tripName==""){
					alert("Please enter trip name.");
			    	return;
			    }
			  	if(tripDescription==""){
			    	alert("Please enter trip description.");
			    	return;
			    }
				
				//console.log(tripName);
				//console.log(tripDescription);
				
				// get info from global variable
				var flight_info_id = flight_bt.substring(1);
				var hotel_info_id = hotel_bt.substring(1);
				var rental_info_id = rental_bt.substring(1);
				
				console.log(flight_info_id);
				console.log(hotel_info_id);
				console.log(rental_info_id);
				
				var outbound = all_info.flightOffers[flight_info_id].originDepartureTime;
				var inbound = all_info.flightOffers[flight_info_id].originDepartureTime;
				var flightTime = all_info.flightOffers[flight_info_id].flightTime;
				var flightPrice = all_info.flightOffers[flight_info_id].price;
				var flightPrice_s = all_info.flightOffers[flight_info_id].priceString;
				var airlineID = all_info.flightOffers[flight_info_id].airlineID;
				
				var airline;
				if(airlineID == 1){
					airline = "jetBlue";
				}
				else if(airlineID == 2){
					airline = "Spirit";
				}
				else if(airlineID == 3){
					airline = "Delta";
				}
				else if(airlineID == 4){
					airline = "Amerian Airlines";
				}
				else if(airlineID == 5){
					airline = "United";
				}
				
				var name = all_info.hotelOffers[hotel_info_id].name;
				var address = all_info.hotelOffers[hotel_info_id].address;
				var rating = all_info.hotelOffers[hotel_info_id].rating;
				var hotelPrice = all_info.hotelOffers[hotel_info_id].price;
				var hotelPrice_s = all_info.hotelOffers[hotel_info_id].priceString;
				
				var rentalPrice = all_info.rentalOffers[rental_info_id].price;
				var rentalPrice_s = all_info.rentalOffers[rental_info_id].priceString;
				
				var numberOfNights = all_info.numOfNights;
				
				var dailyCost = ((flightPrice + hotelPrice*numberOfNights + rentalPrice*numberOfNights)/numberOfNights).toFixed(2);
				
				/*
				console.log(outbound);
				console.log(inbound);
				console.log(flightTime);
				console.log(flightPrice);
				
				console.log(name);
				console.log(address);
				console.log(rating);
				console.log(hotelPrice);
				
				console.log(rentalPrice);
				console.log(numberOfNights);
				
				console.log(dailyCost);
				console.log("end");
				*/
				
				//send to backend to store
				var xhttp = new XMLHttpRequest();
				xhttp.open("POST", "AddPostServlet", false);
				xhttp.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded");
				
				//username from session needed
				xhttp.send("tripName="+tripName+"&dailyCost="
						+dailyCost+"&days="+numberOfNights+"&description="
						+tripDescription+"&startDate="+checkIn+"&endDate="+checkOut
						+"&startAirport="+startAirport+"&endAirport="+endAirport
						+"&hotelName="+name+"&hotelRating="
						+rating+"&airline="+airline+"&car=1");
				var url = "PostFeed.jsp";
				window.location=url;
				return false;
			}
			else{
				var tripName = document.getElementById("tripName").value;
				var tripDescription = document.getElementById("tripDescription").value;
				if(tripName==""){
					alert("Please enter trip name.");
			    	return;
			    }
			  	if(tripDescription==""){
			    	alert("Please enter trip description.");
			    	return;
			    }
				
				//console.log(tripName);
				//console.log(tripDescription);
				
				// get info from global variable
				var flight_info_id = flight_bt.substring(1);
				var hotel_info_id = hotel_bt.substring(1);
				
				console.log(flight_info_id);
				console.log(hotel_info_id);
				
				var outbound = all_info.flightOffers[flight_info_id].originDepartureTime;
				var inbound = all_info.flightOffers[flight_info_id].originDepartureTime;
				var flightTime = all_info.flightOffers[flight_info_id].flightTime;
				var flightPrice = all_info.flightOffers[flight_info_id].price;
				var flightPrice_s = all_info.flightOffers[flight_info_id].priceString;
				var airlineID = all_info.flightOffers[flight_info_id].airlineID;
				
				var airline;
				if(airlineID == 1){
					airline = "jetBlue";
				}
				else if(airlineID == 2){
					airline = "Spirit";
				}
				else if(airlineID == 3){
					airline = "Delta";
				}
				else if(airlineID == 4){
					airline = "Amerian Airlines";
				}
				else if(airlineID == 5){
					airline = "United";
				}
				
				var name = all_info.hotelOffers[hotel_info_id].name;
				var address = all_info.hotelOffers[hotel_info_id].address;
				var rating = all_info.hotelOffers[hotel_info_id].rating;
				var hotelPrice = all_info.hotelOffers[hotel_info_id].price;
				var hotelPrice_s = all_info.hotelOffers[hotel_info_id].priceString;
				
				var numberOfNights = all_info.numOfNights;
				
				var dailyCost = ((flightPrice + hotelPrice*numberOfNights)/numberOfNights).toFixed(2);
				
				/*
				console.log(outbound);
				console.log(inbound);
				console.log(flightTime);
				console.log(flightPrice);
				
				console.log(name);
				console.log(address);
				console.log(rating);
				console.log(hotelPrice);
				
				console.log(numberOfNights);
				
				console.log(dailyCost);
				console.log("end");
				*/
				
				//send to backend to store
				var xhttp = new XMLHttpRequest();
				xhttp.open("POST", "AddPostServlet", false);
				xhttp.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded");
				
				//username from session needed
				xhttp.send("tripName="+tripName+"&dailyCost="
						+dailyCost+"&days="+numberOfNights+"&description="
						+tripDescription+"&startDate="+checkIn+"&endDate="+checkOut
						+"&startAirport="+startAirport+"&endAirport="+endAirport
						+"&hotelName="+name+"&hotelRating="
						+rating+"&airline="+airline+"&car=0");
				var url = "PostFeed.jsp";
				window.location=url;
				
				return false;
			}
		}
	};
	
</script>
</head>
<body>
	<!--  get form input -->
	<%
		String origin = request.getParameter("origin").toString();
		String destination = request.getParameter("destination").toString();
		String checkIn = request.getParameter("checkIn").toString();
		String checkOut = request.getParameter("checkOut").toString();
		String budget_string = request.getParameter("budget").toString();
		String rental = request.getParameter("rental").toString();

		checkIn = checkIn.replace("-", ",");
		checkOut = checkOut.replace("-", ",");
	%>
	<!-- get login information -->


	<div id="main-container">
		<div id="left-form">
			<form method="GET" name="myform" onsubmit="return vvv()"
				action="SearchResult.jsp">
				<div id="formerror"></div>
				<h4>Flying From:</h4>
				<input class="box" id="origin" type="text" name="origin"
					placeholder="Where do you start your trip?"><br> <br>
				<h4>Flying To:</h4>
				<input class="box" id="destination" type="text" name="destination"
					placeholder="Search for your destination"><br> <br>
				<h4>Daily Budget:</h4>
				<input class="box" id="budget" type="text" name="budget"><br>
				<br>
				<h4>Check-in:</h4>
				<input id="checkin" type="date" name="checkIn"><br> <br>
				<h4>Check-out:</h4>
				<input id="checkout" type="date" name="checkOut"><br> <br>
				<h4>Car Rental:</h4>
				<select name="rental" id="car">
					<option value="true">YES!</option>
					<option value="false">NO!</option>
				</select> <br>
				<div class="clearfloat"></div>
				<br> <label><button id="bt" type="submit"
						value="Search!">Search!</button></label>
			</form>
		</div>
		<div id="result">
			<h1>
				Search Result for destination: "<%=destination%>"
			</h1>
			<div class="general_info" id="general">
				<h2>General Information</h2>
				<p id="startAirport"></p>
				<p id="endAirport"></p>
				<p id="numOfNights"></p>
			</div>

			<div class="result-section" id="flight">
				<h2>Flight</h2>
				<h5>*Departure/arrival time based on local time</h5>
			</div>
			<div class="result-section" id="hotel">
				<h2>Hotel</h2>
			</div>
			<div class="result-section" id="rental">
				<h3>Car Rental Options</h3>
			</div>
		</div>
		<div class="float_clear"></div>
	</div>

	<div class="bar">
		<form>
			<h4>Trip Name:</h4>
			<input id="tripName" type="text" name="tripName"
				placeholder="Enter your trip name.">
			<h4>Trip Description:</h4>
			<input id="tripDescription" type="text" name="tripDescription"
				placeholder="Enter your trip description.">
		</form>
		<br>
		<button onclick="saveTrip()">Save Trip</button>
	</div>

	<script>
		var origin = '<%=origin%>';
		var destination = '<%=destination%>';
		var checkIn = '<%=checkIn%>';
		var checkOut = '<%=checkOut%>';
		var budget_string = '<%=budget_string%>';
		var rental = '<%=rental%>';

		console.log(origin);
		console.log(destination);
		console.log(checkIn);
		console.log(checkOut);
		console.log(budget_string);
		console.log(rental);

		getResult(origin, destination, checkIn, checkOut, budget_string, rental);
	</script>

	<script type="text/javascript">
		var loggedIn = false;
	</script>
	<%
		HttpSession sess = request.getSession();
		String username = (String) sess.getAttribute("name");
	%>
	<script type="text/javascript">
		
	</script>
	<%
		if (username != null) {
	%>
	<script type="text/javascript">
		loggedIn = true;
		//loggedin();
	</script>
	<%
		}
	%>
	<%
		if (username == null) {
	%>
	<script type="text/javascript">
		//loggedout();
	</script>
	<%
		}
	%>
</body>
</html>