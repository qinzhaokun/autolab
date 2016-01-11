package com.autolab.api.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by zhao on 15/11/27.
 */
@Data
public class GradeForm3 {
    @NotNull
    private Long [] courseIds;

    @NotNull
    private Long [] studentIds;

    @NotNull
    private Long [] teacherIds;

    @NotNull
    private String [] grades;
}
