//---------------------------------------------------//
// NOTE : MAGLALAGAY KALANG NG 3RD PARAMETER KAPAG   //
//        MAG UPDATE OR DELETE LANG...               //
//---------------------------------------------------//
//.modify('update') kung mag uupdate ka ng category  //
//.modify('delete') kung mag dedelete ka ng category //
//.modify('add') kung mag dedelete ka ng category    //
//---------------------------------------------------//
class category{
    constructor(
        name,
        note,
        id
    ){
        this.name = name ? name : null;
        this.note = note ? note : null;
        this.id = id ? id : null; //maglalagay lang ng id kapag mag update/delete
                                    //by default null.
    }

    modify(e){
        const url = '../assets/php/'+e+'_category.php';

        let form = new FormData();

        form.append("category_id",this.id)
        form.append("category_name",this.name);
        form.append("category_note",this.note);

        fetch(url,{
            method : 'POST',
            body : form
        })
        .then(response => response.text())
        .then(data => {
            if(data == "updated"){
                alert('CATEGORY UPDATED');
                get_categories();
            }
            else if (data == "deleted") {
                alert('CATEGORY IS DELETED!');
                get_categories();
            }
            else if(data == "added"){
                alert('NEW CATEGORY IS ADDED');
                get_categories();
            }
            else{
                alert(data);
            }
        })
        .catch(e => console.log(e));
    }
}

function get_categories(){
    async function selectAll(){
        const url = await fetch('../assets/php/api/CATEGORY_API.php')
        return url.json()
    }
    selectAll().then(data=>{
        const container = document.querySelector('#myTable');
        let storage = '<tr class="header"><th>ID</th><th>Modify</th><th>Category Name</th><th>Note</th></tr>'
        for(let i in data){

            let id = data[i].category_id;
            let name = data[i].category_name;
            let note = data[i].category_note;

            storage += `
                <tr>
                <td>${id}</td>
                <td>
                    <ion-icon name="create-outline" class="nav__icon" id="edit" type="button" onclick='showmodify("${id}")'></ion-icon>
                </td>
                <td>${name}</td>
                <td>${note}</td>
                </tr>

            `;
        }
        container.innerHTML = storage;
    })
}