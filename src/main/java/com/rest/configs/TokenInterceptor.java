package com.rest.configs;

import com.rest.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.*;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String token = (String) session.getAttribute("token");
            if (token != null) {
                try {
                    if (jwtService.isTokenExpired(token)) {
                        session.removeAttribute("token");
                        response.sendRedirect("/app/auth/login");
                        return false;
                    }
                }
                catch (ExpiredJwtException e) {
                    session.removeAttribute("token");
                    response.sendRedirect("/app/auth/login");
                    return false;
                }
            }
        }
        return true;
    }
}