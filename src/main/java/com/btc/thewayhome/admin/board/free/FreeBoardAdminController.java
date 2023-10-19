package com.btc.thewayhome.admin.board.free;

import com.btc.thewayhome.admin.member.AdminMemberDto;
import com.btc.thewayhome.page.PageDefine;
import com.btc.thewayhome.page.PageMakerDto;
import com.btc.thewayhome.user.board.free.FreeBoardUserDto;
import com.btc.thewayhome.user.board.free.FreeBoardUserService;
import com.btc.thewayhome.user.member.UserMemberDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


@Controller
@Log4j2
@RequestMapping("/admin/free/board")
public class FreeBoardAdminController {

    @Autowired
    FreeBoardAdminService freeBoardAdminService;

    // 실종 목격 게시판 리스트 출력과 페이지 네이션
    @GetMapping("/super_free_board_list")
    public String superFreeBoardList(Model model,
                                @RequestParam(value="pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                                @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_AMOUNT) int amount){
        log.info("superFreeBoardList()");

        String nextPage = "admin/board/super_free_board_list";

        //서비스에서 Map으로 넘겨주기 때문에 Map 타입으로 받음
        Map<String, Object> map = freeBoardAdminService.superFreeBoardList(pageNum, amount);
        List<FreeBoardUserDto> freeBoardUserDtos = (List<FreeBoardUserDto>) map.get("freeBoardUserDtos");
        PageMakerDto pageMakerDto = (PageMakerDto) map.get("pageMakerDto");

        if(freeBoardUserDtos == null){

        } else {
            model.addAttribute("freeBoardUserDtos", freeBoardUserDtos);
            model.addAttribute("pageMakerDto", pageMakerDto);

        }
        return nextPage;

    }

    // 실종/목격 게시판 상세보기
    @GetMapping("/super_free_board_detail")
    public String freeBoardDetail(@RequestParam("fb_no") int fb_no, FreeBoardUserDto freeBoardUserDto, Model model, HttpSession session) {
        log.info("freeBoardDetail()");

        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");

        String nextPage = "admin/board/super_free_board_detail";

        Map<String, Object> map = freeBoardAdminService.superFreeBoardDetail(fb_no, freeBoardUserDto);

        FreeBoardUserDto freeBoardDetailDto = (FreeBoardUserDto) map.get("freeBoardDetailDto");
        model.addAttribute("freeBoardDetailDto", freeBoardDetailDto);
        model.addAttribute("loginedAdminMemberDto", loginedAdminMemberDto);

        return nextPage;

    }

    // 실종/목격 게시판 삭제
    @GetMapping("/super_free_board_delete_confirm")
    public String freeBoardDelete(@RequestParam("fb_no") int fb_no, HttpServletResponse response) throws IOException {
        log.info("freeBoardDelete");

        String nextPage = "redirect:/admin/free/board/super_free_board_list";

        int result = freeBoardAdminService.superFreeBoardDelete(fb_no);

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
