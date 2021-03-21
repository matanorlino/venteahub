<?php
class feedback {
    function getData() {
        require_once('../connector.php');
        $sql = 'SELECT feedback.feedback_id, product.product_name, user.username, feedback.feedback_desc, feedback.feedback_date FROM user,feedback,product WHERE feedback.product_id = product.product_id AND feedback.user_id = user.id';
        $result = $conn->query($sql);
        $arr = array();
        $count = 0;

        while($c = $result->fetch_assoc()) {
            $arr[$count+=1] = array(
                'feedback_id'   => $c['feedback_id'],
                'product_name' => $c['product_name'],
                'name' => $c['username'],
                'feedback_desc' => $c['feedback_desc'],
                'feedback_date' => $c['feedback_date']
            );
        }

        return json_encode($arr);
    }
}

$a = new feedback;
header("Content-Type:application/json");
echo $a->getData();
?>