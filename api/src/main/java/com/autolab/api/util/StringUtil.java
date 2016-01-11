package com.autolab.api.util;

import java.util.Random;

/**
 * 关于字符串处理的通用方法。
 * Created by lishuang on 2015/9/30.
 */
public class StringUtil {

    /**
     * 获取长度为n的字符串
     * @param n 字符串长度
     * @return 一个长度为n的随机字符串
     */
    public static String randomString(int n){
        String rawString="abcdefghijklmnopqrstuvwxyz0123456789";
        int rawLength=rawString.length();
        String res="";
        for (int i=0;i<n;i++){
            int pos=new Random().nextInt(rawLength);
            res+=rawString.charAt(pos);
        }
        return res;
    }


    /**
     * Md5加密
     */

}
