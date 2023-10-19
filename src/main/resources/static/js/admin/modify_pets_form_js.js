function modifyPetsForm() {
    console.log('modifyPetsForm() CALLED!!');

    let form = document.admin_pets_modify_form;
    let userConfirmed = confirm('정말 수정하시겠습니까?');
    if(userConfirmed){
        let form = document.getElementById("admin_pets_modify_form");

        form.submit();
    }

}