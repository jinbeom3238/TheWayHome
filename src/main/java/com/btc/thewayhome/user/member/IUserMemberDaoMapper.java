package com.btc.thewayhome.user.member;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface IUserMemberDaoMapper {

    //아이디 중복 검사
    public boolean isMember(String u_m_id);

    //회원가입 유저 정보 입력
    public int insertUserMember(UserMemberDto userMemberDto);

    //로그인
    public UserMemberDto selectUserForLogin(UserMemberDto userMemberDto);

    //회원 정보 수정
    public int updateUserMember(UserMemberDto userMemberDto);

    //비밀번호 변경
    public int updateUserMemberPassword(UserMemberDto userMemberDto);

    //회원 정보 최신화
    public UserMemberDto getLatestMemberInfo(UserMemberDto userMemberDto);

    //계정 삭제
    public int deleteUserMember(int u_m_no);

}
