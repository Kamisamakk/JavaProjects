package org.jbit.news.servlet;

import org.jbit.news.dao.ProductDao;
import org.jbit.news.dao.ShoppingCartDao;
import org.jbit.news.dao.impl.ProductDaoImpl;
import org.jbit.news.dao.impl.ShoppingCartDaoImpl;
import org.jbit.news.entity.Product;
import org.jbit.news.entity.ShoppingCartProduct;
import org.jbit.news.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/shoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {
    ShoppingCartDao shoppingCartDao=new ShoppingCartDaoImpl();
    ProductDao productDao=new ProductDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        if(action!=null&&"cart".equals(action))
        {
            ToCart(req,resp);
        }else if(action!=null&&"add".equals(action))
        {
            try {
                Add(req,resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(action!=null&&"reduce".equals(action))
        {
            Reduce(req,resp);
        }
        else if(action!=null&&"newadd".equals(action))
        {
            try {
                NewAdd(req,resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(action!=null&&"payjsp".equals(action))
        {
            ToPay(req,resp);
        }

    }

    private void ToPay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId=req.getParameter("userId");
        Map<String,Object> searchmap=new HashMap<>();
        searchmap.put("userId",userId);
        List<Map<String,Object>> productList=shoppingCartDao.findProductList(searchmap);
        req.setAttribute("productList",productList);
        //req.setAttribute("userId",userId);
        String path="/newspages/payinfo.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

    private void NewAdd(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String productName=req.getParameter("productName");
        String userId=req.getParameter("userId");
        ShoppingCartProduct shoppingCartProduct =new ShoppingCartProduct();
        User user=new User();
        user.setuId(Integer.parseInt(userId));
        shoppingCartProduct.setCart_id("2019"+userId);
        Product product=productDao.GetProduct(productName);
        shoppingCartProduct.setName(productName);
        shoppingCartProduct.setPrice(product.getPrice());
        shoppingCartProduct.setType(product.getType());
        shoppingCartProduct.setBrand(product.getBrand());
        shoppingCartProduct.setSummary(product.getSummary());
        shoppingCartProduct.setPicpath0(product.getNpicpath()[0]);
        shoppingCartProduct.setPicpath1(product.getNpicpath()[1]);
        shoppingCartProduct.setPicpath2(product.getNpicpath()[2]);
        shoppingCartProduct.setPicpath3(product.getNpicpath()[3]);
        shoppingCartProduct.setPicpath4(product.getNpicpath()[4]);
        shoppingCartProduct.setPicpath5(product.getNpicpath()[5]);
        int result=shoppingCartDao.AddProduct(shoppingCartProduct,user);
        resp.getWriter().print(result);
    }

    private void Reduce(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productName=req.getParameter("productName");
        ShoppingCartProduct shoppingCartProduct =new ShoppingCartProduct();
        shoppingCartProduct.setName(productName);
        int result=shoppingCartDao.ReduceProduct(shoppingCartProduct);
        resp.getWriter().print(result);
    }

    private void Add(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String productName=req.getParameter("productName");
        ShoppingCartProduct shoppingCartProduct =new ShoppingCartProduct();
        shoppingCartProduct.setName(productName);
        //shoppingCart.setCart_id("2019"+userId);
        int result=shoppingCartDao.AddProduct(shoppingCartProduct,null);
        resp.getWriter().print(result);
    }

    private void ToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId=req.getParameter("userId");
        Map<String,Object> searchmap=new HashMap<>();
        searchmap.put("userId",userId);
        List<Map<String,Object>> productList=shoppingCartDao.findProductList(searchmap);
        req.setAttribute("productList",productList);
        req.getSession().setAttribute("userId",userId);
        String path="/newspages/shopping_cart.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }
}
