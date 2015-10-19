<?php

require_once('../models/user.class.php');


class SecurityController {
    
    function check_user_details($user_id, $pass_key)
    {
        
        $um = new User;
        $user = $um->get_user_details($user_id);
        $stored_key = $user['pass_key'];
        
        if($pass_key != $stored_key)
        {
            return false;
        }
        else
        {
            return true;
        }
        
    }
    
    function check_admin_login($email, $password)
    {
        $um = new User;
        $login_ok = false;
        $login_ok = $um->admin_login($email, $password);
        
        if($login_ok)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
}




?>