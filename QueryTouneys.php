<?php
    $connection = mysqli_connect("mysql6.000webhost.com", "a6257373_admin", "1admin", "a6257373_central");
  	if ( !$connection ) {
  	  die( 'connect error: '.mysqli_connect_error() );
  	}

    $statement = mysqli_prepare($connection, "SELECT * FROM tournaments");
  	if ( !$statement ) {
  	  die(mysqli_error($connection));
  	}
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $tournamentID, $host, $team_name);

    $response = array();
    $rows = array();

    while(mysqli_stmt_fetch($statement)){
        $rows["teamID"] = $coach;
        $rows["coach"] = $coach;
        $rows["team_name"] = $team_name;
        $response[] = $rows;
    }

    echo json_encode($response);
?>
