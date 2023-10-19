package com.btc.thewayhome.config;

import com.btc.thewayhome.admin.member.AdminMemberDto;
import com.btc.thewayhome.admin.member.IAdminMemberDaoMapper;
import com.btc.thewayhome.user.member.IUserMemberDaoMapper;
import com.btc.thewayhome.user.member.UserMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MyAdminDetailsService implements UserDetailsService {

    @Autowired
    IAdminMemberDaoMapper iAdminMemberDaoMapper;

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        log.info("loadUserByUsername()");

        log.info("adminname: " + adminName);

        AdminMemberDto adminMemberDto = new AdminMemberDto();
        adminMemberDto.setA_m_id(adminName);

        AdminMemberDto selectedAdminMemberDto = iAdminMemberDaoMapper.selectAdminForLogin(adminMemberDto);

        return User.builder()
                .username(selectedAdminMemberDto.getA_m_id())
                .password(selectedAdminMemberDto.getA_m_pw())
                .roles("ADMIN")
                .build();
    }
}
