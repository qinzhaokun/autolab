package com.autolab.api.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ABC on 2015/11/6 0006.
 */

@Data
public class Time {

    @NotNull
    private Date startTime;

    @NotNull
    private Date endTime;
}

