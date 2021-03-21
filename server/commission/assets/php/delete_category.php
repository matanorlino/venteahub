<?php
require('connector.php');

$category_id = $_POST['category_id'];

$sql = "DELETE FROM category WHERE category_id=?";

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("s",$category_id)) {
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