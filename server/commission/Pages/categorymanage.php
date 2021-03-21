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
    <body id="body-pd" onload="get_categories()">
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
                        <div class="nav__link collapse active">
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
        
        <h2>Category Manage</h2>
        
        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search ID">
        
        <div id="fixtable"> 
        <table id="myTable" width="100%">
  
        </table>
        </div>
        <br>
        <input type="button" id="myBtn" value="Add Category">

        <!-- The Modal -->
        <div id="myModal" class="modal">

            <!-- Modal content -->
            <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Add Category</h3>

            <div id="form">
            <form action="" onsubmit="add_category()">
                <label for="name">Type Name</label>
                <input type="text" id="name" name="" class="category_name" placeholder="Enter Name">

                <label for="lname">Note</label>
                <input type="text" id="note" name="" class="category_note" placeholder="Enter Note">
            
                <input type="submit" value="Submit">
            </form>
            </div>
        </div>
        
        </div>

        
        <div class="modify-form">
            <div class="modify-container">
                <span>
                    <h3>Modify</h3>

                    <span class="close_modify" onclick="hidemodify()">&times;</span>
                </span>

                <div id="form" class='m_f'>
                    <form action="" onsubmit="">
                        <label for="name">Type Name</label>
                        <input type="text" id="name" name="" class="m_category_name" placeholder="Enter Name">

                        <label for="lname">Note</label>
                        <input type="text" id="note" name="" class="m_category_note" placeholder="Enter Note">
                    
                        <span class="btn-actions">
                            <input type="submit" value="UPDATE" class="update-btn">
                            <input type="submit" value="DELETE" class="delete-btn">
                        </span>
                    </form>
                </div>
            </div>
        </div>

        <!-- ===== IONICONS ===== -->
        <script src="https://unpkg.com/ionicons@5.1.2/dist/ionicons.js"></script>
        
        <!-- ===== MAIN JS ===== -->
        <script src="../assets/js/category_action.js"></script>
        <script src="../assets/js/main.js"></script>
        <script src="../assets/js/categorymanage.js"></script>
        
        <script>
            dq = (e)=>{return document.querySelector(e)}

            function showmodify(uniqueIdOfTheData){
                dq('.modify-form').style.display = 'block';

                fetch('../assets/php/api/CATEGORY_API.php')
                .then(response => response.json())
                .then(data => {
                    for(let i in data){
                        if(data[i].category_id == uniqueIdOfTheData){

                            dq('.m_category_name').value = data[i].category_name;
                            dq('.m_category_note').value = data[i].category_note;


                            // ACTION SECTION
                            dq('.update-btn').addEventListener('click',(e)=>{
                                dq('.m_f').onsubmit = function(_f_){
                                    _f_.preventDefault();
                                    let a = new category(
                                        dq('.m_category_name').value,
                                        dq('.m_category_note').value,
                                        data[i].category_id
                                        )
                                    a.modify("update");
                                }
                            })
                            dq('.delete-btn').addEventListener('click',(e)=>{
                                dq('.m_f').onsubmit = function(_f_){
                                    _f_.preventDefault();
                                    let a = new category(
                                        dq('.m_category_name').value,
                                        dq('.m_category_note').value,
                                        data[i].category_id
                                        )
                                    a.modify("delete");
                                    dq('.m_category_name').value = ""
                                    dq('.m_category_note').value = ""
                                    hidemodify();
                                }
                            })
                        }
                    }
                })
            }
        </script>
    </body>
</html>