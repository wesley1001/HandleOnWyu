<?php
$uploads_dir = 'uploads/';
header("Content-Type: text/html; charset=UTF-8");
//require_once 'connect.php';

$uploadname = $_FILES["image"]["name"];
$uploadtitle = $_POST["title"];
move_uploaded_file($_FILES['image']['tmp_name'], $uploads_dir.$uploadname);
//file_put_contents($uploads_dir.'juhl.txt', print_r($uploadtitle, true));
$result= mysql_query("INSERT INTO image(image) VALUES('$uploadtitle')");
//这是给客户端反馈信息。
echo json_encode(
            array(
                'result'=>'success',
                'msg'=>'Report added successfully.'
                )
            );
?>