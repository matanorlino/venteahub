<?php
require('connector.php');

$product_id = $_POST['product_id'];

$sql = "DELETE FROM product WHERE product.product_id=?";

if ($stmt = $conn->prepare($sql)) {
    if ($stmt->bind_param("s",$product_id)) {
        if (!$stmt->execute()) {
            echo "Execute failed: (" . $stmt->errno . ") " . $stmt->error;
        }
        else {
            echo "deleted";
        }
    }
}

$stmt->close();

?>