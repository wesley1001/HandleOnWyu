<?php

	header("Content-Type: text/html; charset=UTF-8");
	require_once 'connect.php';

	$uploads_dir = 'uploads/';
	$uploadname = $_FILES["image"]["name"];
	move_uploaded_file($_FILES['image']['tmp_name'], $uploads_dir.$uploadname);
	if (isset($_POST['title'])) {

	  $current_job_id = $_POST['current_job_id'];
	  $cid = (int)$current_job_id;
	  $title = $_POST['title'];
	  $Salary = $_POST['Salary'];
	  $limit_time = $_POST['limit_time'];
	  $person_limit = $_POST['person_limit'];
	  $work_time = $_POST['work_time'];
	  $place = $_POST['place'];
	  $phone = $_POST['phone'];
      $content = $_POST['content'];
      $contract_person = $_POST['contract_person'];

 	  $result = mysql_query("INSERT INTO concurrent_job" .
 	  "(current_job_id,title,limit_time,salary,person_limit ," .
 	  "sex , work_time,place ,phone ,content ,contract_person) " .
 	  "VALUES('$cid','$title','$limit_time','$Salary','$person_limit'," .
 	  "'sex','$work_time','$place','$phone','$content','$contract_person')");
 		 echo $result;
	}else{
		echo '没有收到post';
	}
?>
