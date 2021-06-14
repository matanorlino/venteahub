<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    if (isset($_GET['order_id'])) {
        $order_id = $_GET['order_id'];
    }
    if (isset($_GET['state'])) {
        $state = $_GET['state'];
    }

    if (is_null($state)) {
        $stmt = $conn->prepare("SELECT * FROM wait_driver_orders WHERE order_id=?");
        $stmt->bind_param('i', $order_id);
        if ($stmt->execute()) {
            $result = $stmt->get_result();
            
            $data = $result->fetch_all(MYSQLI_ASSOC);
            $response = new CommonResponse('1',$data,"SUCCESS");

            echo json_encode($response->toJson());
        } else {
            $response = new CommonResponse('0',null,"ERROR");        
        }
    } else {
        $stmt = $conn->prepare("SELECT * FROM delivering_orders WHERE order_id=?");
        $stmt->bind_param('i', $order_id);
        if ($stmt->execute()) {
            $result = $stmt->get_result();
            
            $data = $result->fetch_all(MYSQLI_ASSOC);
            $response = new CommonResponse('1',$data,"SUCCESS");

            echo json_encode($response->toJson());
        } else {
            $response = new CommonResponse('0',null,"ERROR");        
        }
    }
    
    $stmt->close();
?>