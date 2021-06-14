<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    if (isset($_GET['buyer_id'])) {
        $buyer_id = $_GET['buyer_id'];
    }

    $stmt = $conn->prepare("SELECT dl.delivered_by, dl.order_id, dl.latitude, dl.longitude, co.state, co.address FROM driver_location as dl left join customer_order as co on dl.order_id = co.order_id WHERE co.state = 'delivering' and buyer_id=?");
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