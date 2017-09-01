/*
 * IDEADP是IDEAMOMENT Develop Platform的缩写。
 * 由北京创想时刻信息技术有限公司研发。项目提供各种常见问题的最佳实践和解决方案，用于提高各产品和项目的开发效率。
 * 
 * Copyright @ www.ideamoment.com
 * All right reserved.
 */
package com.dayee.writtenExam.listener;

import javax.servlet.ServletContext;

import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * @author Administrator
 * 
 */
public class YuncaiApplicationContext {

	private static YuncaiApplicationContext instance = new YuncaiApplicationContext();
	
	private static org.springframework.context.ApplicationContext context;

	private String webRoot;

	/**
	 * 单例模式，构造函数私有化
	 */
	private YuncaiApplicationContext() {
	}

	public static YuncaiApplicationContext getInstance() {
		return instance;
	}

	public String getWebRoot() {
		return this.webRoot;
	}

	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

	public static void closeContext(ServletContext servletContext) {

		servletContext.log("Closing Spring root WebApplicationContext");
		if (context != null
				&& context instanceof ConfigurableWebApplicationContext) {
			((ConfigurableWebApplicationContext) context).close();
		}
		context = null;
	}
	
	public static Object getBean(String beanName) {

        org.springframework.context.ApplicationContext context = getContext();
        return context.containsBean(beanName) ? context.getBean(beanName)
                                             : null;
    }
	
	public static org.springframework.context.ApplicationContext getContext() {

        int i = 0;
        while (context == null && i < 5) {

            try {
                i++;
                Thread.sleep(10000);

            } catch (InterruptedException e) {

            }
        }

        if (context != null) {
            return context;
        } 
        return null;
    }
}
