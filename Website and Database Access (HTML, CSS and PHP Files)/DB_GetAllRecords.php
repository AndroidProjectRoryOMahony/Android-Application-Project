<?php
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_connect.asp [accessed 25/11/2014] Connecting to an SQL database
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_insert_lastid.asp [Accessed 26/02/2015] Querying a database
	$con=mysqli_connect("mysql.freehostingnoads.net","u703673749_uom",
						"UoM3rdyearproject","u703673749_uom");
	if (mysqli_connect_errno()){ // Check connection
		echo "Failed to connect to MySQL: " . mysqli_connect_error(); //Connection failed
	} else{
		//echo "Connected to MySQL Database <br><br>"; //Connection Successful
		$result = mysqli_query($con,"SELECT * FROM DB_Information"); //Database query
		//Echo the retrieved data as as table
		echo "<table> 
		<tr>
			<th>Name</th>
			<th>Location</th>
			<th>Data Type</th>
			<th>Units</th>
			<th>Web ID</th>
			<th>ID</th>
			<th>Status</th>
			<th>X Coordinate</th>
			<th>Y Coordinate</th>
		</tr>";
		while($row = mysqli_fetch_array($result)) {
		  echo "<tr>";
		  echo "<td>" . $row['Name'] . "</td>";
		  echo "<td>" . $row['Location'] . "</td>";
		  echo "<td>" . $row['DataType'] . "</td>";
		  echo "<td>" . $row['Units'] . "</td>";
		  echo "<td>" . $row['WebID'] . "</td>";
		  echo "<td>" . $row['ID'] . "</td>";
		  echo "<td>" . $row['Status'] . "</td>";
		  echo "<td>" . $row['XCoordinates'] . "</td>";
		  echo "<td>" . $row['YCoordinates'] . "</td>";
		  echo "</tr>";
		}
        //Previous code
		  /*if(($row['Latitude'] || $row['Longitude']) == NULL){
			echo "<td> Unknown </td>";
		} else{
			echo "<td>" . $row['Latitude'] . ", " . $row['Longitude'] . "</td>";
		}
		  echo "</tr>";
		}*/
		echo "</table>";
	}
?>