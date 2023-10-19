package com.btc.thewayhome.admin.member;

import com.btc.thewayhome.admin.config.GetAreaData;
import com.btc.thewayhome.admin.config.GetPetsData;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

    @Autowired
    AdminMemberService adminMemberService;

    @Autowired
    GetAreaData getAreaData;

    @Autowired
    GetPetsData getPetsData;

    /*
     * API DB에 저장 START
     */

    // 보호소 api db에 삽입
    @Scheduled(cron = "0 50 8 * * *")
    public Object shelterRegistNum() {
        log.info("shelterRegistNum()");

        // DB에 시도, 시군구 데이터를 통해 보호소 api삽입
        getAreaData.getData();

        // DB에 유기동물 데이터 api삽입
        getPetsData.getpets();

        return null;

        }

    // 보호소 이름을 DB에서 비동기 통신을 위한 매핑
    @PostMapping("/searchShelterName")
    @ResponseBody
    public Object searchShelterName(@RequestParam Map<String, String> shelterNameMap) {
        log.info("searchShelterName()");

        Map<String, Object> map = adminMemberService.searchShelterName(shelterNameMap);

        return map;

    }

    // 보호소 고유번호를 DB에서 비동기 통신을 위한 매핑
    @PostMapping("/searchShelterNo")
    @ResponseBody
    public Object searchShelterNo(@RequestParam Map<String, String> shelterNoMap) {
        log.info("searchShelterNo()");

        Map<String, Object> map = adminMemberService.searchShelterNo(shelterNoMap);

        return map;

    }

    // 보호소 주소를 DB에서 비동기 통신을 위한 매핑
    @PostMapping("/searchShelterAddress")
    @ResponseBody
    public Object searchShelterAddress(@RequestParam Map<String, String> shelterAddressMap) {
        log.info("searchShelterAddress()");

        Map<String, Object> map = adminMemberService.searchShelterAddress(shelterAddressMap);

        return map;

    }

    // 보호소 전화번호를 DB에서 비동기 통신을 위한 매핑
    @PostMapping("/searchShelterPhone")
    @ResponseBody
    public Object searchShelterPhone(@RequestParam Map<String, String> shelterPhoneMap) {
        log.info("searchShelterPhone()");

        Map<String, Object> map = adminMemberService.searchShelterPhone(shelterPhoneMap);

        return map;

    }

    /*
     * API DB에 저장 END
     */

    // 보호소 회원가입 FORM(admin 회원가입)
    @GetMapping("/create_account_form")
    public String createAccountForm() {
        log.info("createAccountForm()");

        String nextPage = "admin/member/create_account_form";

        return nextPage;

    }

    // 보호소 회원가입 CONFIRM
    @PostMapping("/create_account_confirm")
    public String createAccountConfirm(AdminMemberDto adminMemberDto, Model model) {
        log.info("[AdminMemberController] createAccountConfirm()");

        // 보호소 회원가입 시 DB에 ID와 고유번호 중복성 체크
        String nextPage = "admin/member/create_account_fail";

        int result = adminMemberService.createAccountConfirm(adminMemberDto);

        if(result > adminMemberService.INSERT_FAIL_AT_DATABASE) {
            model.addAttribute("a_m_id", adminMemberDto.getA_m_id());
            model.addAttribute("s_no", adminMemberDto.getS_no());
            nextPage = "/admin/member/create_account_success";

        }

        return nextPage;

    }

    // 관리자 로그인 기능
    @GetMapping("/member_login_form")
    public String memberLoginForm() {
        log.info("memberLoginForm()");

        String nextPage = "admin/member/member_login_form";

        return nextPage;

    }

    // 관리자 로그아웃 기능
    @GetMapping("/member_logout_comfirm")
    public String memberLogoutConfirm(HttpSession session) {
        log.info("memberLogoutConfirm()");

        String nextPage = "redirect:/admin";

        session.removeAttribute("loginedAdminMemberDto");

        return nextPage;

    }

    /*
     * 관리자 정보 수정 START
     */
    // 관리자 회원정보 수정 FORM
    @GetMapping("/member_modify_form")
    public String memberModifyForm() {
        log.info("memberModifyForm()");

        String nextPage = "admin/member/member_modify_form";

        return nextPage;

    }

    // 관리자 정보 수정 CONFIRM
    @PostMapping("/member_modify_confirm")
    public String memberModifyConfirm(HttpSession session, AdminMemberDto adminMemberDto) {
        log.info("memberModifyConfirm()");

        String nextPage = "redirect:/admin/member/member_modify_form";

        AdminMemberDto loginedAdminMemberDto = adminMemberService.memberModifyConfirm(adminMemberDto);

        if(adminMemberDto != null) {
            session.setAttribute("loginedAdminMemberDto", loginedAdminMemberDto);
            session.setMaxInactiveInterval(60 * 30);

            log.info("loginedAdminMemberDto----------------->", loginedAdminMemberDto.getA_m_pw());

        } else {
            nextPage = "redirect:/admin/member/member_modify_form";

        }
            return nextPage;

    }

    // 관리자 비밀번호 수정 FORM
    @GetMapping ("/admin_password_modify_form")
    public String adminMemeberPasswordModfiyForm() {
        log.info("adminMemeberPasswordModfiyForm()");

        String nextPage = "admin/member/admin_password_modify_form";

        return nextPage;

    }

    // 관리자 비밀번호 수정 CONFIRM
    @PostMapping ("/member_password_modify_confirm")
    public String adminMemeberPasswordModfiyConfirm(HttpSession session,
                                                   HttpServletResponse response,
                                                   AdminMemberDto adminMemberDto,
                                                   @RequestParam("a_m_pw") String currentPw,
                                                   @RequestParam("a_m_Re_pw") String changePw) throws IOException {
        log.info("adminMemeberPasswordModfiyConfirm()");

        String nextPage = "redirect:/admin";

        AdminMemberDto loginedAdminMemberDto = adminMemberService.adminMemberPasswordModifyConfirm(adminMemberDto, currentPw, changePw);

        if(loginedAdminMemberDto != null){
            session.setAttribute("loginedAdminMemberDto", loginedAdminMemberDto);
            session.setMaxInactiveInterval(60 * 30);

        } else {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('비밀번호 수정 실패 했습니다.');</script>");
            out.flush();

        }
        return nextPage;
    }
    /*
     * 관리자 정보 수정 END
     */
    
    // 관리자 계정 삭제 기능
    @GetMapping("/member_delete_confirm")
    public String memberDeleteConfirm(HttpSession session) {
        log.info("memberDeleteConfirm()");

        String nextPage = "redirect:/admin";

        //로그인되어있는 사람만 삭제를 할 수 있기 때문에 확인하기 위해서 세션 정보 들고와줌
        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto)session.getAttribute("loginedAdminMemberDto");

        //분기 태우기
        if(loginedAdminMemberDto == null) { 	//로그인 되어 있지 않다면
            return "redirect:/admin";

        }
        int result = adminMemberService.memberDeleteConfirm(loginedAdminMemberDto.getA_m_no());

        if(result > 0) {  //admin 멤버 삭제시
            session.removeAttribute("loginedAdminMemberDto");	//세션 날려줘야 함

        } else {
            nextPage = "admin/delete_fail";

        }
        return nextPage;

    }

    //관리자 정보 리스트 기능(모든 관리자 목록)
    @GetMapping("/search_admin_list")
    public String searchAdminList(Model model, HttpSession session) {
        log.info("searchAdminList()");

        String nextPage = "/admin/member/search_admin_list";

        List<AdminMemberDto> adminMemberDtos = adminMemberService.searchAdminList();
        model.addAttribute("adminMemberDtos", adminMemberDtos);

        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");

        if (loginedAdminMemberDto == null) {
            return "redirect:/admin";

        }
        return nextPage;

    }

    // 로그인을 위한 관리자 승인 처리 기능 (Super가 일반 관리자 승인시 로그인 가능)
    @PostMapping("/member_approval_confirm")
    @ResponseBody
    public Object memberApprovalConfirm(AdminMemberDto adminMemberDto) {
        log.info("memberApprovalConfirm()");

        // 비동기 통신시 키, 값 쌍을 통해 원하는 값을 한번에 찾기 위해 Map 사용
        Map<String, Object> map = adminMemberService.memberApprovalConfirm(adminMemberDto.getA_m_no());

        return map;

    }

}
