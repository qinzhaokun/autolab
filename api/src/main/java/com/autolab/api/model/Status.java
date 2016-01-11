package com.autolab.api.model;

/**
 * Created by zhao on 15/10/22.
 */
public enum Status {


    //正常
    OK,
    //被删除
    DELETED,
    //被禁用
    DISABLED,
    //出错
    ERROR;


    public static String TAG = Status.class.getSimpleName().toLowerCase();

}
