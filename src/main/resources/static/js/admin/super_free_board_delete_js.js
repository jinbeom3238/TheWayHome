// 실종/목격 게시판 - 삭제 확인 START
function superDeleteFreeBoardConfirm(no) {
    console.log('superDeleteFreeBoardConfirm() CALLED!!');

    let fb_no = no;
    if (confirm("게시물을 삭제하시겠습니까?")) {
        window.location.href = "/admin/free/board/super_free_board_delete_confirm?fb_no=" + fb_no;
    }

}
// 실종/목격 게시판 - 삭제 확인 END

