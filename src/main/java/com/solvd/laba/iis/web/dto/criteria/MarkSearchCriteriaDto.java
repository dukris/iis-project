package com.solvd.laba.iis.web.dto.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "Criteria for searching marks")
public class MarkSearchCriteriaDto {

    @Schema(description = "Subject's id")
    private Long subjectId;

}
