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
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

        // nameAttributeKey
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
        	// naver 환경
        	String name = (String) ((Map) attributes.get("response")).get("name");
            String email = (String) ((Map) attributes.get("response")).get("email");
            String naverKey = (String) ((Map) attributes.get("response")).get("id");
        	
        	Optional<Account> optAccount = this.accountRepository.findByEmail(email);
        	
        	// 데이터베이스에 해당 계정이 존재하는지 확인하고, 없으면 생성
        	if(optAccount.isEmpty()) {
        		Integer iconNum = (int) (Math.random() * 10) + 1;
        		
        		Account user = Account.builder()
        				.name(name)
        				.email(email)
        				.pw(null)
        				.iconFilename("user_icon_file"+iconNum+".png")
        				.authCode(null)
        				.kakaoSocialKey(null)
        				.naverSocialKey(naverKey)
        				.build();
        		this.accountRepository.save(user);
        		
        		user.updateAccount(null, null);
        		this.accountRepository.save(user);
        	}
        	
        	return new DefaultOAuth2User(authorities, Map.of("id", naverKey), "id");
        }else {
        	// kakao 환경
        	String email = (String) ((Map<String, Object>) attributes.get("kakao_account")).get("email");
        	String name = (String) ((Map<String, Object>) attributes.get("properties")).get("nickname");
        	String kakaoKey = attributes.get("id").toString();
        	
        	Optional<Account> optAccount = this.accountRepository.findByEmail(email);
        	
        	// 데이터베이스에 해당 계정이 존재하는지 확인하고, 없으면 생성
        	if(optAccount.isEmpty()) {
        		Integer iconNum = (int) (Math.random() * 10) + 1;
        		
        		Account user = Account.builder()
        				.name(name)
        				.email(email)
        				.pw(null)
        				.iconFilename("user_icon_file"+iconNum+".png")
        				.authCode(null)
        				.kakaoSocialKey(kakaoKey)
        				.naverSocialKey(null)
        				.build();
        		this.accountRepository.save(user);
        		
        		user.updateAccount(null, null);
        		this.accountRepository.save(user);
        	}
        	
        }
        
        return new DefaultOAuth2User(authorities, attributes, userNameAttributeName);
	}
}
