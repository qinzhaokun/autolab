package com.autolab.api.handler;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.Error;
import com.autolab.api.model.Status;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * ControllerAdvice 全局异常处理(这个异常处理也仅仅只能够处理Controller中的异常，并不能够处理SpringMVC框架中所有的异常)
 * 我们采用ExceptionResolver的方式来处理全局的异常。
 * Created by lishuang on 15/4/7.
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    //自定义的异常。
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({UtilException.class})
    public @ResponseBody ModelMap errorCode(HttpServletRequest req, UtilException utilException) {
        ModelMap mav = new ModelMap();

        mav.addAttribute(Status.TAG, Status.ERROR);
        Error error=new Error(utilException.getCode(),utilException.getMessage(),req.getRequestURL().toString());
        mav.addAttribute(Error.TAG,error);

        return mav;
    }


    //方法支持的异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, HttpRequestMethodNotSupportedException.class})
    public  @ResponseBody ModelMap handleAllException(HttpServletRequest req,Exception ex) {

        ModelMap mav = new ModelMap();

        mav.addAttribute(Status.TAG, Status.ERROR);
        Error error=new Error(UtilException.EXCEPTION_CODE,ex.getMessage(),req.getRequestURL().toString());
        mav.addAttribute(Error.TAG, error);

        return mav;

    }
}
