<?php
require('connector.php');

$username = $_POST['username'];
$password = $_POST['password'];
$email = $_POST['email'];
$accesslevel = $_POST['accesslevel'];

$sql = 'INSERT INTO user (username, `password`, email, accesslevel) VALUES(?, ?,?,?)';

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("ssss",$username, $password,$email,$accesslevel)) {
        if (!$stmt->execute()) {
            echo "Execute failed: (" . $stmt->errno . ") " . $stmt->error;
        }
        else {
            echo "registered";
        }
    }
}

$stmt->close();

?>