<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<style>
header {
	height: 85px;
	width: 65%;
	margin: 0 auto;
}
#maincontainer{
	background-image:url("pig.jpg");
	background-size:cover;
	height:100vh;
}
#msgBox {
	font-family: Arial;
	font-size: 20px;
	color: white;
	text-align: center;
	padding-top: 5px;
	padding-bottom: 5px;
}

#logo-holder {
	position: absolute;
	width: 85px;
}

#logo {
	width: 95%;
	height: 95%;
	text-align: center;
	margin-top: 15px;
}

#keyword {
	position: absolute;
	width: 200px;
	height: 20px;
	border-radius: 4px;
	margin-left: 135px;
	border: 1px solid #ECECEC;
	margin-top: 30px;
	margin-left: 135px;
	border: 1px solid #ECECEC;
	border: 1px solid #ECECEC;
}

#button {
	position: absolute;
	width: 55px;
	height: 28px;
	background-color: #ADADAD;
	border: 1px solid #ECECEC;
	padding: 2px 18px;
	margin-top: 28px;
	margin-left: 360px;
	border-radius: 3px;
	outline: none;
}

#button:hover {
	background-color: #B4B4B4;
}

input[type=radio] {
	width: 1em;
	height: 1em;
}

.radio-group {
	position: absolute;
	margin-top: 28px;
	color: grey;
	font-family: Arial;
	font-size: 12px;
	width: 100px;
	height: 40px;
}

#register {
	width: 900px;
	margin-left: auto;
	margin-right: auto;
	margin-top: 50px;
}

.registerInput {
	margin-top: 10px;
	margin-bottom: 10px;
	width: 900px;
	height: 25px;
	border-radius: 4px;
	border: 1px solid #ECECEC;
}

#registerButton {
	position: absolute;
	width: 900px;
	height: 37px;
	background-color: #ADADAD;
	border: 1px solid #ECECEC;
	padding: 2px 18px;
	margin-top: 28px;
	border-radius: 3px;
	outline: none;
	color: white;
	font-family: Arial;
	font-size: 14px;
}

#registerButton:hover {
	background-color: #B4B4B4;
}
.clearfloat{
	clear:both;
}
#formerror{
	color:red;
}
h1{
	text-align:center;
}

#leftbar{
	float:left;
	margin-right:50px;
	margin-left:100px;
}
#rightbar{
	float:left;
	margin-left:20px;
	padding-top:50px;
	width:1000px;
	height:600px;
}
.box{
	margin-left:auto;
	margin-right:auto;
	width:200px;
	height:20px;
	}
</style>
<head>
<meta charset="UTF-8">
<title>Register</title>
<script>
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
};
	function Register() {
		// check if user name input is empty 
		// (we dont want user to register with empty username)
		if (document.register.userName.value.trim().length == 0) {
			alert("Username is empty!");
			return;
		}
		// check if user password input is empty
		if (document.register.userPassword.value.trim().length == 0) {
			alert("Password is empty!");
			return;
		}
		// check if password and confirm password matches
		if (document.register.userPassword.value !== document.register.confirmPassword.value) {
			alert("The passwords do not match.");
			return;
		}
		// passed all error check at this point
		// make post request to RegisterServlet using ajax
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				alert(xhttp.responseText);
				if (xhttp.responseText === "Successfully created a new account!") {
					location.assign("Home.jsp");
				}
			}
		};
		xhttp.open("POST", "RegisterServlet", false);
		xhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// send parameters
		xhttp.send("username=" + document.register.userName.value
				+ "&password=" + document.register.userPassword.value);
		return false;
	}
</script>
</head>
<body>
	<header>
	<a href="Home.jsp"><h1>BudgeTriPlanner</h1></a>
	</header>
	<!-- extra separation line -->
	<div style="border-bottom: 1px double lightgrey;"></div>
	<!-- form to register calls Register function on submit -->
	<div id="maincontainer">
	<div id="leftbar"> 
	 <form method="GET" name="myform" onsubmit="return vvv()"action ="SearchResult.jsp">
		 <div id="formerror"></div>
		 	<h4>Flying From:</h4>
		 	<input class="box" id="origin" type = "text" name="origin" placeholder="Where do you start your trip?"><br>
		 	<br>
		 	<h4>Flying To:</h4>
			<input class="box" id="destination" type = "text" name="destination" placeholder="Search for your destination"><br>
			<br>
			<h4>Daily Budget:</h4>
			<input class="box" id="budget" type="text" name="budget" placeholder="Recommended input 100, 500, or 1000000... if you like"><br>
			<br>
			<h4>Check-in:</h4> <input id="checkin" type="date" name="checkIn"><br>
			<br>
			<h4>Check-out:</h4> <input id="checkout" type="date" name="checkOut"><br>
			<br>
			<h4>Car Rental:</h4><select name="rental" id="car">
				<option value="true">YES!</option>
				<option value="false">NO!</option>
			</select>
			<br>
			<div class="clearfloat"></div>
			<br>
			<label ><button id ="bt"type = "submit" value="Search!">Search!</button></label>
	</form>
	</div>
	<div id="rightbar">
	<form name="register" id="register" method="POST"
		onsubmit="return Register();">
		<h4>Username</h4><br> <input type="text" class="registerInput"
			name="userName"><br> <h4>Password</h4><br> <input
			type="password" class="registerInput" name="userPassword"><br>
		<h4>Confirm Password</h4><br> <input type="password" class="registerInput"
			name="confirmPassword"><br> <input id="registerButton"
			type="submit" value="Register">
	</form>
	</div>
	<div class="clearfloat"></div>
	</div>
</body>
</html>