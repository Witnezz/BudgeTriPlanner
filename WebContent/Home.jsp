<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
<style>
	body{
	font-family:comic sans ms;
	background-color:#FFE1ED;
	color:#F392C0;
	margin:0;
	}
	#headcontainer{
	padding-top:15px;
	padding-bottom:0;
	margin-bottom:0;
	}
	#mainContainer{
		margin-left:0;
		margin-right:0;
		padding-left: 10vh;
		padding-right: 20vh;
		padding-bottom:50px;
		background-image:url("background/pig.jpg");
		background-size:cover;
		height:87vh;
		margin-bottom:0;
	}
	.fl{
	width:40%;
	float:left;
	}
	.fr{
	float:right;
	}
	.box{
	margin-left:auto;
	margin-right:auto;
	width:300px;
	height:30px;
	}
	h1{
	font-family:cursive;
	color:#3EBDFC;
	top:0px;
	left:45%;
	position:absolute;
	text-align:center;
	}
	
	h1:link{
	color:#3EBDFC;
	text-decoration:none;
	}
	h1:visited{
	color:#3EBDFC;
	text-decoration:none;
	}
	h1:hover{
	color:#3EBDFC;
	text-decoration:none;
	}
	h1:active{
	color:#3EBDFC;
	text-decoration:none;
	}
	
	.clearfloat{
	clear:both;
	}
	button{
	margin-left:40px;
	margin-top:78px;
	background-color:black;
	font-family:comic sans ms;
	color:azure;
	width:20vh;
	font-size:large;
	height:5vh;
	}
	#formerror{
	color:yellow;
	font-weight:bold;
	font-size:xx-large;
	position:absolute;
	top:20%;
	right:21%;
	}
	#login{
	float:right;
	margin-left:20vh;
	margin-right:20px;
	}
	#login:link{
	color:#3EBDFC;
	text-decoration:none;
	}
	#login:visited{
	color:#3EBDFC;
	text-decoration:none;
	}
	#login:hover{
	color:#3EBDFC;
	text-decoration:none;
	}
	#login:active{
	color:#3EBDFC;
	text-decoration:none;
	}
	
	#register{
	float:right;
	margin-right:10vh;
	}
	
	#register:link{
	color:#3EBDFC;
	text-decoration:none;
	}
	#register:visited{
	color:#3EBDFC;
	text-decoration:none;
	}
	#register:hover{
	color:#3EBDFC;
	text-decoration:none;
	}
	#register:active{
	color:#3EBDFC;
	text-decoration:none;
	}
	
	#profile{
	float:right;
	margin-right:10vh;
	}
	#profile:link{
	color:#3EBDFC;
	text-decoration:none;
	}
	#profile:visited{
	color:#3EBDFC;
	text-decoration:none;
	}
	#profile:hover{
	color:#3EBDFC;
	text-decoration:none;
	}
	#profile:active{
	color:#3EBDFC;
	text-decoration:none;
	}
	
	#signout{
	float:right;
	margin-left:20vh;
	margin-right:20px;
	}
	
	#signout:link{
	color:#3EBDFC;
	text-decoration:none;
	}
	#signout:visited{
	color:#3EBDFC;
	text-decoration:none;
	}
	#signout:hover{
	color:#3EBDFC;
	text-decoration:none;
	}
	#signout:active{
	color:#3EBDFC;
	text-decoration:none;
	}
	.picholder{
	width:400px;
	height:200px;
	}
	.picbox{
	width:400px;
	height:200px;
	overflow:hidden;
	float: left;
	margin:10px;
	}
	#leftbar{
	float:left;
	}
	#rightbar{
	width:400px;
	float:left;
	padding-top:0px;
	margin-left:40px;
	}
	#origin{
	margin-bottom:5px;
	}
	#destination{
	margin-bottom:5px;
	}
	#budget{
	margin-bottom:5px;
	}
	#checkin{
	margin-bottom:5px;
	}
	#checkout{
	margin-bottom:5px;
	}
	
</style>
<script>
function loggedin() {
	document.getElementById("loggedout").style.display = "blcok";
	document.getElementById("loggedin").style.display = "none";
}
function loggedout() {
	document.getElementById("loggedin").style.display = "block";
	document.getElementById("loggedout").style.display = "none";
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
	
	function fillinVegas(){
		document.myform.origin.value="Los Angeles";
		document.myform.destination.value="Las Vegas";
	}
	function fillinKyoto(){
		document.myform.origin.value="Los Angeles";
		document.myform.destination.value="Kyoto";
	}
	function fillinCancun(){
		document.myform.origin.value="Los Angeles";
		document.myform.destination.value="Cancun";
	}

</script>
</head>
<body>
<div id="headcontainer">
<a href="Home.jsp"><h1>BudgeTriPlanner</h1></a>
<div class="clearfloat"></div>
<div id="loggedin">
<a href ="Register.jsp" id="register"><h3>Register</h3></a>
<a href ="Login.jsp" id="login"><h3>Login</h3></a>
</div>
<div id="loggedout">
<a href ="Profile.jsp" id = "profile"><h3>Profile</h3></a>
<a href="<%=request.getContextPath()%>/LogoutServlet" id="signout"><h3>Sign out</h3></a>
</div>
</div>
<div class="clearfloat"></div>
<div id = "mainContainer">
	
	<div id="leftbar">
		<h3>Top-Viewed Destinations:</h3>
		<strong><p>Vegas</p></strong>
		<div class="picbox">
			<a href="javascript:fillinVegas()"><img class="picholder"src="destinations/vegas.jpg" alt="vegas"></a>
		</div>
		<div class="clearfloat"></div>
		<strong><p>Kyoto</p></strong>
		<div class="picbox">
			<a href="javascript:fillinKyoto()"><img class="picholder"src="destinations/kyoto.jpeg" alt="kyoto"></a>
		</div>
		<div class="clearfloat"></div>
		<strong><p>Cancun</p></strong>
		<div class="picbox">
			<a href="javascript:fillinCancun()"><img class="picholder"src="destinations/cancun.jpg" alt="cancun"></a>
		</div>
	</div>
	<div id="rightbar">
		 <form id="myform" method="GET" name="myform" onsubmit="return vvv()"action ="SearchResult.jsp">
		 	<h4>Flying From:</h4>
		 	<input class="box" id="origin" type = "text" name="origin" placeholder="Where do you start your trip?"><br>
		 	<br>
		 	<h4>Flying To:</h4>
			<input class="box" id="destination" type = "text" name="destination" placeholder="Search for your destination"><br>
			<br>
			<h4>Check-in:</h4> <input id="checkin" type="date" name="checkIn"><br>
			<br>
			<h4>Check-out:</h4> <input id="checkout" type="date" name="checkOut"><br>
			<br>
			<br>
			<h4>Car Rental:</h4><select name="rental" id="car">
				<option value="true">YES!</option>
				<option value="false">NO!</option>
			</select>
			<br>
			<h4>Daily Budget:</h4>
			<input class="box" id="budget" type="text" name="budget" placeholder="Recommended input 100, 500, or 1000000... if you like"><br>
			<br>
			<div class="clearfloat"></div>
			<label ><button id ="bt"type = "submit" value="Search!">LeT's Go0Oo0O!</button></label>
		</form>
	</div>
	<div id="formerror"></div>
</div>
	<%
		HttpSession sess = request.getSession();
	%>
	<%
		if (sess.getAttribute("name") != null) {
	%>
	<script type="text/javascript">
		loggedin();
	</script>
	<%
		}
	%>
	<%
		if (sess.getAttribute("name") == null) {
	%>
	<script type="text/javascript">
		loggedout();
	</script>
	<%
		}
	%>
</body>
</html>