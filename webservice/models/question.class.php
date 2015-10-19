<?php
require_once('database.php');

Class Question {
    
  function add_new_question($question_text, $associated_action, $points)
  {
    $db = new Database;
    $fields = 'question_text'.','.'associated_action'.','.'available_points';
    $values = "'".$question_text."'".", "."'".$associated_action."'".", "."'".$points."'";
    $question_id = $db->insert_new_record('question', $fields, $values);
    return $question_id;  
  }
  
  function get_all_questions()
  {
    $db = new Database;
    $questions = $db->select_all('question');
    return $questions;
  }
  
  function get_question_by_text($question_text)
  {
    $db = new Database;
    $params = "question_text = "."'".$question_text."'";
    $question = $db->select_all_where('question',$params);
    return $question;
  }
  
  function get_user_question($user_id, $group_id, $user_day)
  {
    $db = new Database;
    
    $request_time = (time() % 1440);
    
    $params = "user_id = "."'".$user_id."' AND "."request_day <=". "'" .$user_day."' AND"." request_time <="."'".$request_time ."' AND ". "request_sent = '0'";
    $order = 'request_day ASC, request_time ASC';
    $limit =  ' LIMIT 1';
    $user_question = $db->select_where_order_limit('user_question_request', $params, $order, $limit);
    //$user_question = $db->select_all_where('user_question_request', $params);
    
    // Check if there are any question requests left, if not send dummy question
    if(!$user_question)
    {
      $user_question['question_id'] = '0';
      $user_question['question_text'] = 'none';
      $user_question['available_points'] = '0';
      $user_question['associated_action'] ='none';
      $user_question['request_type'] ='none';
      $user_question['question_responses'][0] = 'no';
    }
    else
    {
      // Update the database to show this question request has been sent
      $db->update('user_request',"request_sent = '1'",'user_request_id', "'".$user_question['user_request_id']."'");
    }

    return $user_question;
  }
  
  
  function get_question_by_id($question_id)
  {
    $db = new Database;
    $params = "question_id = "."'".$question_id."'";
    $question = $db->select_all_where('question',$params);
    if(!$question)
    {
      $question_id = '1';
      $params = "question_id = "."'".$question_id."'";
      $question = $db->select_all_where('question',$params);
      
    }
    return $question;   
  }
  

  
    
    
    
}






?>