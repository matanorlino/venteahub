//function dq ginawa ko lang to para d na mag document.querySelector , nakakatamad e haha
function dq(el){
  return document.querySelector(el);
}
// disable forms ajax purposes
dq('form').addEventListener('submit',e => e.preventDefault());

//form validation
function add_category(){    
  if(dq('.category_name').value.length != 0 && dq('.category_note').value.length != 0){
    let a = new category(
          dq('.category_name').value,
          dq('.category_note').value,
          );

    a.modify('add');
    //clean input's values
    dq('.category_name').value = "";
    dq('.category_note').value = "";

  }else{
    alert('Missing fields');
  }
}

function myFunction() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");
  
    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[0];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
  }

var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
btn.onclick = function() {
  modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}


dq = (e)=>{return document.querySelector(e)}

function hidemodify(){
  dq('.modify-form').style.display = 'none';
}
