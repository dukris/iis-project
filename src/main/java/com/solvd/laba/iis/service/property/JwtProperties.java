package com.solvd.laba.iis.service.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@AllArgsConstructor
@Getter
public class JwtProperties {

    private final String secret;
    private final Long access;
    private final Long refresh;

}
