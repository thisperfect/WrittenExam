
package com.dayee.writtenExam.framework.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.dayee.writtenExam.framework.restful.json.JsonData;
import com.dayee.writtenExam.framework.util.RequestUtils;

/**
 * Filter that allows access if the current user has the permissions specified
 * by the mapped value, or denies access if the user does not have all of the
 * permissions specified.
 * 
 * @since 0.9
 */
/**
 * 自定义角色健鉴权过滤器 + 扩展鉴权失败ajax提示 Author:xiaoyu Date:2016年3月26日 类的作用：
 */
public class CustomPermissionsAuthorizationFilter extends AuthorizationFilter {

    // 登录认证失败
    public static final String           AUTHENTICATION_ACCESSDENIED = "808";

    // 授权失败
    public static final String           AUTHORIZATION_ACCESSDENIED  = "809";

    // 访问受限制
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response)
            throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // HttpServletResponse httpResponse = (HttpServletResponse) response;

        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL

        if (subject.getPrincipal() == null) {

            if (RequestUtils.isAjax(httpRequest)) {

                JsonData json = JsonData.error(AUTHENTICATION_ACCESSDENIED,
                                               "您尚未登录或登录时间过长,请重新登录!");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().print(json.getJson());

            } else {
                saveRequestAndRedirectToLogin(request, response);
            }

        } else {
            // If subject is known but not authorized, redirect to the
            // unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized
            // HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            // SHIRO-142 - ensure that redirect _or_ error code occurs - both
            // cannot happen due to response commit:

            if (RequestUtils.isAjax(httpRequest)) {

                JsonData json = JsonData.error(AUTHORIZATION_ACCESSDENIED,
                                               "您没有足够的权限执行该操作!");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().print(json.getJson());

            } else {
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(401);
                }
            }

        }
        return false;
    }

    // TODO - complete JavaDoc
    // pers权限形式的认证的验证
    public boolean isAccessAllowed(ServletRequest request,
                                   ServletResponse response,
                                   Object mappedValue) throws IOException {

        // 鉴定权限
        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;

        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else {
                for (String eachPerm : perms) {
                    if (subject.isPermitted(eachPerm)) {
                        return true;
                    }
                }
                return false;
            }
        }

        return isPermitted;
    }

    /**
     * 根据请求URL产生权限字符串，这里只产生，而比对的事交给Realm
     * 
     * @param request
     * @return
     */

    protected String[] buildPermissions(ServletRequest request) {

        String[] perms = new String[1];
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        perms[0] = path;// path直接作为权限字符串
        /*
         * String regex = "/(.*?)/(.*?)\\.(.*)"; if(url.matches(regex)){ Pattern
         * pattern = Pattern.compile(regex); Matcher matcher =
         * pattern.matcher(url); String controller = matcher.group(1); String
         * action = matcher.group(2); }
         */
        return perms;
    }
}
