package com.dayee.writtenExam.framework.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.AccessControlFilter;

/**
 * 验证码验证码 Author:xiaoyu Date:2016年4月11日 类的作用：
 */
public class CustomImageCodeValidateFilter extends AccessControlFilter {

    private boolean imageCodeEnabled    = true;               // 是否开启验证码支持

    private String  ImageCodeParam      = "captcha";          // 前台表单提交的验证码参数名

    private String  FailureKeyAttribute = "shiroLoginFailure"; // 验证失败后存储到的属性名



    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response,
                                      Object mappedValue) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // 3、此时是表单提交，验证验证码是否正确
        // 拿到表单提交的验证码
        Boolean flag = false;
        String imageCodeForm = httpServletRequest.getParameter(ImageCodeParam);
        // 拿到生成的验证码
        String imageCodeCreated = (String) httpServletRequest.getSession()
                .getAttribute("imageCodeCreated");
        if (imageCodeCreated != null) {
            if (imageCodeCreated.equalsIgnoreCase(imageCodeForm)) {
                flag = true;
            }
        }

        return flag;

        // 1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
        // request.setAttribute("imageCodeEnabled", imageCodeEnabled);
        // 2、判断验证码是否禁用 或不是表单提交（允许访问）
        // if (imageCodeEnabled == false || !"post"
        // .equalsIgnoreCase(httpServletRequest.getMethod())) {
        // return true;
        // }
    }

    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {

        // 如果验证码失败了，存储失败key属性
        request.setAttribute(FailureKeyAttribute, "Captcha.error");
        return true;
    }

    public boolean isImageCodeEnabled() {

        return imageCodeEnabled;
    }

    public void setImageCodeEnabled(boolean imageCodeEnabled) {

        this.imageCodeEnabled = imageCodeEnabled;
    }

    public String getImageCodeParam() {

        return ImageCodeParam;
    }

    public void setImageCodeParam(String imageCodeParam) {

        ImageCodeParam = imageCodeParam;
    }

    public String getFailureKeyAttribute() {

        return FailureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {

        FailureKeyAttribute = failureKeyAttribute;
    }

}