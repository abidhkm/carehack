<?php
session_start();
//Your authentication key


$apiKey = urlencode('SXy0GTNCRGI-MuG9JDUZ72T9CXXSCtpxthyvDa7Mvf');
	
	// Message details
	//$numbers = array(8111869640,7012939431);
	$sender = urlencode('TXTLCL');
	//$message = rawurlencode('This is your message otp');
	$rndno=rand(1000, 9999);
	$message = urlencode("otp number.".$rndno);
$url="localhost";
$username = "id3584107_root";
$password = "abidhkm432";
$dbname = "id3584107_has";
$conn = mysqli_connect($url, $username, $password, $dbname);
// Check connection
if (!$conn) {
die("Connection failed: " . mysqli_connect_error());
}

 
	$numbers = $_POST["phone"];
 

// Create connection
$sql ="select * from quote where phone='$numbers' ";
 $result1 = mysqli_query($conn, $sql);
$row = mysqli_num_rows($result1);

  if($row == 1)
        {   

             

          
            echo "account already exist";

			mysqli_close($conn);
        }
        else
        {
            




	// Prepare data for POST request
	$data = array('apikey' => $apiKey, 'numbers' => $numbers, "sender" => $sender, "message" => $message);
 
	// Send the POST request with cURL
	$ch = curl_init('https://api.textlocal.in/send/');
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$output = curl_exec($ch);
	
//Print error if any
if(curl_errno($ch))
{
echo 'error:' . curl_error($ch);
}
curl_close($ch);
if(isset($_POST['btn-save']))
{
$_SESSION['name']=$_POST['name'];
$_SESSION['email']=$_POST['email'];
$_SESSION['phone']=$_POST['phone'];
$_SESSION['otp']=$rndno;
header( "Location: otp.php" ); }} ?>



