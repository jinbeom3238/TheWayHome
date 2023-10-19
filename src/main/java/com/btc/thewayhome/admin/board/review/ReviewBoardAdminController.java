package com.btc.thewayhome.admin.board.review;

import com.btc.thewayhome.admin.member.AdminMemberDto;
import com.btc.thewayhome.page.PageDefine;
import com.btc.thewayhome.page.PageMakerDto;
import com.btc.thewayhome.user.board.free.FreeBoardUserDto;
import com.btc.thewayhome.user.board.review.ReviewBoardUserDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/admin/review/board")
public class ReviewBoardAdminController {

    @Autowired
    ReviewBoardAdminService reviewBoardAdminService;

    // 후기 게시판 리스트 보기
    @GetMapping("/super_review_board_list")
    public String superReviewBoardList(Model model,
                                     @RequestParam(value="pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                                     @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_AMOUNT) int amount){
        log.info("superReviewBoardList()");

        String nextPage = "admin/board/super_review_board_list";

        //서비스에서 Map으로 넘겨주기 때문에 Map 타입으로 받음
        Map<String, Object> map = reviewBoardAdminService.superReviewBoardList(pageNum, amount);
        List<ReviewBoardUserDto> reviewBoardDtos = (List<ReviewBoardUserDto>) map.get("reviewBoardDtos");
        PageMakerDto pageMakerDto = (PageMakerDto) map.get("pageMakerDto");

        if(reviewBoardDtos == null){
            log.info("reviewBoardDtos IS NULL!!!");

        } else {
            log.info("reviewBoardDtos SELECT SUCCESS!!!");
            model.addAttribute("reviewBoardDtos", reviewBoardDtos);
            model.addAttribute("pageMakerDto", pageMakerDto);

        }
        return nextPage;

    }

    // 후기 게시판 상세보기
    @GetMapping("/super_review_board_detail")
    public String reviewBoardDetail(@RequestParam("r_b_no") int r_b_no, ReviewBoardUserDto reviewBoardUserDto, Model model, HttpSession session) {
        log.info("reviewBoardDetail()");

        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");

        String nextPage = "admin/board/super_review_board_detail";

        Map<String, Object> map = reviewBoardAdminService.superReviewBoardDetail(r_b_no, reviewBoardUserDto);

//        ReviewBoardUserDto selectReviewDto = reviewBoardAdminService.superReviewBoardDetail(r_b_no);
        ReviewBoardUserDto selectReviewDto = (ReviewBoardUserDto)map.get("selectReviewDto");
        model.addAttribute("selectReviewDto", selectReviewDto);
        model.addAttribute("loginedAdminMemberDto", loginedAdminMemberDto);

        return nextPage;

    }

    // 후기 게시판 삭제
    @GetMapping("/super_review_board_delete_confirm")
    public String reviewBoardDelete(@RequestParam("fb_no") int r_b_no, HttpServletResponse response) throws IOException {
        log.info("reviewBoardDelete");

        String nextPage = "redirect:/admin/review/board/super_review_board_list";

        int result = reviewBoardAdminService.superReviewBoardDelete(r_b_no);

        if (result > 0) {
            log.info("DELETE SUCCESS");

        } else {
            log.info("DELETE FAIL");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('게시글 삭제에 실패했습니다.');");
            out.println("history.back();");
            out.println("</script>");
            out.flush();

            nextPage = "";

        }
        return nextPage;
    }






}
