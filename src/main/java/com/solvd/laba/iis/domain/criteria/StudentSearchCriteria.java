package com.solvd.laba.iis.domain.criteria;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentSearchCriteria {

    private String speciality;
    private String faculty;
    private Integer year;

}
