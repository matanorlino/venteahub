<?php
    require '../assets/php/Connector.php';
    require './CommonResponse.php';

    if (isset($_GET['order_id'])) {
        $order_id = $_GET['order_id'];
    }

    $stmt = $conn->prepare("SELECT * FROM all_orders WHERE order_id=?");
    $stmt->bind_param('i', $order_id);
    if ($stmt->execute()) {
        $result = $stmt->get_result();
        
        $data = $result->fetch_all(MYSQLI_ASSOC);
        $response = new CommonResponse('1',$data,"SUCCESS");

        echo json_encode($response->toJson());
    } else {
        $response = new CommonResponse('0',null,"ERROR");        
    }
    
    $stmt->close();
?>