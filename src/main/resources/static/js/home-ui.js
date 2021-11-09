var elems = document.querySelectorAll("select");
var instances = M.FormSelect.init(elems);


const startFastingBtn = document.getElementById("start-fasting-btn");
const startFastingForm = document.getElementById("start-fasting-form");

startFastingBtn.onclick = function () {
  if(instances[0].getSelectedValues().filter(Boolean).length){
    startFastingForm.submit();
  }else{
    M.toast({text: 'Please, select a fasting length'});
  }
};

function endFast() {
  Swal.fire({
    icon: 'info',
    title: "Do you want to save this fast?",
    showDenyButton: true,
    showCancelButton: true,
    confirmButtonText: "Save",
    denyButtonText: `Don't save`,
  }).then((result) => {
    /* Read more about isConfirmed, isDenied below */
    if (result.isConfirmed) {
      location.href = location.origin + '/finish?save=true';
    } else if (result.isDenied) {
      location.href = location.origin + '/finish?save=false';
    }
  });
}

function confirmDeleteFast(event){
  event.preventDefault();
  Swal.fire({
    title: 'Are you sure?',
    text: "You won't be able to revert this!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, delete it!'
  }).then((result) => {
    if (result.isConfirmed) {
      location.href = event.target.href;
    }
  })
}

const deleteLinks = document.querySelectorAll('.delete-link');
for(const deleteLink of deleteLinks){
  deleteLink.addEventListener("click", confirmDeleteFast);
}

const fastDateItems = document.querySelectorAll(".localize-me");
for(const dateItem of fastDateItems){
  dateItem.innerText = new Date(dateItem.innerText).toLocaleString();
}