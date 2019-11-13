package org.jbit.news.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filterConfig = " + filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("LoginFilter.doFilter");
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String requestURI =request.getRequestURI();
        // 继续执行
        if(requestURI.contains(".css")||requestURI.contains(".js"))
        {
            filterChain.doFilter(request, response);
        }
        //获取session的userId是否存在
        else if(request.getSession().getAttribute("userId")!=null)
        {
            int userId=(Integer)request.getSession().getAttribute("userId");
            System.out.println("变量不为空userId"+userId);
            if(userId>0)
            {
                filterChain.doFilter(request,response);
            }
            else {
                String logionUrl=request.getContextPath()+"/index.jsp";
                response.sendRedirect(logionUrl);
            }
        }
        else
        {
            System.out.println("变量为空");
            String loginUrl=request.getContextPath()+"/index.jsp";
            response.sendRedirect(loginUrl);
        }
    }

    @Override
    public void destroy() {
        System.out.println("LoginFilter.destroy");
    }
}
