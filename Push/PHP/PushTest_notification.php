1.  cURL (Client URL Library Functions)
<br>
제작자 Daniel Stenberg 의 설명을 그대로 변역하면
curl is a comand line tool for transferring files with URL syntax
커맨드라인에서 URL 문법을 사용하여 파일을 전송 프로그램
내가 원하는 주소의 페이지에서 내가 임의의 값을 넣고 그 넣은 값으로 페이지에서 리턴되는 값을 받아오는 역할을 한다. <br>
PHP에서 cURL을 사용하려는 사람들 대부분이 아마도 HTTPS 접속 때문일 것이다.<br>
소켓 또는 그 외 여러가지 접속방법이 있는데 굳이 cURL을 사용하는 건 속도면에서도 빠르고 HTTPS도 쉽게 접속할 수 있기 때문이다.<br>
cURL 모듈을 서버에 설치해야 합니다.(리눅스 - curl.so, 윈도우 - php_curl.dll 확장모듈 필요)
<br>
 출처 : http://xshine.tistory.com/251
<br>
2. 윈도우 환경에서 php curl 설정
<br>
php.ini에서 ;extension=php_curl.dll → extension=php_curl.dll 주석 해제 후 아파치 재시작

<?php

	function send_notification ($tokens, $message)
	{
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'registration_ids' => $tokens,
			 'data' => $message
			);

		$headers = array(
			'Authorization:key =' . GOOGLE_API_KEY,
			'Content-Type: application/json'
			);

	   $ch = curl_init(); // 세션 초기화
		 	// 옵션 세팅
       curl_setopt($ch, CURLOPT_URL, $url); // 접속할 url정보를 설정
       curl_setopt($ch, CURLOPT_POST, true); // 전송 메소드를 설정
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers); // 헤더 정보를 수신 설정
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Request에 대한 결과값 수신 설정
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0); // 인증서 체크
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false); // 인증서 체크 (true시 안되는 경우가 종종 발생)
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields)); // POST 메소드라면 파라미터 값들을 이 옵션에 정의

       $result = curl_exec($ch); // curl을 실행
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch)); // 에러메시지를 가지고 옴
       }

       curl_close($ch); // curl 세션을 닫음
       return $result;
	}

	//데이터베이스에 접속해서 토큰들을 가져와서 FCM에 발신요청
	include_once '__Setting__.php';
	$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

	$sql = "Select Token From users";

	$result = mysqli_query($conn,$sql);
	$tokens = array();

	if(mysqli_num_rows($result) > 0 ){

		while ($row = mysqli_fetch_assoc($result)) {
			$tokens[] = $row["Token"];
      echo $row["Token"];
		}
	}
	mysqli_close($conn);

  $myMessage = $_POST['message']; //폼에서 입력한 메세지를 받음
	if ($myMessage == ""){
		$myMessage = "새글이 등록되었습니다.";
	}

	$message = array("message" => $myMessage);
	$message_status = send_notification($tokens, $message);

 ?>
