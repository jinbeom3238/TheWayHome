package com.btc.thewayhome.user.board.comment;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class CommentService implements ICommentService{

    @Autowired
    ICommentDaoMapper iCommentDaoMapper;

    @Override
    public int writeReviewCommentConfirm(Map<String, Object> msgMap, CommentDto commentDto) {
        log.info("writeReviewCommentConfirm()");

        commentDto.setU_m_id(msgMap.get("u_m_id").toString());
        commentDto.setB_type(msgMap.get("b_type").toString());
        commentDto.setB_no(Integer.parseInt(msgMap.get("b_no").toString()));
        commentDto.setB_c_content(msgMap.get("b_c_content").toString());

        int result = -1;

        result = iCommentDaoMapper.insertReviewComment(commentDto);

        return result;

    }

    @Override
    public int writeFreeBoardCommentConfirm(Map<String, Object> msgMap, CommentDto commentDto) {
        log.info("writeFreeBoardCommentConfirm()");

        commentDto.setU_m_id(msgMap.get("u_m_id").toString());
        commentDto.setB_type(msgMap.get("b_type").toString());
        commentDto.setB_no(Integer.parseInt(msgMap.get("b_no").toString()));
        commentDto.setB_c_content(msgMap.get("b_c_content").toString());

        int result = -1;

        result = iCommentDaoMapper.insertFreeBoardComment(commentDto);

        return result;
    }

    @Override
    public List<CommentDto> getCommentAllForReview(int rBNo) {
        log.info("getCommentAllForReview()");
        return iCommentDaoMapper.selectCommentAllForReview(rBNo);

    }
    @Override
    public List<CommentDto> getCommentAllForFreeBoard(int rBNo) {
        log.info("getCommentAllForFreeBoard()");

        log.info(">>>>>>>>! "+ rBNo);

        return iCommentDaoMapper.selectCommentAllForFreeBoard(rBNo);
    }

    @Override
    public int reviewCommentDelete(int b_c_no) {
        log.info("reviewCommentDelete()");
        return iCommentDaoMapper.deleteCommentCNoForReview(b_c_no);
    }

    @Override
    public int freeBoardCommentDelete(int b_c_no) {
        log.info("freeBoardCommentDelete()");
        return iCommentDaoMapper.deleteCommentCNoForFreeBoard(b_c_no);
    }

}
