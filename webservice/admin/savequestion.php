<?php

include('../models/question.class.php');


// Get form input from post

$question_permission = $_POST['permission_text'];
$points = $_POST['points'];
$associated_action = $_POST['select_permission'];

$question_text = 'Would you like to share on a public website your '. $question_permission . ' for '. $points . ' points?';

$qm = new Question;

$new_question = $qm->add_new_question($question_text, $associated_action, $points);

header("location: admin.php");




?>