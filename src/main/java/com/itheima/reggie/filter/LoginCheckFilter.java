package com.itheima.reggie.filter;

import com.sun.webkit.network.URLs;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //这是比较字符串用的大概是
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        //第一步先获取页面的url请求
        String requestURI = servletRequest1.getRequestURI();

        //第二步是判断需求需不需要得到处理。
        Boolean check = check(urls, requestURI);

        //在这之前先行定义一些不需要处理的url请求？
        String[] urls = new String[]{
                "employee/login","employee/logout","*/frontend/*","*/backend/*"
        };

        for(int i =0;i<urls.length;i++){
            if(employee==urls[j]){
                return;
            }
        }

        //第三步 如果不需要处理，则就放行
        if(check){
            filterChain.doFilter(requestURI,response);
        }
        //第四步 判断登陆状态，如果已经登陆了，那么直接放行
        if(servletRequest1.getSession().getAttribute("employeee")!=null){

        }
        //第五步 如果没有登录那么回到登陆页面
    }

    public Boolean check(String url,){
        for(String a : urls){

        }
    }
}
