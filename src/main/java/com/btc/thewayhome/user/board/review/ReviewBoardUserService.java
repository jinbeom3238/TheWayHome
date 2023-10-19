package com.btc.thewayhome.user.board.review;

import com.btc.thewayhome.page.Criteria;
import com.btc.thewayhome.page.PageMakerDto;
import com.btc.thewayhome.user.board.comment.CommentDto;
import com.btc.thewayhome.user.board.comment.ICommentDaoMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class ReviewBoardUserService implements IReviewBoardUserService{

    @Autowired
    IReviewBoardUserDaoMapper iReviewBoardUserDaoMapper;

    @Autowired
    ICommentDaoMapper iCommentDaoMapper;

    @Override
    public int writeReviewConfirm(String u_m_id, String r_b_image, ReviewBoardUserDto reviewBoardUserDto) {
        log.info("writeReviewConfirm()");

        reviewBoardUserDto.setU_m_id(u_m_id);
        reviewBoardUserDto.setR_b_image(r_b_image);

        return iReviewBoardUserDaoMapper.insertWriteReview(reviewBoardUserDto);

    }

    @Override
    public ReviewBoardUserDto reviewDetailPage(int r_b_no) {
        log.info("reviewDetailPage()");

        int result = iReviewBoardUserDaoMapper.updateHits(r_b_no);

        if(result > 0) {
            log.info("hits update success");
            return iReviewBoardUserDaoMapper.selectReviewForBNo(r_b_no);

        }else {
            log.info("hits update fail");
            return null;

        }

    }

    @Override
    public Map<String, Object> reviewBoardList(int pageNum, int amount) {
        log.info("reviewBoardList()");

        Map<String, Object> map = new HashMap<>();

        //페이지 네이션
        Criteria criteria = new Criteria(pageNum, amount);
        List<ReviewBoardUserDto> reviewBoardDtos = iReviewBoardUserDaoMapper.selectReviewAll(criteria.getSkip(), criteria.getAmount());
        int totalCnt = iReviewBoardUserDaoMapper.getTotalCnt();
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        if(reviewBoardDtos == null) {
            log.info("NULL");
            return null;

        } else {
            log.info("NOT NULL");
            map.put("reviewBoardDtos", reviewBoardDtos);
            map.put("pageMakerDto", pageMakerDto);
            return map;

        }

    }

    @Override
    public int reviewDeleteConfirm(int rBNo) {
        log.info("reviewDeleteConfirm()");
        return iReviewBoardUserDaoMapper.reviewUseNForBNo(rBNo);

    }


    @Override
    public int reviewModifyConfirm(ReviewBoardUserDto reviewBoardUserDto) {
        log.info("reviewModifyConfirm()");
        return iReviewBoardUserDaoMapper.updateReviewboard(reviewBoardUserDto);

    }


}
