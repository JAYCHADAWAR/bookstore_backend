package com.bookstore.website.Interceptor;

import ch.qos.logback.classic.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bookstore.website.util.JwtUtil;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

// Extract token from request headers
        log.info("This is an INFO message");

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false;
        }

        // Remove "Bearer " prefix from token
        token = token.substring(7);

        try {
            // Verify token and extract user information
            UUID id = jwtUtil.getUserIdFromToken(token);
            request.setAttribute("id", id);
            return true;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
            return false;
        }
    }


}
