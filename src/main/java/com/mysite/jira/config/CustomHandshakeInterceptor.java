package com.mysite.jira.config;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
	@Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpSession session = getSession(request);
        Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
        if(jiraIdx != null) {
        	attributes.put("jiraIdx", jiraIdx);
        	return true;
        }
        return false;
    }

    @Nullable
    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(isCreateSession());
        }
        return null;
    }
}

	
