package org.jbit.news.servlet;

import com.alibaba.fastjson.JSON;
import org.jbit.news.dao.ProductTypeDao;
import org.jbit.news.dao.impl.ProductTypeDaoImpl;
import org.jbit.news.entity.ProductType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/productTypeServlet")
public class ProductTypeServlet extends HttpServlet {
    ProductTypeDao productTypeDao=new ProductTypeDaoImpl();
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
            DelType(req, resp);
        }
        else if(action!=null&&"addjsp".equals(action))
        {
            ToAdd(req, resp);
        }
        else if(action!=null&&"add".equals(action))
        {
            AddType(req, resp);
        }else if(action!=null&&"info".equals(action))
        {
            ToInfo(req, resp);
        }else if(action!=null&&"updatejsp".equals(action))
        {
            ToUpdate(req, resp);
        }else if(action!=null&&"update".equals(action))
        {
            UpdateType(req, resp);
        }else if(action!=null&&"queryLeftMenuList".equals(action))
        {
            queryLeftMenuList(req, resp);
        }
    }

    private void queryLeftMenuList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String typeId = req.getParameter("id");
        Map<String, Object> searchMap = new HashMap<String, Object>();
        if (typeId != null && !"".equals(typeId.trim())) {
            searchMap.put("typeParentId", typeId);
        } else {
            searchMap.put("typeParentIdEqualsNull", "1");
        }
        //List<Map<String, Object>> menuList = menuDao.findMenuList(searchMap);//Css
        List<Map<String, Object>> menuList = productTypeDao.findTypeLeftList(searchMap);//ZTree
        //list转json str
        String result = JSON.toJSONString(menuList);
        //System.out.println("result=" + result);
        resp.getWriter().print(result);
    }

    private void queryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String type = req.getParameter("type");
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
        searchMap.put("productTypeId", nid);
        searchMap.put("productType", type);
        int totalCount = productTypeDao.findProductTypeCount(searchMap);//总记录数
        // System.out.println(totalCount);
        if (totalCount > 0) {
            int pageCount = totalCount / Integer.parseInt(pageSize);
            if (totalCount % Integer.parseInt(pageSize) != 0) {
                pageCount += 1;
            }
            List<Map<String, Object>> productTypeList = productTypeDao.findProductTypeList(searchMap);
            req.setAttribute("productTypeList", productTypeList);
            req.setAttribute("pageCount", pageCount);
        } else {
            int pageCount = 0;
            req.setAttribute("pageCount", pageCount);
        }
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPage", currentPage);
        String path="/newspages/producttype_list.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }
    private void DelType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nid = req.getParameter("nid");
        if (nid != null && !"".equals(nid.trim())) {
            int result = productTypeDao.DeleteProductType(nid);
            resp.getWriter().print(result);
        }
    }
    private void ToAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ProductBrand productBrand=new ProductBrand();
        List<Map<String,Object>> productTypeList=productTypeDao.findProductTypeList(null);
        String path="/newspages/producttype_add.jsp";
        req.setAttribute("productTypeList",productTypeList);
        req.getRequestDispatcher(path).forward(req,resp);
    }
    private void AddType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //String productBrandId = req.getParameter("brand");
        String productTypeName = req.getParameter("type");
        String productTypeParentId = req.getParameter("typeparent_id");
        String productTypeSummary = req.getParameter("summary");
        String productTypeSortId = req.getParameter("sort_id");
        String productTypeOperator = req.getParameter("operator");
        if (productTypeName != null && !"".equals(productTypeName.trim())
                && productTypeSortId != null && !"".equals(productTypeSortId.trim())) {
            ProductType productType = new ProductType();
            productType.setType(productTypeName);
            if(productTypeParentId!=null&&!"".equals(productTypeParentId))
                productType.setTypeparent_id(Integer.valueOf(productTypeParentId));
            productType.setSort_id(Integer.valueOf(productTypeSortId));
            productType.setOperator(productTypeOperator);
            productType.setSummary(productTypeSummary);
            productTypeDao.AddProductType(productType);
            resp.getWriter().append("<script>alert('保存成功!');location.href='" + req.getContextPath() + "/productTypeServlet?action=list';</script>");
        } else {
            resp.getWriter().append("<script>alert('请填写完整参数');history.back();</script>");
        }
    }
    private void ToInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productTypeId=req.getParameter("nid");
        ProductType productType=productTypeDao.GetProductType(productTypeId);
        req.setAttribute("productType",productType);
        String path="/newspages/producttype_info.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }
    private void ToUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //String productName=req.getParameter("brand");
        String productTypeId=req.getParameter("nid");
        ProductType productType=productTypeDao.GetProductType(productTypeId);
        List<Map<String,Object>> productTypeList=productTypeDao.findProductTypeList(null);
        req.setAttribute("productType",productType);
        req.setAttribute("productTypeList",productTypeList);
        String path="/newspages/producttype_update.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }
    private void UpdateType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productTypeId = req.getParameter("nid");
        String productTypeName = req.getParameter("type");
        String productTypeParentId = req.getParameter("typeparent_id");
        String productTypeSummary = req.getParameter("summary");
        String productTypeSortId = req.getParameter("sort_id");
        String productTypeOperator = req.getParameter("operator");
        if (productTypeName != null && !"".equals(productTypeName.trim())
                && productTypeSortId != null && !"".equals(productTypeSortId.trim())) {
            ProductType productType = new ProductType();
            productType.setNid(Integer.valueOf(productTypeId));
            productType.setType(productTypeName);
            if(productTypeParentId!=null&&!"".equals(productTypeParentId))
                productType.setTypeparent_id(Integer.valueOf(productTypeParentId));
            productType.setSort_id(Integer.valueOf(productTypeSortId));
            productType.setOperator(productTypeOperator);
            productType.setSummary(productTypeSummary);
            int result=productTypeDao.UpdateProductType(productType);
            if(result>0)
                resp.getWriter().append("<script>alert('修改成功!');location.href='" + req.getContextPath() + "/productTypeServlet?action=list';</script>");
            else resp.getWriter().append("<script>alert('修改失败');history.back();</script>");
        } else {
            resp.getWriter().append("<script>alert('请填写完整参数');history.back();</script>");
        }

    }



}
