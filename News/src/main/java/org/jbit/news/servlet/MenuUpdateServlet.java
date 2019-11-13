package org.jbit.news.servlet;

import com.alibaba.fastjson.JSON;
import org.jbit.news.dao.MenuDao;
import org.jbit.news.dao.impl.MenuDaoImpl;
import org.jbit.news.entity.Menu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuUpdateServlet extends HttpServlet {
    private MenuDao menuDao = new MenuDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && "updateList".equals(action.trim())) {
            updateList(req, resp);
        }
        if (action != null && "update".equals(action.trim())) {
            update(req, resp);
        }
    }

    private void update1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nid = req.getParameter("nid");
       // String menuName = req.getParameter("ntitle");
        String menuName = req.getParameter("ntitle");
        String menuParent_Id = req.getParameter("menuParentName");
        String menuSummary = req.getParameter("nsummary");
        String menuJsp = req.getParameter("path");

        if (menuName != null && !"".equals(menuName.trim())
                && menuSummary != null && !"".equals(menuSummary.trim())
                && menuJsp != null && !"".equals(menuJsp.trim())) {
            Menu menu = new Menu();
            menu.setNtitle(menuName);
            menu.setNid(Integer.valueOf(nid));
            if(menuParent_Id!=null)menu.setNparent_id(menuParent_Id);
            menu.setNsummary(menuSummary);
            menu.setNjsp(menuJsp);
            menuDao.updateMenu(menu);
            resp.getWriter().append("<script>alert('保存成功!');location.href='" + req.getContextPath() + "/menuServlet?action=list';</script>");

        } else {
            resp.getWriter().append("<script>alert('请填写完整参数');history.back();</script>");
        }
    }
    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nid = req.getParameter("menuId");
        String token = req.getHeader("token");
        String userId = req.getHeader("userId");
        String ntitle = req.getParameter("ntitle_up");
        String menuParentName = req.getParameter("menuParentName_up");
        String isParent = req.getParameter("isParent_up");
        String sortId = req.getParameter("menuSortId_up");
        String summary = req.getParameter("nsummary_up");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", "0");
        if (token != null && !"".equals(token.trim()) && userId != null && !"".equals(userId.trim())&&
                nid!=null&&!"".equals(nid)) {
            Menu menu=new Menu();
            menu.setNid(Integer.valueOf(nid));
            menu.setNtitle(ntitle);
            menu.setNparent_id(menuParentName);
            menu.setIsParent(Integer.valueOf(isParent));
            menu.setNsort_id(Integer.valueOf(sortId));
            menu.setNsummary(summary);
            int count=menuDao.updateMenu(menu);
            if (count>0)
            {
                resultMap.put("success", "1");
            }
        }
        String result=JSON.toJSONString(resultMap);
        resp.getWriter().print(result);
    }

    private void updateList1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nid = req.getParameter("menuId");
        String operator=req.getParameter("operator");
        Map<String,String> searchMap=new HashMap<>();
        searchMap.put("nid",nid);
        Menu menu=menuDao.GetMenu(searchMap);
        req.setAttribute("menuUpdate",menu);
        req.setAttribute("operator",operator);
        //menuDao.findMenuList()
        String path = "/newspages/menu_update.jsp";
        req.getRequestDispatcher(path).forward(req, resp);
    }
    private void updateList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nid = req.getParameter("menuId");
        String token = req.getHeader("token");
        String userId = req.getHeader("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", "0");
        if (token != null && !"".equals(token.trim()) && userId != null && !"".equals(userId.trim())&&
        nid!=null&&!"".equals(nid)) {
            Map<String,Object> searchMap=new HashMap<>();
            searchMap.put("menuId",nid);
            List<Map<String, Object>> menu=menuDao.findMenuList(searchMap);
            resultMap.put("menu",menu.get(0));
        }
        resultMap.put("success", "1");
        String result = JSON.toJSONString(resultMap);
        //System.out.println("result=" + result);
        resp.getWriter().print(result);
    }

}
