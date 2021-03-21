<?php
class product {
    function getData() {
        require_once('../connector.php');
        $sql = 'SELECT product.product_id, category.category_name, product.market_price, product.sell_price, product.product_code, product.product_img, product.product_img, product.model ,product.purchase_description, product.product_description, product.product_name , product.status FROM product , category WHERE product.product_category_id = category.category_id';
        $result = $conn->query($sql);
        $arr = array();
        $count = 0;

        while($c = $result->fetch_assoc()) {
            $arr[$count+=1] = array(
                'product_id'   => $c['product_id'],
                'product_category' => $c['category_name'],
                'market_price' => $c['market_price'],
                'sell_price' => $c['sell_price'],
                'product_code' => $c['product_code'],
                'product_img' => $c['product_img'],
                'model' => $c['model'],
                'purchase_description' => $c['purchase_description'],
                'product_description' => $c['product_description'],
                'status' =>  $c['status'],
                'product_name' =>  $c['product_name']
            );
        }

        return json_encode($arr);
    }
}

$a = new product;
header("Content-Type:application/json");
echo $a->getData();
?>