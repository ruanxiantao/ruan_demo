package com.beijin.ruangateway.config;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @Author: ruanxiantao
 * @Description:
 * @Date: 2020/8/1 15:39
 */
//@Component
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
