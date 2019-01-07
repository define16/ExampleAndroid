<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>FCM Push Notification</title>
  <style type="text/css">
	*,
	*:before,
	*:after {
	   box-sizing: border-box;
	}
	body {background-color: #99cc00;}
	.messageWrapper {width:500px; margin:0 auto;}

	ul li {width: 500px; margin:0 auto;}

	#submitButton {
		width: 90px;
		padding:5px;
		border:2px solid #ccc;
		-webkit-border-radius: 5px;
		border-radius: 5px;
		font: 14px/1.4 "Helvetica Neue", Helvetica, Arial, sans-serif;
	}
  </style>
 </head>
 <body>

<div class="messageWrapper">
	<h2>Push Message</h2>

	<form action="PushTest_notification.php" method="post">
		<textarea name="message" rows="4" cols="50" placeholder="메세지를 입력하세요"  required></textarea><br>
		<input type="submit" name="submit" value="Send" id="submitButton">
	</form>

</div>


<?php
	include("__Setting__.php");

	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	$sql = "Select Token From users";

	$result = mysqli_query($conn,$sql);
	while ($row = mysqli_fetch_assoc($result)) {
?>
	<ul>
		<li><?php echo substr($row["Token"], 0, 30); ?> ...</li>
	</ul>

<?php
	}
?>


 </body>
</html>
