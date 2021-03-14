package com.youtube_timestamp.api.security;

import com.youtube_timestamp.api.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

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
        String token = null;


        final String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader == null) {
            Optional<Cookie> optionalCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("access_token")).findFirst();
            if (optionalCookie.isPresent()) {
                token = optionalCookie.get().getValue();
            }
        } else {
            if (requestTokenHeader.startsWith("Bearer ")) {
                token = requestTokenHeader.substring(7);
            }
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            username = jwtService.getEmailFromToken(token);
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
