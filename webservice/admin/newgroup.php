<?php

require_once('../models/group.class.php');

session_start();

$group_name = $_POST['group_name'];

$gm = new Group;

$new_group = $gm->new_group($group_name);

$group_id = $new_group;

$_SESSION['group_id'] = $group_id;

header("location: groups.php");




?>