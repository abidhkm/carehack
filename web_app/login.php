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

$phone=$_POST['userid'];
$pin=$_POST['pin'];
// Create connection
$sql ="select * from quote where phone='$phone' and pin='$pin' ";
 $result1 = mysqli_query($conn, $sql);
$row = mysqli_num_rows($result1);

  if($row == 1)
        {   

            $_SESSION['phone'] = $phone;
            

          
            header( "Location: appointment.php" );

			mysqli_close($conn);
        }
        else
        {
            echo "The username or password are incorrect!\n";
       //     $row = mysqli_fetch_array($result1, MYSQLI_ASSOC) ;
 
    //echo "$row['phone']" ;
    // echo "$row['pin']" ;

          //  echo "$result1\n";
			mysqli_close($conn);
        }


?>