package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateStudentGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateTeacherGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Schema(description = "Information about user")
public class UserInfoDto {

    @Null(groups = OnCreateGroup.class, message = "User's id should be empty")
    @NotNull(groups = {OnUpdateGroup.class, OnCreateTeacherGroup.class, OnCreateStudentGroup.class}, message = "User's id should be filled")
    @Schema(description = "User's id")
    private Long id;

    @NotBlank(message = "Name should be filled")
    @Size(max = 50, message = "Max length of name is {max}")
    @Schema(description = "Name")
    private String name;

    @NotBlank(message = "Surname should be filled")
    @Size(max = 50, message = "Max length of name is {max}")
    @Schema(description = "Surname")
    private String surname;

    @NotBlank(message = "Email should be filled")
    @Size(max = 50, message = "Max length of name is {max}")
    @Schema(description = "Email")
    private String email;

    @NotBlank(message = "Password should be filled")
    @Size(max = 75, message = "Max length of password is {max}")
    @Schema(description = "Password")
    private String password;

    @NotNull(message = "Role of user should be filled")
    @Schema(description = "Role of user")
    private UserInfo.Role role;

}
