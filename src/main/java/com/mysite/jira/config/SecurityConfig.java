
package com.mysite.jira.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers("/account/**").permitAll() 
            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
            .anyRequest().authenticated()
        ) 
        .headers((headers) -> headers
            .addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
        )
        .formLogin((formLogin) -> formLogin
            .loginPage("/account/login")
            .successHandler(new CustomAuthenticationSuccessHandler())
            .defaultSuccessUrl("/project/summation")
		)
        .logout((logout) -> logout
			.logoutRequestMatcher(new AntPathRequestMatcher("/account/logout"))
	        .logoutSuccessUrl("/account/login?logout") // 로그아웃 성공 후 리다이렉트할 URL
	        .invalidateHttpSession(true)
	        .deleteCookies("JSESSIONID")
	        .permitAll()
        )
        .sessionManagement(sessionManagement -> sessionManagement
	        .maximumSessions(1)
	        .maxSessionsPreventsLogin(false) // 최대 세션수를 넘어가도 로그인 허용
	        .expiredUrl("/account/login")
		)
    ;
	    return http.build();
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
}