package org.jbit.news.servlet;

import org.jbit.news.dao.AdminDao;
import org.jbit.news.dao.impl.AdminDaoImpl;
import org.jbit.news.entity.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    AdminDao adminDao=new AdminDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        //System.out.println(action);
        if (action != null && "login".equals(action.trim())) {
            loginUser(req, resp);
        }
        if(action!=null&&"validUserName".equals(action.trim()))
        {
            validUserName(req,resp);
        }
    }
    /**
     * 用户登录验证
     * @param req
     * @param resp
     * @throws IOException
     */
    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String upwd = req.getParameter("upwd");
        int result = 0;
        if (username!=null && !"".equals(username.trim())
                && upwd!=null && !"".equals(upwd.trim())) {
            Admin admin = adminDao.findAdmin(username, upwd);
            if(admin!=null) {
                req.getSession().setAttribute("user", admin);
                req.getSession().setAttribute("userId", admin.getUid());
                result = 1;
            }
        }
        //System.out.println(result);
        resp.getWriter().print(result);
    }

    /**
     * 验证用户名
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    private void validUserName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        System.out.println("username=" + username);
        boolean result = false;
        if (username != null && !"".equals(username.trim())) {
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("uid", username);
            int count = adminDao.findUserCount(searchMap);
            if (count == 0) {
                result = true;
            }
        }
        resp.getWriter().print(result);
    }
}
