package com.btc.thewayhome.user.board.free;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IFreeBoardUserDaoMapper {

    // 실종/목격 게시판 - 작성
    public int insertFreeBoardContent(FreeBoardUserDto freeBoardUserDto);

    // 실종/목격 게시판 - 게시글 전체 리스트
    public List<FreeBoardUserDto> selectAllFreeBoard(int skip, int amount, String searchOption, String searchInput);

    // 실종/목격 게시판 - 조회수
    public int updateHits(int fb_no);

    // 실종/목격 게시판 - 상세보기
    public FreeBoardUserDto selectContent(FreeBoardUserDto freeBoardUserDto);

    // 실종/목격 게시판 - 삭제
    public int deleteFreeBoard(int fb_no);

    // 실종/목격 게시판 - 수정 Confirm
    public int updateFreeboard(FreeBoardUserDto freeBoardUserDto);

    // 실종/목격 게시판 - 페이지네이션 관련
    public int getTotalCnt();
}
