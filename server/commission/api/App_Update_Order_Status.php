<?php
	require '../assets/php/connector.php';
	require './CommonResponse.php';

	if (isset($_POST['user_id'])) {
	    $user_id = $_POST['user_id'];
	}
	if (isset($_POST['order_id'])) {
	    $order_id = $_POST['order_id'];
	}
	if (isset($_POST['state'])) {
	    $state = $_POST['state'];
	}

	$stmt = $conn->prepare("UPDATE customer_order SET state=?, delivered_by=?, date_delivered=NOW() WHERE order_id=?");
	$stmt->bind_param('sii', $state, $user_id, $order_id);
	
	$response = null;
	if ($stmt->execute()) {
		$response = new CommonResponse('1', null,"SUCCESS");
	} else {
		$response = new CommonResponse('0',null,"ERROR");
	}

	echo json_encode($response->toJson());
	$stmt->close();
?>