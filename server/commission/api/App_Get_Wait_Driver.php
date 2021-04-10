<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    $stmt = $conn->prepare("SELECT order_id, user_id, address, username, contact_no, date, SUM(qty * sell_price) AS total FROM wait_driver_orders GROUP BY order_id ORDER BY date DESC");
    
    if ($stmt->execute()) {
        $result = $stmt->get_result();
        
        $data = $result->fetch_all(MYSQLI_ASSOC);
        $response = new CommonResponse('1',$data,"SUCCESS");

        echo json_encode($response->toJson());
    }
    $stmt->close();
?>