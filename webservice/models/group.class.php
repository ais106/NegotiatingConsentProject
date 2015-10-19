<?php

require_once('database.php');

Class Group {
    
   
    function new_group($group_name)
    {
        $db = new Database;

        $fields = 'group_name';
        $values = "'".$group_name."'";
        
        $group_id = $db->insert_new_record('survey_group', $fields, $values);
        return $group_id;
    }
    
    
    function get_group_questions($group_id)
    {
        $db = new Database;
        $params = "group_id = "."'".$group_id."'";
        $order = "request_day ASC, request_time ASC";
        $group_questions = $db->select_where_order_by('group_question_request',$params, $order);
        return $group_questions;
    }
    
    function get_groups()
    {
        $db = new Database;
        $groups = $db->select_all('survey_group');
        return $groups;
    }
    
    function get_group($group_id)
    {
        $db = new Database;
        $params = "group_id = "."'".$group_id."'";
        $groups = $db->select_all_where('survey_group',$params);
        return $groups;
    }
}


?>