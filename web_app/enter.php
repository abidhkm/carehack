<?php
 session_start();

  $phone = $_SESSION['phone'];
    $medicine = $_POST['medicine'];
  /* // $date = $_POST['date'];
if(empty($phone) ||empty($specialisation)||empty($date))
{
header("Location: http://allyouwant.esy.es/appointment.php");	
}
*/

$url="localhost";
$username = "id3584107_root";
$password = "abidhkm432";
$dbname = "id3584107_has";
$conn = mysqli_connect($url, $username, $password, $dbname);
// Check connection
if (!$conn) {
die("Connection failed: " . mysqli_connect_error());
}


// Create connection
$sql = "INSERT INTO appoint (phone, medicine)
VALUES ('$phone', '$medicine')";
if (mysqli_query($conn, $sql)) {
  echo "medicine added\r\n";

//$sql ="select * from appoint where phone='$phone' ";
 //$result = mysqli_query($conn, $sql);
/*
echo "your history :\n";

echo "<table border='1'>
<tr>
<th>specialisation</th>
<th>booked date</th>
<th>booked on</th>
</tr>";

while($row = mysqli_fetch_array($result))
{
echo "<tr>";
echo "<td>" . $row['specialisation'] . "</td>";
echo "<td>" . $row['booked_date'] . "</td>";
echo "<td>" . $row['booking_date'] . "</td>";
echo "</tr>";
}
echo "</table>";
*/
mysqli_close($conn);

}
  ?>


<p><a href="appointment.php">add more medicine</a></p>
<p><a href="index.php">logout</a></p>