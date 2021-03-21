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
}

$a = new order;
header("Content-Type:application/json");
echo $a->getData();
?>