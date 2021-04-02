<?php
require('connector.php');

$driver_id = $_POST['driver_id'];
$driver_name = $_POST['driver_name'];
$driver_phone = $_POST['driver_phone'];
$driver_email = $_POST['driver_email'];

$sql = "UPDATE `user` SET email=?, contact_no=? WHERE id = ?";

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("ssi", $driver_email, $driver_phone, $driver_id)) {
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