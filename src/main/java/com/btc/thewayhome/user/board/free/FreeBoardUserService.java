package com.btc.thewayhome.user.board.free;

import com.btc.thewayhome.page.Criteria;
import com.btc.thewayhome.page.PageMakerDto;
import com.btc.thewayhome.user.board.comment.CommentDto;
import com.btc.thewayhome.user.board.comment.ICommentDaoMapper;
import com.btc.thewayhome.user.board.config.ImageService;
import com.btc.thewayhome.user.board.review.ReviewBoardUserDto;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class FreeBoardUserService implements IFreeBoardUserService {

    @Getter
    @Autowired
    IFreeBoardUserDaoMapper iFreeBoardUserDaoMapper;

    @Autowired
    ImageService imageService;

    @Autowired
    ICommentDaoMapper iCommentDaoMapper;

    @Override
    public int freeBoardWriteConfirm(String u_m_id, String fb_image, FreeBoardUserDto freeBoardUserDto) {
        log.info("freeBoardWriteConfirm()");

        freeBoardUserDto.setU_m_id(u_m_id);
        freeBoardUserDto.setFb_image(fb_image);

        return iFreeBoardUserDaoMapper.insertFreeBoardContent(freeBoardUserDto);

    }

    @Override
    public Map<String, Object> getAllFreeBoard(int pageNum, int amount, String searchOption, String searchInput) {
        log.info("getAllFreeBoard()");

        Map<String, Object> map = new HashMap<>();

        //페이지 네이션
        Criteria criteria = new Criteria(pageNum, amount);
        List<FreeBoardUserDto> freeBoardUserDtos = iFreeBoardUserDaoMapper.selectAllFreeBoard(criteria.getSkip(), criteria.getAmount(), searchOption, searchInput);
        int totalCnt = iFreeBoardUserDaoMapper.getTotalCnt();
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        if(freeBoardUserDtos != null){
            log.info("NOT NULL");
            map.put("freeBoardUserDtos", freeBoardUserDtos);
            map.put("pageMakerDto", pageMakerDto);
            return map;

        } else {
            log.info("NULL");
            return null;

        }

    }
    @Override
    public Map<String, Object> freeBoardDetail(int fb_no, FreeBoardUserDto freeBoardUserDto) {
        log.info("freeBoardDetail()");

        Map<String, Object> map = new HashMap<>();

        int result = iFreeBoardUserDaoMapper.updateHits(fb_no);
        freeBoardUserDto.setFb_no(fb_no);

        if(result > 0){
            FreeBoardUserDto freeBoardDetailDto = iFreeBoardUserDaoMapper.selectContent(freeBoardUserDto);
            map.put("freeBoardDetailDto", freeBoardDetailDto);

        } else {
            log.info("UPDATE HITS FAIL");
            return null;

        }

        return map;
    }
    @Override
    public FreeBoardUserDto freeBoardModify(FreeBoardUserDto freeBoardUserDto) {
        log.info("freeBoardModify()");
        return iFreeBoardUserDaoMapper.selectContent(freeBoardUserDto);

    }
    @Override
    public int freeBoardDelete(int fb_no) {
        log.info("freeBoardDelete()");
        return iFreeBoardUserDaoMapper.deleteFreeBoard(fb_no);

    }

    @Override
    public int freeBoardModifyConfirm(FreeBoardUserDto freeBoardUserDto) {
        log.info("freeBoardModifyConfirm()");

        return  iFreeBoardUserDaoMapper.updateFreeboard(freeBoardUserDto);
    }


}
