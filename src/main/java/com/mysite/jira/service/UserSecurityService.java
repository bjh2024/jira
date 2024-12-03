package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mysite.jira.dto.account.AccountRole;
import com.mysite.jira.entity.Account;
import com.mysite.jira.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
	private final AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Account> existUser = this.accountRepository.findByEmail(email);
		if(existUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		Account user = existUser.get();
		if(user.getAuthCode() != null) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		if ("admin".equals(email)) {
            authorities.add(new SimpleGrantedAuthority(AccountRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(AccountRole.USER.getValue()));
        }
		return new User(user.getEmail(), user.getPw(), authorities);
	}

}
