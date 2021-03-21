class driver{
    constructor(
        name,
        email,
        phone,
        id
    ){
        this.name = name ? name : null;
        this.email = email ? email : null;
        this.phone = phone ? phone : null;
        this.id = id ? id : null; //maglalagay lang ng id kapag mag update/delete
                                    //by default null.
    }

    modify(e){
        const url = '../assets/php/'+e+'_driver.php';

        let form = new FormData();

        form.append("driver_id",this.id);
        form.append("driver_name",this.name);
        form.append("driver_email",this.email);
        form.append("driver_phone",this.phone);

        fetch(url,{
            method : 'POST',
            body : form
        })
        .then(response => response.text())
        .then(data => {
            if(data == "updated"){
                alert('DRIVER PERSONEL UPDATED');
                get_driver();
            }
            else if (data == "deleted") {
                alert('DRIVER PERSONEL IS DELETED!');
                get_driver();
            }
            else if(data == "added"){
                alert('NEW DRIVER PERSONEL IS ADDED');
                get_driver();
            }
            else{
                alert(data);
            }
        })
        .catch(e => console.log(e));
    }
}