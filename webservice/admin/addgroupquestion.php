<?php


require_once('../models/questionrequest.class.php');

$question_id = $_REQUEST['a_question_id'];
$group_id = $_REQUEST['a_group_id'];
$request_type = $_REQUEST['a_request_type'];
$request_day = $_REQUEST['select_day'];
$hour = $_REQUEST['select_hour'];
$minute = $_REQUEST['select_minute'];

$converted_time = ($hour * 60) + $minute;

$qrm = new QuestionRequest;

$question_request = $qrm->new_question_request($group_id, $question_id, $request_type, $request_day, $converted_time);



header("location: groups.php");

?>