package com.btc.thewayhome.user.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserMemberService implements IUserMemberService {

    final static public int DATABASE_COMMUNICATION_TROUBLE = -1;
    final static public int INSERT_FAIL_AT_DATABASE = 0;
    final static public int INSERT_SUCCESS_AT_DATABASE = 1;

    @Autowired
    IUserMemberDaoMapper iUserMemberDaoMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 사용자 계정 생성
    public int createAccountConfirm(UserMemberDto userMemberDto) {
        log.info("createAccountConfirm()");

        boolean isMember = iUserMemberDaoMapper.isMember(userMemberDto.getU_m_id());

        if (!isMember) {

            userMemberDto.setU_m_pw(passwordEncoder.encode(userMemberDto.getU_m_pw()));

            int result = iUserMemberDaoMapper.insertUserMember(userMemberDto);

            switch (result) {
                case DATABASE_COMMUNICATION_TROUBLE:
                    log.info("DATABASE COMMUNICATION TROUBLE");
                    break;

                case INSERT_FAIL_AT_DATABASE:
                    log.info("INSERT FAIL AT DATABASE");
                    break;

                case INSERT_SUCCESS_AT_DATABASE:
                    log.info("INSERT SUCCESS AT DATABASE");
                    break;

            }
            return result;

        } else {
            return 0;

        }

    }

    // 사용자 계정 수정
    public UserMemberDto userMemberModifyConfirm(UserMemberDto userMemberDto) {
        log.info("userMemberModifyConfirm()");

        int result = iUserMemberDaoMapper.updateUserMember(userMemberDto);

        if (result > 0) {
            return iUserMemberDaoMapper.getLatestMemberInfo(userMemberDto);

        } else {
            return null;

        }

    }

    // 사용자 비밀번호 변경
    public UserMemberDto userMemberPasswordModifyConfirm(UserMemberDto userMemberDto, String currentPw, String changePw) {
        log.info("userMemberPasswordModifyConfirm()");

        UserMemberDto idVerifiedMemberDto = iUserMemberDaoMapper.selectUserForLogin(userMemberDto);

        if (idVerifiedMemberDto != null && !passwordEncoder.matches(passwordEncoder.encode(userMemberDto.getU_m_pw()),
                idVerifiedMemberDto.getU_m_pw())) {
            userMemberDto.setU_m_pw(passwordEncoder.encode(changePw));
            int result = iUserMemberDaoMapper.updateUserMemberPassword(userMemberDto);

            if (result > 0){
                return iUserMemberDaoMapper.getLatestMemberInfo(userMemberDto);

            } else{
                System.out.println("service false tp2");
                return null;

            }
        } else{
            System.out.println("service false tp1");
            return null;

        }

    }

    //사용자 계정 삭제
    public int userMemberDeleteConfirm(int u_m_no) {
        log.info("userMemeberDeleteConfirm()");

        return iUserMemberDaoMapper.deleteUserMember(u_m_no);

    }

}
