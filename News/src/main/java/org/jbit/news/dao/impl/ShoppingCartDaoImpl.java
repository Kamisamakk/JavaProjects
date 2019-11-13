package org.jbit.news.dao.impl;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.ShoppingCartDao;
import org.jbit.news.entity.ShoppingCartProduct;
import org.jbit.news.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ShoppingCartDaoImpl extends BaseDao implements ShoppingCartDao {
    @Override
    public List<Map<String, Object>> findProductList(Map<String, Object> searchMap) {
        String sql = "select t.nid,t.cart_id,t.name,t.price,t.count,t.type,t.brand,t.total,"
                +"t.picpath0,t.picpath1,t.picpath2,t.picpath3,t.picpath4,t.picpath5,"
                + "p.nid,p.total"
                + " from shopping_product t left join shopping_cart p"
                + " on t.cart_id=p.nid"
                + " where 1=1 ";
        //商品名称查询
        if (searchMap != null && searchMap.get("productName") != null) {
            sql += " and  t.name = '" + searchMap.get("productName") + "'";
        }
        if (searchMap != null && searchMap.get("userId") != null) {
            sql += " and  p.userid = '" + searchMap.get("userId") + "'";
        }
//        if (searchMap != null && searchMap.get("pageSize") != null
//                && searchMap.get("currentPage") != null) {
//            String currentPage = (String) searchMap.get("currentPage");//0,1*5
//            String pageSize = (String) searchMap.get("pageSize");//5,
//            int indexCount = Integer.parseInt(currentPage) * Integer.parseInt(pageSize);
//            sql += " limit " + indexCount + "," + pageSize;
//        }
        System.out.println("sql"+sql);
        ResultSet rs = querySql(sql);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            if(rs.next())
            {
                do{
                    Map<String, Object> map = new HashMap<String,Object>();
                    map.put("productId",rs.getInt("nid"));
                    map.put("productCartId",rs.getString("cart_id"));
                    map.put("productName",rs.getString("name"));
                    map.put("productCount",rs.getString("count"));
                    map.put("productType",rs.getString("type"));
                    map.put("productBrand",rs.getString("brand"));
                    map.put("productPrice",rs.getString("price"));
                    map.put("productTotal",rs.getString("t.total"));
                    map.put("listTotal",rs.getString("p.total"));
                    map.put("productPicPath0",rs.getString("picpath0"));
                    map.put("productPicPath1",rs.getString("picpath1"));
                    map.put("productPicPath2",rs.getString("picpath2"));
                    map.put("productPicPath3",rs.getString("picpath3"));
                    map.put("productPicPath4",rs.getString("picpath4"));
                    map.put("productPicPath5",rs.getString("picpath5"));
                    result.add(map);
                }
                while(rs.next());
            }
            else {
                result=null;
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int AddProduct(ShoppingCartProduct shoppingCartProduct, User user) throws SQLException {
        String sql0 = "select count(t.name) from shopping_product t left join shopping_cart p on t.cart_id=p.nid where 1=1";
        if(shoppingCartProduct !=null&& shoppingCartProduct.getName()!=null)
            sql0+=" and t.name ='"+ shoppingCartProduct.getName()+"'";
        if(shoppingCartProduct!=null&&shoppingCartProduct.getCart_id()!=null)
            sql0+=" and p.nid ='"+shoppingCartProduct.getCart_id()+"'";
        ResultSet rs = querySql(sql0);
        int result=0;
        if(rs.next())
        {
            if(rs.getInt(1)>0)
            {
                String sql1 ="update shopping_product a LEFT JOIN shopping_cart b ON a.cart_id = b.nid set a.count=a.count + 1,b.count=b.count + 1,a.total =a.count*a.price,b.total=a.total"
                        + " where name =?";
                Object[] params = new Object[]{shoppingCartProduct.getName()};
                result = exceuteUpdate(sql1, params);
            }
            else {
                String sql2="INSERT INTO shopping_cart (nid,count,total,userid,operate_time) VALUES (?,1,?,?,now())  ON DUPLICATE KEY UPDATE count=count+1,total=total+?";
                Object[] params1 = new Object[]{shoppingCartProduct.getCart_id(),shoppingCartProduct.getPrice(),user.getuId(),shoppingCartProduct.getPrice()};
                exceuteUpdate(sql2, params1);
                String sql3 = "insert into shopping_product(cart_id,name,count,price,total,summary,type,brand,"
                        +"picpath0,picpath1,picpath2,picpath3,picpath4,picpath5)"
                        +"values(?,?,1,?,?,?,?,?,?,?,?,?,?,?)";
                Object[] params = new Object[]{shoppingCartProduct.getCart_id(), shoppingCartProduct.getName(), shoppingCartProduct.getPrice(), shoppingCartProduct.getPrice(),
                        shoppingCartProduct.getSummary(), shoppingCartProduct.getType(), shoppingCartProduct.getBrand(), shoppingCartProduct.getPicpath0(),
                        shoppingCartProduct.getPicpath1(), shoppingCartProduct.getPicpath2(), shoppingCartProduct.getPicpath3(), shoppingCartProduct.getPicpath4(),
                        shoppingCartProduct.getPicpath5()};
                result = exceuteUpdate(sql3, params);
            }
        }
        return result;
    }

    @Override
    public int ReduceProduct(ShoppingCartProduct shoppingCartProduct) {
        String sql = "update shopping_product a LEFT JOIN shopping_cart b ON a.cart_id = b.nid set a.count=a.count - 1,b.count=b.count - 1,a.total =a.count*a.price,b.total=b.total-a.price"
                + " where name =?";
        Object[] params = new Object[]{shoppingCartProduct.getName()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

}
