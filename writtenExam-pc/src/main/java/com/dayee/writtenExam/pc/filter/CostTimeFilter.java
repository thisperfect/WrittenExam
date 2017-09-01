/**
 * 
 */

package com.dayee.writtenExam.pc.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class CostTimeFilter implements Filter {

    private static final Logger logger = LoggerFactory
                                               .getLogger(CostTimeFilter.class);

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {

    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        logger.debug("START-RequestURI:{}", request.getRequestURI());
        Date startDate = new Date();

        // 执行相应的请求
        chain.doFilter(req, resp);

        // 请求的耗时
        long costTime = new Date().getTime() - startDate.getTime();
        logger.debug("END-costTime:{}ms, URI:{}", costTime, request.getRequestURI());
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
