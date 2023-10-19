// 실종/목격 게시판 - 삭제 확인 START
function superDeleteReviewBoardConfirm(no) {
    console.log('superDeleteReviewBoardConfirm() CALLED!!');

    let r_b_no = no;
    if (confirm("게시물을 삭제하시겠습니까?")) {
        window.location.href = "/admin/review/board/super_review_board_delete_confirm?fb_no=" + r_b_no;
    }

}
// 실종/목격 게시판 - 삭제 확인 END

