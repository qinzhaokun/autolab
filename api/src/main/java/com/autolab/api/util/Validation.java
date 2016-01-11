package com.autolab.api.util;

import java.util.Collection;

/**
 * 和参数处理有关的通用方法
 * Created by lishuang on 2015/3/17.
 */
public class Validation {
	/**
	 * 检查参数是否为空
	 *
	 * @param list the list
	 * @return true, if successful
	 */
	public static boolean checkParam(Object... list) {
		boolean res = true;

		for (Object obj : list) {
			//普通对象我们看看是不是为空，或者空字符串
			if (obj == null || "".equals(obj)) {
				res = false;
				break;
			}
			//如果是容器类的话，那么我们看看是否大小为0
			//String.class.isAssignableFrom(Object.class); false
			//Object.class.isAssignableFrom(String.class); true
			//Class1.isAssignableFrom(Class2);表示判断Class1是否是Class2的父类。
			else if (Collection.class.isAssignableFrom(obj.getClass())) {

				Collection c = (Collection) obj;

				if (c.size() == 0) {
					res = false;
				}
				break;
			}
		}
		return res;
	}
}
