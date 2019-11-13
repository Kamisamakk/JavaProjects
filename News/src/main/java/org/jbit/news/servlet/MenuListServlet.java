package org.jbit.news.servlet;


import com.alibaba.fastjson.JSON;
import org.jbit.news.dao.MenuDao;
import org.jbit.news.dao.impl.MenuDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListServlet extends HttpServlet {
    private MenuDao menuDao = new MenuDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        System.out.println(action);
        if (action != null && "list".equals(action.trim())) {
            queryList1(req, resp);
        } else if (action != null && "delete".equals(action.trim())) {
            delList(req, resp);
        }
        else if (action != null && "queryLeftMenuList".equals(action.trim())) {
            queryLeftMenuList(req, resp);
        }else if(action!=null&&"listTable".equals(action))
        {
            queryListTable(req, resp);
        }else if(action !=null&&"validMenuName".equals(action))
        {
            validMenuName(req,resp);
        }else if(action !=null&&"del".equals(action))
        {
            Del(req,resp);
        }
    }

    private void Del(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", "0");
        String menuId = req.getParameter("menuId");
        String token = req.getHeader("token");
        String userId = req.getHeader("userId");
        if(menuId!=null&&!"".equals(menuId)&&token!=null&&!"".equals(token)&&userId!=null&&!"".equals(userId)){
            int count=menuDao.deleteMenus(menuId);
            if(count>0)
            {
                resultMap.put("success", "1");
            }
        }
        String result=JSON.toJSONString(resultMap);
       resp.getWriter().print(result);
    }

    private void validMenuName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String menuName = req.getParameter("ntitle");
        boolean result = false;
        if (menuName != null && !"".equals(menuName.trim())) {
            Map<String, Object> searchMap=new HashMap<>();
            searchMap.put("menuName",menuName);
            int count=menuDao.findMenuCount(searchMap);
            if (count == 0) {
                result = true;
            }
            resp.getWriter().print(result);
        }
    }

    private void queryListTable(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String menuNameLike = req.getParameter("menuNameLike");
        String nid=req.getParameter("menuId");
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", "0");
        String menuId = req.getParameter("id");
        String token = req.getHeader("token");
        String userId = req.getHeader("userId");
        System.out.println("menuId="+menuId+",token="+token+",userId="+userId);
        int totalCount = 0;
        if (currentPage == null || "".equals(currentPage.trim())) {
            currentPage = "0";
        }
        if (pageSize == null || "".equals(pageSize.trim())) {
            pageSize = "10";
        }
        if (token != null && !"".equals(token.trim()) && userId != null && !"".equals(userId.trim())) {
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("currentPage", currentPage);
            searchMap.put("pageSize", pageSize);
            searchMap.put("menuName",menuNameLike);
            searchMap.put("orderBySortNoDesc", "1");
            searchMap.put("menuId",nid);
            totalCount = menuDao.findMenuCount(searchMap);// 总记录数
            if (totalCount > 0) {
                List<Map<String, Object>> menuTableList = menuDao.findMenuList(searchMap);
                if (menuTableList.size() > 0) {
                    resultMap.put("success", "1");
                    resultMap.put("currentPage", Integer.parseInt(currentPage) + 1);
                    resultMap.put("pageTotal", totalCount);
                    resultMap.put("menuTableList", menuTableList);
                }
            }
        }
        //list转json str
        String result = JSON.toJSONString(resultMap);
        //System.out.println("result=" + result);
        resp.getWriter().print(result);

    }


    /**
     * 获取列表
     *
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    private void queryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String menuName = req.getParameter("menuName");
        if (currentPage == null || "".equals(currentPage.trim())) {
            currentPage = "0";
        }
        if (pageSize == null || "".equals(pageSize.trim())) {
            pageSize = "5";
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("currentPage", currentPage);
        searchMap.put("pageSize", pageSize);
        searchMap.put("menuName", menuName);
        int totalCount = menuDao.findMenuCount(searchMap);//总记录数
        // System.out.println(totalCount);
        if (totalCount > 0) {
            int pageCount = totalCount / Integer.parseInt(pageSize);
            if (totalCount % Integer.parseInt(pageSize) != 0) {
                pageCount += 1;
            }
            List<Map<String, Object>> menuList = menuDao.findMenuList(searchMap);
            req.setAttribute("menuList", menuList);
            req.setAttribute("pageCount", pageCount);
        } else {
            int pageCount = 0;
            req.setAttribute("pageCount", pageCount);
        }
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPage", currentPage);
        String path = "/newspages/menu_list.jsp";
        req.getRequestDispatcher(path).forward(req, resp);
    }
    private void queryList1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", "0");
        String menuId = req.getParameter("id");
        String token = req.getHeader("token");
        String userId = req.getHeader("userId");
        System.out.println("menuId="+menuId+",token="+token+",userId="+userId);
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("orderBySortNoDesc", "1");
        List<Map<String, Object>> menuList = menuDao.findMenuLeftList(searchMap);//ZTree
        if (menuList.size()>0) {
            resultMap.put("success", "1");
            resultMap.put("menuList", menuList);
        }
        //list转json str
        String result = JSON.toJSONString(resultMap);
        //System.out.println("result=" + result);
        resp.getWriter().print(result);
    }

    private void queryLeftMenuList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String menuId = req.getParameter("id");
        //System.out.println("menuId=" + menuId);
        Map<String, Object> searchMap = new HashMap<String, Object>();
        if (menuId != null && !"".equals(menuId.trim())) {
            searchMap.put("menuParentId", menuId);
        } else {
            searchMap.put("menuParentIdEqualsNull", "1");
        }
        //List<Map<String, Object>> menuList = menuDao.findMenuList(searchMap);//Css
        List<Map<String, Object>> menuList = menuDao.findMenuLeftList(searchMap);//ZTree
        //list转json str
        String result = JSON.toJSONString(menuList);
        //System.out.println("result=" + result);
        resp.getWriter().print(result);


    }

    private void delList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //int nid=Integer.parseInt(req.getParameter("nid"));
        String nid = req.getParameter("nid");
        if (nid != null && !"".equals(nid.trim())) {
            int result = menuDao.deleteMenus(nid);
            System.out.println(result);
            if (result == 1) {
                resp.getWriter().append("<script>alert('删除成功!');location.href='" + req.getContextPath() + "/menuServlet?action=list';</script>");
            } else {
                resp.getWriter().append("<script>alert('删除失败');history.back();</script>");
            }
            //out.print("<script>alert('删除成功');location.href='advertisement_list.jsp';</script>");
        }
        //queryList(req,resp);
    }
}
