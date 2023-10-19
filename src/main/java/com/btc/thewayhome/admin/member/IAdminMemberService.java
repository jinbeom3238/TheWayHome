package com.btc.thewayhome.admin.member;

import java.util.List;
import java.util.Map;

public interface IAdminMemberService {

    // 보호소 고유번호와 보호소 명을 받기위한 메서드
    public void shelterRegistNum(List<String> shelterNumLists, List<String> shelterNameLists, ShelterNumDto shelterNumDto);

    // 보호소 명과, 보호소 전화번호, 보호소 주소를 받기 위한 메서드
    public void shelterRegistInfo(String result, ShelterInfoDto shelterInfoDto);

    // 보호소 명을 비동기 통신 하기 위한 서비스 메서드
    public Map<String, Object> searchShelterName(Map<String, String> shelterNameMap);

    // 보호소 고유번호를 비동기 통신 하기 위한 서비스 메서드
    public Map<String, Object> searchShelterNo(Map<String, String> shelterNoMap);

    // 보호소 주소를 비동기 통신 하기 위한 서비스 메서드
    public Map<String, Object> searchShelterAddress(Map<String, String> shelterAddressMap);

    // 보호소 전화번호를 비동기 통신 하기 위한 서비스 메서드
    public Map<String, Object> searchShelterPhone(Map<String, String> shelterPhoneMap);

    //관리자 회원가입
    public int createAccountConfirm(AdminMemberDto memberDto);

    //관리자 정보 수정
    public AdminMemberDto memberModifyConfirm(AdminMemberDto adminMemberDto);

    //관리자 비밀번호 변경
    public AdminMemberDto adminMemberPasswordModifyConfirm(AdminMemberDto adminMemberDto, String currentPw, String changePw);

    //관리자 계정 삭제
    public int memberDeleteConfirm(int a_m_no);

    //관리자 정보 리스트
    public List<AdminMemberDto> searchAdminList();

    //관리자 승인 처리
    public Map<String, Object> memberApprovalConfirm(int a_m_no);


}
