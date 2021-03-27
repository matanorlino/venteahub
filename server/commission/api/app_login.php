<?php
    require '../assets/php/connector.php';

    if (mysqli_connect_errno()) {
      echo "Failed to connect to database: " . mysqli_connect_error();
    }
    $username = $_POST['username'];
    $password = $_POST['password'];

    $stmt = $conn->prepare('SELECT * FROM user WHERE username = ? AND password = ?');
    $stmt = $stmt->bind_param('ss', $username, $password);
    $stmt->execute();
    
    $result = $stmt->get_result();
    $data = $result->fetch_assoc();
    echo json_encode($data);
?>