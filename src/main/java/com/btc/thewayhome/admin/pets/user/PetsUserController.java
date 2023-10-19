package com.btc.thewayhome.admin.pets.user;

import com.btc.thewayhome.admin.pets.admin.AdminShelterListInfoDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RequestMapping("/user/pets")
@Controller
public class PetsUserController {

    @Autowired
    PetsUserService petsUserService;

    /*
     *  사용자(USER)에게 보이는 페이지
     */

    //보호소 전체 리스트
    @GetMapping("/shelter_list")
    public String shelterList(Model model,
                              @RequestParam(required = false, value = "searchOption")String searchOption,
                              @RequestParam(required = false, value = "sNameInput")String searchInput) {
        log.info("shelterList()");

        log.info("searchOption " + searchOption);
        log.info("searchInput " + searchInput);

        String nextPage = "admin/pets/user/user_shelter_list";

        // 전체 보호소 정보들을 List에 담기 위함
        List<UserShelterListInfoDto> userShelterListInfoDtos = petsUserService.searchShelterList(searchOption, searchInput);
        if(userShelterListInfoDtos != null){
            log.info("searchShelterList SUCCESS");
            model.addAttribute("userShelterListInfoDtos", userShelterListInfoDtos);
        } else {
            log.info("searchShelterList FAIL");

        }

        return nextPage;
        
    }

    @GetMapping("/pets_list")
    public String petsList(Model model, PetsUserDto petsUserDto,
                           @RequestParam("s_no") String s_no,
                           @RequestParam(required = false, value = "searchOption")String searchOption,
                           @RequestParam(required = false, value = "sNameInput")String searchInput) {
        log.info("petsList()");

        String nextPage = "admin/pets/user/user_pets_list";

        List<PetsUserDto> petsUserDtos = petsUserService.searchPetsList(s_no, searchOption, searchInput);

        if(petsUserDtos != null) {
            log.info("searchPetsList SUCCESS");
            model.addAttribute("petsUserDtos", petsUserDtos);

        } else {
            log.info("searchPetsList FAIL");

        }
        return nextPage;

    }

    //보호 동물 전체 리스트 -> 메뉴바에서 보호 동물 클릭 시 나타나는 페이지
    @GetMapping("/all_pets_list")
    public String allPetsList(Model model) {
        log.info("allPetsList()");

        String nextPage = "admin/pets/user/user_pets_list";

        List<PetsUserDto> petsUserDtos = petsUserService.searchAllPetsList();

        model.addAttribute("petsUserDtos", petsUserDtos);

        return nextPage;

    }

    //보호 동물 상세 페이지
    @GetMapping("/pets_list_detail")
    public String petsListDetail(Model model, PetsUserDto petsUserDto, HttpSession session, @RequestParam("an_no") String an_no) {
        log.info("petsListDetail()");

        String nextPage = "admin/pets/user/user_pets_list_detail";

        petsUserDto = petsUserService.searchPetsListDetail(an_no);

        session.setAttribute("petsUserDto", petsUserDto);
        model.addAttribute("petsUserDto", petsUserDto);

        return nextPage;

    }

    // 보호 동물 입양 문의
    @GetMapping("/adopt_pets_form")
    public String adoptPetsConfirm(Model model,
                                   @RequestParam("s_name") String s_name) {
        log.info("adoptPetsConfirm()");

        String nextPage = "admin/pets/user/user_adopt_pets_form";

        PetsUserDto petsUserDto = petsUserService.searchShelterInfo(s_name);
        model.addAttribute("petsUserDto", petsUserDto);

        return nextPage;

    }

}
