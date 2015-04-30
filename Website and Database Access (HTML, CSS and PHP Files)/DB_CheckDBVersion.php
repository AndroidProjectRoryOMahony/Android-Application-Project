<?php
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_connect.asp [accessed 25/11/2014] Connecting to an SQL database
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_insert_lastid.asp [Accessed 26/02/2015] Querying a database
	mysql_connect("mysql.freehostingnoads.net","u703673749_uom","UoM3rdyearproject");
	mysql_select_db("u703673749_uom");		
	$response = array();
	if (mysqli_connect_errno()){ //Check connection to the server
		echo "Failed to connect to MySQL: " . mysqli_connect_error(); //Connection failed
		echo mysql_error(); //Echo back the error
	} else{ //Connection Successful
		$result = mysql_query("SELECT * FROM DB_Version"); //Database query: get the database verison, store in variable
		while($e=mysql_fetch_assoc($result)) //loop through $result and get the contents
			$response[]=$e;  //Store version ID in a variable	
		echo json_encode($response); //Echo the retrieved data
	}
	mysql_close(); //Close the server connection

?>