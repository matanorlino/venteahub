<?php
require('connector.php');

$driver_name = $_POST['driver_name'];
$driver_phone = $_POST['driver_phone'];
$driver_email = $_POST['driver_email'];

$sql = 'INSERT INTO driver (driver_name, driver_email,driver_phone) VALUES(?,?,?)';

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("sss",$driver_name, $driver_email, $driver_phone)) {
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