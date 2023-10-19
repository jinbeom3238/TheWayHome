package com.btc.thewayhome.admin.board.free;

import com.btc.thewayhome.page.Criteria;
import com.btc.thewayhome.page.PageMakerDto;
import com.btc.thewayhome.user.board.free.FreeBoardUserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class FreeBoardAdminService implements IFreeBoardAdminService{

    @Autowired
    IFreeBoardAdminDaoMapper iFreeBoardAdminDaoMapper;

    // 실종/목격 게시글 리스트 출력과 페이지네이션
    @Override
    public Map<String, Object> superFreeBoardList(int pageNum, int amount) {
        log.info("superFreeBoardList");

        Map<String, Object> map = new HashMap<>();

        //페이지 네이션
        Criteria criteria = new Criteria(pageNum, amount);
        List<FreeBoardUserDto> freeBoardUserDtos = iFreeBoardAdminDaoMapper.selectAllFreeBoard(criteria.getSkip(), criteria.getAmount());
        int totalCnt = iFreeBoardAdminDaoMapper.getTotalCnt();
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

    // 실종/목격 게시글 상세 페이지
    @Override
    public Map<String, Object> superFreeBoardDetail(int fb_no, FreeBoardUserDto freeBoardUserDto) {

        log.info("superFreeBoardDetail()");

        Map<String, Object> map = new HashMap<>();

//        int result = iFreeBoardAdminDaoMapper.updateHits(fb_no);
        freeBoardUserDto.setFb_no(fb_no);

//        if(result > 0){
            FreeBoardUserDto freeBoardDetailDto = iFreeBoardAdminDaoMapper.selectContent(freeBoardUserDto);
            map.put("freeBoardDetailDto", freeBoardDetailDto);
//
//        } else {
//            log.info("UPDATE HITS FAIL");
//            return null;
//
//        }

        return map;

    }

    // 실종/목격 게시글 삭제
    @Override
    public int superFreeBoardDelete(int fb_no) {
        log.info("superFreeBoardDelete()");

        return iFreeBoardAdminDaoMapper.deleteFreeBoard(fb_no);
    }
}
