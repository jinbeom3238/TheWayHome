package com.btc.thewayhome.user.board.comment;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

public interface ICommentService {

    public int writeReviewCommentConfirm(Map<String, Object> msgMap, CommentDto commentDto);
    public int writeFreeBoardCommentConfirm(Map<String, Object> msgMap, CommentDto commentDto);

    public List<CommentDto> getCommentAllForReview(int rBNo);
    public List<CommentDto> getCommentAllForFreeBoard(int rBNo);

    public int reviewCommentDelete(int r_c_no);

    public int freeBoardCommentDelete(int b_c_no);
}
