<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    if (isset($_GET['order_id'])) {
        $order_id = $_GET['order_id'];
    }


    $stmt = $conn->prepare("SELECT feedback_id, order_id, feedback_desc, feedback_date FROM feedback WHERE order_id = ? ORDER BY feedback_date DESC LIMIT 1;");

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