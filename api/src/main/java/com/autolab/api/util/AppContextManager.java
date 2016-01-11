package com.autolab.api.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 一个用作搭建Spring Bean和非Spring Bean的桥梁
 * Created by lishuang on 2015/3/20.
 */
@Service
public class AppContextManager implements ApplicationContextAware {
	private static Logger logger = Logger.getLogger(AppContextManager.class);
	private static ApplicationContext appCtx;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appCtx = applicationContext;
	}

	public static ApplicationContext getAppContext() {
		return appCtx;
	}
}
