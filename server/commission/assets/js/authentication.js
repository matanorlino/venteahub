class login_{
    constructor(
        username,
        password
    ){
        this.username = username
        this.password = password
    }

    valid(){    
        const url = 'assets/php/login.php';
        let f = new FormData();

        f.append("username",this.username)
        f.append("password",this.password)

        fetch(url,{
            method:'POST',
            body:f
        })
        .then(response => response.text())
        .then(data => {
            if(data == "admin"){
                location.href='Pages/index.php'
            }
            else{
                alert('INVALID ACCOUNT')
            }
        })
        .catch(e => console.log(e));
    }
}