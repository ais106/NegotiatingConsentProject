<?php

require_once('database.php');

Class Answer {
    
    function get_user_answers($user_id)
    {
        return null;
    }
    
    function save_answer($user_id, $question_id, $answer_content)
    {
        $db = new Database;
        $date = date('Y-m-d H:i:s');
        $fields = 'question_id'.', '.'date_answered'.', '.'answer_option'.', '.'user_id';
        $values = "'".$question_id."'".", "."'".$date."', " ."'".$answer_content."'".","."'".$user_id."'";
        
        $answer_id = $db->insert_new_record('answer', $fields, $values);
        return $answer_id;
    }
}


?>