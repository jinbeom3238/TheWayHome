package com.btc.thewayhome.user.board.free;

import com.btc.thewayhome.page.PageDefine;
import com.btc.thewayhome.page.PageMakerDto;
import com.btc.thewayhome.user.board.config.UploadFileService;
import com.btc.thewayhome.user.member.UserMemberDto;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
@RequestMapping("/user/board")
@Controller
public class FreeBoardUserController {

    @Autowired
    FreeBoardUserService freeBoardUserService;

    @Autowired
    UploadFileService uploadFileService;

    // 실종/목격 게시판 Home
    @GetMapping({"", "/"})
    public String freeBoardHome() {
        log.info("freeBoardHome()");

        return "redirect:/user/board/free_board_list";
    }

    @GetMapping("/free_board_list")
    public String freeBoardList(Model model,
                                @RequestParam(value="pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                                @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_AMOUNT) int amount,
                                @RequestParam(required = false, value = "searchOption")String searchOption,
                                @RequestParam(required = false, value = "sNameInput")String searchInput){
        log.info("freeBoardList()");

        String nextPage = "user/board/free/free_board_list";

        //서비스에서 Map으로 넘겨주기 때문에 Map 타입으로 받음
        Map<String, Object> map = freeBoardUserService.getAllFreeBoard(pageNum, amount, searchOption, searchInput);
        List<FreeBoardUserDto> freeBoardUserDtos = (List<FreeBoardUserDto>) map.get("freeBoardUserDtos");
        PageMakerDto pageMakerDto = (PageMakerDto) map.get("pageMakerDto");

        if(freeBoardUserDtos == null){
            log.info("freeBoardUserDtos IS NULL!!!");

        } else {
            log.info("freeBoardUserDtos SELECT SUCCESS!!!");
            model.addAttribute("freeBoardUserDtos", freeBoardUserDtos);
            model.addAttribute("pageMakerDto", pageMakerDto);

        }
        return nextPage;

    }

    // 실종/목격 게시판 글 작성 Form
    @GetMapping({"free_board_form"})
    public String freeBoardWriteForm(HttpSession session) {
        log.info("freeBoardWriteForm()");

        String nextPage = "/user/board/free/free_board_form";

        UserMemberDto loginedUserDto = (UserMemberDto) session.getAttribute("loginedUserMemberDto");

        if(loginedUserDto == null){
            nextPage = "user/member/member_login_form";
        }

        return nextPage;
    }

    // 실종/목격 게시판 글 작성 Confirm
    @PostMapping({"/free_board_write_confirm"})
    public String freeBoardWriteConfirm(HttpSession session, FreeBoardUserDto freeBoardUserDto, @RequestParam("file") MultipartFile file) {
        log.info("freeBoardWriteConfirm()");

        String nextPage = "redirect:/user/board/";

        UserMemberDto loginedUserMemberDto = (UserMemberDto) session.getAttribute("loginedUserMemberDto");

        String saveFileName = "noImage";

        // SAVE FILE
        if(!file.isEmpty()) {
            saveFileName = uploadFileService.upload(file);
        }

        int result = freeBoardUserService.freeBoardWriteConfirm(loginedUserMemberDto.getU_m_id(), saveFileName, freeBoardUserDto);

        if(result > 0){
            log.info("DB UPLOAD SUCCESS");

        } else {
            log.info("DB UPLOAD FAIL");
            nextPage = "/";

        }
        return nextPage;

    }

    // 실종/목격 게시판 상세보기
    @GetMapping("/free_board_detail")
    public String freeBoardDetail(@RequestParam("fb_no") int fb_no, FreeBoardUserDto freeBoardUserDto, Model model, HttpSession session) {
        log.info("freeBoardDetail()");

        String nextPage = "user/board/free/free_board_detail";

        Map<String, Object> map = freeBoardUserService.freeBoardDetail(fb_no, freeBoardUserDto);

        FreeBoardUserDto freeBoardDetailDto = (FreeBoardUserDto) map.get("freeBoardDetailDto");
        model.addAttribute("freeBoardDetailDto", freeBoardDetailDto);

        return nextPage;

    }

    // 실종/목격 게시판 수정 Form
    @GetMapping("/free_board_modify_form")
    public String freeBoardModify(FreeBoardUserDto freeBoardUserDto, Model model){
        log.info("freeBoardModify()");

        String nextPage = "user/board/free/free_board_modify_form";

        freeBoardUserDto = freeBoardUserService.freeBoardModify(freeBoardUserDto);

        model.addAttribute("freeBoardUserDto", freeBoardUserDto);

        return nextPage;
    }

    // 실종/목격 게시판 수정 Confirm
    @PostMapping("/free_board_modify_confirm")
    public String freeBoardModifyConfirm(FreeBoardUserDto freeBoardUserDto, HttpServletResponse response) throws IOException  {
        log.info("freeBoardModifyConfirm()");

        String nextPage = "redirect:/user/board/free_board_detail?fb_no=" + freeBoardUserDto.getFb_no();

        int result = freeBoardUserService.freeBoardModifyConfirm(freeBoardUserDto);
        if(result > 0){
            log.info("MODIFY SUCCESS");
        } else {
            log.info("MODIFY FAIL");

            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('게시글 수정에 실패했습니다.');");
            out.println("history.back();");
            out.println("</script>");
            out.flush();

            nextPage = "";
        }


        return nextPage;
    }


    // 실종/목격 게시판 삭제
    @GetMapping("/free_board_delete_confirm")
    public String freeBoardDelete(@RequestParam("fb_no") int fb_no, HttpServletResponse response) throws IOException {
        log.info("freeBoardModify");

        String nextPage = "redirect:/user/board/";

        int result = freeBoardUserService.freeBoardDelete(fb_no);

        if(result > 0){
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
