<?php
class driver {
    function getData() {
        require_once('../connector.php');
        $sql = 'SELECT * FROM driver';
        $result = $conn->query($sql);
        $arr = array();
        $count = 0;

        while($c = $result->fetch_assoc()) {
            $arr[$count+=1] = array(
                'driver_id'   => $c['driver_id'],
                'driver_name' => $c['driver_name'],
                'driver_email' => $c['driver_email'],
                'driver_phone' => $c['driver_phone']
            );
        }

        return json_encode($arr);
    }
}

$a = new driver;
header("Content-Type:application/json");
echo $a->getData();
?>