function createAccountForm() {
    console.log('adminCreateAccountForm() CALLED!!');

    let form = document.create_account_form;
    if (form.a_m_id.value == '') {
        alert('INPUT ID');
        form.a_m_id.focus();

    } else if (form.a_m_pw.value == '') {
        alert('INPUT PW');
        form.a_m_pw.focus();

    } else if (form.s_no.value == '') {
        alert('INPUT SHELTER NUMBER');
        form.s_no.focus();

    } else if (form.s_name.value == '') {
        alert('INPUT SHELTER NAME');
        form.s_name.focus();

    } else if (form.s_address.value == '') {
        alert('INPUT SHELTER ADDRESS');
        form.s_address.focus();

    } else if (form.s_phone.value == '') {
        alert('INPUT SHELTER ADDRESS');
        form.s_phone.focus();

    } else {
        form.submit();
    }

}