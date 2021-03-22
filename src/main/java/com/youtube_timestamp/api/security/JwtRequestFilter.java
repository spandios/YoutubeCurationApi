package com.youtube_timestamp.api.security;

import com.youtube_timestamp.api.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String token = jwtService.getTokenFromHeader(request);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            username = jwtService.getEmailFromToken(token);
            System.out.println("username"+ username);
            if (username != null) {
                UserDetails userDetails = this.jwtUserDetailService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails.getUsername())) {
                    SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(userDetails));
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return PermitWithNoTokenUrl.url.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }

}
