<?php
        header("Content-Type: text/html; charset=UTF-8");
		$current_job_id = $_GET['current_job_id'];
		$add = $_GET['add'];
		require_once 'connect.php';
        //   ?current_job_id=5&add=0
        $countResult = mysql_query("select * from concurrent_job ");
		$count = mysql_num_rows($countResult);


       	$query = "select * from concurrent_job  order by current_job_id desc limit $add,$current_job_id  ";

        $result = mysql_query ( $query );
        while ( $row = mysql_fetch_assoc ( $result ) )
        {
            $response [] = $row;
        }
        foreach ( $response as $key => $value )
        {
            $newData [$key] = $value;
            $newData [$key] ['current_job_id'] = urlencode ( $value ['current_job_id'] );
            $newData [$key] ['title'] = urlencode ( $value ['title'] );
            $newData [$key] ['limit_time'] = urlencode ( $value ['limit_time'] );
            $newData [$key] ['salary'] = urlencode ( $value ['salary'] );
            $newData [$key] ['person_limit'] = urlencode ( $value ['person_limit'] );
            $newData [$key] ['sex'] = urlencode ( $value ['sex'] );
            $newData [$key] ['work_time'] = urlencode ( $value ['work_time'] );
            $newData [$key] ['place'] = urlencode ( $value ['place'] );
            $newData [$key] ['phone'] = urlencode ( $value ['phone'] );
            $newData [$key] ['content'] = urlencode ( $value ['content'] );
            $newData [$key] ['contract_person'] = urlencode ( $value ['contract_person'] );
            $newData [$key] ['count'] = urlencode ( $count );
        }
        echo urldecode ( json_encode ( array("value"=>$newData) ));
                  mysql_free_result($result);

        mysql_close ( $con );





?>