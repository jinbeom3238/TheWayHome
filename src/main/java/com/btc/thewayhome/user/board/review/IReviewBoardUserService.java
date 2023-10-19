package com.btc.thewayhome.user.board.review;

import com.btc.thewayhome.user.board.comment.CommentDto;

import java.util.List;
import java.util.Map;

public interface IReviewBoardUserService {

    // 후기 게시판 - 작성
    public int writeReviewConfirm(String u_m_id, String saveFileName, ReviewBoardUserDto reviewBoardUserDto);

    // 후기 게시판 - 상세보기
    public ReviewBoardUserDto reviewDetailPage(int r_b_no);

    // 후기 게시판 - 게시글 전체 리스트
    public Map<String, Object> reviewBoardList(int pageNum, int amount);

    // 후기 게시판 - 삭제
    public int reviewDeleteConfirm(int rBNo);

    int reviewModifyConfirm(ReviewBoardUserDto reviewBoardUserDto);
}
