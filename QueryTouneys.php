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
    mysqli_stmt_bind_result($statement, $tournament_name, $host, $start_date, $end_date, $type, $format, $num_teams);

    $response = array();
    $rows = array();

    while(mysqli_stmt_fetch($statement)){
      $rows["tournament_name"] = $tournament_name;
      $rows["host"] = $host;
      $rows["start_date"] = $start_date;
      $rows["end_date"] = $end_date;
      $rows["type"] =$type;
      $rows["format"] = $format;
      $rows["num_teams"] = $num_teams;
      $response[] = $rows;
    }

    echo json_encode($response);
?>
