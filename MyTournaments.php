<?php
    $connection = mysqli_connect("mysql6.000webhost.com", "a6257373_admin", "1admin", "a6257373_central");
  	if ( !$connection ) {
  	  die( 'connect error: '.mysqli_connect_error() );
  	}
    $username = $_POST["username"];

    $statement = mysqli_prepare($connection, "SELECT * FROM registered_teams INNER JOIN teams ON registered_teams.teamID=teams.teamID");
  	if ( !$statement ) {
  	  die(mysqli_error($connection));
  	}
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $tournament_name, $teamID1, $teamID2, $coach, $team_name);

    $response = array();
    $rows = array();

    while(mysqli_stmt_fetch($statement)){
      $rows["tournament_name"] = $tournament_name;
      $rows["teamID1"] = $teamID1;
      $rows["teamID2"] = $teamID2;
      $rows["coach"] = $coach;
      $rows["team_name"] =$team_name;
      $response[] = $rows;
    }

    echo json_encode($response);
?>
