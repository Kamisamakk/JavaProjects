package org.jbit.news.servlet;


import org.jbit.news.dao.ProductBrandDao;
import org.jbit.news.dao.impl.ProductBrandDaoImpl;
import org.jbit.news.entity.ProductBrand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/productBrandServlet")
public class ProductBrandServlet extends HttpServlet {
    ProductBrandDao productBrandDao=new ProductBrandDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        if(action!=null&&"list".equals(action))
        {
            queryList(req, resp);
        }else if(action!=null&&"delete".equals(action))
        {
            DelBrand(req, resp);
        }
        else if(action!=null&&"addjsp".equals(action))
        {
            ToAdd(req, resp);
        }
        else if(action!=null&&"add".equals(action))
        {
            AddBrand(req, resp);
        }else if(action!=null&&"info".equals(action))
        {
            ToInfo(req, resp);
        }else if(action!=null&&"updatejsp".equals(action))
        {
            ToUpdate(req, resp);
        }else if(action!=null&&"update".equals(action))
        {
            UpdateBrand(req, resp);
        }
    }
    private void queryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String brand = req.getParameter("brand");
        String nid = req.getParameter("nid");
        if (currentPage == null || "".equals(currentPage.trim())) {
            currentPage = "0";
        }
        if (pageSize == null || "".equals(pageSize.trim())) {
            pageSize = "5";
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("currentPage", currentPage);
        searchMap.put("pageSize", pageSize);
        searchMap.put("productId", nid);
        searchMap.put("productBrand", brand);
        int totalCount = productBrandDao.findProductBrandCount(searchMap);//总记录数
        // System.out.println(totalCount);
        if (totalCount > 0) {
            int pageCount = totalCount / Integer.parseInt(pageSize);
            if (totalCount % Integer.parseInt(pageSize) != 0) {
                pageCount += 1;
            }
            List<Map<String, Object>> productBrandList = productBrandDao.findProductBrandList(searchMap);
            req.setAttribute("productBrandList", productBrandList);
            req.setAttribute("pageCount", pageCount);
        } else {
            int pageCount = 0;
            req.setAttribute("pageCount", pageCount);
        }
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPage", currentPage);
        String path="/newspages/productbrand_list.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }
    private void DelBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nid = req.getParameter("nid");
        if (nid != null && !"".equals(nid.trim())) {
            int result = productBrandDao.DeleteProductBrand(nid);
            resp.getWriter().print(result);
        }
    }
    private void ToAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // ProductBrand productBrand=new ProductBrand();
        List<Map<String,Object>> productBrandList=productBrandDao.findProductBrandList(null);
        String path="/newspages/productbrand_add.jsp";
        req.setAttribute("productBrandList",productBrandList);
        req.getRequestDispatcher(path).forward(req,resp);
    }
    private void AddBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //String productBrandId = req.getParameter("brand");
        String productBrandName = req.getParameter("brand");
        String productBrandParentId = req.getParameter("brandparent_id");
        String productBrandSummary = req.getParameter("summary");
        String productBrandSortId = req.getParameter("sort_id");
        String productBrandOperator = req.getParameter("operator");
        if (productBrandName != null && !"".equals(productBrandName.trim())
                && productBrandSortId != null && !"".equals(productBrandSortId.trim())) {
            ProductBrand productBrand = new ProductBrand();
            productBrand.setBrand(productBrandName);
            if(productBrandParentId!=null&&!"".equals(productBrandParentId))
                productBrand.setBrandparent_id(Integer.valueOf(productBrandParentId));
            productBrand.setSort_id(Integer.valueOf(productBrandSortId));
            productBrand.setOperator(productBrandOperator);
            productBrand.setSummary(productBrandSummary);
            productBrandDao.AddProductBrand(productBrand);
            resp.getWriter().append("<script>alert('保存成功!');location.href='" + req.getContextPath() + "/productBrandServlet?action=list';</script>");
        } else {
            resp.getWriter().append("<script>alert('请填写完整参数');history.back();</script>");
        }
    }
    private void ToInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productBrandId=req.getParameter("nid");
        ProductBrand productBrand=productBrandDao.GetProductBrand(productBrandId);
        req.setAttribute("productBrand",productBrand);
        String path="/newspages/productbrand_info.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }
    private void ToUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //String productName=req.getParameter("brand");
        String productBrandId=req.getParameter("nid");
        ProductBrand productBrand=productBrandDao.GetProductBrand(productBrandId);
        List<Map<String,Object>> productBrandList=productBrandDao.findProductBrandList(null);
        req.setAttribute("productBrand",productBrand);
        req.setAttribute("productBrandList",productBrandList);
        String path="/newspages/productbrand_update.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }
    private void UpdateBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productBrandId = req.getParameter("nid");
        String productBrandName = req.getParameter("brand");
        String productBrandParentId = req.getParameter("brandparent_id");
        String productBrandSummary = req.getParameter("summary");
        String productBrandSortId = req.getParameter("sort_id");
        String productBrandOperator = req.getParameter("operator");
        if (productBrandName != null && !"".equals(productBrandName.trim())
                && productBrandSortId != null && !"".equals(productBrandSortId.trim())) {
            ProductBrand productBrand = new ProductBrand();
            productBrand.setNid(Integer.valueOf(productBrandId));
            productBrand.setBrand(productBrandName);
            if(productBrandParentId!=null&&!"".equals(productBrandParentId))
                productBrand.setBrandparent_id(Integer.valueOf(productBrandParentId));
            productBrand.setSort_id(Integer.valueOf(productBrandSortId));
            productBrand.setOperator(productBrandOperator);
            productBrand.setSummary(productBrandSummary);
            int result=productBrandDao.UpdateProductBrand(productBrand);
            if(result>0)
                resp.getWriter().append("<script>alert('修改成功!');location.href='" + req.getContextPath() + "/productBrandServlet?action=list';</script>");
            else resp.getWriter().append("<script>alert('修改失败');history.back();</script>");
        } else {
            resp.getWriter().append("<script>alert('请填写完整参数');history.back();</script>");
        }

    }

}
