/**
 * 
 */
package com.dayee.writtenExam.pc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dayee.writtenExam.framework.usercontext.UserContext;
import com.dayee.writtenExam.model.sercurity.vo.ShiroUserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Chinakite
 *
 */
public class UserContextFilter implements Filter {

    private final Logger logger = LoggerFactory
                                        .getLogger(UserContextFilter.class);

    
    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {

    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        UserContext.setCurrentContext(request, (HttpServletResponse)resp);
        UserContext userContext = UserContext.getCurrentContext();
        Subject subject = SecurityUtils.getSubject();
        if(subject != null && subject.isAuthenticated()) {
            ShiroUserVo userVo = (ShiroUserVo)subject.getSession().getAttribute("RESUME_USER");
            if(userVo!=null){
                userContext.setUser(userVo);
                userContext.setDbName((String)subject.getSession().getAttribute("RESUME_DBNAME"));
                if(logger.isInfoEnabled()){
                    logger.info("UserId:{}",userVo.getUserId());
                    logger.info("DbName:{}",userContext.getDbName());
                }
            }
        }

        // 执行相应的请求
        chain.doFilter(req, resp);
        
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
