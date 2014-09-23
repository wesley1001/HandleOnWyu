<?php

	header("Content-Type: text/html; charset=UTF-8");

//?content='wocao'
	require_once 'connect.php';


	if (isset($_POST['content'])) {
	  $first_id = $_POST['first_id'];
	  $time = $_POST['time'];
      $content = $_POST['content'];
      $title = $_POST['title'];
 	  $result = mysql_query("INSERT INTO home_table(first_id,time,content,title) " .
 		"VALUES('$first_id','$time','$content','$title')");
 		 echo $result;
	}else{
		echo '没有收到post';
	}
?>
