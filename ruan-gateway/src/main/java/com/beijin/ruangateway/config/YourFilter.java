package com.beijin.ruangateway.config;

import java.io.IOException;

/**
 * @Author: ruanxiantao
 * @Description:
 * @Date: 2020/8/1 15:45
 */
//@Component
public class YourFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
