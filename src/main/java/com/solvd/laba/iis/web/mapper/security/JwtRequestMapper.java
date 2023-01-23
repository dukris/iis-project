package com.solvd.laba.iis.web.mapper.security;

import com.solvd.laba.iis.domain.security.JwtRequest;
import com.solvd.laba.iis.web.dto.security.JwtRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtRequestMapper {

    JwtRequestDto entityToDto(JwtRequest jwtRequest);

    JwtRequest dtoToEntity(JwtRequestDto jwtRequestDto);

}
