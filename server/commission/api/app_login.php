<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    if (mysqli_connect_errno()) {
      echo "Failed to connect to database: " . mysqli_connect_error();
    }
    if (isset($_POST['username'])) {
        $username = $_POST['username'];
    }
    if (isset($_POST['password'])) {
        $password = $_POST['password'];
    }

    $stmt = $conn->prepare("SELECT * FROM user WHERE username=? AND password=?;");
    $stmt->bind_param('ss', $username, $password);
    $stmt->execute();
    $result = $stmt->get_result();
    $data = $result->fetch_assoc();
    $response = new CommonResponse('1',$data);
    echo json_encode($response->toJson());
?>