<?php
require('connector.php');

$product_category_id = $_POST['product_category_id'];
$market_price = $_POST['market_price'];
$sell_price = $_POST['sell_price'];
$product_code = $_POST['product_code'];
$product_img = $_FILES['product_img'];
$model = $_POST['model'];
$purchase_description = $_POST['purchase_description'];
$product_description = $_POST['product_description'];
$product_name = $_POST['product_name'];
$product_id = $_POST['product_id'];
$status = $_POST['status'];
if(!empty($product_img)){
    $filename = $product_img['name'];
    $tempName = $product_img['tmp_name'];

    $sql = 'UPDATE `product` SET product_category_id = ? ,`market_price` = ?, `sell_price` = ?, `product_code` = ?, `product_img` = ?, `model` = ?, `purchase_description` = ?, `product_description` = ?, `status` = ? , product_name = ? WHERE `product`.`product_id` = ?';

    if ($stmt = $conn->prepare($sql)) {
        if ($stmt->bind_param("sssssssssss",$product_category_id, $market_price,$sell_price,$product_code,$filename,$model,$purchase_description,$product_description,$status,$product_name,$product_id)) {
            if (!$stmt->execute()) {
                echo "Execute failed: (" . $stmt->errno . ") " . $stmt->error;
            }
            else {
                move_uploaded_file($tempName, '../product_img/'.$filename);
                echo "updated";
            }
        }
    }
}
else{
    $sql = 'UPDATE `product` SET product_category_id = ? ,`market_price` = ?, `sell_price` = ?, `product_code` = ?, `model` = ?, `purchase_description` = ?, `product_description` = ?, `status` = ? , product_name = ? WHERE `product`.`product_id` = ?';

    if ($stmt = $conn->prepare($sql)) {
        if ($stmt->bind_param("ssssssssss",$product_category_id, $market_price,$sell_price,$product_code,$model,$purchase_description,$product_description,$status,$product_name,$product_id)) {
            if (!$stmt->execute()) {
                echo "Execute failed: (" . $stmt->errno . ") " . $stmt->error;
            }
            else {
                echo "updated";
            }
        }
    }
}

$stmt->close();

?>