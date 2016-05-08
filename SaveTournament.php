<?php
    $con = mysqli_connect("mysql6.000webhost.com", "a6257373_admin", "1admin", "a6257373_central");

    $tournament_name = $_POST["tournament_name"];
    $host = $_POST["host"];
    $type = $_POST["type"];
    $format = $_POST["format"];
    $num_teams = $_POST["num_teams"];
    $start_date = $_POST["start_date"];
    $end_date = $_POST["end_date"];

    $statement = mysqli_prepare($con, "INSERT INTO tournaments (tournament_name, host, type, format, num_teams) VALUES (?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssssi", $tournament_name, $host, $type, $format, $num_teams);
    mysqli_stmt_execute($statement);

    $statement = mysqli_prepare($con, "UPDATE tournaments SET start_date = ?, end_date = ? WHERE tournament_name = ?");
    mysqli_stmt_bind_param($statement, "sss", $start_date, $end_date, $tournament_name);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
