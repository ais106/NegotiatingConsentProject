<?php

// Declare include files
require_once('controllers/question.php');
//include('controllers/security.php');
require_once('views/views.php');


// Receive user details from post
$user_id = $_REQUEST['user_id'];
$pass_key = $_REQUEST['pass_key'];
$group_id = $_REQUEST['group_id'];


//$sc = new SecurityController;
$user_verified = true;

if($user_verified)
{
    // Create question controller
    $qc = new QuestionController;
      
    $question = $qc->get_question_for_user($user_id, $user_id);
    
    // Set the view to JSON and render the content
    $view = new JsonView();
    $view->render($question);
    
    return $view;
}



?>