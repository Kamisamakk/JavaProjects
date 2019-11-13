package org.jbit.news.servlet;

import com.alibaba.fastjson.JSON;
import org.jbit.news.dao.UserDao;
import org.jbit.news.dao.impl.UserDaoImpl;
import org.jbit.news.entity.User;
import org.jbit.news.util.Token;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserListServlet extends HttpServlet {
    UserDao userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        //System.out.println(action);
        if (action != null && "list".equals(action.trim())) {
            queryList1(req, resp);
        }
        else if (action != null && "logout".equals(action.trim())) {
            LoginOut(req, resp);
        }else if (action != null && "login".equals(action.trim())) {
            login(req, resp);
        }else if (action != null && "delete".equals(action.trim())) {
            delList(req, resp);
        }
        else if(action!=null&&"info".equals(action.trim()))
        {
            Info(req,resp);
        }else if(action!=null&&"updatejsp".equals(action.trim()))
        {
            ToUpdate(req,resp);
        }
        else if(action!=null&&"update".equals(action.trim()))
        {
            UpdateUser(req,resp);
        }
        else if(action!=null&&"lock".equals(action.trim()))
        {
            LockUser(req,resp);
        }
        else if(action!=null&&"unlock".equals(action.trim()))
        {
            UnLockUser(req,resp);
        }else if (action != null && "loginUser".equals(action.trim())) {
            loginUser(req, resp);
        }
    }

    private void UnLockUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId=req.getParameter("userId");
        User user=new User();
        user.setuId(Integer.valueOf(userId));
        user.setUstates(1);
        int result=userDao.UnLock(user);
        if(result>0)
        {
            resp.getWriter().append("<script>alert('解冻成功!');location.href='" + req.getContextPath() + "/userListServlet?action=list';</script>");
        }
        else {
            resp.getWriter().append("<script>alert('解冻失败!');history.back();</script>");
        }

    }

    private void LockUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId=req.getParameter("userId");
        User user=new User();
        user.setuId(Integer.valueOf(userId));
        user.setUstates(0);
        int result=userDao.UnLock(user);
        if(result>0)
        {
            resp.getWriter().append("<script>alert('冻结成功!');location.href='" + req.getContextPath() + "/userListServlet?action=list';</script>");
        }
        else {
            resp.getWriter().append("<script>alert('冻结失败!');history.back();</script>");
        }
    }

    private void ToUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operator=req.getParameter("operator");
        String userId=req.getParameter("userId");
        Map<String,String> searchMap=new HashMap<>();
        searchMap.put("userId",userId);
        User user=userDao.getUser(searchMap);
        req.setAttribute("operator",operator);
        req.setAttribute("userUpdate",user);
        String path = "/newspages/user_update.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

    private void UpdateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId=req.getParameter("uid");
        String userName = req.getParameter("uname");
        String userNumber = req.getParameter("unumber");
        String userQQ = req.getParameter("qq");
        String operator = req.getParameter("operator");
        if (userId != null && !"".equals(userId.trim())
                && userName != null && !"".equals(userName.trim())
                && userNumber != null && !"".equals(userNumber.trim())
                && userQQ != null && !"".equals(userQQ.trim())) {
            User user = new User();
            user.setuId(Integer.valueOf(userId));
            user.setuName(userName);
            user.setuNumber(userNumber);
            user.setQQ(userQQ);
            user.setOperator(operator);
            userDao.UpdateUser(user);
            resp.getWriter().append("<script>alert('修改成功!');location.href='" + req.getContextPath() + "/userListServlet?action=list';</script>");
        } else {
            resp.getWriter().append("<script>alert('请填写完整参数');history.back();</script>");
        }

    }
    private void Info(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId=req.getParameter("userId");
        Map<String,String> search=new HashMap<>();
        search.put("userId",userId);
        User user=userDao.getUser(search);
       // List<Map<String, Object>> userList=userDao.getAdvertisements(search);
        int result=0;
        if(user!=null)
        {
            //System.out.println(user.getuName());
            //req.getSession().setAttribute("userinfo", user);
            req.setAttribute("userinfo", user);
            result=1;
            //eq.setAttribute("result",result);
        }
        //resp.getWriter().print(result);
        String path = "/newspages/user_info.jsp";
        req.getRequestDispatcher(path).forward(req, resp);
        //resp.sendRedirect();
    }

    private void delList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uid = req.getParameter("uid");
        if (uid != null && !"".equals(uid.trim())) {
            int result = userDao.deleteUser(uid);
            //System.out.println(result);
            resp.getWriter().print(result);
        }
    }

    /**
     * 用户退出
     * @param req
     * @param resp
     */
    private void LoginOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        String url = req.getContextPath()+"/index.jsp";
        resp.sendRedirect(url);
    }

    /**
     * 获取列表
     *
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    private void queryList1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String uid = req.getParameter("uid");
        if (currentPage == null || "".equals(currentPage.trim())) {
            currentPage = "0";
        }
        if (pageSize == null || "".equals(pageSize.trim())) {
            pageSize = "5";
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("currentPage", currentPage);
        searchMap.put("pageSize", pageSize);
        searchMap.put("uid", uid);
        int totalCount = userDao.findUserCount(searchMap);//总记录数
        // System.out.println(totalCount);
        if (totalCount > 0) {
            int pageCount = totalCount / Integer.parseInt(pageSize);
            if (totalCount % Integer.parseInt(pageSize) != 0) {
                pageCount += 1;
            }
            List<Map<String, Object>> userList = userDao.findUserList(searchMap);
            req.setAttribute("userList", userList);
            req.setAttribute("pageCount", pageCount);
        } else {
            int pageCount = 0;
            req.setAttribute("pageCount", pageCount);
        }
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPage", currentPage);
        String path = "/newspages/user_List.jsp";
        req.getRequestDispatcher(path).forward(req, resp);
    }
    /**
     * 用户登录验证
     * @param req
     * @param resp
     * @throws IOException
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String upwd = req.getParameter("upwd");
        int result = 0;
        if (username!=null && !"".equals(username.trim())
                && upwd!=null && !"".equals(upwd.trim())) {
            User user = userDao.findUser(username, upwd);
            if(user!=null) {
                req.getSession().setAttribute("user", user);
                req.getSession().setAttribute("userId", user.getuId());
                result = 1;
            }
        }
        //System.out.println(result);
        resp.getWriter().print(result);
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
        System.out.println("username="+username+",upwd="+upwd);
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", "0");
        if (username != null && !"".equals(username.trim()) && upwd != null && !"".equals(upwd.trim())) {
            User user = userDao.findUser(username, upwd);
            if (user != null) {
                String token = Token.getTokenString(req.getSession());
                resultMap.put("success", "1");
                resultMap.put("token", token);
                resultMap.put("userId", user.getuId());
                resultMap.put("userName", user.getuName());
                resultMap.put("userHeadPic", user.getHeadPicPath());
            }
        }
        String result = JSON.toJSONString(resultMap);
        System.out.println("result="+result);
        resp.getWriter().print(result);
    }
    private void queryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String uid = req.getParameter("userId");
        if (currentPage == null || "".equals(currentPage.trim())) {
            currentPage = "0";
        }
        if (pageSize == null || "".equals(pageSize.trim())) {
            pageSize = "5";
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success","0");
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("currentPage", currentPage);
        searchMap.put("pageSize", pageSize);
        if(uid!=null&&!"".equals(uid))
            searchMap.put("uid", uid);
        int totalCount = userDao.findUserCount(searchMap);//总记录数
        // System.out.println(totalCount);
        if (totalCount > 0) {
            List<Map<String, Object>> userList = userDao.findUserList(searchMap);
            resultMap.put("success", "1");
            resultMap.put("currentPage", Integer.parseInt(currentPage) + 1);
            resultMap.put("pageTotal", totalCount);
            resultMap.put("userList",userList);
            //resultMap.put("userMap",userList.get(0));
        }
        //list转json str
        String result = JSON.toJSONString(resultMap);
        //System.out.println("result=" + result);
        resp.getWriter().print(result);
    }

}
