<?php
require('connector.php');

$driver_id = $_POST['driver_id'];
$driver_name = $_POST['driver_name'];
$driver_phone = $_POST['driver_phone'];
$driver_email = $_POST['driver_email'];

$sql = "UPDATE `driver` SET `driver_name` = ?, driver_email=?, driver_phone=? WHERE `driver`.driver_id = ?";

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("ssss",$driver_name,$driver_email, $driver_phone,$driver_id)) {
        if (!$stmt->execute()) {
            echo "Execute failed: (" . $stmt->errno . ") " . $stmt->error;
        }
        else {
            echo "updated";
        }
    }
}

$stmt->close();

?>