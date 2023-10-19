package com.btc.thewayhome.admin.pets.admin;


import com.btc.thewayhome.admin.member.AdminMemberDto;
import com.btc.thewayhome.page.PageDefine;
import com.btc.thewayhome.page.PageMakerDto;
import com.btc.thewayhome.user.board.config.UploadFileService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Log4j2
@RequestMapping("/admin/pets")
@Controller
public class PetsAdminController {

    @Autowired
    PetsAdminService petsAdminService;

    @Autowired
    UploadFileService uploadFileService;

    /*
     * 관리자(ADMIN)에게 보이는 페이지
     */

    // 보호소 전체 리스트
    @GetMapping("/shelter_list")
    public String shelterList(Model model, HttpSession session) {
        log.info("shelterList()");

        String nextPage = "admin/pets/admin/admin_shelter_list";

        // 로그인된 관리자 계정으로 볼 수 있도록 하기 위해 session에 담아놨던 정보 가지고 옴
        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");

        // 여러 개의 보호소들을 담아주기 위해 List 사용
//        List<AdminShelterListInfoDto> adminShelterListInfoDtos = petsAdminService.searchShelterList(loginedAdminMemberDto, pageNum, amount);
//        Map<String, Object> map = (Map<String, Object>) petsAdminService.searchShelterList(loginedAdminMemberDto);

        List<AdminShelterListInfoDto> adminShelterListInfoDtos = petsAdminService.searchShelterList(loginedAdminMemberDto);

//        List<AdminShelterListInfoDto> adminShelterListInfoDtos = (List<AdminShelterListInfoDto>) map.get("adminShelterListInfoDtos");
//        PageMakerDto pageMakerDto = (PageMakerDto) map.get("pageMakerDto");

        model.addAttribute("adminShelterListInfoDtos", adminShelterListInfoDtos);
//        model.addAttribute("pageMakerDto", pageMakerDto);

        return nextPage;

    }

    //보호 동물 리스트 -> 보호소 리스트에서 보호소명을 클릭 시 나타나는 페이지
    /*@GetMapping("/pets_list")
    public String petsList(Model model, @RequestParam("s_no") String s_no) {
        log.info("petsList()");

        String nextPage = "admin/pets/admin/admin_pets_list";

        List<PetsAdminDto> petsAdminDtos = petsAdminService.searchPetsList(s_no);
        model.addAttribute("petsAdminDtos", petsAdminDtos);

        return nextPage;

    }*/

    // 보호 동물(보호소 눌렀을때 나오는 보호 동물)
    @GetMapping("/pets_list")
    public String petsList(Model model,
                           @RequestParam("s_no") String s_no,
                           @RequestParam(required = false, value = "searchOption")String searchOption,
                           @RequestParam(required = false, value = "sNameInput")String searchInput) {
        log.info("petsList()");

        log.info("----------->{}", searchOption);
        log.info("----------->{}", searchInput);

        String nextPage = "admin/pets/admin/admin_pets_list";

        List<PetsAdminDto> petsAdminDtos = petsAdminService.searchPetsList(s_no, searchOption, searchInput);

        if(petsAdminDtos != null) {
            log.info("searchPetsList SUCCESS");
            model.addAttribute("petsAdminDtos", petsAdminDtos);



        } else {
            log.info("searchPetsList FAIL");

        }
        return nextPage;

    }

    // 보호 동물 리스트 -> 사용자 메뉴바에서 보호동물 클릭 시 나타나는 페이지
    @GetMapping("/all_pets_list")
    public String allPetsList(Model model, HttpSession session) {
        log.info("allPetsList()");

        String nextPage = "admin/pets/admin/admin_pets_list";

        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");

        List<PetsAdminDto> petsAdminDtos = petsAdminService.searchAllPetsList(loginedAdminMemberDto);
        model.addAttribute("petsAdminDtos", petsAdminDtos);

        return nextPage;

    }

    // 보호 동물 상세 페이지
    @GetMapping("/pets_list_detail")
    public String petsListDetail(Model model, PetsAdminDto petsAdminDto, HttpSession session, @RequestParam("an_no") String an_no) {
        log.info("petsListDetail()");

        String nextPage = "admin/pets/admin/admin_pets_list_detail";

        petsAdminDto = petsAdminService.searchPetsListDetail(an_no);

        session.setAttribute("petsAdminDto", petsAdminDto);
        model.addAttribute("petsAdminDto", petsAdminDto);

        return nextPage;

    }


    // 보호 동물 등록 페이지()
    @GetMapping("/admin_pets_regist_form")
    public String createRegistPetsForm(HttpSession session) {
        log.info("createRegistPetsForm()");

        // loginedAdminMemberDto가 null인 경우, 로그인 페이지로 이동
        String nextPage = "redirect:/admin/member/member_login_form";

        // 보호 동물 등록 시 로그인 된 관리자만 사용하기 위함
        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");

        if (loginedAdminMemberDto != null) {

            nextPage = "admin/pets/admin/admin_pets_regist_form";

        }
        return nextPage;

    }

    // 보호 동물 등록 성공 or 실패
    @PostMapping("/admin_pets_regist_confirm")
    public String petsRegistConfirm(PetsAdminDto petsAdminDto,
                                    @RequestParam("file") MultipartFile file,
                                    HttpSession session) {
        log.info("createRegistConfirm()");

        String nextPage = "admin/pets/admin/admin_pets_regist_success";


        // 파일을 바로 쓸수는 없고 객체로 만들어서 써야한다.
        // SAVE FILE (바이너리 파일을 서버로 저장하는 방법)
        String saveFileName = uploadFileService.upload(file);


        if (saveFileName != null) {
            petsAdminDto.setAn_thumbnail(saveFileName);
            petsAdminDto.setAn_image(saveFileName);

            int result = petsAdminService.petsRegistConfirm(petsAdminDto);

            if (result <= 0) {
                nextPage = "admin/pets/admin/admin_regist_pets_fail";

            }

        }
        return nextPage;
    }


    // 보호 동물 수정
    @GetMapping("/admin_modify_pets_form")
    public String modifyPetsForm(HttpSession session,
                                 @RequestParam("an_no") String an_no,
                                 Model model) {
        log.info("modifyPetsForm()");

        // loginedAdminMemberDto가 null인 경우, 로그인 페이지로 이동
        String nextPage = "admin/pets/admin/admin_pets_modify_form";


        // 보호 동물 등록 시 로그인 된 관리자만 사용하기 위함
        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");

        if (loginedAdminMemberDto == null) {
            nextPage = "redirect:/admin/member/member_login_form";

        } else {
            PetsAdminDto petsAdminDto = petsAdminService.modifyPetsForm(an_no);
            model.addAttribute("petsAdminDto", petsAdminDto);
        }
        return nextPage;

    }

    // 보호 동물 등록한 것 수정
    @PostMapping("/admin_pets_modify_confirm")
    public String modifyPetsConfirm(PetsAdminDto petsAdminDto,
                                    @RequestParam("file") MultipartFile file,
                                    HttpSession session) {
        log.info("modifyPetsConfirm()");

        String nextPage = "admin/pets/admin/admin_pets_modify_success";

        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");

        if (loginedAdminMemberDto == null) {
            nextPage = "redirect:/admin/member/member_login_form";
        }
        if (!file.getOriginalFilename().equals("")) {
            String saveFileName = uploadFileService.upload(file);

            if (saveFileName != null) {
                petsAdminDto.setAn_image(saveFileName);
                petsAdminDto.setAn_thumbnail(saveFileName);
            }
        }
        int result = petsAdminService.modifyPetsConfirm(petsAdminDto);

        log.info("------------>" + petsAdminDto.getAn_no());

        if (result <= 0) {
            nextPage = "redirect:admin/pets/admin/admin_pets_list";
        }

        return nextPage;

    }


    // 보호 동물 삭제
    @GetMapping("/admin_delete_pet_confirm")
    public String deletePetsConfirm(HttpSession session, @RequestParam("an_no") String an_no) {
        log.info("petDeleteConfirm()");

        String nextPage = "redirect:/admin/pets/all_pets_list";

        AdminMemberDto loginedAdminMemberDto = (AdminMemberDto) session.getAttribute("loginedAdminMemberDto");

        if (loginedAdminMemberDto == null) {
            nextPage = "redirect:/admin/member/member_login_form";
        }

        int result = petsAdminService.deletePetsConfirm(an_no);

        if (result <= 0) {
            nextPage = "/admin/pets/admin/admin_delete_pet_fail";
        }

        return nextPage;

    }
}