<?php

require_once('database.php');

Class QuestionRequest {
    
    
    function new_question_request( $group_id, $question_id,  $request_type, $request_day, $request_time)
    {
        $db = new Database;
        
        // Create new group question request    
        $fields = 'group_id'.', '.'question_id'.', '.'request_type'.', '. 'request_day' .', '. 'request_time';
        $values = "'".$group_id."'".", "."'".$question_id."', " ."'".$request_type."'".","."'".$request_day."'".","."'".$request_time."'";
        
        $request_id = $db->insert_new_record('question_request', $fields, $values);
        
        // Get all group member id
        $group_members = $db->get_all_group_user_ids($group_id);
       
       // Update user request table to include new question request
       $user_group_params = "";
       $ug_fields = 'user_id'.','.'question_request_id';
    
       foreach($group_members as $gm)
       {
            $user_group_params .= "('".$gm['user_id']."'".",". "'".$request_id."'),";
       }
       
       $user_group_params = rtrim($user_group_params,',');
       
       $db->insert_multiple('user_request',$ug_fields, $user_group_params);
       
       return $request_id;
    }
    
    function remove_question_request($request_id)
    {
        $db = new Database;
        $params = "question_request_id = '". $request_id."'";
        $db->delete_record('question_request', $params);
        

    }
}


?>