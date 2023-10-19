package com.btc.thewayhome.admin.member;

import lombok.Data;
import org.springframework.stereotype.Component;

// 회원가입시 필요한 데이터를 객체에 넣기위함
@Data
public class AdminMemberDto {

    private int a_m_no;
    private String use_yn;
    private String a_m_id;
    private String a_m_pw;
    private String s_no;
    private String s_name;
    private String s_address;
    private String s_phone;
    private String a_m_approval;
    private String a_m_reg_date;
    private String a_m_mod_date;
    private String s_reg_date;


}
