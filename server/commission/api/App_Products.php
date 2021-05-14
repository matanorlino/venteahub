<?php
    require '../assets/php/connector.php';
    require './CommonResponse.php';


    if (isset($_GET['product_id'])) {
        $product_id = $_GET['product_id'];
        $stmt = $conn->prepare("SELECT * FROM product WHERE product_id=? ORDER BY product_category_id, product_name;");
        $stmt->bind_param('i', $product_id);
    } else if (isset(($_GET['product_code']))){
        $product_code = $_GET['product_code'];
        $stmt = $conn->prepare("SELECT * FROM product WHERE product_code=?;");
        $stmt->bind_param('s',$product_code);
    } else {
        $stmt = $conn->prepare("SELECT * FROM product ORDER BY product_category_id, product_code;");
    }
    
    if ($stmt->execute()) {
        $result = $stmt->get_result();
        
        $data = $result->fetch_all(MYSQLI_ASSOC);
        $response = new CommonResponse('1',$data,"SUCCESS");

        echo json_encode($response->toJson());
    }
    $stmt->close();
?>