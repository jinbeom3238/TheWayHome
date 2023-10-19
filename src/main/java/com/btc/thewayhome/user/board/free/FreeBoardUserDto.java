package com.btc.thewayhome.user.board.free;

import lombok.Data;

@Data
public class FreeBoardUserDto {

    private int fb_no;              // not null
    private String use_yn;          // not null
    private String u_m_id;          // not null
    private String fb_category;     // not null
    private String fb_image;
    private String fb_title;        // not null
    private String fb_content;      // not null
    private String fb_region;
    private String fb_kind;
    private String fb_color;
    private String fb_age;
    private String fb_weight;
    private String fb_gender;
    private String fb_neuter;
    private String fb_reg_date;     // not null
    private String fb_mod_date;     // not null

}
