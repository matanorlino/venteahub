<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- ===== CSS ===== -->
        <link rel="stylesheet" href="../assets/css/styles.css">
        
        <title>Admin Dashboard</title>
    </head>
    <body id="body-pd">
        <div class="l-navbar" id="navbar">
            <nav class="nav">
                <div>
                    <div class="nav__brand">
                        <a href="index.php" class="nav__logo">Admin Dashboard</a>
                    </div>
                    <div class="nav__list">
                        <div  class="nav__link collapse">
                            <ion-icon name="receipt-outline" class="nav__icon"></ion-icon>
                            <span class="nav__name">Order Manage</span>

                            <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>

                            <ul class="collapse__menu">
                                <a href="order.php" class="collapse__sublink">Order Query</a><br>
                                <a href="orderunexamined.php" class="collapse__sublink">Order Unexamined</a><br>
                                <a href="orderwaitdeliver.php" class="collapse__sublink">Order Wait Deliver</a><br>
                                <a href="orderdelivered.php" class="collapse__sublink">Order Delivered</a><br>
                                <a href="orderrecieved.php" class="collapse__sublink">Order Received</a><br>
                                <a href="ordercancel.php" class="collapse__sublink">Order Cancelled</a>
                           </ul>
                        </div>
                        <div class="nav__link collapse">
                            <ion-icon name="folder-open-outline" class="nav__icon"></ion-icon>
                            <span class="nav__name">Product Manage</span>

                            <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>

                            <ul class="collapse__menu">
                                <a href="categorymanage.php" class="collapse__sublink">Category Manage</a><br>
                                <a href="productmanage.php" class="collapse__sublink">Product Manage</a>
                            </ul>
                        </div>
                        <div class="nav__link collapse">
                            <ion-icon name="people-outline" class="nav__icon"></ion-icon>
                            <a href="deliverypersonel.php" style="color: inherit;"><span class="nav__name">Delivery Personnel</span></a>
                        </div>
                        <div class="nav__link collapse">
                            <ion-icon name="people-outline" class="nav__icon"></ion-icon>
                            <a href="feedbacks.php" style="color: inherit;"><span class="nav__name">Feedbacks</span></a>
                        </div>
                    </div>
                </div>

                <a href="../assets/php/logout.php" class="nav__link">
                    <ion-icon name="log-out-outline" class="nav__icon"></ion-icon>
                    <span class="nav__name">Logout</span>
                </a>
            </nav>
        </div>

        <img src="../assets/product_img/logo.png" alt="logo" id="logo">
        <!-- ===== IONICONS ===== -->
        <script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
        
        <!-- ===== MAIN JS ===== -->
        <script src="../assets/js/main.js"></script>
        
    </body>
</html>