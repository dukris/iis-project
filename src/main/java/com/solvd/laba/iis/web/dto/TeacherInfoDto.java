package com.solvd.laba.iis.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.solvd.laba.iis.web.dto.validation.UpdateGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherInfoDto {
    @NotNull(groups = UpdateGroup.class, message = "Teacher's id should be filled")
    private long id;
    @NotNull(message = "Teacher should contain user")
    @Valid
    private UserDto user;
    @Valid
    private List<SubjectDto> subjects;

}
