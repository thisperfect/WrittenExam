/*
 * IDEADP是IDEAMOMENT Develop Platform的缩写。
 * 由北京创想时刻信息技术有限公司研发。项目提供各种常见问题的最佳实践和解决方案，用于提高各产品和项目的开发效率。
 * 
 * Copyright @ www.ideamoment.com
 * All right reserved.
 */
package com.dayee.writtenExam.framework.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dayee.writtenExam.exception.ResumeCommonException;
import com.dayee.writtenExam.framework.restful.json.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 
 * @author Chinakite Zhang
 *
 */
public class ResumeExceptionHandler implements HandlerExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger("waringAndException");
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handle, Exception e) {
		if(e instanceof YuncaiMessage) {
		    logger.debug(e.getMessage(), e);
		    YuncaiMessage ycm = (YuncaiMessage)e;
		    String state = ycm.getState();
		    String message = ycm.getMessage();
		    
		    try {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JsonData.warning(state, message).getJson());
            } catch (IOException ioe) {
                logger.error(ioe.getMessage(), ioe);
                ioe.printStackTrace();
                throw new RuntimeException(ioe);
            }
		    
	    } else if (e instanceof YuncaiBaseException) {
	        logger.error(e.getMessage(), e);
		    YuncaiBaseException ibe = (YuncaiBaseException)e;
			String state = ibe.getCode();
			String message = ibe.getMessage();
			try {
	            response.setContentType("application/json;charset=utf-8");
			    response.getWriter().write(JsonData.exception(state, message).getJson());
			} catch (IOException ioe) {
				logger.error(ioe.getMessage(), ioe);
				ioe.printStackTrace();
				throw new RuntimeException(ioe);
			}
		} else if (e instanceof ResumeCommonException){
			try {
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(((ResumeCommonException) e).parseJsonData().getJson());
			} catch (Exception ioe) {
				logger.error(ioe.getMessage(), ioe);
				ioe.printStackTrace();
				throw new RuntimeException(ioe);
			}
		} else {
            logger.error(e.getMessage(), e);
			String message = e.getMessage();
			String trace = getStackTrace(e);
			StringBuffer buf = new StringBuffer("{\"Exception\":{");
			buf.append("\"message\":\"").append(message).append("\",")
			   .append("\"trace\":\"").append(trace).append("\"}}");
			try {
                response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(buf.toString());
			} catch (IOException ioe) {
				logger.error(ioe.getMessage(), ioe);
				ioe.printStackTrace();
				throw new RuntimeException(ioe);
			}
		}
		
		return new ModelAndView();
	}

	/** 
	 * 获取异常的堆栈信息 
	 *  
	 * @param t 
	 * @return 
	 */  
	private static String getStackTrace(Throwable t)  
	{  
	    StringWriter sw = new StringWriter();  
	    PrintWriter pw = new PrintWriter(sw);  
	  
	    try {  
	        t.printStackTrace(pw);  
	        String result = sw.toString();
	        result = result.replaceAll("\r", "");
	        result = result.replaceAll("\t", "");
	        result = result.replaceAll("\n", "_##_");		//用一个特殊的字串替换回车符，否则客户端无法解析
	        return result;  
	    } finally {
	        pw.close();  
	    }  
	}  
}
