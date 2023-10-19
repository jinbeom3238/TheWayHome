package com.btc.thewayhome.admin.member;

import lombok.Data;

// 보호소 명과 보호소 전화번호 보호소 주소를 객체에 담기 위한 클래스
@Data
public class ShelterInfoDto {

    private String s_name;
    private String s_phone;
    private String s_address;
    private String s_reg_date;
    private String s_mod_date;

}
