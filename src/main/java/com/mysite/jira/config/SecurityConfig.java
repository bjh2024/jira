package com.mysite.jira.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mysite.jira.service.OAuth2UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private SocialAuthenticationHandler socialAuthenticationHandler;
	
	@Autowired
	private OAuth2UserService oAuth2UserService;
	
	@Autowired
	private KakaoLogoutHandler kakaoLogoutHandler;
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers("/account/**").permitAll() 
            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
            .anyRequest().authenticated()
        ).headers((headers) -> headers
            .addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
        ).csrf((csrf) -> csrf.disable())
        .formLogin((formLogin) -> formLogin
            .loginPage("/account/login")
            .successHandler(customAuthenticationSuccessHandler)
		).logout((logout) -> logout
			.logoutRequestMatcher(new AntPathRequestMatcher("/account/logout"))
			.logoutSuccessHandler((request, response, authentication) -> {
			    kakaoLogoutHandler.handleLogout(request, response, authentication);
			    response.sendRedirect("/account/login?logout"); // 로그아웃 성공 후 리다이렉트
			})
	        .logoutSuccessUrl("/account/login?logout") // 로그아웃 성공 후 리다이렉트할 URL
	        .invalidateHttpSession(true)
	        .deleteCookies("JSESSIONID")
	        .permitAll()
        ).sessionManagement(sessionManagement -> sessionManagement
	        .maximumSessions(1)
	        .maxSessionsPreventsLogin(false) // 최대 세션수를 넘어가도 로그인 허용
	        .expiredUrl("/account/login")
		).oauth2Login(oauth2Configurer -> oauth2Configurer
                .loginPage("/login")
                .successHandler(socialAuthenticationHandler)
                .userInfoEndpoint()
                .userService(oAuth2UserService));
	    return http.build();
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}