<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    if (isset($_POST['username'])) {
        $username = $_POST['username'];
    }
    if (isset($_POST['password'])) {
        $password = $_POST['password'];
    }

    $stmt = $conn->prepare("SELECT * FROM user WHERE username=? AND password=?;");
    $stmt->bind_param('ss', $username, $password);

    if ($stmt->execute()) {
        $result = $stmt->get_result();
        $data = $result->fetch_assoc();
        
        if ($result->num_rows > 0) {

            $response = new CommonResponse('1',$data,"SUCCESS");
        } else {
            $response = new CommonResponse('0',null,"ERROR");
        }
        echo json_encode($response->toJson());
    }
    $stmt->close();
?>