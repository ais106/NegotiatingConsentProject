<?php

require_once('processlogin.php');


if(isset($_SESSION['user']))
{
    header("location: admin.php");
}

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
        <script type="text/javascript"></script>
    </head>
    <body>
        <div class="container" style="margin-top:60px">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading"><h3 class="panel-title"><strong>Sign In </strong></h3></div>
                <div class="panel-body">
                 <form role="form" method="post" action="">
                    <div class="form-group">
                      <label for="inputEmail">Email</label>
                      <input type="email" class="form-control" name="inputEmail" id="inputEmail" placeholder="Enter email">
                    </div>
                    <div class="form-group">
                      <label for="inputPassword">Password</label>
                      <input type="password" class="form-control" name="inputPassword" id="inputPassword" placeholder="Password">
                      <span><?php echo $error; ?></span>
                    </div>
                    <button type="submit" id ="submit" name="submit"class="btn btn-sm btn-default">Sign in</button>                  
                  </form>
                </div>
            </div>
    </div>
</div>
</body>
</html>

