package com.solvd.laba.iis.web.security;

import com.solvd.laba.iis.domain.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration.access}")
    private long accessExpiration;

    @Value("${jwt.expiration.refresh}")
    private long refreshExpiration;

    private SecretKey secret;

    @PostConstruct
    private void prepareKey() {
        secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String generateAccessToken(UserInfo user) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(accessExpiration).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(accessExpiration)
                .signWith(secret)
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("surname", user.getSurname())
                .claim("role", user.getRole())
                .compact();
        return token;
    }

    public String generateRefreshToken(UserInfo user) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusDays(refreshExpiration).atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpiration = Date.from(refreshExpirationInstant);
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(secret)
                .compact();
        return token;
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token);
        return true;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmail(token));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        return authenticationToken;
    }

    public String getEmail(String token) {
        String email = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return email;
    }

    public Long getId(String token) {
        Long id = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
        return id;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
