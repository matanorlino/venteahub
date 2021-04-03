<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- ===== CSS ===== -->
        <link rel="stylesheet" href="../assets/css/styles.css">
        <link rel="stylesheet" href="../assets/css/categorymanage.css">

        <title>Admin Dashboard</title>
    </head>
    <body id="body-pd" onload="">
        <div class="l-navbar" id="navbar">
            <nav class="nav">
                <div>
                    <div class="nav__brand">
                        <a href="index.php" class="nav__logo">Admin Dashboard</a>
                    </div>
                    <div class="nav__list">
                        <div  class="nav__link collapse active">
                            <ion-icon name="receipt-outline" class="nav__icon"></ion-icon>
                            <span class="nav__name">Order Manage</span>

                            <ion-icon name="chevron-down-outline" class="collapse__link"></ion-icon>

                            <ul class="collapse__menu">
                                <a href="order.php" class="collapse__sublink">Order Query</a><br>
                                <a href="orderunexamined.php" class="collapse__sublink">Order Unexamined</a><br>
                                <a href="orderwaitdeliver.php" class="collapse__sublink">Order Wait Deliver</a><br>
                                <a href="orderdelivered.php" class="collapse__sublink">Order Delivering</a><br>
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
        
        <h2>Wait Deliver</h2>

        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search ID">

        
        <div id="fixtable">
        <table id="myTable" width="100%">
            
        </table>
        </div>

        <!-- The Modal -->
        <div id="myModal" class="modal">

            <!-- Modal content -->
            <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Products</h3>

            <div id="form">
                <form id="order_product_form" action="" onsubmit="updateState()">
                    <input type="hidden" id="order_id" name="order_id" value="" >
                    <div id="fixtable">
                        <table class="order_product" width="100%">
                            <thead>
                                <tr class="header">
                                    <th></th>
                                    <th>Code</th>
                                    <th>Name</th>
                                    <th>Qty</th>
                                    <th>Sub Total</th>
                                    <th>Date</th>
                                    <th>Request</th>
                                    <th>State</th>
                                </tr>
                            </thead>
                            <tbody id="body_product_order"></tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <div class="col-50">
                            <label for="state">Update State</label>
                            <select name="state" id="state" class="state">
                                <option value="unexamined">Unexamined</option>
                                <option value="cancelled">Cancelled</option>
                                <option value="wait_deliver">Wait Deliver</option>
                                <option value="delivering">Delivering</option>
                                <option value="received">Received</option>
                            </select>
                        </div>
                        <div class="col-50">
                            <label>&nbsp</label>
                            <input type="submit" value="Update Status">    
                        </div>
                    </div>
                </form>
            </div>
            </div>
        
        </div>
    
        <!-- ===== IONICONS ===== -->
        <script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
        
        <!-- ===== MAIN JS ===== -->
        <script src="../assets/js/main.js"></script>
        <script src="../assets/js/categorymanage.js"></script>
        <script>
            fetch('../assets/php/api/ORDER_API.php')
            .then(res=>res.json())
            .then(data =>{
                let container = document.querySelector('#myTable');
                let temp = `
                    <tr class="header">
                        <th>Customer</th>
                        <th>Address</th>
                        <th>Contact No.</th>
                        <th>Email</th>
                        <th>Instruction</th>
                        <th>Total Qty</th>
                        <th>Total Price</th>
                        <th>State</th>
                        <th>Action</th>
                    </tr>
                `
                for(let i in data){
                    let order_id = data[i].order_id;
                    let customer = data[i].username;
                    let address = data[i].address;
                    let contact_no = data[i].contact_no;
                    let email = data[i].email;
                    let instruction = data[i].instruction;
                    let total_qty = data[i].total_qty;
                    let total_price = data[i].grand_total;
                    let state = data[i].state;
                    let action = '<span><a href="#" onClick="viewOrder('+ order_id +')">View Order</a></span>';
                    if(state === "wait_deliver"){
                        temp += `
                        <tr>
                            <td>${customer}</td>
                            <td>${address}</td>
                            <td>${contact_no}</td>
                            <td>${email}</td>
                            <td>${instruction}</td>
                            <td>${total_qty}</td>
                            <td>${total_price}</td>
                            <td>${state}</td>
                            <td>${action}</td>
                            </tr>
                        `
                    }
                }
                container.innerHTML = temp;
            })
        </script>
        <script type="text/javascript">
            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];
            // When the user clicks on <span> (x), close the modal
            span.onclick = function() {
              modal.style.display = "none";
            }
            function viewOrder(id) {
                var modal = document.getElementById("myModal");
                var order_id = document.getElementById('order_id');

                modal.style.display = "block";
                order_id.value = id;

                var url = '../assets/php/api/ORDER_PRODUCT_API.php?id=' + id
                fetch(url)
                .then(res=>res.json())
                .then(data =>{
                    var tbody = document.getElementById('body_product_order');
                    var state = document.getElementById('state');
                    tbody.innerHTML = '';
                    data.forEach(d => {
                        tbody.innerHTML += ''
                        + '<td><img class="modal-product-img" src="../assets/product_img/'+ d.product_img+ '"></td>'
                        + '<td>'+ d.product_code+ '</td>'
                        + '<td>'+ d.product_name+ '</td>'
                        + '<td>'+ d.qty+ '</td>'
                        + '<td>'+ d.sub_total+ '</td>'
                        + '<td>'+ d.date+ '</td>'
                        + '<td>'+ d.request+ '</td>'
                        + '<td>'+ d.state+ '</td>';
                        state.value = d.state
                    });
                });
            }

            function updateState() {
                var op_form = document.getElementById('order_product_form');
                var order_id = op_form.order_id;
                var state = op_form.state;
                var formData = new FormData();
                formData.append('order_id', order_id.value);
                formData.append('state', state.value);

                var request = {
                    method: 'POST',
                    body: formData
                }
                fetch('../assets/php/update_order_product.php', request)
                .then(res => res.text())
                .then(data => {
                    location.reload()
                });
            }
        </script>
    </body>
</html>