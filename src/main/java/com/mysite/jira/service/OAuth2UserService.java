package com.mysite.jira.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Account;
import com.mysite.jira.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService{
	private final AccountRepository accountRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

        // Role generate
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");

        // nameAttributeKey
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        // db 저장
        String email = (String) ((Map<String, Object>) attributes.get("kakao_account")).get("email");
        String name = (String) ((Map<String, Object>) attributes.get("properties")).get("nickname");
        String kakaoKey = attributes.get("id").toString();
        System.out.println(email + " " + name + " " + kakaoKey);
        
        Optional<Account> optAccount = this.accountRepository.findByEmail(email);
        
        if(optAccount.isEmpty()) {
        	Account user = Account.builder()
					.name(name)
					.email(email)
					.pw(null)
					.authCode(null)
					.kakaoSocialKey(kakaoKey)
					.naverSocialKey(null)
					.build();
			this.accountRepository.save(user);
			
			user.updateAccount(null, null);
			this.accountRepository.save(user);
        }
        
        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
	}
}
