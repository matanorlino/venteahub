<?php
	

class User 
{
	
	function withPassword($email, $password, $contact_no, $id) {
		require_once('../assets/php/connector.php');
		require_once('./CommonResponse.php');

		$stmt = $conn->prepare("UPDATE user SET email = ?, password = ?,  contact_no = ? WHERE id = ?");
		$stmt->bind_param('sssi', $email, $password, $contact_no, $id);
		$response = null;
		if ($stmt->execute()) {
		$response = new CommonResponse('1', null,"SUCCESS");
		} else {
			$response = new CommonResponse('0',null,"ERROR");
		}
		echo json_encode($response->toJson());
		$stmt->close();
	}

	function withOutPassword($email, $contact_no, $id) {
		require_once('../assets/php/connector.php');
		require_once('./CommonResponse.php');

		$stmt = $conn->prepare("UPDATE user SET email = ?, contact_no = ? WHERE id = ?");
		$stmt->bind_param('ssi', $email, $contact_no, $id);

		$response = null;
		if ($stmt->execute()) {
		$response = new CommonResponse('1', null,"SUCCESS");
		} else {
			$response = new CommonResponse('0',null,"ERROR");
		}
		echo json_encode($response->toJson());
		$stmt->close();
	}
}

$user = new User;

$password = null;

if (isset($_POST['email'])) {
    $email = $_POST['email'];
}
if (isset($_POST['password'])) {
	$password = $_POST['password'];
}
if (isset($_POST['contact_no'])) {
	$contact_no = $_POST['contact_no'];
}
if (isset($_POST['id'])) {
	$id = $_POST['id'];
}

if (is_null($password)) {
	$user->withOutPassword($email, $contact_no, $id);	
} else {
	$user->withPassword($email, $password, $contact_no, $id);
}


	
?>