
function adoptPetsConfirm() {
    console.log('adoptPetsConfirm() CALLED!!');
    let s_name  = $('#s_name').text();
    // let s_name = document.getElementById("s_name").value;
    console.log(s_name);
        alert(`${s_name} 정보 페이지로 이동됩니다. \n${s_name}에 전화 혹은 방문해주세요.`);
        // location.href="/user/pets/adopt_pets_form";

}
