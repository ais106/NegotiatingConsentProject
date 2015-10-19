<?php


class Database
{
    
    /**
     * Create a new database connect object
     */
    function connect()
    {
        // Database connection details
        $db_host='localhost';
        $db_user='root';
        $db_pass='N3go!at3';
        $db_name='ncdatabase';
        
        // Create a new database connection object
        $mysqli = new  mysqli($db_host,$db_user, $db_pass, $db_name);
        
        return $mysqli;   
    }
    
    function run_query($query)
    {
        // Get database connection object
         $mysqli = $this->connect();
        
        // Run the query
        $result = $mysqli->query($query);
        $numrows = $result->num_rows;
        
        if($numrows > 1)
        {
            while($r = $result->fetch_assoc())
            {
                $rows[] = $r;
            }
        }
        else
        {
            $rows = $result->fetch_assoc();
        }
        
        $mysqli->close();
        
        return $rows;
    }
    
    function run_delete($query)
    {
        // Get database connection object
         $mysqli = $this->connect();
        
        // Run the query
        $mysqli->query($query);
        
        $mysqli->close();
    }
    
    function run_insert($query)
    {
        // Get database connection object
         $mysqli = $this->connect();
        
        // Run the query
        $mysqli->query($query);
        
        // Place user details in array, close connection, return array
        $new_id = $mysqli->insert_id;
        
        $mysqli->close();
        return $new_id; 
    }
    
    
    function run_update($query)
    {
        // Get database connection object
         $mysqli = $this->connect();
        
        // Run the query
        $mysqli->query($query);
        
        $mysqli->close();
    }
    
    
    function select_all($table)
    {
        
        $query = "SELECT * FROM " . $table;
        $result = $this->run_query($query);
        return $result;
    }
    
    function delete_record($table, $params)
    {
        $query ="DELETE FROM ".$table ." WHERE ".$params;
        $this->run_delete($query);
    }
    
    function select_all_where($table, $params)
    {
        
        $query = "SELECT * FROM " . $table . " WHERE " . $params;
        $result = $this->run_query($query);
        return $result;
    }
    
    function select_where_order_by($table, $params, $order)
    {
        $query = "SELECT * FROM " . $table . " WHERE " . $params . " ORDER BY ". $order;
        $result = $this->run_query($query);
        return $result;
        
    }
    
    function select_where_order_limit($table, $params, $order, $limit)
    {
        $query = "SELECT * FROM " . $table . " WHERE " . $params . " ORDER BY ". $order . $limit;
        $result = $this->run_query($query);
        return $result;
    }
    
    function insert_new_record($table, $fields, $values)
    {
        $query = "INSERT INTO " . $table . " (" . $fields . ") VALUES (" . $values . ")";
        $result = $this->run_insert($query);
        return $result;
    }
    
    function insert_multiple($table, $fields, $values)
    {
        $mysqli = $this->connect();
        
        $query = "INSERT INTO " . $table . " (" . $fields . ") VALUES ". $values;
        
        // Run the query
        $mysqli->query($query);
        
        $mysqli->close();
    }
    
    function update($table, $update_fields, $where_key, $where_value)
    {         
        $query = "UPDATE " . $table . " SET " . $update_fields . " WHERE " . $where_key. " = " . $where_value;      
        $result = $this->run_update($query);
    }
    
    function get_all_group_user_ids($group_id)
    {
        $query = "SELECT user_id FROM user WHERE group_id = '".$group_id."'";
        $result = $this->run_query($query);
        return $result;
      
    }
    
   
    
    
}



?>