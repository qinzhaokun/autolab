package com.autolab.api.exception;

/**
 * 在这里定义一些常见类型的错误。
 * Created by lishuang on 15/4/1.
 */
public class UtilExceptions {

    public static final UtilException FILE_NOT_FOUND = new UtilException(20001, "文件未找到");

    public static final UtilException PHONE_EXIST = new UtilException(20002, "该手机号码已被注册");

    public static final UtilException USERNAME_EXIST = new UtilException(20002, "该用户名已被注册");

}
