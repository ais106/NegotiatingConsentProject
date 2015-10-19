<?php

require_once('models/user.class.php');

class UserController {
    
    function register_user()
    {
        $usermodel = new User;
        $new_user = $usermodel->new_user();
        return $new_user;
    }   
    
}





?>