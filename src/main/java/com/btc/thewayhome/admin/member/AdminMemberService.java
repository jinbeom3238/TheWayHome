package com.btc.thewayhome.admin.member;

import com.btc.thewayhome.user.member.UserMemberDto;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class AdminMemberService implements IAdminMemberService {

    final static public int DATABASE_COMMUNICATION_TROUBLE = -1;
    final static public int INSERT_FAIL_AT_DATABASE = 0;
    final static public int INSERT_SUCCESS_AT_DATABASE = 1;

    @Autowired
    IAdminMemberDaoMapper iAdminMemberDaoMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 보호소 고유번호와 보호소 명을 받기위한 메서드
    @Override
    public void shelterRegistNum(List<String> shelterNumLists, List<String> shelterNameLists, ShelterNumDto shelterNumDto) {
        log.info("shelterRegistNum()");
        
        // 리스트에 담아준 api를 Dto에 담아주기 위한 반복문
        for(int i = 0; i < shelterNumLists.size(); i++){
            shelterNumDto.setS_no(shelterNumLists.get(i));
            shelterNumDto.setS_name(shelterNameLists.get(i));

            // 혹시나 DB에 현재 들어오는 보호소 고유번호와 들어가 있는 보호소 고유번호가 중복되는지 확인
            boolean isShelterNameForNum = iAdminMemberDaoMapper.isShelterNameForNum(shelterNumDto);

            if (!isShelterNameForNum) {  // 중복되는 값이 없어야 DB에 데이터 삽입
                iAdminMemberDaoMapper.insertShelterNum(shelterNumDto);

            }
        }
    }

    // 보호소 명과, 보호소 전화번호, 보호소 주소를 받기 위한 메서드
    @Override
    public void shelterRegistInfo(String result, ShelterInfoDto shelterInfoDto) {
        log.info("shelterRegistInfo()");

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
            JSONObject parseResponse = (JSONObject) jsonObj.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject items = (JSONObject) parseBody.get("items"); // items is a JSONObject
            JSONArray array = (JSONArray) items.get("item"); // item is a JSONArray inside items

            for (int i = 0; i < array.size(); i++) {
                JSONObject jObj = (JSONObject) array.get(i);
                shelterInfoDto.setS_name(jObj.get("careNm").toString());
                shelterInfoDto.setS_phone(jObj.get("careTel").toString());
                shelterInfoDto.setS_address(jObj.get("careAddr").toString());

                // 혹시나 DB에 현재 들어오는 보호소 이름과 들어가 있는 보호소 이름이 중복되는지 확인
                boolean isShelterNameForInfo = iAdminMemberDaoMapper.isShelterNameForInfo(shelterInfoDto);

                if (!isShelterNameForInfo) {
                    // 중복되는 값이 있으면 못 들어가게 한다.
                    iAdminMemberDaoMapper.insertShelterInfo(shelterInfoDto);

                }
            }
        } catch (ParseException e) {
            e.printStackTrace();

        }

    }

    // 보호소 명을 비동기 통신 하기 위한 서비스 메서드
    @Override
    public Map<String, Object> searchShelterName(Map<String, String> shelterNameMap) {
        log.info("[AdminMemberService] searchShelterName()");

        Map<String, Object> map = new HashMap<>();

        // map으로 받은 데이터에서 jquery를 통해 프론트에서 입력하는 word를 String타입으로 바꿔주기 위함 그리고 리스트에 담음
        List<ShelterSearchDto> shelterSearchDtos = iAdminMemberDaoMapper.selectSearchShelterName(shelterNameMap.get("word").toString());

        // 리스트에 담은 DB의 보호소명을 map에 넣어주기 위함
        map.put("shelterSearchDtos", shelterSearchDtos);

        return map;

    }

    // 보호소 고유번호를 비동기 통신 하기 위한 서비스 메서드
    @Override
    public Map<String, Object> searchShelterNo(Map<String, String> shelterNoMap) {
        log.info("[AdminMemberService] searchShelterNo()");

        Map<String, Object> map = new HashMap<>();

        List<ShelterSearchDto> shelterSearchDtos = iAdminMemberDaoMapper.selectSearchShelterNo(shelterNoMap.get("word").toString());

        map.put("shelterSearchDtos", shelterSearchDtos);

        return map;

    }

    // 보호소 주소를 비동기 통신 하기 위한 서비스 메서드
    @Override
    public Map<String, Object> searchShelterAddress(Map<String, String> shelterAddressMap) {
        log.info("[AdminMemberService] searchShelterAddress()");

        Map<String, Object> map = new HashMap<>();

        List<ShelterSearchDto> shelterSearchDtos = iAdminMemberDaoMapper.selectSearchShelterAddress(shelterAddressMap.get("word").toString());

        map.put("shelterSearchDtos", shelterSearchDtos);

        return map;

    }

    // 보호소 전화번호를 비동기 통신 하기 위한 서비스 메서드
    @Override
    public Map<String, Object> searchShelterPhone(Map<String, String> shelterPhoneMap) {
        log.info("[AdminMemberService] searchShelterPhone()");

        Map<String, Object> map = new HashMap<>();

        List<ShelterSearchDto> shelterSearchDtos = iAdminMemberDaoMapper.selectSearchShelterPhone(shelterPhoneMap.get("word").toString());

        map.put("shelterSearchDtos", shelterSearchDtos);

        return map;

    }


    // 회원가입을 위한 서비스 메서드
    @Override
    public int createAccountConfirm(AdminMemberDto adminMemberDto) {
        log.info("[AdminMemberService] createAccountConfirm()");

        Map<String, String> isAdminMap = new HashMap<>();

        // 회원 가입시 DB에 중복된 ID혹은 고유번호가 있는지 확인 하기 위해 map에 담아줌
        isAdminMap.put("a_m_id", adminMemberDto.getA_m_id());
        isAdminMap.put("s_no", adminMemberDto.getS_no());

        // 회원 가입시 DB에 중복된 ID 혹은 고유번호가 있는지 확인
        boolean isAdmin = iAdminMemberDaoMapper.isAdmin(isAdminMap);

        // 회원 가입시 DB에 중복된 ID 혹은 고유번호가 없으면 실행
        if (!isAdmin) {
            
            // 비밇번호는 passwordencoder를 통해 암호화된 비밀번호가 들어가도록 함
            adminMemberDto.setA_m_pw(passwordEncoder.encode(adminMemberDto.getA_m_pw()));
            
            // 회원 가입시 적은 데이터 DB에 삽입
            int result = iAdminMemberDaoMapper.insertNewAccount(adminMemberDto);

            switch (result) {
                case DATABASE_COMMUNICATION_TROUBLE:
                    log.info("DATABASE_COMMUNICATION_TROUBLE");
                    break;

                case INSERT_FAIL_AT_DATABASE:
                    log.info("INSERT_FAIL_AT_DATABASE");
                    break;

                case INSERT_SUCCESS_AT_DATABASE:
                    log.info("INSERT_SUCCESS_DATABASE");

                    break;

            }
            return result;

        } else {


            return INSERT_FAIL_AT_DATABASE; // 0

        }

    }

    // 관리자 정보 수정 기능
    @Override
    public AdminMemberDto memberModifyConfirm(AdminMemberDto adminMemberDto) {
        log.info("[AdminMemberService] memberModifyConfirm()");

        // 수정한 정보를 db에 update하기 위함
        int result = iAdminMemberDaoMapper.updateAccount(adminMemberDto);

        // 위의 updateAccount 메서드에서 update가 되었다면 정보를 가장 최신화 해주기 위한 작업
        if (result > 0) {
            return iAdminMemberDaoMapper.getLatestAccountInfo(adminMemberDto);

        } else {
            return null;

        }

    }

    @Override
    // 관리자 비밀번호 변경 기능
    public AdminMemberDto adminMemberPasswordModifyConfirm(AdminMemberDto adminMemberDto, String currentPw, String changePw) {
        log.info("adminMemberPasswordModifyConfirm()");

        AdminMemberDto loginedAdminMemberDto = iAdminMemberDaoMapper.selectAdminForLogin(adminMemberDto);

        if (loginedAdminMemberDto != null && !passwordEncoder.matches(passwordEncoder.encode(adminMemberDto.getA_m_pw()),
                loginedAdminMemberDto.getA_m_pw())) {
            
            adminMemberDto.setA_m_pw(passwordEncoder.encode(changePw));
            int result = iAdminMemberDaoMapper.updateAdminMemberPassword(adminMemberDto);

            if (result > 0){
                return iAdminMemberDaoMapper.getLatestAccountInfo(adminMemberDto);

            } else{
                log.info("service false");
                return null;

            }
        } else{
            log.info("service false");
            return null;

        }

    }

    // 관리자 계정 삭제 기능
    @Override
    public int memberDeleteConfirm(int am_no) {
        log.info("deleteConfirm()");

        return iAdminMemberDaoMapper.deleteAdmin(am_no);

    }

    // 관리자 정보 리스트 기능
    @Override
    public List<AdminMemberDto> searchAdminList() {
        log.info("[AdminMemberService] searchAdminList()");

        return iAdminMemberDaoMapper.selectAdminForApproval();

    }

    // 로그인을 위한 관리자 승인 처리 기능 (Super가 일반 관리자 승인시 로그인 가능)
    @Override
    public Map<String, Object> memberApprovalConfirm(int a_m_no) {
        log.info("[AdminMemberService] memberApprovalConfirm()");

        // 키, 값 쌍을 통해 원하는 값을 한번에 찾기 위해 Map 사용
        Map<String, Object> map = new HashMap<>();

        int result = -1;

        // update되면 1, 안되면 0 => 문제: ajax에서 if(result > 0) 값 변경으로 설정했으나, 승인대기/승인완료일 때 모두 변경해야 하는데 문제가 생김
        result = iAdminMemberDaoMapper.updateAdminForApporoval(a_m_no);     // 0 , 1

        //위의 문제점을 해결하기 위해 a_m_approval 값만 꺼낸 후, a_m_approval = 1, a_m_approval = 0일 때로 나눠줌
        if(result > 0)
            result = Integer.parseInt(iAdminMemberDaoMapper.selectAdminForApprovalFromNo(a_m_no)) ;

        map.put("result", result);

        return map;

    }


}
