<?php
    
    require_once('../controllers/security.php');
    session_start();
    $error ="";


    $sc = new SecurityController;

    if(isset($_POST['submit']))
    {
        $email = $_POST['inputEmail'];
        $password = $_POST['inputPassword'];
    
        $sc = new SecurityController;
        $check =  $sc->check_admin_login($email, $password);
        
        if($check)
        {
            // Start session
            $_SESSION['user'] = $email;
            header("location: admin.php");
        }
        else
        {
            $error = "Username or password not valid";
        }
        
    }
    
    
    
    
    
?>