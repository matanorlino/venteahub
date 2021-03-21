//------------------------------------------------------------//
// NOTE : MAGLALAGAY KALANG NG ID / STATUS PARAMETER KAPAG   //
//        MAG UPDATE OR DELETE LANG...                       //
//-----------------------------------------------------------//
//.modify('update') kung mag uupdate ka ng product           //
//.modify('delete') kung mag dedelete ka ng product          //
//.modify('add') kung mag dedelete ka ng product             //
//-----------------------------------------------------------//
class product{
    constructor(
        product_category_id,
        market_price,
        sell_price,
        product_code,
        product_img,
        model,
        purchase_description,
        product_description,
        product_name,
        product_id,
        status
    ){
        this.product_category_id = product_category_id ? product_category_id:null
        this.market_price = market_price ? market_price:null
        this.sell_price = sell_price ? sell_price:null
        this.product_code = product_code ? product_code:null
        this.product_img = product_img ? product_img:null
        this.model = model ? model:null
        this.product_name = product_name ? product_name : null
        this.purchase_description = purchase_description ? purchase_description:null
        this.product_description = product_description ? product_description:null
        this.product_id = product_id ? product_id : null; //maglalagay lang ng id kapag mag update/delete
                                                          //by default null.
        this.status = status ? status : null; //maglalagay lang ng status kapag mag update/delete
                                              //by default null.
    }

    modify(e){
        const url = '../assets/php/'+e+'_product.php';

        let form = new FormData();
    
        form.append("product_category_id",this.product_category_id)
        form.append("market_price",this.market_price)
        form.append("sell_price",this.sell_price)
        form.append("product_code",this.product_code)
        form.append("product_img",this.product_img)
        form.append("model",this.model)
        form.append("purchase_description",this.purchase_description)
        form.append("product_description",this.product_description)
        form.append("product_name",this.product_name)
        form.append("product_id",this.product_id)
        form.append("status",this.status)

        fetch(url,{
            method : 'POST',
            body : form
        })
        .then(response => response.text())
        .then(data => {
            if(data == "updated"){
                alert('product UPDATED');
                get_products();
            }
            else if (data == "deleted") {
                alert('product IS DELETED!')
                get_products();
            }
            else if(data == "added"){
                alert('NEW product IS ADDED')
                get_products();
            }
            else{
                alert('PRODUCT UPDATED');
                get_products();
            }
        })
        .catch(e => console.log(e));
    }
}

function get_products(){
    async function selectAll(){
        const url = await fetch('../assets/php/api/PRODUCT_API.php')
        return url.json()
    }
    selectAll().then(data=>{
        const container = document.querySelector('#myTable');
        let storage = '<tr class="header"><th>Product ID</th><th>Product Code</th><th>Modify</th><th>Product Name</th><th>Category Belong</th><th>Sell Price</th><th>Status</th><th>Image</th></tr>'
        for(let i in data){

            let product_id = data[i].product_id;
            let product_code = data[i].product_code;
            let product_name = data[i].product_name ;
            let product_category = data[i].product_category;
            let market_price = data[i].market_price;
            let status = data[i].status;
            let product_img = data[i].product_img;

            storage += `
                <tr>
                    <td>${product_id}</td>
                    <td>${product_code}</td>
                    <td>
                        <ion-icon name="create-outline" class="nav__icon" id="edit" type="button" onclick='showmodify("${product_id}")'></ion-icon>
                    </td>
                    <td>${product_name}</td>
                    <td>${product_category}</td>
                    <td>${market_price}</td>
                    <td>${status}</td>
                    <td>${product_img}</td>
                </tr>

            `;
        }
        container.innerHTML = storage;
    })
}