<?php
class category {
    function getData() {
        require_once('../connector.php');
        $sql = 'SELECT * FROM category';
        $result = $conn->query($sql);
        $arr = array();
        $count = 0;

        while($c = $result->fetch_assoc()) {
            $arr[$count+=1] = array(
                'category_id'   => $c['category_id'],
                'category_name' => $c['category_name'],
                'category_note' =>  $c['category_note']
            );
        }

        return json_encode($arr);
    }
}

$a = new category;
header("Content-Type:application/json");
echo $a->getData();
?>