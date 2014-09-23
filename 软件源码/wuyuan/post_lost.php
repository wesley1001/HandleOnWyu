<?php

	header("Content-Type: text/html; charset=UTF-8");

	require_once 'connect.php';
	if (isset($_POST['flag'])) {

	  $flagRst_temp  = $_POST['flag'];
	  $flagRst =(int)$flagRst_temp;
	  $lost_id_temp = $_POST['lost_id'];
	  $lost_id = (int)$lost_id_temp;
	  $title = $_POST['title'];
	  $lost_time = $_POST['lost_time'];
	  $path = $_POST['path'];
	  $Link_man = $_POST['Link_man'];
      $QQ = $_POST['QQ'];
	  $phone = $_POST['phone'];
      $content = $_POST['content'];

 	  $result = mysql_query("INSERT INTO lost_property" .
 	  "       (flag,      lost_id,   title,   time,         content,  place , contract_person ,phone ,  QQ) " .
 	  "VALUES('$flagRst','$lost_id','$title','$lost_time','$content','$path','$Link_man',     '$phone','$QQ')");
 		 echo $result;
	}else{
		echo '没有收到post';
	}
?>
