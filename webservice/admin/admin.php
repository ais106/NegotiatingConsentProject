<?php
    require_once('../models/question.class.php');
    session_start();
    
    $qm = new Question;
    
    $questions = $qm->get_all_questions();
    
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
        
        <script>

          $(document).ready(function() {
            $("#select_permission").change(function(event){
              $("#permission_text").val($("#select_permission option:selected").text());
            });
          });
        </script>
        
    </head>
    <body>
        <div class="container">
            <div class="page-header">
                <h1>Negotiating Consent Project</h1>
            </div>
            <div id="main-nav" class="navbar navbar-default" role="navigation">
            <ul class="nav navbar-nav">
              <li class="active"><a href="admin.php">Questions</a></li>
              <li><a href="groups.php">Groups</a></li>
              <li><a href="results.php">Results</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right"><li style="padding-right:15px"><a href="logout.php">Logout</a></li></ul>
            </div>
            <div class="row">
                <div id="question_form">
                    <form class="form-inline" action="savequestion.php" method="post">
                        <fieldset>
                        
                        <!-- Form Name -->
                        <legend>Question Generator</legend>
                        
                        <!-- Select Basic -->
                        <div class="form-group">
                          <label class="col-md-6 control-label" for="select_permission">Would you like to share <em>on a public website</em></label>
                          <div class="col-md-4">
                            <select id="select_permission" name="select_permission" class="form-control">
                              <option value="sms">your text messages</option>
                              <option value="location">your location</option>
                              <option value="phonestate">phone state and identity</option>
                              <option value="contacts">your contacts</option>
                              <option value="calendar">your calendar</option>
                              <option value="browser history">your browser history</option>
                              <option value="gallery">photo gallery</option>
                              <option value="installed apps">installed applications</option>
                            </select>
                          </div>
                        </div>
                        
                        <div class="form-group">
                            <input type="hidden" name="permission_text" id="permission_text" class="form-control">
                        </div>
                        
                        
                        
                        
                        <!-- Text input-->
                        <div class="form-group">
                          <label class="col-md-4 control-label" for="points">stored on your device for</label>  
                          <div class="col-md-2">
                          <input id="points" name="points" placeholder="eg: 100" class="form-control input-md" type="text">
                          </div>
                        </div>
                        
                        <!-- Button -->
                        <div class="form-group">
                          <label class="col-md-6 control-label" for="save_question">points?</label>
                          <div class="col-md-4">
                            <button id="save_question" type="submit" name="save_question" class="btn btn-primary">Save</button>
                          </div>
                        </div>
                        
                        </fieldset>
                    </form>
                </div>
            </div>
            <div class="row">
                <div id="question_list">
                    <table class="table">
                        <tr>
                            <th>Question ID</th>
                            <th>Question Text</th>
                            <th></th>
                        </tr>
                            <?php
                                            
                                foreach($questions as $q)
                                {
                                    $question_id = $q['question_id'];
                                    echo "<tr>";
                                    echo "<td>".$question_id."</td>";
                                    echo "<td>". $q['question_text'] ."</td>";
                                    echo "<td>";
                                    echo "<form method='POST' action='editquestion.php'>";
                                    echo "<input type='hidden' name='question_id' id='question_id' value='".$question_id."'>";
                                    echo "<button id='btn_edit' type='submit' name='btn_edit' class='btn btn-primary'>Edit</button>";
                                    echo "<button id='btn_delete' type='button' name='btn_delete' class='btn btn-danger'>Delete</button>";
                                    echo "</form>";
                                    echo "</td>";
                                    echo "</tr>";
                                }
                            ?>
                    </table>
                </div>
            </div>
        </div>
    </body>


