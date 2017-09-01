/**
 * 
 */

package com.dayee.writtenExam.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.dayee.writtenExam.framework.ideajdbc.WrittenJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dayee.writtenExam.exception.ReportBaseException;

/**
 * @author Chinakite
 */
public class IdeaJdbcTxFilter implements Filter {


    private static final Logger logger = LoggerFactory.getLogger(IdeaJdbcTxFilter.class);
    
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

        chain.doFilter(req, resp);

        if (WrittenJdbc.getCurrentTransaction() != null || WrittenJdbc
                    .db("dayee8").getCurrentTransaction() != null) {
            HttpServletRequest request= (HttpServletRequest) req;
            logger.error("RequestURI:", request.getRequestURI());
            ReportBaseException ex = new ReportBaseException("500", "事务没有正常关闭("+request.getRequestURI()+")");
            logger.error("事务没有正常关闭", ex);
            throw ex;
        }

    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
