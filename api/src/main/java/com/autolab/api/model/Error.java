package com.autolab.api.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhao on 15/10/22.
 */
@Data
public class Error {

    public static String TAG = Error.class.getSimpleName().toLowerCase();

    //    @JsonFormat(pattern=)
    private Date time;

    private String message;

    private String path;

    private Integer code;


    public Error(Integer code, String message, String path) {
        this.time = new Date();
        this.code = code;
        this.message = message;
        this.path = path;
    }
}
