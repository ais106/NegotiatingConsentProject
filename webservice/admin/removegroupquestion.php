<?php


require_once('../models/questionrequest.class.php');

$request_id = $_POST['question_request_id'];


$qrm = new QuestionRequest;

$qrm->remove_question_request($request_id);


header("location: groups.php");

?>