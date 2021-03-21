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
    <body id="body-pd" onload="get_products()">
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
        
        <h2>Product Manage</h2>

        <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search ID">
        
        <div id="fixtable">
        <table id="myTable" width="100%">
        
        </table>
        </div>
        <br>
        <input type="button" id="myBtn" value="Add Product">

         <!-- The Modal -->
         <div id="myModal" class="modal">

            <!-- Modal content -->
            <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Add Product</h3>

            <div id="form">
            <form action="" onsubmit="add_product()">
                <label for="pname">Product Name</label>
                <input type="text" id="pname" class="add_pname" name="pname" placeholder="Enter Product Name" required>

                <label for="ptype">Product Type</label>
                <select id="ptype" class="add_category categories_" name="ptype ">
                    <!-- function -->
                </select>

                <label for="mprice">Market Price</label>
                <input type="text" id="mprice" class="add_marketprice" name="mprice" placeholder="Enter Market Price" required>

                <label for="sprice">Sell Price</label>
                <input type="text" id="sprice" class="add_sellprice" name="sprice" placeholder="Enter Sell Price" required>
                
                <label for="pcode">Product Code</label>
                <input type="text" id="pcode" class="add_code" name="pcode" placeholder="Enter Product Code" required><br><br>
                
                <label for="myfile">Select Image</label><br>
                <input type="file" id="myfile" class="add_img" name="myfile" required><br><br>

                <label for="model">Model</label>
                <input type="text" id="model" class="add_model" name="model" placeholder="Enter Model" required>

                <label for="purchasedesc">Purchase Description</label>
                <input type="text" id="purchasedesc" class="add_purchDesc" name="purchasedesc" required placeholder="Enter Purchase Description">

                <label for="pdescription">Product Description</label>
                <input type="text" id="pdescription" class="add_prodDesc" name="pdescription" required placeholder="Enter Product Description">
            
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
                        <label for="pname">Product Name</label>
                        <input type="text" id="pname" class="m_pname" name="pname" placeholder="Enter Product Name" required>

                        <label for="ptype">Product Type</label>
                        <select id="ptype" class="m_category categories_" name="ptype ">
                            <!-- function -->
                        </select>

                        <label for="mprice">Market Price</label>
                        <input type="text" id="mprice" class="m_marketprice" name="mprice" placeholder="Enter Market Price" required>

                        <label for="sprice">Sell Price</label>
                        <input type="text" id="sprice" class="m_sellprice" name="sprice" placeholder="Enter Sell Price" required>
                        
                        <label for="pcode">Product Code</label>
                        <input type="text" id="pcode" class="m_code" name="pcode" placeholder="Enter Product Code" required><br><br>
                        
                        <label for="myfile">Select Image</label><br>
                        <input type="file" id="myfile" class="m_img" name="myfile" ><br><br>

                        <label for="model">Model</label>
                        <input type="text" id="model" class="m_model" name="model" placeholder="Enter Model" required>

                        <label for="status">status</label>
                        <select name="" id="status" class="m_status">
                            <option value="notSale">Not Sale</option>
                            <option value="sale">Sale</option>
                        </select>


                        <label for="purchasedesc">Purchase Description</label>
                        <input type="text" id="purchasedesc" class="m_purchDesc" name="purchasedesc" required placeholder="Enter Purchase Description">

                        <label for="pdescription">Product Description</label>
                        <input type="text" id="pdescription" class="m_prodDesc" name="pdescription" required placeholder="Enter Product Description">
                    
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
        <script src="../assets/js/product_action.js"></script>
        <script src="../assets/js/categorymanage.js"></script>
        <script>
            _ = (e)=>{return document.querySelector(e)}
            _file= (el)=> { return document.querySelector(el).files[0]}
            _all = (e) => {return document.querySelectorAll(e)}

            _all('form').forEach((each)=> each.addEventListener('submit', e => e.preventDefault()))

            function add_product(){
                let a = new product(
                    _('.add_category').value,
                    _('.add_marketprice').value,
                    _('.add_sellprice').value,
                    _('.add_code').value,
                    _file('.add_img'),
                    _('.add_model').value,
                    _('.add_purchDesc').value,
                    _('.add_prodDesc').value,
                    _('.add_pname').value
                    )
                a.modify('add');
                _('.add_category').value = "";
                _('.add_marketprice').value = "";
                _('.add_sellprice').value = "";
                _('.add_code').value = "";
                _('.add_model').value = "";
                _('.add_purchDesc').value = "";
                _('.add_prodDesc').value = "";
                _('.add_pname').value = "";
            }
        </script>
        <script src="../assets/js/main.js"></script>
        <script src="../assets/js/categorymanage.js"></script>
        <script>
            function get_categories(){
                async function selectAll(){
                    const url = await fetch('../assets/php/api/CATEGORY_API.php')
                    return url.json()
                }
                selectAll().then(data=>{
                    const container = document.querySelectorAll('.categories_');
                    let storage = '';
                    for(let i in data){
                        let id = data[i].category_id;
                        let name = data[i].category_name;

                        storage += `
                            <option class="o_cat" value='${id}'>${name}</option>
                        `;
                    }

                    container.forEach(each=>{
                        each.innerHTML = storage
                    })
                })
            }
            get_categories();
        </script>
        <script>
            dq = (e)=>{return document.querySelector(e)}

            function showmodify(uniqueIdOfTheData){
                dq('.modify-form').style.display = 'block';

                fetch('../assets/php/api/PRODUCT_API.php')
                .then(response => response.json())
                .then(data => {
                    for(let i in data){
                        if(data[i].product_id == uniqueIdOfTheData){

                            let options_ = document.querySelectorAll('o_cat');
                            for(let x = 0 ;x < options_.length ; x++){
                                if(options_.innerHTML == data[i].category_name){
                                    _('.m_category').selectedIndex = x;
                                }
                            }
                            _('.m_marketprice').value = data[i].market_price
                            _('.m_sellprice').value = data[i].sell_price
                            _('.m_code').value = data[i].product_code
                            _('.m_model').value = data[i].model
                            _('.m_purchDesc').value = data[i].purchase_description
                            _('.m_prodDesc').value = data[i].product_description
                            _('.m_pname').value = data[i].product_name

                            let option_stats = document.querySelectorAll('.m_status option');
                            for(let x = 0 ;x < option_stats.length ; x++){
                                if(option_stats.innerHTML == data[i].status){
                                    _('.m_status').selectedIndex = x;
                                }
                                else{
                                    _('.m_status').selectedIndex = 0;
                                }
                            }

                            // ACTION SECTION
                            dq('.update-btn').addEventListener('click',(e)=>{
                                dq('.m_f').onsubmit = function(_f_){
                                    _f_.preventDefault();
                                    let a = new product(
                                        _('.m_category').value,
                                        _('.m_marketprice').value,
                                        _('.m_sellprice').value,
                                        _('.m_code').value,
                                        _file('.m_img'),
                                        _('.m_model').value,
                                        _('.m_purchDesc').value,
                                        _('.m_prodDesc').value,
                                        _('.m_pname').value,
                                        data[i].product_id,
                                        _('.m_status').value
                                        )
                                    a.modify("update");
                            
                                    _('.m_marketprice').value = ""
                                    _('.m_sellprice').value = ""
                                    _('.m_code').value = ""
                                    _('.m_model').value = ""
                                    _('.m_purchDesc').value = ""
                                    _('.m_prodDesc').value = ""
                                    _('.m_pname').value = ""

                                    hidemodify();
                                }
                            })
                            dq('.delete-btn').addEventListener('click',(e)=>{
                                dq('.m_f').onsubmit = function(_f_){
                                    _f_.preventDefault();
                                    let a = new product("_","_","_","_","_","_","_","_","_",data[i].product_id)
                                    a.modify("delete");
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