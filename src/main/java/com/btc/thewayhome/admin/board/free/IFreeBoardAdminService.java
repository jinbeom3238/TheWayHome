package com.btc.thewayhome.admin.board.free;

import com.btc.thewayhome.user.board.free.FreeBoardUserDto;

import java.util.Map;

public interface IFreeBoardAdminService{

    public Map<String, Object> superFreeBoardList(int pageNum, int amount);

    public Map<String, Object> superFreeBoardDetail(int fbNo, FreeBoardUserDto freeBoardUserDto);

    public int superFreeBoardDelete(int fb_no);
}
