package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.service.JwtService;
import com.solvd.laba.iis.service.property.JwtProperties;
import com.solvd.laba.iis.web.security.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;
    private SecretKey secret;

    @PostConstruct
    private void prepareKey() {
        secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

    @Override
    public String generateAccessToken(UserInfo user) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(jwtProperties.getAccess()).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(accessExpiration)
                .signWith(secret)
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .compact();
    }

    @Override
    public String generateRefreshToken(UserInfo user) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusDays(jwtProperties.getRefresh()).atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(secret)
                .compact();
    }

    @Override
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Long getExpirationTime() {
        return jwtProperties.getAccess();
    }

    @Override
    public Authentication getAuthenticationForUser(JwtUser jwtUser) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUser.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
