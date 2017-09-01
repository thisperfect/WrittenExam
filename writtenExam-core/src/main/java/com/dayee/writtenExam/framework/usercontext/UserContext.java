/*
 * IDEADP是IDEAMOMENT Develop Platform的缩写。
 * 由北京创想时刻信息技术有限公司研发。项目提供各种常见问题的最佳实践和解决方案，用于提高各产品和项目的开发效率。 Copyright @
 * www.ideamoment.com All right reserved.
 */

package com.dayee.writtenExam.framework.usercontext;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dayee.writtenExam.framework.config.YuncaiConfig;
import com.dayee.writtenExam.model.sercurity.vo.ShiroUserVo;

/**
 * @author Chinakite Zhang
 */
public class UserContext {

    /**
     * ThreadLocal object for storing object in current thread.
     */
    protected static ThreadLocal          tl    = new ThreadLocal();

    /**
     * The http request object. The lifecycle of the request object is defined
     * as the request scope. It may be reused in another incoming connection, so
     * dont use it in another thread.
     */
    private HttpServletRequest            request;

    /**
     * The http response object. The lifecycle of the response object is defined
     * as the request scope. Dont use it in another thread. Also dont write
     * output to the response when it is used in the context, but you may get or
     * set some response header when it is safe.
     */
    private HttpServletResponse           response;

    private String                        contextId;

    private String                        corpCode;

    private String                        dbName;

    private String                         gender; 
    
    private ShiroUserVo                   user;

    private final HashMap<String, Object> attrs = new HashMap<String, Object>();

    /**
     * Get current context value
     * 
     * @return The current context
     */
    public static UserContext getCurrentContext() {

        return (UserContext) tl.get();
    }

    /**
     * Set current context
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponses object
     */
    public static void setCurrentContext(HttpServletRequest request,
                                         HttpServletResponse response) {

        UserContext us = getCurrentContext();
        if (us == null) {
            us = new UserContext(request, response);
            tl.set(us);
        } else {
            us.setRequest(request);
            us.setResponse(response);
        }
    }
    
    public static void setCurrentTaskContext() {
        
        UserContext us = getCurrentContext();
        if (us == null) {
            us = new UserContext();
            tl.set(us);
        }
    }

    /**
     * The constructor is private, to get an instance of the AMFContext, please
     * use getCurrentContext() method.
     * 
     * @param request
     * @param response
     */
    protected UserContext(HttpServletRequest request,
                          HttpServletResponse response) {

        this.request = request;
        this.response = response;
    }

    protected UserContext() {

    }

    /**
     * Get request object
     * 
     * @return Http request object
     */
    public HttpServletRequest getRequest() {

        return request;
    }

    /**
     * Set request object
     * 
     * @param request
     *            HttpServletRequest object
     */
    public void setRequest(HttpServletRequest request) {

        this.request = request;
    }

    /**
     * Get response object
     * 
     * @return Http response object
     */
    public HttpServletResponse getResponse() {

        return response;
    }

    /**
     * Set response object
     * 
     * @param response
     *            Http response object
     */
    public void setResponse(HttpServletResponse response) {

        this.response = response;
    }

    public String getContextId() {

        return contextId;
    }

    public void setContextId(String contextId) {

        this.contextId = contextId;
    }

    public String getCorpCode() {

        return corpCode;
    }

    public void setCorpCode(String corpCode) {

        this.corpCode = corpCode;
    }

    public String getDbName() {

        return dbName;
    }

    public void setDbName(String dbName) {

        this.dbName = dbName;
    }

    public void setContextAttribute(String key, Object value) {

        this.attrs.put(key, value);
    }

    public void removeContextAttribute(String key) {

        this.attrs.put(key, null);
    }

    /**
     * @return the user
     */
    public ShiroUserVo getUser() {

        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(ShiroUserVo user) {

        this.user = user;
    }

    public static boolean isConsole() {

        return getCurrentCorpCode().equals(YuncaiConfig.getConsoleCorpCode());
    }

    public static String getCurrentCorpCode() {

        return getCurrentContext().getCorpCode();
    }

    public static ShiroUserVo getCurrentUser() {

        return getCurrentContext().getUser();
    }

    public static String getCurrentUserName() {

        ShiroUserVo user = getCurrentUser();
        if (user != null) {
            return user.getName();
        }
        return null;
    }

    public static String getCurrentUserId() {

        ShiroUserVo user = getCurrentUser();
        if (user != null) {
            return user.getUserId();
        }
        return null;
    }
    
    /**
     * 获取用户昵称
     * @return
     */
    public static String getCurrentNickName() {
        
        ShiroUserVo user = getCurrentUser();
        if (user != null) {
            if (isConsole()) {
                return user.getName();
            }
            return user.getNickName();
        }
        return null;
    }
    
    /**
     * 获取用户实名
     * @return
     */
    public static String getCurrentRealName() {

        ShiroUserVo user = getCurrentUser();
        if (user != null) {
            if (isConsole()) {
                return user.getName();
            }
            return user.getName();
        }
        return null;
    }
    
    public static String getCurrentDB() {
        return getCurrentContext().getDbName();
    }
    
    public static String getCurrentGender() {
        return getCurrentContext().getGender();
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public static void setCurrentUserName(String userName){
        ShiroUserVo user = getCurrentUser();
        if (user != null) {
            user.setName(userName);
        }
    }
}
