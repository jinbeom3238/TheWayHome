function searchBoxShelterConfirm() {
    console.log('searchBoxShelterConfirm() CALLED!!');

    var searchOption = document.getElementById("search_option").value;
    var sNameInput = document.getElementById("s_name").value;

    location.href= "/user/pets/shelter_list?searchOption="+searchOption+"&sNameInput="+sNameInput;


}