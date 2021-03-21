<?php
    require 'connector.php';

    $username = $_POST['username'];
    $password = $_POST['password'];

    $sql = 'SELECT * FROM user';

    if ($res = mysqli_query($conn, $sql)){
        while ($row = mysqli_fetch_assoc($res)) {
            if ($row['username'] == $username && $row['password'] == $password) {
                echo 'admin';
                session_start();
                $_SESSION['uid'] = $row['id'];
                $_SESSION['uname'] = $row['username'];
                $_SESSION['lvl'] = $row['accesslevel'];
                break;
            }
        }
    }
?>