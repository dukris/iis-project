package com.solvd.laba.iis.web.mapper.security;

import com.solvd.laba.iis.domain.security.JwtRefreshRequest;
import com.solvd.laba.iis.web.dto.security.JwtRefreshRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtRefreshRequestMapper {

    JwtRefreshRequestDto entityToDto(JwtRefreshRequest jwtRefreshRequest);

    JwtRefreshRequest dtoToEntity(JwtRefreshRequestDto jwtRefreshRequestDto);

}
