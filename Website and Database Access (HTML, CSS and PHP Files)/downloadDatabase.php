<?php
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_connect.asp [accessed 25/11/2014] Connecting to an SQL database
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_insert_lastid.asp [Accessed 26/02/2015] Querying a database
		
	//$con=mysqli_connect("mysql.freehostingnoads.net","u703673749_uom", "UoM3rdyearproject","u703673749_uom");
						
	mysql_connect("mysql.freehostingnoads.net","u703673749_uom","UoM3rdyearproject");
	mysql_select_db("u703673749_uom");		
	$response = array();
	if (mysqli_connect_errno()){ // Check connection
		echo "Failed to connect to MySQL: " . mysqli_connect_error(); //Connection failed
		echo mysql_error();
	} else{
		//echo "Connected to MySQL Database"; //Conenction Successful
		$result = mysql_query("SELECT * FROM DB_Information"); //Database query
		while($e=mysql_fetch_assoc($result))
			$response[]=$e;
		//Echo the retrieved data
		echo json_encode($response);
	}
	mysql_close();
?>