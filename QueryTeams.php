<?php
    $connection = mysqli_connect("mysql6.000webhost.com", "a6257373_admin", "1admin", "a6257373_central");
	if ( !$connection ) {
	  die( 'connect error: '.mysqli_connect_error() );
	}

    //$coach = $_POST["coach"];

    $statement = mysqli_prepare($connection, "SELECT * FROM teams");
	if ( !$statement ) {
	  die(mysqli_error($connection));
	}
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $teamID, $coach, $team_name);

    $response = array();
    //$response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        //$response["success"] = true;
        $response["coach"] = $coach;
        $response["team_name"] = $team_name;
    }

    echo json_encode($response);
?>
