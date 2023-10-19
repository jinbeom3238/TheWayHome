package com.btc.thewayhome.user.board.free;

import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface IFreeBoardUserService {

    // 실종/목격 게시판 - 작성
    public int freeBoardWriteConfirm(String u_m_id, String fb_image, FreeBoardUserDto freeBoardUserDto);

    // 실종/목격 게시판 - 게시글 전체 리스트
    public Map<String, Object> getAllFreeBoard(int pageNum, int amount, String searchOption, String searchInput);

    // 실종/목격 게시판 - 상세보기
    public Map<String, Object> freeBoardDetail(int fb_no, FreeBoardUserDto freeBoardUserDto);

    // 실종/목격 게시판 - 수정
    public FreeBoardUserDto freeBoardModify(FreeBoardUserDto freeBoardUserDto);

    // 실종/목격 게시판 - 삭제
    public int freeBoardDelete(int fb_no);

    // 실종/목격 게시판 - 수정 Confirm
    public int freeBoardModifyConfirm(FreeBoardUserDto freeBoardUserDto);

}
