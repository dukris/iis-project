package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.web.dto.validation.UpdateGroup;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class GroupDto {
    @NotNull(groups = UpdateGroup.class, message = "Group's id should be filled")
    private long id;
    @NotNull(message = "Number of group should be filled")
    private int number;
}
