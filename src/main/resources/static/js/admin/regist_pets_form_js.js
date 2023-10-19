function registPetsForm() {
    console.log('registPetsForm() CALLED!!');

    let form = document.admin_pets_regist_form;
    if (form.an_no.value == '') {
        alert('유기번호를 입력해주세요.');
        form.an_no.focus();

    } else if (form.an_image.value == '') {
        alert('사진 파일을 첨부해주세요.');
        form.an_image.focus();

    } else if (form.an_happen_date.value == '') {
        alert('발견 날짜를 정해주세요.');
        form.an_happen_date.focus();

    } else if (form.an_happen_place.value == '') {
        alert('발견 장소를 입력해주세요.');
        form.an_happen_place.focus();

    } else if (form.an_an_kind.value == '') {
        alert('축종을 선택해주세요.');
        form.an_an_kind.focus();

    } else if (form.an_k_kind.value == '') {
        alert('품종 ex) 포메라니안 형식으로 입력해주세요.');
        form.an_k_kind.focus();

    } else if (form.an_color.value == '') {
        alert('색상 ex) 흰색 + 갈색 형식으로 입력해주세요.');
        form.an_color.focus();

    } else if (form.an_age.value == '') {
        alert('나이 ex) 2023(년생) 형식으로 입력해주세요.');
        form.an_age.focus();

    } else if (form.an_n_start.value == '') {
        alert('공고 시작일을 정해주세요.');
        form.an_n_start.focus();

    } else if (form.an_n_end.value == '') {
        alert('공고 마감일을 정해주세요.');
        form.an_n_end.focus();

    } else if (form.an_p_s_state.value == '') {
        alert('상태를 골라주세요.');
        form.an_p_s_state.focus();

    } else if (form.an_g_gender.value == '') {
        alert('성별을 정해주세요.');
        form.an_g_gender.focus();

    } else if (form.an_special_mark.value == '') {
        alert('특징을 입력해주세요.');
        form.an_special_mark.focus();

    } else if (form.s_name.value == '') {
        alert('보호소 명을 입력해주세요.');
        form.s_name.focus();

    } else if (form.s_phone.value == '') {
        alert('보호소 전화번호를 입력해주세요.');
        form.s_phone.focus();

    } else if (form.s_address.value == '') {
        alert('보호소 주소를 입력해주세요.');
        form.s_address.focus();

    } else {

        let userConfirmed = confirm('정말 등록하시겠습니까?');
        if(userConfirmed){
            let form = document.getElementById("admin_pets_regist_form");

            form.submit();
        }

    }

}