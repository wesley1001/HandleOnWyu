<?php
        header("Content-Type: text/html; charset=UTF-8");
		$forum_cid = $_GET['cid'];
		$forum_nid = $_GET['nid'];
		$forum_add = $_GET['add'];
		require_once 'connect.php';
        //   ?cid=1&add=0&nid=5
        $countResult = mysql_query("select nid from forum_content where cid = $forum_cid");
		$count = mysql_num_rows($countResult);


       	$query = "select * from forum_content where cid = $forum_cid " .
       			"order by nid desc limit $forum_add,$forum_nid";

        $result = mysql_query ( $query );
        while ( $row = mysql_fetch_assoc ( $result ) )
        {
            $response [] = $row;
        }
        foreach ( $response as $key => $value )
        {
            $newData [$key] = $value;
            $newData [$key] ['cid'] = urlencode ( $value ['cid'] );
            $newData [$key] ['nid'] = urlencode ( $value ['nid'] );
            $newData [$key] ['title'] = urlencode ( $value ['title'] );
            $newData [$key] ['time'] = urlencode ( $value ['time'] );
            $newData [$key] ['content'] = urlencode ( $value ['content'] );
            $newData [$key] ['count'] = urlencode ( $count );
        }
        echo urldecode ( json_encode ( array("value"=>$newData) ));
                  mysql_free_result($result);

        mysql_close ( $con );

?>


