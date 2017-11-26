<?php
session_start();
$url="localhost";
$username = "id3584107_root";
$password = "abidhkm432";
$dbname = "id3584107_has";
$conn = mysqli_connect($url, $username, $password, $dbname);
// Check connection
if (!$conn) {
die("Connection failed: " . mysqli_connect_error());
}

$rno=$_SESSION['otp'];
$urno=$_POST['otpvalue'];
if(!strcmp($rno,$urno))
{
$name=$_SESSION['name'];
$email=$_SESSION['email'];
$phone=$_SESSION['phone'];
$pin=rand(100000, 999999);
// Create connection
$sql = "INSERT INTO quote (name, email, phone, pin)
VALUES ('$name', '$email', '$phone', '$pin')";
if (mysqli_query($conn, $sql)) {




//Define route 

$apiKey = urlencode('SXy0GTNCRGI-MuG9JDUZ72T9CXXSCtpxthyvDa7Mvf');
	
	// Message details
	//$numbers = array(8111869640,7012939431);
	$sender = urlencode('TXTLCL');
	//$message = rawurlencode('This is your message otp');


$message = urlencode("Thank u for register with us. your password is ".$pin);
 
	$numbers = $_POST["phone"];
 
	// Prepare data for POST request
	$data = array('apikey' => $apiKey, 'numbers' => $phone, "sender" => $sender, "message" => $message);
 
	// Send the POST request with cURL
	$ch = curl_init('https://api.textlocal.in/send/');
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$output = curl_exec($ch);
	
//Print error if any

//Print error if any
if(curl_errno($ch))
{
echo 'error:' . curl_error($ch);
}
curl_close($ch);
header( "Location: success.php" );
} else {
echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
return true;
}
else
{
echo "failure";
return false;
}
?>