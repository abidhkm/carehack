<?php
 session_start();
 $phone = $_SESSION['phone'];
 
 $_SESSION['phone'] = $phone;
if(empty($phone))
{
header("Location: http://abidhone.000webhostapp.com");	
}
  ?>
<!DOCTYPE html>
<html>
<head>
 
</head>
<body>
 

 <form action="enter.php" method="post">
 
  <p>available medicine:
  	<input type="text" name="medicine">
  <br><br></p>
  <input type="submit">






</form>
</body>
</html>