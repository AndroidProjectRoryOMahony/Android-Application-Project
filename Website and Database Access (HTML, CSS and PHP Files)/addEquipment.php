<?php
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//w3schools. (N.D) Available from http://www.w3schools.com/php/php_mysql_connect.asp [accessed 25/11/2014] Connecting to an SQL database

$servername = "mysql.freehostingnoads.net";
$username = "u703673749_uom";
$password = "UoM3rdyearproject";
$dbname = "u703673749_uom";

//echo $_POST[]; //put the names of the variables passed here e.g. <input type = "text" name = "ThisValue">

//Check if their password is ok. Only update database if password matches
if($_POST[password] == $password){
    // Create connection
    $conn = mysqli_connect($servername, $username, $password, $dbname);
    // Check connection
    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
    }

    $name = $_POST[name];
    $location = $_POST[location];
    $dataType = $_POST[dataType];
    $units = $_POST[units];
    $webID = $_POST[webID];
    $ID = $_POST[ID];

    if($_POST[status] == 'online'){
        $status = "Online"; 
    } else{
        $status = "Offline"; 
    }

    if($_POST[latitude] != NULL){
        $latitude = $_POST[latitude];
    } else{
        $latitude = NULL;
    }

    if($_POST[longitude] != NULL){
        $longitude = $_POST[longitude];
    } else{
        $longitude = NULL;
    }

    //had an issue where string values were not quoted. Issue solved by quoting the string valyes
    $sql = "INSERT INTO DB_Information "."(Name, Location, DataType, Units, WebID, ID, Status, XCoordinates, YCoordinates) "."VALUES ('$name', '$location', '$dataType', '$units', '$webID', '$ID', '$status', '$latitude', '$longitude')";

    if (mysqli_query($conn, $sql)) {
        echo "New record created successfully.";
        echo "\n";
        $sql = "UPDATE DB_Version SET Version_ID = Version_ID + 1";
        if (mysqli_query($conn, $sql)) {
            echo "Version ID updated successfully";    
        } else {
            echo "Error: " . $sql . "<br>" . mysqli_error($conn);
        }  
        
    } else {
        echo "Error: " . $sql . "<br>" . mysqli_error($conn);
    }
    mysqli_close($conn);
} 
else{
    echo "Failed to add entry. Password invalid";
}


?>