package com.song.demo.config;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: MyFilter
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/25 15:14
 * @Version: 1.0
 **/
@WebFilter(filterName = "AAA",urlPatterns = {"/login/testFilter"},initParams = {@WebInitParam(name="charset",value = "utf-8")})
@Slf4j
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String filterName = filterConfig.getFilterName();
        log.info("名称："+filterName);
        log.info("编码："+filterConfig.getInitParameter("charset"));
        log.info("初始化。。。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        log.info("执行调用。。。");
        String aa = req.getParameter("aa");
        log.info("aa的值="+aa);
        if(!"123".equalsIgnoreCase(aa)){
            res.sendRedirect("/login/success");
        }else{
            chain.doFilter(request,response);
        }

    }

    @Override
    public void destroy() {
        log.info("销毁。。。");
    }
}
