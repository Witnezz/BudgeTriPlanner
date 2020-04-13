<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile Page</title>
<style type="text/css">
.topbar .box {
	position: relative;
	width: 100%;
	height: 100px;
	left: 5%;
	display: table;
}

.topbar .box .box-row {
	display: table-row;
}

.topbar .box .box-cell {
	display: table-cell;
	width: 33.3%;
	height: 100px;
}

.topbar .box .box-cell .box2 {
	position: relative;
	margin-left: 20px;
	margin-top: 50px;
	margin-bottom: 20px;
}

.topbar .box .box-cell .box3 {
	margin-left: 300px;
}

.main .box2 {
	position: relative;
	width: 100%;
	height: 80%;
	left: 5%;
	display: table;
}

.main .box2 .box-row2 {
	display: table-row;
}

.main .box2 .box-cell2 {
	display: table-cell;
	/* width: 50%;  */
}

.main .box2 .box-cell2 .box1 {
	width: 25%;
}

.main .box2 .box-cell2 .box2 {
	width: 70%;
}

#logo {
	display: block;
	margin-left: auto;
	margin-right: auto;
	margin-top: 30px;
	margin-bottom: auto;
	position: relative;
	width: 200px;
	height: 200px;
	float: left;
	border-radius: 50%;
}

#username {
	position: absolute;
	font-style: italic;
	font-size: 50px;
	font-weight: bold;
	font-family: "Comic Sans MS", cursive, sans-serif;
	margin-top: 100px;
}

h1 {
	font-style: italic;
	font-size: 40px;
	font-weight: bold;
}

#signout {
	position: absolute;
	color: white;
	font-size: 30px;
	background-color: lightblue;
	margin-left: 100px;
	margin-top: 130px;
}

#ta {
	color: black;
	font-size: 30px;
}

#stats {
	color: grey;
}

#likePic {
	width: 60px;
	height: 60px;
}

#likes {
	position: absolute;
	font-size: 15px;
	font-weight: bold;
	color: black;
	margin-top: 20px;
}

#block {
	background-color: lightblue;
}
</style>
</head>
<body>
	<div class="topbar">
		<div class="box">
			<div class="box-row">
				<div class="box-cell box1">
					<a href="homepage.jsp"><img src="Logo.jpeg" id="logo"></a>
				</div>
				<div class="box-cell box2">
					<div id="username">
						Welcome!
						<%=session.getAttribute("username")%></div>
					<div id="birthday"></div>
					<div id="joinedDate"></div>
				</div>
				<div class="box-cell box3">
					<a href="SignOut.jsp" id="signout">Sign Out</a>
				</div>
			</div>
		</div>
	</div>
	<div class="main">
		<div class="box2">
			<div class="box-row2">
				<div class="box-cell2 box1">
					<div class="leftbar">
						<div class="search">
							<!-- Search form by Zhe -->
							<form method="GET" name="myform" onsubmit="return vvv()"
								action="SearchResult.jsp">
								<div id="formerror"></div>
								<h4>Flying From:</h4>
								<input class="box" id="origin" type="text" name="origin"
									placeholder="Where do you start your trip?"><br> <br>
								<h4>Flying To:</h4>
								<input class="box" id="destination" type="text"
									name="destination" placeholder="Search for your destination"><br>
								<br>
								<h4>Daily Budget:</h4>
								<input class="box" id="budget" type="text" name="budget"
									placeholder="Recommended input 100, 500, or 1000000... if you like"><br>
								<br>
								<h4>Check-in:</h4>
								<input id="checkin" type="date" name="checkIn"><br>
								<br>
								<h4>Check-out:</h4>
								<input id="checkout" type="date" name="checkOut"><br>
								<br>
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
					</div>
				</div>
				<div class="box-cell2 box2">
					<div class="content">
						<div class="mytrips">
							<h1>My Trips</h1>
						</div>
						<div class="likedtrips">
							<h1>My Collections</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	/* Search function by Zhe*/
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
	
	<%
	HttpSession sess = request.getSession();
	String username = (String) sess.getAttribute("name");
	%>
	
	var username = '<%= username %>';
	console.log(username);

	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "GetPostServlet?username=" + username, false);
	xhttp.send();
	console.log(xhttp.responseText);
	var result = JSON.parse(xhttp.responseText);

	var mytrips = result[0];
	var savedtrips = result[1];

	/* My trips Block */
	var mytripsblock = document.querySelector(".mytrips");

	if (mytrips.length == 0) {
		var m = document.createElement("div");

		var emptyMsg = document.createElement("h1");
		emptyMsg.id = "emptyMsg";
		emptyMsg.innerHTML = "You haven't created any trip!";

		m.appendChild(emptyMsg);

		mytripsblock.appendChild(m);
	}

	for (var i = 0; i < mytrips.length; i++) {
		let item = mytrips[i];
		/* Get trip information to display */
		var tripID = item.tripID;
		var title = item.title;
		var author = item.author;
		var budget = item.budget;
		var days = item.days;
		var start = item.start;
		var destination = item.destination;
		var startDate = item.startDate;
		var endDate = item.endDate;
		var description = item.description;
		var count = item.count;

		/* Create html block */
		var body = document.createElement("div");

		var t = document.createElement("a");
		var info = document.createElement("h3");
		var stats = document.createElement("h3");
		var des = document.createElement("p");
		var likedC = document.createElement("div");

		body.id = "block";

		t.id = "ta";
		t.href = "Details.jsp" + "?tripID=" + tripID;
		t.innerHTML = title + " Created by " + author;

		info.id = "info";
		info.innerHTML = "From " + start + " to " + destination + "     Date: "
				+ startDate + "-" + endDate;

		stats.id = "stats";
		stats.innerHTML = "$" + budget + "/person, " + days + " days";

		des.id = "description";
		des.innerHTML = "Description: " + description;

		likedC.id = "likedCount";
		likedC.innerHTML = "<img src=\"like.png\" id=\"likePic\">"
				+ "<span id=\"likes\">likes: " + count + "</span>";

		body.appendChild(t);
		body.appendChild(info);
		body.appendChild(stats);
		body.appendChild(des);
		body.appendChild(likedC);

		mytripsblock.appendChild(body);
	}
	/* Liked trips block */
	var likedtripsblock = document.querySelector(".likedtrips");

	if (savedtrips.length == 0) {
		var m = document.createElement("div");

		var emptyMsg = document.createElement("h1");
		emptyMsg.id = "emptyMsg";
		emptyMsg.innerHTML = "You haven't liked any trip!";

		m.appendChild(emptyMsg);

		likedtripsblock.appendChild(m);
	}

	for (var i = 0; i < savedtrips.length; i++) {
		let item = savedtrips[i];
		/* Get trip information to display */
		var tripID = item.tripID;
		var title = item.title;
		var author = item.author;
		var budget = item.budget;
		var days = item.days;
		var start = item.start;
		var destination = item.destination;
		var startDate = item.startDate;
		var endDate = item.endDate;
		var description = item.description;

		/* Create html block */
		var body = document.createElement("div");

		var t = document.createElement("a");
		var info = document.createElement("h3");
		var stats = document.createElement("h3");
		var des = document.createElement("p");

		body.id = "block";

		t.id = "ta";
		t.href = "Details.jsp" + "?tripID=" + tripID;
		t.innerHTML = title + " Created by " + author;

		info.id = "info";
		info.innerHTML = "From " + start + " to " + destination + "     Date: "
				+ startDate + "->" + endDate;

		stats.id = "stats";
		stats.innerHTML = "$" + budget + "/person, " + days + " days";

		des.id = "description";
		des.innerHTML = "<strong> Description: </strong>" + description;

		body.appendChild(t);
		body.appendChild(info);
		body.appendChild(stats);
		body.appendChild(des);

		likedtripsblock.appendChild(body);
	}
</script>
</html>