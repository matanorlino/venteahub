<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- ===== CSS ===== -->
    <link rel="stylesheet" href="assets/css/login.css">
    
    <title>Login</title>
</head>
<body>
    <div class="form-box">
        <div class="button-box">
            <div id="btn"></div>
            <button type="button" class="toggle-btn" onclick="login()">Log In</button>
            <button type="button" class="toggle-btn" onclick="register()">Register</button>
        </div>
        <!-- LOGIN FORM -->
        <form id="login" action="#" class="input-group" onsubmit="auth()">
            <h2>Login</h2><br>
            <input type="text" class="input-field username" placeholder="User Name" required>
            <input type="password" class="input-field password" placeholder="User Password" required>
            <button type="submit" class="submit-btn">Login</button>
        </form>
        <!-- REGISTRATION FORM -->
        <form id="register" action="#" class="input-group" onsubmit="reg()">
            <h2>Register</h2><br>
            <input type="text" class="input-field reg_user" placeholder="User Name" required>
            <input type="email" class="input-field reg_email" placeholder="User Email" required>
            <input type="password" class="input-field reg_pass" placeholder="User Password" required>
            <input type="password" class="input-field reg_repass" placeholder="Retype Password" required>
            <button type="submit" class="submit-btn">Register</button>
        </form>
    </div>
    <script src='assets/js/create_account.js'></script>
    <script src='assets/js/authentication.js'></script>
    <script>
        function dq(e){
            return document.querySelector(e);
        }

        function dqAll(e){
            return document.querySelectorAll(e);
        }

        dqAll('form').forEach((el)=>{
            el.addEventListener('submit',e => e.preventDefault());
        });

        function auth(){
            if(dq('.username').value.length != 0 && dq('.password').value.length != 0){
                const a = new login_(dq('.username').value,dq('.password').value)
                a.valid();
            }
            else{
                alert('Missing Fields')
            }
        }

        function reg(){
            if(dq('.reg_pass').value == dq('.reg_repass').value){
                const a = new register_acc(
                    dq('.reg_user').value,
                    dq('.reg_pass').value,
                    dq('.reg_email').value,
                    "admin"
                    )
                a.exist();
            }
            else{
                alert("PASSWORD NOT MATCH")
            }
        }
        var loginform = document.getElementById("login")
        var registerform = document.getElementById("register")
        var selectbtn = document.getElementById("btn")

        function register(){
            loginform.style.left = "-400px";
            registerform.style.left = "50px";
            selectbtn.style.left = "110px";
        }
        function login(){
            loginform.style.left = "50px";
            registerform.style.left = "450px";
            selectbtn.style.left = "0";
        }
    </script>
    
</body>
</html>