package com.btc.thewayhome.admin.member;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminMemberDaoMapper {

    // 보호소 고유번호 중복 체크
    public boolean isShelterNameForNum(ShelterNumDto shelterNumDto);

    // 보호소 고유번호와 보호소 명을 DB에 넣음
    public int insertShelterNum(ShelterNumDto shelterNumDto);

    // 보호소 명 중복 체크
    public boolean isShelterNameForInfo(ShelterInfoDto shelterInfoDto);

    // 보호소 명, 보호소 주소, 보호소전화번호를 DB에 넣음
    public void insertShelterInfo(ShelterInfoDto shelterInfoDto);

    // 보호소 명으로 비동기 통신 하기 위함
    public List<ShelterSearchDto> selectSearchShelterName(String ShelterName);

    // 보호소 고유번호로 비동기 통신 하기 위함
    public List<ShelterSearchDto> selectSearchShelterNo(String ShelterNo);

    // 보호소 주소로 비동기 통신 하기 위함
    public List<ShelterSearchDto> selectSearchShelterAddress(String ShelterAddress);

    // 보호소 전화번호로 비동기 통신 하기 위함
    public List<ShelterSearchDto> selectSearchShelterPhone(String ShelterPhone);

    // 관리자 회원가입 - 중복체크
    public boolean isAdmin(Map<String, String> isAdminMap);

    // 관리자 회원가입 - 계정생성
    public int insertNewAccount(AdminMemberDto adminMemberDto);

    // 관리자 로그인
    public AdminMemberDto selectAdminForLogin(AdminMemberDto adminMemberDto);

    // 관리자 정보 수정
    public int updateAccount(AdminMemberDto adminMemberDto);

    // 관리자 정보 수정 - 최신화
    public AdminMemberDto getLatestAccountInfo(AdminMemberDto adminMemberDto);

    // 관리자 비밀번호 변경
    int updateAdminMemberPassword(AdminMemberDto adminMemberDto);

    // 관리자 계정 삭제
    public int deleteAdmin(int a_m_no);

    //관리자 정보 리스트
    public List<AdminMemberDto> selectAdminForApproval();

    //관리자 승인 처리
    public String selectAdminForApprovalFromNo(int a_m_no);  // approval 값 받기 위한 메서드
    public int updateAdminForApporoval(int a_m_no);  // approval이 1일 때 0으로, 0일 때 1으로 바꿈으로써 승인완료, 승인대기로 변경해줌

}
