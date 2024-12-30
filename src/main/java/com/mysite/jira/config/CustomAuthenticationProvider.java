package com.mysite.jira.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mysite.jira.entity.Account;
import com.mysite.jira.service.AccountService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        Account account = accountService.getAccountByEmail(email);
        
        if (account == null || !passwordEncoder.matches(authentication.getCredentials().toString(), account.getPw())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        if (account.getAuthCode() != null) {
        	throw new LockedException("Account is not authorized to login");
        }

        return new UsernamePasswordAuthenticationToken(email, password, authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
