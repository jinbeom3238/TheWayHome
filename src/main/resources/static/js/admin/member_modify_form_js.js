function memberModifyForm() {
    console.log('modifyForm() CALLED!!');

    let form = document.member_modify_form;
    if (form.a_m_id.value == '') {
        alert('아이디를 입력해주세요.');
        form.a_m_id.focus();

    } else if (form.a_m_pw.value == '' && loginedAdminMemberDto.getA_m_pw == form.a_m_pw.value) {
        alert('비밀번호를 입력해주세요.');
        form.a_m_pw.focus();

    } else {
        form.submit();

    }

}