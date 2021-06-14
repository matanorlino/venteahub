<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    if (isset($_POST['order_id'])) {
        $order_id = $_POST['order_id'];
    }
    if (isset($_POST['product_id'])) {
        $product_id = $_POST['product_id'];
    }
    if (isset($_POST['qty'])) {
        $qty = $_POST['qty'];
    }
    if (isset($_POST['request'])) {
        $request = $_POST['request'];
    }

    $stmt = $conn->prepare("INSERT INTO customer_order(order_id, product_id, qty, request)VALUES(?,?,?,?);");
    $stmt->bind_param('ssss', $order_id, $product_id, $qty, $request);
    
    $response = null;
    if ($stmt->execute()) {
        $response = new CommonResponse('1', null,"SUCCESS");
    } else {
        $response = new CommonResponse('0',null,"ERROR");
    }

    echo json_encode($response->toJson());
    $stmt->close();
?>