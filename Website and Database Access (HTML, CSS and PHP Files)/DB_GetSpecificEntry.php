<?php
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_connect.asp [accessed 25/11/2014] Connecting to an SQL database
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_insert_lastid.asp [Accessed 26/02/2015] Querying a database
	$con=mysqli_connect("mysql.freehostingnoads.net","u703673749_uom",
						"UoM3rdyearproject","u703673749_uom");
	if (mysqli_connect_errno()){ // Check connection
		echo "Failed to connect to MySQL: " . mysqli_connect_error(); //Connection failed
	} else{ //Connection successful
        $nameOfEntry = $_POST['dataEntry'];
        //Database query
		$result = mysqli_query($con,"SELECT * FROM `DB_Information` WHERE Name ='$nameOfEntry'"); 
		$entry = array();
        $row = mysqli_fetch_array($result);
        array_push($entry, $row['Name']);
        array_push($entry, $row['Location']);
        array_push($entry, $row['DataType']);
        array_push($entry, $row['Units']);
        array_push($entry, $row['WebID']);
        array_push($entry, $row['ID']);
        array_push($entry, $row['Status']);
        array_push($entry, $row['XCoordinates']);
        array_push($entry, $row['YCoordinates']);
        echo json_encode($entry);
	}
?>