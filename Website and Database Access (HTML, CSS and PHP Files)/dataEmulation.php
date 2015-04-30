<?php
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//w3schools. (N.D) Available from http://www.w3schools.com/php/func_math_mt_rand.asp [Accessed 20/01/2015] Generating a random number
	$response = array();
	
	for($x = 0; $x < 20; $x++){
		$result = (mt_rand(240,260)); //faster than rand()
		$response[$x] = $result;
	}
	
	echo json_encode($response);
?>