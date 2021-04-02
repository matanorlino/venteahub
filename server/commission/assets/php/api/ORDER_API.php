<?php
class order {
    function getData() {
        require_once('../connector.php');
        $sql = 'SELECT customer_order.order_id, user.username, customer_order.state, customer_order.address, customer_order.request, customer_order.phone, product.product_name, customer_order.qty FROM customer_order , product , user WHERE customer_order.product_id = product.product_id AND customer_order.buyer_id = user.id';
        $result = $conn->query($sql);
        $arr = array();
        $count = 0;

        while($c = $result->fetch_assoc()) {
            $arr[$count+=1] = array(
                'order_id'   => $c['order_id'],
                'buyer' => $c['username'],
                'state' => $c['state'],
                'address' => $c['address'],
                'request' => $c['request'],
                'phone' => $c['phone'],
                'product' => $c['product_name'],
                'qty' => $c['qty']
            );
        }

        return json_encode($arr);
    }

    function getOrders() {
        require_once('../connector.php');
        $sql = 'SELECT co.order_id, user.username, user.contact_no, co.address, user.email, SUM(op.qty) AS total_qty, SUM(op.qty * prod.sell_price) AS grand_total, co.date, co.request AS instruction, co.state FROM customer_order co LEFT JOIN order_products op ON co.order_id = op.order_id LEFT JOIN user ON co.buyer_id = user.id LEFT JOIN product prod ON op.product_id = prod.product_id GROUP BY op.order_id ORDER BY state, co.date, username DESC;';

        $stmt = $conn->prepare($sql);
    
        if ($stmt->execute()) {
            $result = $stmt->get_result();
            $data = $result->fetch_all(MYSQLI_ASSOC);
            
            $stmt->close();

            return json_encode($data);
        }
        
    }
}

$a = new order;
header("Content-Type:application/json");
echo $a->getOrders();
?>