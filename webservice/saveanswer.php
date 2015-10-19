<?php
header('Access-Control-Allow-Origin: *');
// Declare include files
require_once('views/views.php');
require_once('controllers/answer.php');


//Recieve answer from client application
$question_id = $_REQUEST['question_id'];
$user_id = $_REQUEST['user_id'];
$pass_key = $_REQUEST['pass_key'];
$answer_content = $_REQUEST['answer_content'];


// Create question controller
$ac = new AnswerController;

$answer = $ac->receive_user_answer($user_id, $question_id, $answer_content);

// Set the view to JSON and render the content
$view = new JsonView();
$view->render($answer);

return $view;




?>