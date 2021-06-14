<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

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
    if (isset($_POST['order_no'])) {
        $order_no = $_POST['order_no'];
    }
    $state = "wait_deliver";

    $stmt = $conn->prepare("INSERT INTO customer_order(buyer_id, state, address, phone, order_date, order_no)VALUES(?,?,?,?,?,?);");
    $stmt->bind_param('isssss', $buyer_id, $state, $address, $phone, $order_date, $order_no);

    $response = null;
    if ($stmt->execute()) {
        $last_id = $conn->insert_id;
        $response = new CommonResponse('1', $last_id, "SUCCESS");
    } else {
        $response = new CommonResponse('0', null, "ERROR");
    }

    echo json_encode($response->toJson());
    $stmt->close();
?>