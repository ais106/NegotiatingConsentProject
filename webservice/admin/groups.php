<?php
    require_once('../models/group.class.php');
    require_once('../models/question.class.php');
    session_start();
    
    function isAssoc($arr)
    {
        return array_keys($arr) !== range(0, count($arr) - 1);
    }
    
    function covertTime($time)
    {
        $converted_time = date('H:i', mktime(0,$time));
        return $converted_time;
    }
    
    
    
    if(!isset($_SESSION['group_id']))
    {
        $group_id = 1;
    }
    else
    {
        $group_id = $_SESSION['group_id'];
    }
    
    
    $gm = new Group;
    $qm = new Question;
    
    $group_questions = $gm->get_group_questions($group_id);
    $group_detail = $gm->get_group($group_id);
    $all_questions = $qm->get_all_questions();
    $all_groups = $gm->get_groups();

?>


<html>
    <head>
        <title>Negotiating Consent</title>
       <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="page-header">
                <h1>Negotiating Consent Project</h1>
            </div>
            <div id="main-nav" class="navbar navbar-default" role="navigation">
            <ul class="nav navbar-nav">
              <li><a href="admin.php">Questions</a></li>
              <li class="active"><a href="groups.php">Groups</a></li>
              <li><a href="results.php">Results</a></li>
            </ul>
                <ul class="nav navbar-nav navbar-right"><li style="padding-right:15px"><a href="logout.php">Logout</a></li></ul>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <h2>Group Menu</h2>
                  <form class="form-horizontal" action="addgroupquestion.php" method="post">
                    <fieldset>
                    
                    <!-- Form Name -->
                    <legend>Add Question</legend>
                    
                    <!-- Select Basic -->
                    <div class="form-group">
                    
                      <div class="col-md-12">
                        <select id="a_question_id" name="a_question_id" class="form-control">
                            <?php
                            if(!empty($all_questions)){
                                foreach($all_questions as $aq)
                                {
                                      $a_question_id = $aq['question_id'];
                                      $a_question_text = $aq['question_text'];
                                      echo "<option value='".$a_question_id."'>".$a_question_text."</option>";
                                }
                            }
                            ?>
                        </select>
                      </div>
                    </div>
                    
                    <?php echo "<input type='hidden' class='form-control' name='a_group_id' id='a_group_id' value='".$group_id."'>";?>
                    
                    <!-- Select Basic -->
                    <div class="form-group">
                      <div class="col-md-4">
                        <select id="a_request_type" name="a_request_type" class="form-control">
                            <option value="survey">Survey</option>
                            <option value="practice">Practice</option>
                        </select>
                      </div>
                    </div>
                    
                    <!-- Select Basic -->
                    <div class="form-group">
        
                        <div class="col-md-4">
                            <select id="select_day" name="select_day" class="form-control">
                                <option value="0">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                            </select>
                            <span class="help-block">Day</span> 
                        </div>
                        <div class="col-md-4">
                            <select id="select_hour" name="select_hour" class="form-control">
                                <option value="8">08</option>
                                <option value="9">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                            </select>
                             <span class="help-block">Hour</span> 
                        </div>
                        <div class="col-md-4">
                            <select id="select_minute" name="select_minute" class="form-control">
                                <option value="0">00</option>
                                <option value="15">15</option>
                                <option value="30">30</option>
                                <option value="45">45</option>
                            </select>
                            <span class="help-block">Minute</span> 
                        </div>
                       
        
                    </div>
                    
                    <!-- Button -->
                    <div class="form-group">
                      <div class="col-md-4">
                        <button id="btnadd_question" name="btnadd_question"  type="submit" class="btn btn-primary">Add Question to Group</button>
                      </div>
                    </div>
                    
                    </fieldset>
                    </form>
                  
                  <form class="form-horizontal" action="changegroup.php" method="post">
                    <fieldset>
                    
                    <!-- Form Name -->
                    <legend>Change Group</legend>
                    
                    <!-- Select Basic -->
                    <div class="form-group">
                      <div class="col-md-12">
                        <select id="selectgroup_id" name="selectgroup_id" class="form-control">
                            <?php
                            if(!isAssoc($all_groups))
                            {
                                foreach($all_groups as $ag)
                                {
                                    if($ag['group_id'] == $group_id)
                                    {
                                        echo "<option value='". $ag['group_id'] ."' selected>".$ag['group_name']."</option>";
                                    }
                                    else
                                    {
                                        echo "<option value='". $ag['group_id'] ."'>".$ag['group_name']."</option>";                                   
                                    }
                                }
                            }
                            else
                            {
                                echo "<option value='". $all_groups['group_id'] ."'>".$all_groups['group_name']."</option>";
                            }
                            
                            ?>
                        </select>
                      </div>
                    </div>
                    
                    
                    
                    
                    <!-- Button -->
                    <div class="form-group">
                      <div class="col-md-4">
                        <button id="btnchange_group" name="btnchange_group" type="submit" class="btn btn-primary">Go to Group</button>
                      </div>
                    </div>
                    
                    </fieldset>
                    </form>
                  
                  
                <form class="form-horizontal" action="newgroup.php" method="post">
                  <fieldset>
                  
                  <!-- Form Name -->
                  <legend>New Group</legend>
                  
                  <!-- Text input-->
                  <div class="form-group">
                    <div class="col-md-12">
                    <input id="textinput" name="group_name" placeholder="Group name" class="form-control input-md" type="text">
                      
                    </div>
                  </div>
                  
                  <!-- Button -->
                  <div class="form-group">
                    <div class="col-md-4">
                      <button id="btnadd_group" name="btnadd_group" type="submit" class="btn btn-primary">Save Group</button>
                    </div>
                  </div>
                  
                  </fieldset>
                  </form>
                                        
                </div>
                
                
                <div class="col-md-8">
                    <h2 id="group_title">Group <?php echo $group_detail['group_id'] .": ". $group_detail['group_name']; ?></h2>
                    <div id="question_list">
                        <table class="table">
                            <tr>
                                <th>Question ID</th>
                                <th>Question Text</th>
                                <th>Type</th>
                                <th>Scheduled</th>
                                <th></th>
                            </tr>
                                <?php
                                                                
                                if($group_questions)
                                {
                                    if(isAssoc($group_questions))
                                    {
                                            $question_id = $group_questions['question_id'];
                                            $request_type = $group_questions['request_type'];
                                            $request_id = $group_questions['question_request_id'];
                                            $scheduled = "Day: ". $group_questions['request_day']. " ". covertTime($group_questions['request_time']);
                                            
                                            echo "<tr>";
                                            echo "<td>".$question_id."</td>";
                                            echo "<td>". $group_questions['question_text'] ."</td>";
                                            echo"<td>".$request_type."</td>";
                                            echo"<td>".$scheduled."</td>";
                                            echo "<td>"; 
                                            echo "<form method='POST' action='removegroupquestion.php'>";
                                            echo "<input type='hidden' name='question_request_id' id='question_request_id' value='".$request_id."'>";
                                            echo "<button id='btn_delete' type='submit' name='btn_delete' class='btn btn-warning'>Remove</button>";
                                            echo "</form>";
                                            echo "</td>";
                                            echo "</tr>";
                                    }
                                    else{
                                        foreach($group_questions as $q)
                                        {
                                            $question_id = $q['question_id'];
                                            $request_type = $q['request_type'];
                                            $request_id = $q['question_request_id'];
                                            $scheduled = "Day ". $q['request_day']. " ". covertTime($q['request_time']);
                                            
                                            echo "<tr>";
                                            echo "<td>".$question_id."</td>";
                                            echo "<td>". $q['question_text'] ."</td>";
                                            echo"<td>".$request_type."</td>";
                                            echo"<td>".$scheduled."</td>";
                                            echo "<td>"; 
                                            echo "<form method='POST' action='removegroupquestion.php'>";
                                            echo "<input type='hidden' name='question_request_id' id='question_request_id' value='".$request_id."'>";
                                            echo "<button id='btn_delete' type='submit' name='btn_delete' class='btn btn-warning'>Remove</button>";
                                            echo "</form>";
                                            echo "</td>";
                                            echo "</tr>";
                                        }
                                    }
                                }
                                ?>
                        </table>
                    </div>              
                </div>
            </div>
            
            
            
            
            
        </div>
    </body>
</html>