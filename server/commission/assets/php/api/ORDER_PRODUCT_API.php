<?php 
    class Order_Product {
        function getOrderProduct() {
            if (isset($_GET['id'])) {
                $id = $_GET['id'];
            }
            require_once('../connector.php');
            $stmt = $conn->prepare('SELECT co.order_id, user.username, prod.product_code, prod.product_name, prod.product_img, op.qty, op.qty * prod.sell_price AS sub_total, co.order_date, op.request, co.state FROM customer_order co LEFT JOIN order_products op ON co.order_id = op.order_id LEFT JOIN user ON co.buyer_id = user.id LEFT JOIN product prod ON op.product_id = prod.product_id WHERE op.order_id=? ORDER BY state, co.order_date, username DESC;');
            $stmt->bind_param('i', $id);
            
            if ($stmt->execute()) {
                $result = $stmt->get_result();
                $data = $result->fetch_all(MYSQLI_ASSOC);
                
                $stmt->close();
                return json_encode($data);    
            }
            $stmt->close();
        }
    }

    $op = new Order_Product();
    header("Content-Type:application/json");
    echo $op->getOrderProduct();
?>