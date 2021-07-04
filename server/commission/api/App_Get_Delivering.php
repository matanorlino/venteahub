<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    if (isset($_GET['delivered_by'])) {
        $delivered_by = $_GET['delivered_by'];
    }

    $stmt = $conn->prepare("SELECT order_id, user_id, address, username, contact_no, date, SUM(qty * sell_price) AS total, delivered_by FROM delivering_orders WHERE delivered_by=? GROUP BY order_id ORDER BY date DESC LIMIT 1");
    $stmt->bind_param('i', $delivered_by);
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