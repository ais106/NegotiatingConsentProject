<?php

session_start();

$group_id = $_POST['selectgroup_id'];

$_SESSION['group_id'] = $group_id;

header("location: groups.php");


?>