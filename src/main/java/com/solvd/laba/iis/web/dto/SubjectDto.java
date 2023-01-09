package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.web.dto.validation.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SubjectDto {
    @NotNull(groups = UpdateGroup.class, message = "Subject's id should be filled")
    private long id;
    @NotBlank(message = "Name of subject should be filled")
    @Size(max = 50, message = "Max length of name of subject is 50")
    private String name;
}
