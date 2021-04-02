<?php
require('connector.php');

    $order_id = $_POST['order_id'];
    $state = $_POST['state'];

    $stmt = $conn->prepare("UPDATE customer_order SET state = ? WHERE order_id = ?");

    $stmt->bind_param('si', $state, $order_id);
    $response = [];
    if ($stmt->execute()) {
        $response['msg'] = 'success';
    } else {
        $response['msg'] = 'error';
    }
    $stmt->close();
    return json_encode($response);
?>