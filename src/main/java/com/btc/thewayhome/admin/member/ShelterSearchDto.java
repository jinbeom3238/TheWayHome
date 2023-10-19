package com.btc.thewayhome.admin.member;

import lombok.Data;

// 회원가입시 보호소 고유번호, 보호소 명, 보호소 주소, 보호소 전화번호를 비동기통신 할 때 객체에 담기위한 클래스
@Data
public class ShelterSearchDto {

    private String s_no;
    private String s_name;
    private String s_phone;
    private String s_address;

}
