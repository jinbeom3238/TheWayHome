function searchFreeBoardConfirm() {
    console.log('searchFreeBoardConfirm() CALLED!!');


    var searchOption = document.getElementById("searchFreeboardOption").value;
    var sNameInput = document.getElementById("searchFreeboardInput").value;

    location.href= "/user/board/free_board_list?searchOption="+searchOption+"&sNameInput="+sNameInput;

}