<?php
//Not used. Problem passing host parameter between android studio and php
    $con = mysqli_connect("mysql6.000webhost.com", "a6257373_admin", "1admin", "a6257373_central");

    $host = $_POST["host"] ;

    $statement = mysqli_prepare($con, "SELECT * FROM tournaments WHERE host = ?");
    mysqli_stmt_bind_param($statement, "s", $host);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $tourneyID, $tournament_name, $host, $start_date, $end_date, $type, $format, $num_teams);

    $response = array();

    while(mysqli_stmt_fetch($statement)){
        $rows["tourneyID"] = $tourneyID;
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
