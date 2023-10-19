package com.btc.thewayhome.admin.board.review;

import com.btc.thewayhome.user.board.review.ReviewBoardUserDto;

import java.util.Map;

public interface IReviewBoardAdminService {
    public Map<String, Object> superReviewBoardList(int pageNum, int amount);

    public Map<String, Object> superReviewBoardDetail(int r_b_no, ReviewBoardUserDto reviewBoardUserDto);

    public int superReviewBoardDelete(int r_b_no);
}
