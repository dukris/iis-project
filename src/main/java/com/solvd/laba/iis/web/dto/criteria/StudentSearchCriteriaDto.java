package com.solvd.laba.iis.web.dto.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "Criteria for searching students")
public class StudentSearchCriteriaDto {

    @Schema(description = "Name of speciality")
    private String speciality;

    @Schema(description = "Name of faculty")
    private String faculty;

    @Schema(description = "Year of admission")
    private Integer year;

}
