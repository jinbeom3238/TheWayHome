function searchBoxPetConfirmAdmin() {
    console.log('searchBoxPetConfirmAdmin() CALLED!!');

    var searchOption = document.getElementById("search_option").value;
    var sNameInput = document.getElementById("search").value;
    var s_no = document.getElementById("s_no").value;

    location.href= "/admin/pets/pets_list?s_no="+s_no+"&searchOption="+searchOption+"&sNameInput="+sNameInput;

}