<?php
	require '../assets/php/connector.php';
	require './CommonResponse.php';

	if (isset($_POST['email'])) {
	    $email = $_POST['email'];
	}
	if (isset($_POST['username'])) {
	    $username = $_POST['username'];
	}
	if (isset($_POST['password'])) {
	    $password = $_POST['password'];
	}
	if (isset($_POST['contact_no'])) {
	    $contact_no = $_POST['contact_no'];
	}
	if (isset($_POST['accesslevel'])) {
	    $accesslevel = $_POST['accesslevel'];
	}

	$stmt = $conn->prepare("INSERT INTO user(email, username, password, contact_no, accesslevel)VALUES(?,?,?,?,?);");
	$stmt->bind_param('sssss', $email, $username, $password, $contact_no, $accesslevel);
	
	$response = null;
	if ($stmt->execute()) {
		$response = new CommonResponse('1', null,"SUCCESS");
	} else {
		$response = new CommonResponse('0',null,"ERROR");
	}

	echo json_encode($response->toJson());
	$stmt->close();
?>