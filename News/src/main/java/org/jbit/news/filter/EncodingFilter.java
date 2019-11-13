package org.jbit.news.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private String encoding = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        System.out.println("EncodingFilter..init..encoding="+encoding);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("EncodingFilter..doFilter..encoding="+encoding);
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestURI =req.getRequestURI();
        // 继续执行
        if(requestURI.contains(".css")||requestURI.contains(".js"))
        {
            filterChain.doFilter(req, resp);
        }
        else {
            req.setCharacterEncoding(encoding);
            resp.setContentType("text/html;charset="+encoding);
            filterChain.doFilter(req, resp);
        }

    }

    @Override
    public void destroy() {
        System.out.println("EncodingFilter..destroy");
    }
}
