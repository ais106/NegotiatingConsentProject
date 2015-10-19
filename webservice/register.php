<?php

// Include class files
require_once('controllers/user.php');
require_once('views/views.php');



// Create new user controller
$uc = new UserController;

// Create a new user
$new_user = $uc->register_user();


// Set the view to JSON and render the content
$view = new JsonView();
$view->render($new_user);

// Return data
return $view;
?>