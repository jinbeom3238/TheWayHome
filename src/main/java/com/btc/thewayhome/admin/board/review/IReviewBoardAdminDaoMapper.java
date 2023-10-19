package com.btc.thewayhome.admin.board.review;

import com.btc.thewayhome.user.board.review.ReviewBoardUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IReviewBoardAdminDaoMapper {

    // 후기 게시글 리스트 출력과 페이지 네이션
    public List<ReviewBoardUserDto> selectAllReviewBoard(int skip, int amount);

    // 후기 게시글 리스트 총 페이지
    public int getTotalCnt();

    // 후기 게시글 상세보기
    public ReviewBoardUserDto selectReviewDetail(ReviewBoardUserDto reviewBoardUserDto);

    // 후기 게시글 삭제
    public int deleteReviewBoard(int r_b_no);
}
