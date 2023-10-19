package com.btc.thewayhome.admin.pets.user;

import com.btc.thewayhome.admin.pets.admin.AdminShelterListInfoDto;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class PetsUserService implements IPetsUserService {

    @Autowired
    IPetsUserDaoMapper iPetsUserDaoMapper;

    @Override
    public void petsRegistInfo(String responseString, PetsUserDto petsAdminDto) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(responseString);

            JSONObject parseResponse = (JSONObject) jsonObj.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");

            JSONObject items = (JSONObject) parseBody.get("items"); // items is a JSONObject

            JSONArray array = (JSONArray) items.get("item"); // item is a JSONArray inside items

            for (int i = 0; i < array.size(); i++) {
                JSONObject jObj = (JSONObject) array.get(i);
//                petsAdminDto.setS_name(jObj.get("careNm").toString());
//                petsAdminDto.setS_phone(jObj.get("careTel").toString());
//                petsAdminDto.setS_address(jObj.get("careAddr").toString());
//                iPetsAdminDaoMapper.insertPetsInfo(shelterInfoDto);

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //보호소 리스트
    @Override
    public List<UserShelterListInfoDto> searchShelterList(String searchOption, String searchInput) {
        log.info("searchShelterList()");

        return iPetsUserDaoMapper.selectShelter(searchOption, searchInput);

    }

    //보호 동물 리스트 -> 보호소 리스트 상세 페이지에서 보호소명 클릭 시 나타나는 페이지
    @Override
    public List<PetsUserDto> searchPetsList(String s_no, String searchOption, String searchInput) {
        log.info("searchShelterList()");

            log.info("11111 : "+ s_no);
        if(searchInput == null){
            log.info("22222 : "+ searchInput);
        }else {
            log.info("33333 : "+ searchInput);

        }

        List<PetsUserDto> petsUserDtos = iPetsUserDaoMapper.selectPets(s_no, searchOption, searchInput);

        return petsUserDtos;
        
    }

    //보호 동물 리스트 - 메뉴바에서 보호 동물 클릭 시 나타나는 페이지
    @Override
    public List<PetsUserDto> searchAllPetsList() {
        log.info("searchAllPetsList()");

        List<PetsUserDto> petsUserDtos = iPetsUserDaoMapper.selectAllPets();

        return petsUserDtos;

    }

    //보호 동물 상세 페이지
    @Override
    public PetsUserDto searchPetsListDetail(String an_no) {
        log.info("searchPetsListDetail()");

        PetsUserDto petsUserDto = iPetsUserDaoMapper.selectPetsListDetail(an_no);

        return petsUserDto;

        }

    // 보호소 검색엔진
    @Override
    public List<UserShelterListInfoDto> shelterSearchBoxConfirm(UserShelterListInfoDto userShelterListInfoDto) {
        log.info("searchPetsListDetail()");

        List<UserShelterListInfoDto> userShelterListInfoDtos = iPetsUserDaoMapper.shelterSelectBoxBySearch(userShelterListInfoDto);

        return userShelterListInfoDtos;

    }

    // 보호동물 검색엔진
    public List<UserPetsListInfoDto> petsSearchBoxConfirm(UserPetsListInfoDto userPetsListInfoDto) {
        log.info("petsSearchBoxConfirm()");

        return iPetsUserDaoMapper.petsSelectBoxBySearch(userPetsListInfoDto);

    }

    // 보호소 정보 상세 페이지
    @Override
    public PetsUserDto searchShelterInfo(String s_name) {
        log.info("searchShelterInfo()");

        Map<String, Object> map = new HashMap<>();
        PetsUserDto petsUserDto = iPetsUserDaoMapper.selectShelterDetail(s_name);

        return petsUserDto;

    }
}
