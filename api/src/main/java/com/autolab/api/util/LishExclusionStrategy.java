package com.autolab.api.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * 自己写的@NotExpose排除策略。
 * 使用方法：Gson gson=new GsonBuilder().setExclusionStrategies(new LishExclusionStrategy()).create();
 * Created by lishuang on 2014/9/13.
 */
public class LishExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getAnnotation(NotExpose.class) != null;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}
}
