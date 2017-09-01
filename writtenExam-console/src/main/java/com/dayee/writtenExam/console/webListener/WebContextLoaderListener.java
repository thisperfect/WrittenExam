
package com.dayee.writtenExam.console.webListener;

import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class WebContextLoaderListener implements ServletContextListener {
	
	private final Logger logger = LoggerFactory
			.getLogger(WebContextLoaderListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//停止socketServer
		destroy(sce);
		//清空驱动
		clearDriver();
		//清除threadlocal
		immolate();
		
	}
	
	//停止socketServer
	private void destroy(ServletContextEvent event){
		
		ServletContext context = event.getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		SocketIOServer server= (SocketIOServer) applicationContext.getBean("socketIOServer");
		server.stop();
	}
	
	private void clearDriver() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.debug(String.format("deregistering jdbc driver: %s", driver));
			} catch (SQLException e) {
				logger.debug(String.format("Error deregistering driver %s", driver), e);
			}
			
		}
	}
	
	private Integer immolate() {
		int count = 0;
		try {
			final Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
			threadLocalsField.setAccessible(true);
			final Field inheritableThreadLocalsField = Thread.class.getDeclaredField("inheritableThreadLocals");
			inheritableThreadLocalsField.setAccessible(true);
			for (final Thread thread : Thread.getAllStackTraces().keySet()) {
				count += clear(threadLocalsField.get(thread));
				count += clear(inheritableThreadLocalsField.get(thread));
				if (thread != null) {
					thread.setContextClassLoader(null);
				}
			}
			logger.info("immolated " + count + " values in ThreadLocals");
		} catch (Exception e) {
			throw new Error("ThreadLocalImmolater.immolate()", e);
		}
		return count;
	}
	
	private int clear(final Object threadLocalMap) throws Exception {
		if (threadLocalMap == null)
			return 0;
		int count = 0;
		final Field tableField = threadLocalMap.getClass().getDeclaredField("table");
		tableField.setAccessible(true);
		final Object table = tableField.get(threadLocalMap);
		for (int i = 0, length = Array.getLength(table); i < length; ++i) {
			final Object entry = Array.get(table, i);
			if (entry != null) {
				final Object threadLocal = ((WeakReference)entry).get();
				if (threadLocal != null) {
					//log(i, threadLocal);
					Array.set(table, i, null);
					++count;
				}
			}
		}
		return count;
	}
	
}