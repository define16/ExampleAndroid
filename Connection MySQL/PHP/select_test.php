<?php

    // error_reporting(E_ALL);
    // ini_set('display_errors',1);
    include_once '__Setting__.php';
    $host = DB_HOST;
    $username = DB_USER; # MySQL 계정 아이디
    $password = DB_PASSWORD; # MySQL 계정 패스워드
    $dbname = DB_NAME;  # DATABASE 이름
    
    try {

        $con = new PDO("mysql:host={$host};dbname={$dbname};charset=utf8",$username, $password);
    } catch(PDOException $e) {

        die("Failed to connect to the database: " . $e->getMessage());
    }


    $stmt = $con->prepare('select * from test');
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array();

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);

            array_push($data,
                array('id'=>$id,
                'date'=>$date,
                'num1'=>$num1,
                'float1'=>$float1
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }

?>
