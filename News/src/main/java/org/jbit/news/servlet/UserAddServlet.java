package org.jbit.news.servlet;

import com.alibaba.fastjson.JSON;
import org.jbit.news.dao.UserDao;
import org.jbit.news.dao.impl.UserDaoImpl;
import org.jbit.news.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserAddServlet extends HttpServlet {
    UserDao userDao=new UserDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        if(action!=null&&"addList".equals(action))
        {
            addList(req,resp);
        }
        else if(action!=null&&"validUserName".equals(action))
        {
            validUserName(req,resp);
        }
        else if(action!=null&&"add".equals(action))
        {
            AddUser(req,resp);
        }
    }

    private void addList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path="/newspages/user_add.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }
    private void AddUser1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("uid");
        String userName = req.getParameter("uname");
        String userNumber = req.getParameter("unumber");
        String userQq = req.getParameter("qq");
        String userHobby=req.getParameter("hobby");
        int userSex = Integer.valueOf(req.getParameter("usex"));//0 男 1女
        String userPassword = req.getParameter("upassword");
        String userOperator = req.getParameter("operator");
        //String isParent = req.getParameter("isParent");
        if (userId != null && !"".equals(userId.trim())
                && userPassword != null && !"".equals(userPassword.trim())) {
            User user = new User();
            user.setuId(Integer.valueOf(userId));
            user.setuName(userName);
            user.setuNumber(userNumber);
            user.setQQ(userQq);
            user.setuSex(userSex);
            user.setuPassword(userPassword);
            user.setOperator(userOperator);
            user.setHobby(userHobby);
            //menu.setNparent_id(new String(isParent.getBytes("ISO8859-1"), "utf-8"));
            //menu.setNoperator_time(areaParentCode);
            //menu.setNjsp((int)req.getSession().getAttribute("userId"));
            int result= userDao.saveUser(user);
            if(result>0) {
                resp.getWriter().append("<script>alert('保存成功!');location.href='" + req.getContextPath() + "/userListServlet?action=list';</script>");
//                String path = "/newspages/user_List.jsp";
//                req.getRequestDispatcher(path).forward(req, resp);
            }else {
                resp.getWriter().append("<script>alert('保存失败');history.back();</script>");
            }
        } else {
            resp.getWriter().append("<script>alert('请填写完整参数');history.back();</script>");
        }

    }
    /**
     * 验证用户名
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    private void validUserName1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userId");
        System.out.println("userId=" + userId);
        boolean result = false;
        if (userId != null && !"".equals(userId.trim())) {
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("uid", userId);
            int count = userDao.findUserCount(searchMap);
            if (count == 0) {
                result = true;
            }
        }
        resp.getWriter().print(result);
    }
    private void validUserName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("adduserId");
        boolean result = false;
        if (userId != null && !"".equals(userId.trim())) {
            Map<String, Object> searchMap=new HashMap<>();
            searchMap.put("uid",userId);
            int count=userDao.findUserCount(searchMap);
            if (count == 0) {
                result = true;
            }
        }
        resp.getWriter().print(result);
    }
    private void AddUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("addUserId");
        String userName = req.getParameter("addUserName");
        String userNumber = req.getParameter("addMobile");
        String userHobby = req.getParameter("addHobbyValue");
        int userSex = Integer.valueOf(req.getParameter("sexDivId"));//0 男 1女
        String userPassword = req.getParameter("addPasswd");
        String headPicPath=req.getParameter("addHeadpic");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", "0");
        String token = req.getHeader("token");
        String userOperator = req.getHeader("userId");
        if (userId != null && !"".equals(userId.trim())
                && userPassword != null && !"".equals(userPassword.trim())
                && userOperator != null && !"".equals(userOperator.trim())
                && token != null && !"".equals(token.trim())) {
            User user = new User();
            user.setuId(Integer.valueOf(userId));
            user.setuName(userName);
            user.setuNumber(userNumber);
            user.setuSex(userSex);
            user.setuPassword(userPassword);
            user.setOperator(userOperator);
            user.setHobby(userHobby);
            user.setHeadPicPath(headPicPath);
            int count = userDao.saveUser(user);
            if (count > 0) {
                resultMap.put("success", "1");
            }
            String result=JSON.toJSONString(resultMap);
            resp.getWriter().print(result);
        }
    }

}
