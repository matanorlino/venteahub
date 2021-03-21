class register_acc{
    constructor(
        username,
        password,
        email,
        accesslevel
    ){
        this.username = username
        this.password = password
        this.email = email
        this.accesslevel = accesslevel
    }
    saved(){
        const url = 'assets/php/register.php';

        let f = new FormData();

        f.append("username",this.username)
        f.append("password",this.password)
        f.append("email",this.email)
        f.append("accesslevel",this.accesslevel)

        fetch(url,{
            method:'POST',
            body:f
        })
        .then(response => response.text())
        .then(data=>{
            if(data == "registered"){
                alert("REGISTERED SUCCESSFULLY")
                location.href='index.php'
            }
            else{
                alert(data)
            }
        })
    }

    exist(){
        const url_ = 'assets/php/user_exist.php';

        let form = new FormData();

        form.append("username",this.username)

        fetch(url_,{
            method:'POST',
            body:form
        })
        .then(response => response.text())
        .then(data=>{
            if(data!="1"){
                this.saved()
            }
            else{
                alert(data)
            }
        })
    }
}