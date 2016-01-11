package com.autolab.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * Created by lishuang on 2015/9/23.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class UtilException extends RuntimeException {
    private Integer code;
    private String message;

    public static Integer DEFAULT_CODE=10000;
    public static Integer EXCEPTION_CODE=20000;

    public UtilException(String message){
        super(message);
        //10000表示自定义错误
        this.code =DEFAULT_CODE;
        this.message=message;
    }

    public UtilException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}