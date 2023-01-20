package com.solvd.laba.iis.web;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebConfig {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.simpleDateFormat(DATE_FORMAT)
                .serializerByType(LocalDate.class,
                        new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .deserializerByType(LocalDate.class,
                        new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .serializerByType(LocalTime.class,
                        new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)))
                .deserializerByType(LocalTime.class,
                        new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

}


