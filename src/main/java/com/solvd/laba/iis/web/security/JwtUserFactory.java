package com.solvd.laba.iis.web.security;

import com.solvd.laba.iis.domain.UserInfo;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public final class JwtUserFactory {

    public static JwtUser create(UserInfo userInfo){
        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(userInfo.getId());
        jwtUser.setEmail(userInfo.getEmail());
        jwtUser.setPassword(userInfo.getPassword());
        jwtUser.setGrantedAuthorities(getGrantedAuthorities(userInfo.getRole()));
        return jwtUser;
    }

    public static JwtUser create(Claims claims){
        JwtUser jwtUser = new JwtUser();
        jwtUser.setEmail(claims.getSubject());
        return jwtUser;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(UserInfo.Role role){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

}
