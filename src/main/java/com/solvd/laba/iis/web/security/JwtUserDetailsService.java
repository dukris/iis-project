package com.solvd.laba.iis.web.security;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userService.retrieveByEmail(email);
        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }

}
