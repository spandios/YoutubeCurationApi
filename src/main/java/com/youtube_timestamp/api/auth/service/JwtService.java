package com.youtube_timestamp.api.auth.service;

import com.youtube_timestamp.api.auth.dto.AuthDto;
import com.youtube_timestamp.api.common.exception.UnAuthorizedException;
import com.youtube_timestamp.api.user.entity.UserEntity;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

    public String createAccessToken(UserEntity user) {
        return createToken(user, plusHour(accessTokenExpiredHour));
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
        return (tokenEmail.equals(email)) && !isTokenExpired(token);
    }

    public String getEmailFromToken(String token) throws JwtException {
        Claims claims = tokenToClaims(token);
        return claims.get("email").toString();
    }

    private Boolean isTokenExpired(String token) {
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
        } catch (ExpiredJwtException e) {
            throw new UnAuthorizedException("Token Expired");
        } catch (Exception e) {
            System.out.println(" Some other exception in JWT parsing ");
        }
        return null;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Date plusHour(int hour) {
        return Date.from(LocalDateTime.now().plusHours(hour).atZone(ZoneId.systemDefault()).toInstant());
    }


}
