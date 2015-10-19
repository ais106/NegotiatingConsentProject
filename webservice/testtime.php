<?php

require_once('models/user.class.php');

$user_id = $_REQUEST['user_id'];

$um = new User;

$time_registered = $um->time_since_joining($user_id);

var_dump($time_registered);

$request_time = (time() % 1440);

var_dump($request_time);


?>