<?php

require_once('models/question.class.php');
require_once('models/user.class.php');


class QuestionController {
    
    function get_question_for_user($user_id, $group_id) 
    {
        $um = new User;
        
        $user_day = $um->time_since_joining($user_id);
        $user_question = array();

        $qm = new Question;

        $question = $qm->get_user_question($user_id, $group_id, $user_day);
        unset($question['request_sent'], $question['user_id'], $question['user_request_id'], $question['request_day'], $question['request_time']);
        $question['question_responses'][0] ='Yes';
        $question['question_responses'][1] ='No';
        
        return $question;
    }
    
    function get_question($question_id)
    {
        $question = array();
        $qm = new Question;
        $question = $qm->get_question_by_id($question_id);
        $question['question_responses'][0] ='Yes';
        $question['question_responses'][1] ='No';
        return $question;      
    }
    
   
    
    
    
}





?>