package com.btc.thewayhome.admin.pets.admin;

import com.btc.thewayhome.admin.member.AdminMemberDto;
import com.btc.thewayhome.page.Criteria;
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
public class PetsAdminService implements IPetsAdminService{

    final static public int PETS_REGISTER_SUCCESS = 1;		// 신규 도서 등록 성공
    final static public int PETS_REGISTER_FAIL = -1;

    @Autowired
    IPetsAdminDaoMapper iPetsAdminDaoMapper;

    @Override
    public void petsRegistInfo(String responseString, PetsAdminDto petsAdminDto) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(responseString);

            JSONObject parseResponse = (JSONObject) jsonObj.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");

            JSONObject items = (JSONObject) parseBody.get("items"); // items is a JSONObject

            JSONArray array = (JSONArray) items.get("item"); // item is a JSONArray inside items

            for (int i = 0; i < array.size(); i++) {
                JSONObject jObj = (JSONObject) array.get(i);

                petsAdminDto.setAn_no(jObj.get("desertionNo").toString());
                petsAdminDto.setAn_thumbnail(jObj.get("filename").toString());
                petsAdminDto.setAn_happen_date(jObj.get("happenDt").toString());
                petsAdminDto.setAn_happen_place(jObj.get("happenPlace").toString());

                String kindCd = jObj.get("kindCd").toString();
//                petsAdminDto.setAn_k_kind(jObj.get("kindCd").toString());
                String[] splitKindCd = kindCd.split(" ");
                if (splitKindCd.length > 0) {
                    petsAdminDto.setAn_an_kind(splitKindCd[0]);
                    if(splitKindCd.length > 1){
                        StringBuilder an_k_kind = new StringBuilder();
                        for (int j = 1; j < splitKindCd.length; j++) {
                            if (j > 1) {
                                an_k_kind.append(" "); // 중간에 공백 추가
                            }
                            an_k_kind.append(splitKindCd[j]); // 나머지 한 글자를 이어붙임
                        }
                        petsAdminDto.setAn_k_kind(an_k_kind.toString());
                    }
                }

                log.info(petsAdminDto.getAn_k_kind());
                log.info(petsAdminDto.getAn_an_kind());

                petsAdminDto.setAn_color(jObj.get("colorCd").toString());
                petsAdminDto.setAn_age(jObj.get("age").toString());
                petsAdminDto.setAn_weight(jObj.get("weight").toString());
                petsAdminDto.setAn_n_start(jObj.get("noticeSdt").toString());
                petsAdminDto.setAn_n_end(jObj.get("noticeEdt").toString());
                petsAdminDto.setAn_image(jObj.get("popfile").toString());
                petsAdminDto.setAn_p_s_state(jObj.get("processState").toString());
                petsAdminDto.setAn_g_gender(jObj.get("sexCd").toString());
                petsAdminDto.setAn_ne_neuter(jObj.get("neuterYn").toString());
                petsAdminDto.setAn_special_mark(jObj.get("specialMark").toString());
                petsAdminDto.setS_name(jObj.get("careNm").toString());
                petsAdminDto.setS_phone(jObj.get("careTel").toString());
                petsAdminDto.setS_address(jObj.get("careAddr").toString());

                boolean isPetsNumInfo = iPetsAdminDaoMapper.isPetsNumInfo(petsAdminDto);

                if(!isPetsNumInfo) {
                    // 중복되는 유기번호가 없어야만 DB에 api데이터가 들어갈 수 있다.
                    iPetsAdminDaoMapper.insertPetsInfo(petsAdminDto);
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //보호소 리스트 -> SUPER, 일반 ADMIN에 따라 나눔
    @Override
    public List<AdminShelterListInfoDto> searchShelterList(AdminMemberDto loginedAdminMemberDto) {
        log.info("searchShelterList()");
//        Map<String, Object> map = new HashMap<>();

//        Criteria criteria = new Criteria(pageNum, amount);

//        List<AdminShelterListInfoDto> adminShelterListInfoDtos = iPetsAdminDaoMapper.pageList(criteria);
//
//        int total

        //일반 admin 찾기
        boolean isAdmin = iPetsAdminDaoMapper.isAdminMemberBasic(loginedAdminMemberDto.getA_m_id(), loginedAdminMemberDto.getA_m_approval());

        List<AdminShelterListInfoDto> adminShelterListInfoDtos;

        if(isAdmin) {
            log.info("isAdmin()");

            // 일반 ADMIN인 경우
            adminShelterListInfoDtos = iPetsAdminDaoMapper.selectShelter(loginedAdminMemberDto);

        } else {
            //super 계정 찾기
            boolean isSuper = iPetsAdminDaoMapper.isAdminMemberSuper(loginedAdminMemberDto.getA_m_approval());

            if (isSuper) {
                log.info("isSuper()");
                // SUPER ADMIN인 경우
                adminShelterListInfoDtos = iPetsAdminDaoMapper.selectShelterSuper();

            } else {
                return null;

            }

        }
        return adminShelterListInfoDtos;

    }

    //보호 동물 리스트 - 보호소 리스트 상세 페이지
    @Override
    public List<PetsAdminDto> searchPetsList(String s_no, String searchOption, String searchInput) {
        log.info("searchShelterList()");

        List<PetsAdminDto> petsAdminDtos = iPetsAdminDaoMapper.selectPets(s_no, searchOption, searchInput);

        return petsAdminDtos;

    }

    // 보호 동물 리스트 - 메뉴바에서 보호 동물 클릭 시 나타나는 페이지
    @Override
    public List<PetsAdminDto> searchAllPetsList(AdminMemberDto loginedAdminMemberDto) {
        log.info("searchPetsListBasic()");

        //일반 admin 찾기
        boolean isAdmin = iPetsAdminDaoMapper.isAdminMemberBasic(loginedAdminMemberDto.getA_m_id(), loginedAdminMemberDto.getA_m_approval());

        List<PetsAdminDto> petsAdminDtos;

        if(isAdmin) {
            // 일반 ADMIN인 경우
            petsAdminDtos = iPetsAdminDaoMapper.selectAllPets(loginedAdminMemberDto);

        } else {
            //super 계정 찾기
            boolean isSuper = iPetsAdminDaoMapper.isAdminMemberSuper(loginedAdminMemberDto.getA_m_approval());

            if (isSuper) {
                // SUPER ADMIN인 경우
                petsAdminDtos = iPetsAdminDaoMapper.selectAllPetsSuper(loginedAdminMemberDto);

            } else {
                return null;

            }
        }
        return petsAdminDtos;

    }

    // 보호 동물 상세 페이지
    @Override
    public PetsAdminDto searchPetsListDetail(String an_no) {
        log.info("searchPetsListDetail()");

        PetsAdminDto petsAdminDto = iPetsAdminDaoMapper.selectPetsListDetail(an_no);

        return petsAdminDto;

    }

    // 보호 동물 등록 성공 or 실패 확인
    @Override
    public int petsRegistConfirm(PetsAdminDto petsAdminDto) {
        log.info("petsRegistConfirm()");

        int result = iPetsAdminDaoMapper.registPets(petsAdminDto);

        if(result > 0){
            return PETS_REGISTER_SUCCESS;

        } else {
            return PETS_REGISTER_FAIL;

        }
    }

    // 보호 동물 수정 시 수정 전 정보 가져오기
    @Override
    public PetsAdminDto modifyPetsForm(String an_no) {

        PetsAdminDto petsAdminDto = iPetsAdminDaoMapper.selectPetsForModify(an_no);
        return petsAdminDto;
    }


    // 보호 동물 수정 성공 or 실패 확인
    @Override
    public int modifyPetsConfirm(PetsAdminDto petsAdminDto) {
        log.info("modifyPetsConfirm()");

        return iPetsAdminDaoMapper.updatePets(petsAdminDto);

    }

    // 보호 동물 삭제
    @Override
    public int deletePetsConfirm(String an_no) {
        log.info("petsDeleteConfirm()");

        return iPetsAdminDaoMapper.deletePetsConfirm(an_no);

    }

}