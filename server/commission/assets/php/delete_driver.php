<?php
require('connector.php');

$driver_id = $_POST['driver_id'];

$sql = "DELETE FROM user WHERE id=?";

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("s",$driver_id)) {
        if (!$stmt->execute()) {
            echo "Execute failed: (" . $stmt->errno . ") " . $stmt->error;
        }
        else {
            echo "deleted";
        }
    }
}

$stmt->close();

?>