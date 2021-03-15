package com.youtube_timestamp.api.auth.service;

import com.youtube_timestamp.api.auth.dto.AuthDto;
import com.youtube_timestamp.api.user.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    @Value("${jwt.secret_key}")
    private String key; // 토큰 암호화키
    private static final int accessTokenExpiredHour = 1;
    private static final int refreshTokenExpiredDay = 24;


    public AuthDto.JwtResponse createToken(UserEntity user) {
        String accessToken = this.createAccessToken(user);
        String refreshToken = this.createRefreshToken(user);
        return new AuthDto.JwtResponse(accessToken, refreshToken);
    }

    public String getTokenFromHeader(HttpServletRequest request){
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
        return token;
    }

    public String createAccessToken(UserEntity user) {
        return createToken(user, plusMinute(accessTokenExpiredHour));
    }

    public String createRefreshToken(UserEntity user) {
        return createToken(user, plusHour(refreshTokenExpiredDay));
    }

    private String createToken(UserEntity user, Date expiryDate) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setIssuer("yc")
                .claim("email", user.getEmail())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Boolean validateToken(String token, String email) {
        final String tokenEmail = getEmailFromToken(token);
        if(tokenEmail == null){
            return false;
        }
        return (tokenEmail.equals(email)) && !isTokenExpired(token);
    }

    public String getEmailFromToken(String token) {
        Claims claims = tokenToClaims(token);
        if(claims == null || claims.get("email") == null){
            return null;
        }
        return claims.get("email").toString();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return tokenToClaims(token).getExpiration();
    }

    public Claims tokenToClaims(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Date plusHour(int hour) {
        return Date.from(LocalDateTime.now().plusHours(hour).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date plusMinute(int m) {
        return Date.from(LocalDateTime.now().plusMinutes(m).atZone(ZoneId.systemDefault()).toInstant());
    }


}
