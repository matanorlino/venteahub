<?php
class driver {
    function getData() {
        require_once('../connector.php');
        $sql = "SELECT * FROM user WHERE accesslevel = 'driver'";
        $result = $conn->query($sql);
        $arr = array();
        $count = 0;

        while($c = $result->fetch_assoc()) {
            $arr[$count+=1] = array(
                'driver_id'   => $c['id'],
                'driver_name' => $c['username'],
                'driver_email' => $c['email'],
                'driver_phone' => $c['contact_no']
            );
        }

        return json_encode($arr);
    }
}

$a = new driver;
header("Content-Type:application/json");
echo $a->getData();
?>