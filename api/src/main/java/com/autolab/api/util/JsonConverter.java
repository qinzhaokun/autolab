package com.autolab.api.util;

/**
 * Created by lishuang on 2015/9/21.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 数据转换器
 * Created by lishuang on 2015/3/25.
 */
public class JsonConverter {

    public static Gson gson() {
        return new GsonBuilder().disableHtmlEscaping().setExclusionStrategies(new LishExclusionStrategy()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public static String toJson(Object obj) {

        //logger.debug(json);
        return gson().toJson(obj);
    }



    //从json字符串构建出一个对象
    public static <T> T fromJson(String json, Class<T> classOfT) {
        Gson gson = JsonConverter.gson();
        T entity;
        try {
            entity = gson.fromJson(json, classOfT);
        } catch (Exception e) {
            return null;
        }

        return entity;

    }
}
