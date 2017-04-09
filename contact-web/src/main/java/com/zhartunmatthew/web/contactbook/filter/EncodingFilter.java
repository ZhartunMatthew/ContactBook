package com.zhartunmatthew.web.contactbook.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private final static Logger LOG = LoggerFactory.getLogger(EncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
                            throws IOException, ServletException {
        try {
            servletRequest.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("text/html; charset=UTF-8");
            servletResponse.setCharacterEncoding("UTF-8");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            LOG.error("Error in Filter", ex);
        }
    }

    @Override
    public void destroy() {

    }
}
