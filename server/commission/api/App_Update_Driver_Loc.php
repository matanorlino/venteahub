<?php
	require '../assets/php/connector.php';
	require './CommonResponse.php';

	if (isset($_POST['flag'])) {
	    $flag = $_POST['flag'];
	}
	if (isset($_POST['delivered_by'])) {
	    $delivered_by = $_POST['delivered_by'];
	}
	if (isset($_POST['order_id'])) {
	    $order_id = $_POST['order_id'];
	}
	if (isset($_POST['latitude'])) {
	    $latitude = $_POST['latitude'];
	}
	if (isset($_POST['longitude'])) {
	    $longitude = $_POST['longitude'];
	}

	// do insert
	if ($flag == 'i') {
		$latitude = isset($_POST['latitude']) ? $latitude : 0.0;
		$longitude = isset($_POST['longitude']) ? $longitude : 0.0;

		$stmt = $conn->prepare("INSERT INTO driver_location(delivered_by, order_id, latitude, longitude) VALUES(?,?,?,?)");
		$stmt->bind_param('iidd', $delivered_by, $order_id, $latitude, $longitude);
		$response = null;
		if ($stmt->execute()) {
			$response = new CommonResponse('1', null,"SUCCESS");
		} else {
			$response = new CommonResponse('0',$conn->error,"ERROR");
		}
	// do update
	} else if ($flag == 'u') {
		$latitude = isset($_POST['latitude']) ? $latitude : 0.0;
		$longitude = isset($_POST['longitude']) ? $longitude : 0.0;
		$stmt = $conn->prepare("UPDATE driver_location SET latitude=?, longitude=? WHERE delivered_by=? AND order_id=?");
		$stmt->bind_param('ddii', $latitude, $longitude, $delivered_by, $order_id);
		
		$response = null;
		if ($stmt->execute()) {
			$response = new CommonResponse('1', null,"SUCCESS");
		} else {
			$response = new CommonResponse('0',null,"ERROR");
		}
	}

	echo json_encode($response->toJson());
	$stmt->close();
?>