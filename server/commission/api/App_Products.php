<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';

    $stmt = $conn->prepare("SELECT * FROM product ORDER BY product_category_id;");
    
    if ($stmt->execute()) {
        $result = $stmt->get_result();
        
        $data = $result->fetch_all(MYSQLI_ASSOC);
        $response = new CommonResponse('1',$data,"SUCCESS");

        echo json_encode($response->toJson());
    }
?>