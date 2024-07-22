package com.inventory.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.inventory.repositories.vo.UserVo;
import com.inventory.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // Authentication 객체에서 사용자 이름 가져오기
        String username = ((User) authentication.getPrincipal()).getUsername();
        
        // 사용자 정보를 가져오기 위해 UserService 사용
        UserVo authUser = userService.getUserByNameForLogin(username);
        
        // 로그인 정보 session에 기록
        HttpSession session = request.getSession();
        session.setAttribute("authUser", authUser);
        session.setAttribute("username", username);

        // 권한에 따른 리다이렉트 URL 결정
        String targetUrl = determineTargetUrl(authentication);
        response.sendRedirect(request.getContextPath() + targetUrl);
    }

    
    
    protected String determineTargetUrl(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        boolean isUser = authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
        boolean isGuest = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_GUEST"));

        if (isAdmin) {
            return "/admin/home";
        } else if (isUser) {
            return "/branch/inventory";
        } else if (isGuest) {
        	return "/user/waiting";
        }
        else {
            return "/users/authcode";
        }
    }
}
