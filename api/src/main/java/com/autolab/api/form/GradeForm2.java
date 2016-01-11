package com.autolab.api.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by zhao on 15/11/27.
 */
@Data
public class GradeForm2 {
    @NotNull
    private Long [] bookIds;

    @NotNull
    private String [] grades;
}
