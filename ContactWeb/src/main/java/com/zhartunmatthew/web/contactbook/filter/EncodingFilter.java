package com.zhartunmatthew.web.contactbook.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static Logger log = Logger.getLogger(EncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            servletRequest.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("text/html; charset=UTF-8");
            servletResponse.setCharacterEncoding("UTF-8");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("FILTER");
    }

    @Override
    public void destroy() {

    }
}
