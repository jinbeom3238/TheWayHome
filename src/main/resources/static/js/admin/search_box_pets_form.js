function searchBoxPetConfirm() {
    console.log('searchBoxPetConfirm() CALLED!!');

    var searchOption = document.getElementById("search_option").value;
     var sNameInput = document.getElementById("search").value;
     // \var s_no = document.getElementById("s_no").value;
     var s_no = $('div.card-body input[name="s_no"]').val();
    console.log("searchOption: " + searchOption);
    console.log("sNameInput: "  + sNameInput);
    console.log("s_no: " + s_no);

    location.href= "/user/pets/pets_list?s_no="+s_no+"&searchOption="+searchOption+"&sNameInput="+sNameInput;


}