function writeCommentForm(editor) {
    console.log('writeCommentForm() CALLED!!');

    let form = document.write_comment_form;
    if (form.c_content.value == '') {
        alert('작성한 댓글이 없습니다.');
        form.c_content.focus();

    } else {
        form.submit();

    }

}