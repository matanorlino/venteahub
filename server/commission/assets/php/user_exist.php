<?php
    require 'connector.php';

    $username = $_POST['username'];

    $sql = 'SELECT username FROM user';

    if ($res = mysqli_query($conn, $sql)){
        while ($row = mysqli_fetch_assoc($res)) {
            if ($row['username'] == $username) {
                echo "1";
                break;
            }
        }
        echo "0";
    }
?>