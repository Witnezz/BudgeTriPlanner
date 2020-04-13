<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Detail Page</title>
<style>
#mainContainer {
	width: 1300px;
	position: absolute;
	left: 35%;
}

#show {
	border: 1px black solid;
	width: 40%;
	padding: 10px;
	margin-top: 10px;
}

button {
	width: 10vh;
}

#likebt {
	float: left;
}

#box {
	width: 30%;
}
</style>
<script>
	//global variable to store JSON object
	var all_info;
	
	
	function getResult(tripID,username){
		var xhttp = new XMLHttpRequest();

		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				// good to go
				if (this.status == 200 && this.readyState == 4) {
					// parse the response text into Json object
					jSONobject = JSON.parse(this.responseText);
					all_info = jSONobject;
					console.log(all_info);					
					//do some operations on JSON object
					
					//display
					displayResult(jSONobject);
				}
			}
		};	
		xhttp.open("POST", "TripDetail", true);
		xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		// make check request
		xhttp.send("tripID="+tripID+"&username="+username);
	}
	
	function displayResult(JSONobject){
		//use the returned JSON to display trip detail and the page
		var all = JSONobject;
		var tripName = all.tripName;
		var budget = all.budget;
		var days = all.days;
		var description = all.description;
		var startDate = all.startDate;
		var endDate = all.endDate;
		var sa = all.startAirport;
		var ea = all.endAirport;
		var hotel = all.hotelName;
		var rating = all.hotelRating;
		console.log(all.hotelRating);
		var airline = all.airline; 
		var car = all.car; //0 or 1
		var owner = all.owner //owner's username
		var likes = all.likeList;
		var likeflag = all.likeFlag;
		var comments = all.commentList;
		var owner = all.owner;
		document.getElementById("remove").onclick=function(){
			var xhttp = new XMLHttpRequest();
			xhttp.open("POST", "RemovePostServlet", true);
			xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
			// make check request
			xhttp.send("tripID="+tripID);
			var url = "Profile.jsp";
			window.location = url;
		}
		if(owner!=username){
			document.getElementById("remove").style.display="none";
		}
		
		//console.log(likes);
		if(likeflag==0){
			document.getElementById("likebt").innerHTML="LIKE";
		}
		else if(likeflag==1){
			document.getElementById("likebt").innerHTML="DISLIKE";
		}
		else{
			document.getElementById("likebt").innerHTML="ERROR";
		}
		document.getElementById("name").innerHTML=tripName;
		document.getElementById("budget").innerHTML+=budget;
		document.getElementById("description").innerHTML+=description;
		document.getElementById("Start-End").innerHTML+=startDate+" from " +sa +" to "+ endDate + " "+ ea; 
		document.getElementById("hotel").innerHTML+=hotel;
		document.getElementById("rating").innerHTML+=rating;
		document.getElementById("airline").innerHTML+=airline;
		document.getElementById("likebt").onclick=function(){
			var action = "";
			if(username==""){
				alert("You have to login first");
			}
			else{
				var temp =document.getElementById("likebt").innerHTML;
				if(temp=="LIKE"){
					action="like";
					document.getElementById("likebt").innerHTML="DISLIKE";
				}else if(temp=="DISLIKE"){
					action="dislike";
					document.getElementById("likebt").innerHTML="LIKE";
				}
				else{
					alert("ERROR");
					return;
				}
				var xhttp = new XMLHttpRequest();
				xhttp.open("POST", "LikeDisLikeServlet", true);
				xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
				// make check request
				console.log(action);
				xhttp.send("tripID="+tripID+"&action="+action);
				
				
			}
		};
		if(car==0){
			document.getElementById("car").innerHTML+="No Car Rental";
		}
		else{
			document.getElementById("car").innerHTML+="Rented a Car";
		}
		document.getElementById("likenumber").innerHTML+=likes.length;
		document.getElementById("commentnumber").innerHTML+=comments.length;
		if(likes.length!=0&&comments.length==0){
			for(var i = 0; i<likes.length; i++){
				var temp = document.createElement("p");
				temp.innerHTML=likes[i].username;
				document.getElementById("show").appendChild(temp);
			}
		}
		else if(likes.length==0&&comments.length!=0){
			for(var i = 0; i<comments.length; i++){
				var temp = document.createElement("p");
				temp.innerHTML=comments[i].username+": "+comments[i].content;
				document.getElementById("show").appendChild(temp);
			}
		}
		else if(likes.length==0&&comments.length==0){
			document.getElementById("show").innerHTML+="<h3>No Comments and Likes.</h3>";
		}
		else{
			for(var i = 0; i<likes.length; i++){
				var temp = document.createElement("p");
				temp.innerHTML=likes[i].username;
				document.getElementById("show").appendChild(temp);
			}
		}
		document.getElementById("likenumber").onclick=function(likes){
			document.getElementById("show").innerHTML="";
			if(likes.length==0){
				document.getElementById("show").innerHTML+="<h3>No likes</h3>";
			}
			else{
				for(var i = 0; i<likes.length; i++){
					var temp = document.createElement("p");
					temp.innerHTML=likes[i].username;
					document.getElementById("show").appendChild(temp);
				}
			}
		};
		document.getElementById("commentnumber").onclick=function(comments){
			document.getElementById("show").innerHTML="";
			if(comments.length==0){
				document.getElementById("show").innerHTML+="<h3>No comments</h3>";
			}
			else{
				for(var i = 0; i<comments.length; i++){
					var temp = document.createElement("p");
					temp.innerHTML=comments[i].username+": "+comments[i].content;
					document.getElementById("show").appendChild(temp);
				}
			}
		};
	}
	
	function vvv(){
		if(username==""){
			alert("You have to login first.")
			return;
		}
		var comment = document.getElementById("box").value;
		if(comment==""){
			alert("Comment cannot be empty.");
			return false;
		}
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", "CommentServlet", true);
		xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
		// make check request
		xhttp.send("tripID="+tripID+"&comment="+comment);
		return true;
	}
	function loggedin() {
		document.getElementById("loggedout").style.display = "blcok";
		document.getElementById("loggedin").style.display = "none";
	}
	function loggedout() {
		document.getElementById("loggedin").style.display = "block";
		document.getElementById("loggedout").style.display = "none";
	}
</script>

</head>
<body>
	<div id="mainContainer">
		<h1 id="name"></h1>
		<button id="remove">Remove!</button>
		<button id="likebt"></button>
		<form name="myform" method="post" onsubmit="return vvv()">
			<div id="formerror"></div>
			<input type="text" id="box" placeholder="Please leave a comment.">
			<button id="comment" type="submit">COMMENT!</button>
		</form>
		<div id="words">
			<p id="description">Description:</p>
			<p id="budget">Budget: $</p>
			<p id="Start-End">Start and End Date:</p>
			<p id="hotel">Hotel Name:</p>
			<p id="rating">Hotel Rating:</p>
			<p id="airline">Airline:</p>
			<p id="car">Car Rental:</p>
			<button id="likenumber">Likes:</button>
			<button id="commentnumber">Comments:</button>
			<div id="show"></div>
		</div>
	</div>
	<!--  get trip id from the previous page -->
	<script type="text/javascript">
		var loggedIn = false;
	</script>
	<%
		String tripID = request.getParameter("tripID");
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
	  	loggedin();
	 </script>
	<%
		}
	%>
	<%
		if (username == null) {
	%>
	<script type="text/javascript">
	  	loggedout();
	 </script>
	<%
		}
	%>


	<script>
		var tripID = <%= tripID %>;	
		//need to get username
		var username = "mark";
		if(username==null){
			username="";
		}
		console.log(tripID);	
		console.log(username);
		getResult(tripID,username);		
	</script>



</body>
</html>