package com.mysite.jira.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class KakaoLogoutHandler {

	@Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public void handleLogout(HttpServletRequest request, HttpServletResponse response, Object authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;
            String provider = oAuth2Token.getAuthorizedClientRegistrationId();

            if ("kakao".equals(provider)) {
                OAuth2AuthorizedClient client = authorizedClientService
                        .loadAuthorizedClient(oAuth2Token.getAuthorizedClientRegistrationId(), oAuth2Token.getName());

                if (client != null && client.getAccessToken() != null) {
                    String accessToken = client.getAccessToken().getTokenValue();

                    // Call Kakao logout API
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + accessToken);

                    HttpEntity<String> entity = new HttpEntity<>(headers);
                    try {
                        restTemplate.exchange(
                                "https://kapi.kakao.com/v1/user/logout",
                                HttpMethod.POST,
                                entity,
                                String.class
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
