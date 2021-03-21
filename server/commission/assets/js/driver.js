dq = (e) => {return document.querySelector(e)}
function get_driver(){
    fetch('../assets/php/api/DRIVER_API.php')
        .then(response => response.json())
        .then(data =>{
            let container = dq('#myTable');
            let temp = `<tr class="header">
                            <th>ID</th>
                            <th>Modify</th>
                            <th>Complete name</th>
                            <th>Email</th>
                            <th>Phone</th>
                        </tr>`
            for(let i in data){
                let id = data[i].driver_id;
                let name = data[i].driver_name;
                let phone = data[i].driver_phone;
                let email = data[i].driver_email;
                
                temp += `
                    <tr>
                        <td>${id}</td>
                        <td><ion-icon name="create-outline" class="nav__icon" id="edit" type="button" onclick='showmodify("${id}")'></ion-icon></td>
                        <td>${name}</td>
                        <td>${email}</td>
                        <td>${phone}</td>
                    </tr>
                `
            }
            container.innerHTML = temp;
        })
}

get_driver();