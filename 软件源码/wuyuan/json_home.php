<?php
        header("Content-Type: text/html; charset=UTF-8");
		$first_id = $_GET['first_id'];
		require_once 'connect.php';
        //   ?first_id=5

       	$query = "select * from home_table where  first_id <=$first_id  ";
		//$countResult = mysql_query("select * from home_table ");
		//$row1 = mysql_num_rows($countResult);


        //$insert_result = mysql_query("INSERT INTO comment(content) VALUES('$content')");
        $result = mysql_query ( $query );
        while ( $row = mysql_fetch_assoc ( $result ) )
        {
            $response [] = $row;

        }
        foreach ( $response as $key => $value )
        {
            $newData [$key] = $value;
            $newData [$key] ['first_id'] = urlencode ( $value ['first_id'] );
            $newData [$key] ['title'] = urlencode ( $value ['title'] );
            $newData [$key] ['content'] = urlencode ( $value ['content'] );
            $newData [$key] ['time'] = urlencode ( $value ['time'] );
        }
        echo urldecode ( json_encode ( array("value"=>$newData) ));
        mysql_free_result($result);

        mysql_close ( $con );





?>