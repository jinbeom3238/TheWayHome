package com.btc.thewayhome.user.board.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICommentDaoMapper {
    public int insertReviewComment(CommentDto commentDto);
    public int insertFreeBoardComment(CommentDto commentDto);

    public List<CommentDto> selectCommentAllForReview(int b_no);
    public List<CommentDto> selectCommentAllForFreeBoard(int b_no);


    public int deleteCommentCNoForReview(int r_c_no);

    public int deleteCommentCNoForFreeBoard(int r_c_no);


}
