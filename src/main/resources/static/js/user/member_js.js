// 회원가입 START
function createAccountForm() {
    console.log('createAccountForm() CALLED!!');

    var patternPhone = /01[016789]-[^0][0-9]{2,3}-[0-9]{3,4}/;
    var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    let form = document.create_account_form;
    if (form.u_m_id.value == '') {
        alert('아이디를 입력하세요.');
        form.u_m_id.focus();

    } else if (form.u_m_pw.value == '') {
        alert('비밀번호를 입력하세요.');
        form.u_m_pw.focus();

    }  else if (form.u_m_pw_again.value == '') {
        alert('비밀번호 확인란을 입력하세요.');
        form.u_m_pw_again.focus();

    } else if (form.u_m_name.value == '') {
        alert('이름을 입력하세요.');
        form.u_m_name.focus();

    }  else if (form.u_m_mail.value == '') {
        alert('메일을 입력하세요.');
        form.u_m_mail.focus();

    }  else if (!regExpEmail.test(form.u_m_mail.value)) {
        alert('메일을 형식이 틀립니다..');
        form.u_m_mail.focus();

    }  else if (form.u_m_phone.value == '') {
        alert('연락처를 입력하세요.');
        form.u_m_phone.focus();

    } else if (!patternPhone.test(form.u_m_phone.value)) {
        alert('연락처를 형식이 틀립니다.');
        form.u_m_phone.focus();

    }   else if (form.u_m_pw_again.value != form.u_m_pw.value) {
        alert('비밀번호가 일치하지 않습니다. \n다시 확인해주세요.');
        form.u_m_pw_again.focus();

    } else {
        form.submit();

    }

}
// 회원가입 END

// 로그인 START
function memberLoginForm() {
    console.log('memberLoginForm() CALLED!!');

    let form = document.member_login_form;
    if (form.u_m_id.value == '') {
        alert('INPUT ID');
        form.u_m_id.focus();

    } else if (form.u_m_pw.value == '') {
        alert('INPUT PW');
        form.u_m_pw.focus();

    } else {
        form.submit();

    }

}
// 로그인 END

// 수정 - 계정 정보(비밀번호 제외) START
function memberModifyForm() {
    console.log('memberModifyForm() CALLED!!');

    var patternPhone = /01[016789]-[^0][0-9]{2,3}-[0-9]{3,4}/;
    var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;


    let form = document.member_modify_form;

    if (form.u_m_name.value == '') {
        alert('이름을 입력하세요.');
        form.u_m_name.focus();

    }  else if (form.u_m_mail.value == '') {
        alert('이메일을 입력하세요.');
        form.u_m_mail.focus();

    } else if (!regExpEmail.test(form.u_m_mail.value)) {
        alert('메일을 형식이 틀립니다..');
        form.u_m_mail.focus();

    } else if (form.u_m_phone.value == '') {
        alert('연락처를 입력하세요.');
        form.u_m_phone.focus();

    } else if (!patternPhone.test(form.u_m_phone.value)) {
        alert('연락처를 형식이 틀립니다.');
        form.u_m_phone.focus();

    }else {
        form.submit();

    }

}
// 수정 - 계정 정보(비밀번호 제외)  END

// 수정 - 비밀번호 START
function memberPasswordModifyForm() {
    console.log('memberPasswordModifyForm() CALLED!!');

    let form = document.member_password_modify_form;
    if (form.u_m_pw.value == '') {
        alert('기존 비밀번호가 필수값 입니다.');
        form.u_m_pw.focus();

    } else if (form.u_m_Re_pw.value == '') {
        alert('변경 비밀번호가 필수값 입니다.');
        form.u_m_Re_pw.focus();

    } else if (form.u_m_Re_pw.value != form.u_m_Re_pw_confirm.value) {
        alert('변경 후 비밀번호 재입력이 틀렸습니다.');
        form.u_m_Re_pw_confirm.focus();

    }   else {
        form.submit();

    }

}
// 수정 - 비밀번호  END

// 삭제 확인 START
function AlertDeleteConfirm() {
    console.log('AlertDeleteConfirm() CALLED!!');
    if (confirm("계정을 삭제하시겠습니까?")) {
        window.location.href = "/user/member/member_delete_confirm";
    }

}
// 삭제 확인 END
