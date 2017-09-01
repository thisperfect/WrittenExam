/*
 * INK是IDEAEE开发平台的日志组件，项目的目标是完成通用的包含日志记录、分析、展示等多功能的日志组件。
 * 版权归属北京创想时刻信息技术有限公司所有。
 * 
 * Copyright @ 2011 - 2012 www.ideamoment.com All right reserved.
 */
package com.dayee.writtenExam.framework.restful.json;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dayee.writtenExam.framework.restful.YuncaiAnnotationMethodHandlerAdapter;
import com.dayee.writtenExam.framework.restful.YuncaiAnnotationMethodHandlerAdapter.ServletHandlerMethodInvoker;
import com.dayee.writtenExam.framework.restful.YuncaiAnnotationMethodHandlerAdapter.ServletHandlerMethodResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

/**
 * 
 * @author Chinakite Zhang
 *
 */
@Controller
public class JsonDataPackageAction {
	
	@Autowired
	private DefaultAnnotationHandlerMapping hm;
	
	@Autowired
	private YuncaiAnnotationMethodHandlerAdapter ha;
	
	@RequestMapping(value="/idp", method=RequestMethod.GET)
	public JsonDataPackage packageJsonData(HttpServletRequest request, HttpServletResponse response) {
		String dp = request.getParameter("dp");
		String[] dpUrls = dp.split(",");
		JsonDataPackage jdp = new JsonDataPackage();
		for(String dpUrl : dpUrls) {
			String[] keyValue = dpUrl.split(":");
			String name = keyValue[0];
			String url = keyValue[1];
			try {
				MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET", url);
				mockRequest.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
				HandlerExecutionChain chain = hm.getHandler(mockRequest);
				ServletHandlerMethodResolver resolver = ha.getMethodResolver(chain.getHandler());
				ServletHandlerMethodInvoker methodInvoker = ha.getMethodInvoker(resolver);
				Method method = resolver.resolveHandlerMethod(mockRequest);
				ServletWebRequest webRequest = new ServletWebRequest(mockRequest, new MockHttpServletResponse());
				ExtendedModelMap implicitModel = new BindingAwareModelMap();
				
				Object result = methodInvoker.invokeHandlerMethod(method, chain.getHandler(), webRequest, implicitModel);
				
				jdp.addJsonData(name, (JsonData)result);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return jdp;
	}
	
	
	
}
