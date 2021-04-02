<?php
require('connector.php');

$driver_name = $_POST['driver_name'];
$driver_phone = $_POST['driver_phone'];
$driver_email = $_POST['driver_email'];
$default_pass = $_POST['driver_name'];
$accesslevel = 'driver';
$sql = 'INSERT INTO user (username, email, contact_no, accesslevel, password) VALUES(?,?,?,?,?)';

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("sssss",$driver_name, $driver_email, $driver_phone, $accesslevel, $default_pass)) {
        if (!$stmt->execute()) {
            echo "Execute failed: (" . $stmt->errno . ") " . $stmt->error;
        }
        else {
            echo "added";
        }
    }
}

$stmt->close();

?>