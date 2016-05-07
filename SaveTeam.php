<?php
    $con = mysqli_connect("mysql6.000webhost.com", "a6257373_admin", "1admin", "a6257373_central");

    $coach = $_POST["coach"];
    $team_name = $_POST["team_name"];


    $statement = mysqli_prepare($con, "INSERT INTO teams (coach, team_name) VALUES (?, ?)");
    mysqli_stmt_bind_param($statement, "siss", $coach, $team_name);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
