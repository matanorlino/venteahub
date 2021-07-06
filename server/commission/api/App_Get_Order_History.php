<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    if (isset($_GET['buyer_id'])) {
        $buyer_id = $_GET['buyer_id'];
    }


    $stmt = $conn->prepare("SELECT order_id, user_id, address, username, contact_no, date, SUM(qty * sell_price) AS total FROM all_orders WHERE user_id= ? and (state = 'received' OR state = 'cancelled') GROUP BY order_id ORDER BY date DESC");

    $stmt->bind_param('i', $buyer_id);
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