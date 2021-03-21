<?php
    $host = "localhost";
    $user = "root";
    $pass = "";
    $db_name = "management_db";

    $conn = new mysqli($host, $user,$pass, $db_name);

    if ($conn->connect_errno) {
        echo "Failed to connect to MySQL: " . $mysqli -> connect_error;
        exit();
    }
?>