<?php
        header("Content-Type: text/html; charset=UTF-8");
        $flag = $_GET['flag'];
		$lost_id = $_GET['lost_id'];
		$next = $_GET['next'];
		//$nid = $_GET['nid'];
        //include("connect.php");
        require_once 'connect.php';
        //   ?lost_id=5&next=0&flag=0

		$countResult = mysql_query("select * from lost_property where flag = $flag ");
		$count = mysql_num_rows($countResult);

		$query = "select * from lost_property where flag = $flag order by lost_id desc limit $next,$lost_id";



        //$insert_result = mysql_query("INSERT INTO comment(content) VALUES('$content')");
        $result = mysql_query ( $query );
        while ( $row = mysql_fetch_assoc ( $result ) )
        {
            $response [] = $row;
        }
        foreach ( $response as $key => $value )
        {
            $newData [$key] = $value;
            $newData [$key] ['lost_id'] = urlencode ( $value ['lost_id'] );
            $newData [$key] ['title'] = urlencode ( $value ['title'] );
            $newData [$key] ['content'] = urlencode ( $value ['content'] );
            $newData [$key] ['time'] = urlencode ( $value ['time'] );
            $newData [$key] ['place'] = urlencode ( $value ['place'] );
            $newData [$key] ['contract_person'] = urlencode ( $value ['contract_person'] );
            $newData [$key] ['phone'] = urlencode ( $value ['phone'] );
            $newData [$key] ['QQ'] = urlencode ( $value ['QQ'] );
            $newData [$key] ['count'] = urlencode ( $count );
        }
        echo urldecode ( json_encode ( array("value"=>$newData) ));
                  mysql_free_result($result);

        mysql_close ( $con );





?>