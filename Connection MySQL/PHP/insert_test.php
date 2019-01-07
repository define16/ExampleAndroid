content
<?php

include_once '__Setting__.php';
$host = DB_HOST;
$username = DB_USER; # MySQL 계정 아이디
$password = DB_PASSWORD; # MySQL 계정 패스워드
$dbname = DB_NAME;  # DATABASE 이름

$con=mysqli_connect($host,$username,$password,$dbname);

mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$id = $_POST['id'];
$date = $_POST['date'];
$num1 = $_POST['num1'];
$float1 = $_POST['float1'];


$result = mysqli_query($con,"insert into test (id,date,num1,float1) values ('$id','$date','$num1','$float1')");

  if($result){
    echo 'success';
  }
  else{
    echo 'failure';
  }

mysqli_close($con);
?>



<html>
 <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
   <body>
      <form action = "<?php $_PHP_SELF ?>" method = "POST">
         ID: <input type = "text" name = "id" />
         Date: <input type = "text" name = "date" />
         Num: <input type = "text" name = "num1" />
 	       Float: <input type = "text" name = "float1" />
         <input type = "submit" />
      </form>

   </body>
</html>
