// 수정 - 계정 정보(비밀번호 제외) START
function memberModifyForm() {
    console.log('memberModifyForm() CALLED!!');

    let form = document.member_modify_form;
    if (form.a_m_id.value == '') {
        alert('아이디를 입력해주세요.');
        form.a_m_id.focus();

    } else if (form.a_m_pw.value == '' && loginedAdminMemberDto.getA_m_pw == form.a_m_pw.value) {
        alert('비밀번호를 입력해주세요.');
        form.a_m_pw.focus();

    } else if (form.s_phone.value == ''){
        alert('INPUT PHONE');
        form.s_phone.focus();

    } else {
        form.submit();
        alert('수정이 완료되었습니다.');

    }

}
// 수정 - 계정 정보(비밀번호 제외)  END

// 수정 - 비밀번호 START
function adminPasswordModifyForm() {
    console.log('adminPasswordModifyForm() CALLED!!');

    let form = document.admin_password_modify_form;
    if (form.a_m_pw.value == '') {
        alert('기존 비밀번호가 필수값 입니다.');
        form.a_m_pw.focus();

    } else if (form.a_m_Re_pw.value == '') {
        alert('변경 비밀번호가 필수값 입니다.');
        form.a_m_Re_pw.focus();

    } else if (form.a_m_Re_pw.value != form.a_m_Re_pw_confirm.value) {
        alert('변경 후 비밀번호 재입력이 틀렸습니다.');
        form.a_m_Re_pw_confirm.focus();

    }   else {
        alert('비밀번호가 변경되었습니다.');
        form.submit();

    }

}
// 수정 - 비밀번호  END


