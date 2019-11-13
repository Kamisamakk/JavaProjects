package org.jbit.news.servlet;

import org.jbit.news.dao.ShopListDao;
import org.jbit.news.dao.ShoppingCartDao;
import org.jbit.news.dao.impl.ShopListDaoImpl;
import org.jbit.news.dao.impl.ShoppingCartDaoImpl;
import org.jbit.news.entity.ShopList;
import org.jbit.news.entity.ShopProduct;
import org.jbit.news.entity.ShoppingCartProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.now;

@WebServlet("/shoplistServlet")
public class ShopListServlet extends HttpServlet {
    ShopListDao shopListDao=new ShopListDaoImpl();
    ShoppingCartDao shoppingCartDao=new ShoppingCartDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        if(action!=null&&"list".equals(action))
        {
            queryList(req,resp);
        }else if(action!=null&&"check".equals(action))
        {
            Check(req,resp);
        }
        else if(action!=null&&"checkjsp".equals(action))
        {
            ToCheck(req,resp);
        }else if(action!=null&&"pay".equals(action))
        {
            Pay(req,resp);
        }
    }

    private void Pay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId=req.getParameter("userId");
//        String listNo=req.getParameter("userId");
        Map<String,Object> searchmap=new HashMap<>();
        searchmap.put("userId",userId);
        List<Map<String,Object>> productList=shoppingCartDao.findProductList(searchmap);
        ShopList shopList=new ShopList();
        for (Map<String,Object> list:productList) {
            shopList.setNo(now()+userId);
            shopList.setMoney(Integer.valueOf((String) list.get("listTotal")));
            shopList.setUserid(userId);
            shopList.setOperator(userId);
            ShopProduct shopProduct=new ShopProduct();
            shopProduct.setNo(now()+userId);
            shopProduct.setName((String)list.get("productName"));
            shopProduct.setTotal(Integer.valueOf((String)list.get("productTotal")));
            shopProduct.setCount(Integer.valueOf((String)list.get("productCount")));
            shopProduct.setPrice(Integer.valueOf((String)list.get("productPrice")));
            shopProduct.setBrand((String)list.get("productBrand"));
            shopProduct.setType((String)list.get("productType"));
            shopProduct.setPicpath0((String)list.get("productPicPath0"));
            shopProduct.setPicpath1((String)list.get("productPicPath1"));
            shopProduct.setPicpath2((String)list.get("productPicPath2"));
            shopProduct.setPicpath3((String)list.get("productPicPath3"));
            shopProduct.setPicpath4((String)list.get("productPicPath4"));
            shopProduct.setPicpath5((String)list.get("productPicPath5"));
            shopListDao.AddProductList(shopProduct);
        }
        int result= shopListDao.AddMainList(shopList);
        resp.getWriter().print(result);
        //
    }

    private void Check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nid=req.getParameter("nid");
        String states=req.getParameter("check_states");
        String checkComment=req.getParameter("checkcomment");
        String operator=req.getParameter("operator");
        ShopList shopList=new ShopList();
        shopList.setNid(Integer.valueOf(nid));
        shopList.setStates(Integer.valueOf(states));
        shopList.setCheckcomment(checkComment);
        shopList.setOperator(operator);
        int result=shopListDao.UpdateList(shopList);
        if(result>0)
        {
            resp.getWriter().append("<script>alert('审核成功');location.href='" + req.getContextPath() + "/shoplistServlet?action=list';</script>");
        } else {
            resp.getWriter().append("<script>alert('审核失败');history.back();");
        }
    }

    private void ToCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nid=req.getParameter("list_no");
        Map<String,Object> shopList= shopListDao.GetList(nid);
        req.setAttribute("shopList",shopList);
        String path="/newspages/list_check.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

    private void queryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        //String nid = req.getParameter("nid");
        String list_no=req.getParameter("list_no");
        if (currentPage == null || "".equals(currentPage.trim())) {
            currentPage = "0";
        }
        if (pageSize == null || "".equals(pageSize.trim())) {
            pageSize = "5";
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("currentPage", currentPage);
        searchMap.put("pageSize", pageSize);
        //searchMap.put("nid", nid);
        searchMap.put("list_no",list_no);
        int totalCount = shopListDao.findListCount(searchMap);//总记录数
        // System.out.println(totalCount);
        if (totalCount > 0) {
            int pageCount = totalCount / Integer.parseInt(pageSize);
            if (totalCount % Integer.parseInt(pageSize) != 0) {
                pageCount += 1;
            }
            List<Map<String, Object>> list = shopListDao.findAllList(searchMap);
            req.setAttribute("shopList", list);
            req.setAttribute("pageCount", pageCount);
        } else {
            int pageCount = 0;
            req.setAttribute("pageCount", pageCount);
        }
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPage", currentPage);
        String path="/newspages/shop_list.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

}
