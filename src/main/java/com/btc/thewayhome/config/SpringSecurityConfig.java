package com.btc.thewayhome.config;

import com.btc.thewayhome.admin.member.AdminMemberDto;
import com.btc.thewayhome.admin.member.IAdminMemberDaoMapper;
import com.btc.thewayhome.user.member.IUserMemberDaoMapper;
import com.btc.thewayhome.user.member.UserMemberDto;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.PrintWriter;

@Log4j2
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	IUserMemberDaoMapper iUserMemberDaoMapper;

	@Autowired
	IAdminMemberDaoMapper iAdminMemberDaoMapper;

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Autowired
	MyAdminDetailsService myAdminDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain filterChainForUser(HttpSecurity http) throws Exception {
		log.info("filterChain");

		http.csrf().disable()
				.cors().disable()
				.authorizeHttpRequests(request -> request
						.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()  // HTTP 요청 인증 설정
						.requestMatchers("/css/**", "/error/**", "/img/**", "/js/**", "", "/",
								"/user/member/create_account_form",
                                "/user/member/create_account_confirm",
								"/user/board/review_board",
                                "/user/board/review_detail",
								"/user/board",
								"/user/board/free_board_list",
								"/user/board/free_board_detail",
								"/UploadImg/**",
								"/user/comment/review_detail_json",
								"/user/pets/list",
								"/user/pets/all_pets_list",
								"/user/pets/shelter_list"
								).permitAll()
						.anyRequest().authenticated()  // 해당 경로 외의 요청은 모두 인증 필요
				)
				.formLogin(login -> login  // 로그인 시 폼(form)을 이용
						.loginPage(
								"/user/member/member_login_form"
						)  // 로그인 시 폼(form) 주소 설정
						.loginProcessingUrl("/user/member/member_login_confirm")
						.usernameParameter("u_m_id")
						.passwordParameter("u_m_pw")
						.successHandler((request, response, authentication) -> {  // 로그인 성공 시(추가 구현 예정: 로그인 페이지로 오기 이전에 있던 페이지로 이동)
							log.info("successHandler!!");

							UserMemberDto userMemberDto = new UserMemberDto();
							userMemberDto.setU_m_id(authentication.getName());
							UserMemberDto loginedUserMemberDto = iUserMemberDaoMapper.selectUserForLogin(userMemberDto);

							HttpSession session = request.getSession();
							session.setAttribute("loginedUserMemberDto", loginedUserMemberDto);
							session.setMaxInactiveInterval(60 * 30);

							log.info("--> {}", authentication.isAuthenticated());

							response.sendRedirect("/");

						})
						.failureHandler((request, response, exception) -> {		//로그인 실패 시(추가 구현 예정: 아이디 혹은 비밀번호를 다시 확인해주세요 알림)
							log.info("failureHandler!!");
							response.sendRedirect("/user/member/member_login_form");

						})
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/user/member/member_logout_confirm")
						.logoutSuccessHandler((request, response, authentication) -> {
							log.info("logoutSuccessHandler!!");

							HttpSession session = request.getSession();
							session.invalidate();	//세션 데이터 삭제

							response.sendRedirect("/");

						})
				)
				.userDetailsService(myUserDetailsService)	//사용자 정보 들고옴
				.sessionManagement()
				.maximumSessions(1)
				.maxSessionsPreventsLogin(false);

		return http.build();
	}

	@Bean
	@Order(1)
	public SecurityFilterChain filterChainForAdmin(HttpSecurity http) throws Exception {
		log.info("filterChainForAdmin");

		http.csrf().disable()
				.cors().disable()
				.securityMatcher("/admin/**")  // "/admin/**" 경로 보안 설정
				.authorizeHttpRequests(request -> request
						.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
						.requestMatchers("/css/**", "/error/**", "/img/**", "/js/**", "", "/",
								"/admin/member/create_account_form", "/admin/member/create_account_confirm", "/admin/pets/shelter_list").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(login -> login
						.loginPage("/admin/member/member_login_form")
						.loginProcessingUrl("/admin/member/member_login_confirm")
						.usernameParameter("a_m_id")
						.passwordParameter("a_m_pw")
						.successHandler((request, response, authentication) -> {
							log.info("successHandler!!");

							AdminMemberDto adminMemberDto = new AdminMemberDto();
							adminMemberDto.setA_m_id(authentication.getName());
							AdminMemberDto loginedAdminMemberDto = iAdminMemberDaoMapper.selectAdminForLogin(adminMemberDto);

							HttpSession session = request.getSession();
							session.setAttribute("loginedAdminMemberDto", loginedAdminMemberDto);
							session.setMaxInactiveInterval(60 * 30);

							log.info("--> {}", authentication.isAuthenticated());

							response.sendRedirect("/admin/");

						})
						.failureHandler((request, response, exception) -> {
							response.setContentType("text/html; charset=utf-8");
							PrintWriter out = response.getWriter();
							out.println("<script>");
							out.println("alert('관리자의 승인이 필요합니다.');");
							out.println("history.back();");
							out.println("</script>");
							out.flush();
							response.sendRedirect("/admin/member/member_login_form");

						})
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/admin/member/member_logout_confirm")
						.logoutSuccessHandler((request, response, authentication) -> {
							log.info("logoutSuccessHandler!!");

							HttpSession session = request.getSession();
							session.invalidate();

							response.sendRedirect("/admin/");

						})
				)
				.userDetailsService(myAdminDetailsService)
				.sessionManagement()
				.maximumSessions(1)
				.maxSessionsPreventsLogin(false);

		return http.build();
	}



}


