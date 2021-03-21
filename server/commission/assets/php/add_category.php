<?php
require('connector.php');

$category_name = $_POST['category_name'];
$category_note = $_POST['category_note'];

$sql = 'INSERT INTO category (category_name, category_note) VALUES(?, ?)';

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("ss",$category_name, $category_note)) {
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