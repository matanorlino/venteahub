<?php
	require '../assets/php/Connector.php';
	require './CommonResponse.php';

	if (isset($_POST['user_id'])) {
	    $user_id = $_POST['user_id'];
	}
	if (isset($_POST['order_id'])) {
	    $order_id = $_POST['order_id'];
	}
	if (isset($_POST['feedback'])) {
	    $feedback = $_POST['feedback'];
	}

	$stmt = $conn->prepare("INSERT INTO feedback(order_id, user_id, feedback_desc)VALUES(?,?,?);");
	$stmt->bind_param('iis', $order_id, $user_id, $feedback);
	
	$response = null;
	if ($stmt->execute()) {
		$response = new CommonResponse('1', null,"SUCCESS");
	} else {
		$response = new CommonResponse('0',null,"ERROR");
	}

	echo json_encode($response->toJson());
	$stmt->close();
?>