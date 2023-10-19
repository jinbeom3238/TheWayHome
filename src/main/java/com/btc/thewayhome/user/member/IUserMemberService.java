package com.btc.thewayhome.user.member;


public interface IUserMemberService {

    //회원 가입
    public int createAccountConfirm(UserMemberDto userMemberDto);

    //회원 정보 수정
    public UserMemberDto userMemberModifyConfirm(UserMemberDto userMemberDto);

    //비밀번호 변경
    public UserMemberDto userMemberPasswordModifyConfirm(UserMemberDto userMemberDto, String currentPw, String changePw);

    //계정 삭제
    public int userMemberDeleteConfirm(int uMNo);

}
