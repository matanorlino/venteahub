<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    if (isset($_POST['order_id'])) {
        $order_id = $_POST['order_id'];
    }
    if (isset($_POST['buyer_id'])) {
        $buyer_id = $_POST['buyer_id'];
    }
    if (isset($_POST['address'])) {
        $address = $_POST['address'];
    }
    if (isset($_POST['phone'])) {
        $phone = $_POST['phone'];
    }
    if (isset($_POST['order_date'])) {
        $order_date = $_POST['order_date'];
    }

    $stmt = $conn->prepare("INSERT INTO customer_order(order_id, buyer_id, address, phone, order_date)VALUES(?,?,?,?,?);");
    $stmt->bind_param('sssss', $order_id, $buyer_id, $address, $phone, $order_date);
    
    $response = null;
    if ($stmt->execute()) {
        $response = new CommonResponse('1', null,"SUCCESS");
    } else {
        $response = new CommonResponse('0',null,"ERROR");
    }

    echo json_encode($response->toJson());
    $stmt->close();
?>