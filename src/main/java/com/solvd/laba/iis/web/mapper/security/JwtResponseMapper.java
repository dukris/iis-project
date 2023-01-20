package com.solvd.laba.iis.web.mapper.security;

import com.solvd.laba.iis.domain.security.JwtResponse;
import com.solvd.laba.iis.web.dto.security.JwtResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtResponseMapper {

    JwtResponseDto entityToDto(JwtResponse jwtResponse);

    JwtResponse dtoToEntity(JwtResponseDto jwtResponseDto);

}
