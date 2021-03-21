<?php
require('connector.php');

$category_id = $_POST['category_id'];
$category_name = $_POST['category_name'];
$category_note = $_POST['category_note'];

$sql = "UPDATE `category` SET `category_name` = ?, category_note=? WHERE `category`.category_id = ?";

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("sss",$category_name, $category_note,$category_id)) {
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