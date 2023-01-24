package com.solvd.laba.iis.web.dto.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "Criteria for searching groups")
public class GroupSearchCriteriaDto {

    @Schema(description = "Subject's id")
    private Long subjectId;

}
