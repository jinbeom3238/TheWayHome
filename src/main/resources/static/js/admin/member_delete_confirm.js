function deleteConfirm() {
    console.log('deleteConfirm() CALLED!!');

    var result = confirm("계정을 삭제하시겠습니까?");

    if(result) {
        alert("삭제가 완료되었습니다.");
        location.href="/admin/member/member_delete_confirm";
    } else {
        alert("취소되었습니다.");
    }

}