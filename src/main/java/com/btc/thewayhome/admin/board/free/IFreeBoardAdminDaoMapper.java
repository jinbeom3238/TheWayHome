package com.btc.thewayhome.admin.board.free;

import com.btc.thewayhome.user.board.free.FreeBoardUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IFreeBoardAdminDaoMapper {

    // 실종 목격 게시판 보기
    public List<FreeBoardUserDto> selectAllFreeBoard(int skip, int amount);

    // 실종 목격 게시판 - 페이지 네이션
    public int getTotalCnt();

    // 실종 목격 게시판 - 상세 보기
    public FreeBoardUserDto selectContent(FreeBoardUserDto freeBoardUserDto);

    // 실종 목격 게시판 - 게시글 삭제
    public int deleteFreeBoard(int fb_no);
}
