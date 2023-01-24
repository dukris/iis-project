package com.solvd.laba.iis.web.dto.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "Criteria for searching lessons")
public class LessonSearchCriteriaDto {

    @Schema(description = "Weekday")
    private String weekday;

}
