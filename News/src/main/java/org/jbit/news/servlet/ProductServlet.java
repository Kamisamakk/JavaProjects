package org.jbit.news.servlet;

import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.jbit.news.dao.AdvertisementDao;
import org.jbit.news.dao.ProductBrandDao;
import org.jbit.news.dao.ProductDao;
import org.jbit.news.dao.ProductTypeDao;
import org.jbit.news.dao.impl.ProductBrandDaoImpl;
import org.jbit.news.dao.impl.ProductDaoImpl;
import org.jbit.news.dao.impl.ProductTypeDaoImpl;
import org.jbit.news.entity.Advertisement;
import org.jbit.news.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

public class ProductServlet extends HttpServlet {
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload fileUpload = new ServletFileUpload(factory);
    ProductDao productDao=new ProductDaoImpl();
    ProductTypeDao productTypeDao=new ProductTypeDaoImpl();
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
            queryList(req,resp);
        }else if(action!=null&&"productlist".equals(action))
        {
            ProductList1(req, resp);
        }
        else if(action!=null&&"info".equals(action))
        {
            ToInfo(req,resp);
        }
        else if(action!=null&&"addjsp".equals(action))
        {
            ToAdd(req,resp);
        }
        else if(action!=null&&"add".equals(action))
        {
            try {
                AddProduct(req,resp);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
        else if(action!=null&&"delete".equals(action))
        {
            DelProduct(req,resp);
        }else if (action!=null&&"validProductName".equals(action))
        {
            validProductName(req,resp);
        }
        else if(action!=null&&"updatejsp".equals(action))
        {
            ToUpdate(req,resp);
        }
        else if(action!=null&&"update".equals(action))
        {
            try {
                Update(req,resp);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }else if(action!=null&&"productview".equals(action))
        {
            ProductView(req, resp);
        }
    }

    private void Update(HttpServletRequest req, HttpServletResponse resp) throws IOException, FileUploadException {
        String nid= req.getParameter("nid");
        String []path=new String[6];
        List<FileItem> fileList = fileUpload.parseRequest(req);
        Product product = new Product();
        String oldpicpath;
        int index=0;
        for (FileItem fileItem : fileList ) {
            oldpicpath="oldpicpath"+index;
            if (fileItem.isFormField()) {
                if ("name".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setName(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
                } else if ("type_id".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setType_id(Integer.valueOf(fileItem.getString()));
                } else if ("brand_id".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setBrand_id(Integer.parseInt(fileItem.getString()));
                } else if ("summary".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setSummary(fileItem.getString());
                } else if ("price".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setPrice(Integer.valueOf(fileItem.getString()));
                } else if ("sort_id".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setSort_id(Integer.valueOf(fileItem.getString()));
                }else if ("operator".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setOperator(fileItem.getString());
                }
                else if (oldpicpath.equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    path[index]=new String(fileItem.getString().getBytes("ISO8859-1"), "UTF-8");
                    index++;
               }
            }
            else {
                if (fileItem.getName() != null && !"".equals(fileItem.getName().trim())) {
                    String realPath = req.getServletContext().getRealPath("/");
                    String extendName = fileItem.getName().substring(fileItem.getName().lastIndexOf(".") + 1);
                    String filename = System.currentTimeMillis() + "." + extendName;
                    path[index] = filename;
                    index++;
                    OutputStream os = new FileOutputStream(new File(realPath + "/" + filename));
                    IOUtils.copy(fileItem.getInputStream(), os);
                }
            }
        }
        product.setNpicpath(path);
        //System.out.println(cout);
        product.setNid(Integer.valueOf(nid));
        ProductDao productDao = new ProductDaoImpl();
        int result = productDao.UpdateProduct(product);
        System.out.println("result = " + result);
        if (result > 0) {
            resp.getWriter().append("<script>alert('修改成功');location.href='" + req.getContextPath() + "/productServlet?action=list';</script>");
        } else {
            resp.getWriter().append("<script>alert('修改失败');location.href='" + req.getContextPath() + "/productServlet?action=updatejsp';</script>");
        }
    }

    private void ToUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName=req.getParameter("name");
        String productId=req.getParameter("nid");
        List<Map<String,Object>> productTypeList=productTypeDao.findProductTypeList(null);
        List<Map<String,Object>> productBrandList=productBrandDao.findProductBrandList(null);
        req.setAttribute("productTypeList",productTypeList);
        req.setAttribute("productBrandList",productBrandList);
        Product product=productDao.GetProduct(productName);
        req.setAttribute("product",product);
        req.setAttribute("productId",productId);
        String path="/newspages/product_update.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

    private void validProductName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productName = req.getParameter("product");
        System.out.println("productName=" + productName);
        boolean result = false;
        if (productName != null && !"".equals(productName.trim())) {
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("productName", productName);
            int count = productDao.findProductCount(searchMap);
            if (count == 0) {
                result = true;
            }
        }
        resp.getWriter().print(result);
    }

    private void DelProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nid = req.getParameter("nid");
        if (nid != null && !"".equals(nid.trim())) {
            int result = productDao.DeleteProduct(nid);
            if(result>0)
            {
                resp.getWriter().append("<script>alert('删除成功');location.href='" + req.getContextPath() + "/productServlet?action=list';</script>");
            } else {
                resp.getWriter().append("<script>alert('删除失败');history.back();");
            }
        }
    }

    private void AddProduct(HttpServletRequest req, HttpServletResponse resp) throws FileUploadException, IOException {
        List<FileItem> fileList = fileUpload.parseRequest(req);

        Product product = new Product();
        String []path=new String[6];
        int cout=0;
        for (FileItem fileItem : fileList ) {
            if (fileItem.isFormField()) {
                if ("name".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setName(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
                } else if ("type_id".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setType_id(Integer.parseInt(fileItem.getString()));
                } else if ("brand_id".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setBrand_id(Integer.parseInt(fileItem.getString()));
                } else if ("summary".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setSummary(fileItem.getString());
                } else if ("price".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setPrice(Integer.valueOf(fileItem.getString()));
                } else if ("sort_id".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setSort_id(Integer.valueOf(fileItem.getString()));
                } else if ("operator".equals(fileItem.getFieldName())
                        && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                    product.setOperator(fileItem.getString());
                }
            } else {
                if (fileItem.getName() != null && !"".equals(fileItem.getName().trim())) {
                    String realPath = req.getServletContext().getRealPath("/");
                    String extendName = fileItem.getName().substring(fileItem.getName().lastIndexOf(".") + 1);
                    String filename = System.currentTimeMillis() + "." + extendName;
                    path[cout]=filename;
                    cout++;
                    product.setNpicpath(path);
                    OutputStream os = new FileOutputStream(new File(realPath + "/" + filename));
                    IOUtils.copy(fileItem.getInputStream(), os);
                }
            }
        }
        ProductDao productDao = new ProductDaoImpl();
        int result = productDao.AddProduct(product);
        System.out.println("product = " + result);
        if (result > 0) {

            resp.getWriter().append("<script>alert('保存成功');location.href='" + req.getContextPath() + "/productServlet?action=list';</script>");
        } else {
            resp.getWriter().append("<script>alert('保存失败');history.back();");
        }
    }

    private void ToAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Map<String,Object>> productTypeList=productTypeDao.findProductTypeList(null);
        List<Map<String,Object>> productBrandList=productBrandDao.findProductBrandList(null);
        req.setAttribute("productTypeList",productTypeList);
        req.setAttribute("productBrandList",productBrandList);
        String path="/newspages/product_add.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

    private void ToInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName=req.getParameter("name");
        Product product=productDao.GetProduct(productName);
        req.setAttribute("product",product);
        String path="/newspages/product_info.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

    private void queryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        //String nid = req.getParameter("nid");
        String type_id=req.getParameter("type_id");
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
        searchMap.put("type_id",type_id);
        int totalCount = productDao.findProductCount(searchMap);//总记录数
        // System.out.println(totalCount);
        if (totalCount > 0) {
            int pageCount = totalCount / Integer.parseInt(pageSize);
            if (totalCount % Integer.parseInt(pageSize) != 0) {
                pageCount += 1;
            }
            List<Map<String, Object>> productList = productDao.findProductList(searchMap);
            req.setAttribute("productList", productList);
            req.setAttribute("pageCount", pageCount);
        } else {
            int pageCount = 0;
            req.setAttribute("pageCount", pageCount);
        }
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPage", currentPage);
        String path="/newspages/product_list.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

    private void ProductList1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String type_id=req.getParameter("type_id");
        if (currentPage == null || "".equals(currentPage.trim())) {
            currentPage = "0";
        }
        if (pageSize == null || "".equals(pageSize.trim())) {
            pageSize = "3";
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("currentPage", currentPage);
        searchMap.put("pageSize", pageSize);
        //searchMap.put("nid", nid);
        if(type_id!=null&&!"".equals(type_id))
            searchMap.put("productTypeId",type_id);
        int totalCount = productDao.findProductCount(searchMap);//总记录数
        Map<String,Object> pagesMap=new HashMap<String, Object>();
        // System.out.println(totalCount);
        if (totalCount > 0) {
            int pageCount = totalCount / Integer.parseInt(pageSize);
            if (totalCount % Integer.parseInt(pageSize) != 0) {
                pageCount += 1;
            }
            pagesMap.put("pageCount",pageCount);

        } else {
            int pageCount = 0;
            pagesMap.put("pageCount",pageCount);
        }
        pagesMap.put("currentPage",currentPage);
        pagesMap.put("totalCount",totalCount);
        List<Map<String, Object>> pagestList =new ArrayList<>();
        pagestList.add(pagesMap);
        String result = JSON.toJSONString(pagestList);
        resp.getWriter().print(result);
    }
    private void ProductView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        String type_id=req.getParameter("type_id");
        if (currentPage == null || "".equals(currentPage.trim())) {
            currentPage = "0";
        }
        if (pageSize == null || "".equals(pageSize.trim())) {
            pageSize = "3";
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        if(type_id!=null&&!"".equals(type_id))
            searchMap.put("productTypeId",type_id);
        searchMap.put("currentPage", currentPage);
        searchMap.put("pageSize", pageSize);
        //searchMap.put("orderBySortNoDesc", "1");//按排序号降序
        List<Map<String, Object>> productList = productDao.findProductList(searchMap);
        String result = JSON.toJSONString(productList);
        resp.getWriter().print(result);
    }

}
