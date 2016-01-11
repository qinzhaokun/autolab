package com.autolab.api.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by KUN on 2015/10/31.
 */

@Data
public class GradeForm {

    @NotNull
    private Long batchId;

    @NotNull
    private Long [] studentIds;

    @NotNull
    private String [] grades;


}
