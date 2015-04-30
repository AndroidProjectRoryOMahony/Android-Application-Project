<?php
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_connect.asp [accessed 25/11/2014] Connecting to an SQL database
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_insert_lastid.asp [Accessed 26/02/2015] Querying a database
	$con=mysqli_connect("mysql.freehostingnoads.net","u703673749_uom",
						"UoM3rdyearproject","u703673749_uom");
	if (mysqli_connect_errno()){ // Check connection
		echo "Failed to connect to MySQL: " . mysqli_connect_error(); //Connection failed
	} else{
		$result = mysqli_query($con,"SELECT Name FROM `DB_Information` WHERE 1"); //Database query
		$names = array(); //Create a new array variable to store the information in
        while($row = mysqli_fetch_array($result)) { //Loop through the obtained information
            array_push($names, $row['Name']);   //Push information into the array at different element locations
		}
        echo json_encode($names); //Echo the array 
	}
?>