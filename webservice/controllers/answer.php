<?php

require_once('models/answer.class.php');


class AnswerController {
    
    
    // Receives a users response to a question
    function receive_user_answer($user_id, $question_id, $answer_content)
    {
        $am = new Answer;
        $answer_id = $am->save_answer($user_id, $question_id, $answer_content);
        $answer['question_id'] = $question_id;
        $answer['answer_id'] = $answer_id;
        return $answer;
    }
    
    
    
    
}






?>