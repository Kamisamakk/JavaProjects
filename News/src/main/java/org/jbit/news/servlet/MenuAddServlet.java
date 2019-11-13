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
import java.util.Map;

public class MenuAddServlet extends HttpServlet {
    private MenuDao menuDao = new MenuDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        if (action != null && "addList".equals(action.trim())) {
            addList(req, resp);
        }
        else if (action != null && "add".equals(action.trim())) {
            add(req, resp);
        }
        else {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("success", "0");
            resultMap.put("msg", "请上传action参数或核对action的参数值!");
            String result = JSON.toJSONString(resultMap);
            System.out.println("result=" + result);
            resp.getWriter().print(result);
        }
    }


    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String menuName = req.getParameter("ntitle");
        String menuParent_Id = req.getParameter("menuParentName");
        String menuSummary = req.getParameter("nsummary");
        String menuOperator = req.getParameter("operator");
        String menuSortId=req.getParameter("menuSortId");
        String isParent = req.getParameter("isParent");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", "0");
        String menuId = req.getParameter("id");
        String token = req.getHeader("token");
        String userId = req.getHeader("userId");
        //String menuOperator_time = req.getParameter("operator_time");
        //int result=0;
        if (menuName != null && !"".equals(menuName.trim()) && menuSummary != null
                && !"".equals(menuSummary.trim())
                &&menuId!=null&&"".equals(menuId)&& token!=null
                &&"".equals(token)&& userId!=null&&"".equals(userId)
        ) {
            Menu menu = new Menu();
            menu.setNtitle(menuName);
            menu.setNparent_id(menuParent_Id);
            menu.setNsummary(menuSummary);
            menu.setNoperator(menuOperator);
            menu.setNsort_id(Integer.valueOf(menuSortId));
            menu.setIsParent(Integer.valueOf(isParent));
            menuDao.saveMenu(menu);
            resultMap.put("success", "1");
            String result = JSON.toJSONString(resultMap);
            resp.getWriter().print(result);
            //resp.getWriter().append("<script>alert('保存成功!');location.href='" + req.getContextPath() + "/menuServlet?action=list';</script>");
        } else {
            //resp.getWriter().append("<script>alert('请填写完整参数');history.back();</script>");
        }
    }

    private void addList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getAttribute("admin").toString();
        String path = "/newspages/menu_add.jsp";
        req.getRequestDispatcher(path).forward(req, resp);
    }

}
