<?php

require_once('database.php');


class User {
     
      /**
     * Create a new user record in the database
     */
    function new_user()
    {
        // Generate pass key
        $pass_key = $this->generate_pass_key();
        
        // New database class
        $db = new Database;
        
        // Assign a user group
        $user_group_id = $this->assign_user_group();
        
        // Create new user record
        $date = date('Y-m-d H:i:s');
        $fields = 'pass_key'.', '.'date_joined'.', '.'group_id';
        $values = "'".$pass_key."'".", "."'".$date."', " ."'".$user_group_id."'";
        $new_user_id = $db->insert_new_record('user',$fields, $values);
        
        $new_user['user_id'] = $new_user_id; 
        $new_user['pass_key'] = $pass_key;
        $new_user['group_id'] = $user_group_id;
        
        
        // Get all group questions and add them to the user question request table
        $group_param = "group_id = "."'".$user_group_id."'";
        $group_question = $db->select_all_where('question_request',$group_param);
        
        $user_group_params = "";
        $i = 0;
        $ug_fields = 'user_id'.','.'question_request_id';
        
        if($group_question)
        {
            foreach($group_question as $gq)
            {
                $user_group_params .= "('".$new_user_id."'".",". "'".$gq['question_request_id']."'),";
            }
        
        }
      
        $user_group_params = rtrim($user_group_params,',');
        
        // Save the question requests from the group treatment
        $user_group_params = $db->insert_multiple('user_request',$ug_fields, $user_group_params);       
        
        
        return $new_user;   
    }
    
    /**
     * Function to determine which user group to assign the user
     */
    function assign_user_group()
    {
        // Test user group
        $user_group_id = 1;
        return $user_group_id;
    }
    
    function get_user_details($user_id)
    {
        $db = new Database;
        $params = "user_id = '".$user_id."'";
        $user = $db->select_all_where('user', $params);
        return $user;
        
    }
    
    
    function time_since_joining($user_id)
    {       
        $user = $this->get_user_details($user_id);
        $user_joined = $user['date_joined'];
        
        $time = $this->time_to_string(time()-strtotime($user_joined));
        return $time;
    }


    function time_to_string($timeline)
    {
        $num = floor($timeline / 86400);
        return $num;
    }
    
    
    /**
     * Generate a random string to use as a pass key for the user
     */
    function generate_pass_key()
    {
        $length = 16;
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*_-=+;:,.?";
        $chars_length = strlen($chars);
        $pass_key = '';
        
        for($i = 0; $i < $length; $i++)
        {
            $pass_key .= $chars[rand(0, $chars_length - 1)];
        }
        
        return $pass_key;
    }
    
    function admin_login($email, $password)
    {
        $db = new Database;
        $params = "username = '".$email."' LIMIT 1";
        $user = $db->select_all_where('admin',$params);
        $hashed_password = $user['password'];
        
        if(crypt($password, $hashed_password) == $hashed_password)
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