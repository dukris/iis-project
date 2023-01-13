package com.solvd.laba.iis.web.dto;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.web.dto.validation.OnCreateGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateStudentGroup;
import com.solvd.laba.iis.web.dto.validation.OnCreateTeacherGroup;
import com.solvd.laba.iis.web.dto.validation.OnUpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    @Null(groups = OnCreateGroup.class, message = "User's id should be empty")
    @NotNull(groups = {OnUpdateGroup.class, OnCreateTeacherGroup.class, OnCreateStudentGroup.class}, message = "User's id should be filled")
    private Long id;

    @NotBlank(message = "Name should be filled")
    @Size(max = 50, message = "Max length of name is {max}")
    private String name;

    @NotBlank(message = "Surname should be filled")
    @Size(max = 50, message = "Max length of name is {max}")
    private String surname;

    @NotBlank(message = "Email should be filled")
    @Size(max = 50, message = "Max length of name is {max}")
    private String email;

    @NotBlank(message = "Password should be filled")
    @Size(max = 75, message = "Max length of password is {max}")
    private String password;

    @NotNull(message = "Role of user should be filled")
    private UserInfo.Role role;

}
