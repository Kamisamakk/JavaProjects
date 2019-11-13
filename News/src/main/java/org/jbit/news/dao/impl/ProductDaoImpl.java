package org.jbit.news.dao.impl;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.ProductDao;
import org.jbit.news.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDaoImpl extends BaseDao implements ProductDao {
    @Override
    public List<Map<String, Object>> findProductList(Map<String, Object> searchMap) {
        String sql = "select t.nid,t.name,p.type,c.brand,t.type_id,t.brand_id,t.price,t.summary,t.sort_id,"
                +"t.picpath0,t.picpath1,t.picpath2,t.picpath3,t.picpath4,t.picpath5,"
                + "t.operator,DATE_FORMAT(t.operate_time,'%Y-%m-%d %h:%i:%s') as oprDate"
                + " from product t left join product_type p "
                + "on t.type_id=p.nid left join product_brand c "
                +"on t.brand_id=c.nid"
                + " where 1=1 ";
        //商品名称模糊查询
        if (searchMap != null && searchMap.get("productName") != null) {
            sql += " and  t.name like '%" + searchMap.get("productName") + "%'";
        }
        //类别精确查询
        if(searchMap != null && searchMap.get("productTypeId") != null){
            sql += " and  t.type_id ='" + searchMap.get("productTypeId") + "'";
        }
        //品牌精确查询
        if(searchMap != null && searchMap.get("productBrandId") != null){
            sql += " and  t.brand_id =" + searchMap.get("productBrandId");
        }
        if (searchMap != null && searchMap.get("pageSize") != null
                && searchMap.get("currentPage") != null) {
            String currentPage = (String) searchMap.get("currentPage");//0,1*5
            String pageSize = (String) searchMap.get("pageSize");//5,
            int indexCount = Integer.parseInt(currentPage) * Integer.parseInt(pageSize);
            sql += " limit " + indexCount + "," + pageSize;
        }
        System.out.println("sql"+sql);
        ResultSet rs = querySql(sql);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            while(rs.next()) {
                Map<String, Object> map = new HashMap<String,Object>();
                map.put("productId",rs.getInt("nid"));
                map.put("productName",rs.getString("name"));
                map.put("productType",rs.getString("type"));
                map.put("productBrand",rs.getString("brand"));
                map.put("productTypeId",rs.getString("type_id"));
                map.put("productBrandId",rs.getString("brand_id"));
                map.put("productPrice",rs.getString("price"));
                map.put("productSummary",rs.getString("summary"));
                map.put("productSortId",rs.getInt("sort_id"));
                map.put("productOperator",rs.getString("operator"));
                map.put("productOperateTime",rs.getString("oprDate"));
                map.put("productPicPath0",rs.getString("picpath0"));
                map.put("productPicPath1",rs.getString("picpath1"));
                map.put("productPicPath2",rs.getString("picpath2"));
                map.put("productPicPath3",rs.getString("picpath3"));
                map.put("productPicPath4",rs.getString("picpath4"));
                map.put("productPicPath5",rs.getString("picpath5"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public int findProductCount(Map<String, Object> searchMap) {
        String sql = "select count(nid) from product where 1=1";
        if (searchMap != null && searchMap.get("nid") != null) {
            sql += " and nid='" + searchMap.get("nid") + "'";
        }
        if(searchMap!=null&&searchMap.get("productName")!=null)
        {
            sql += " and name='" + searchMap.get("productName") + "'";
        }
        if(searchMap!=null&&searchMap.get("productTypeId")!=null)
        {
            sql += " and type_id=" + searchMap.get("productTypeId") ;
        }
        ResultSet rs = querySql(sql);
        int count=0;
        try {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int AddProduct(Product product) {
        String[] path=product.getNpicpath();
        String sql = "insert into product(name,type_id,brand_id,"
                + "price,sort_id,summary,picpath0,picpath1,picpath2,"
                + "picpath3,picpath4,picpath5,operator,operate_time)"
                + "values(?,?"
                + ",?,?,?,?,?,?,?,?,?,?,?,now())";
        Object[] params = new Object[]{product.getName(),product.getType_id(),product.getBrand_id(),
        product.getPrice(),product.getSort_id(),product.getSummary(),path[0],
                path[1],path[2],path[3],path[4],path[5],
                product.getOperator()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public int DeleteProduct(String nid) {
        String sql = "delete from product where nid=" + nid;
        int result = exceuteUpdate(sql);
        if(result>0)
        {
            result=1;
        }
        return result;
    }

    @Override
    public Product GetProduct(String name) {
        String sql = "select t.name,p.type,c.brand,t.price,t.summary,t.sort_id,"
                +"t.picpath0,t.picpath1,t.picpath2,t.picpath3,t.picpath4,t.picpath5,t.operator"
                + " from product t left join product_type p "
                + "on t.type_id=p.nid left join product_brand c "
                +"on t.brand_id=c.nid"
                + " where 1=1 ";
        if (name != null) {
            sql += " and name = '" + name+"'";
        }
        ResultSet rs = querySql(sql);
        Product product = new Product();
        String[]picpath=new String[6];
        try {
            if (rs.next()) {
                product.setName(rs.getString("name"));
                product.setBrand(rs.getString("brand"));
                product.setType(rs.getString("type"));
                product.setPrice(rs.getInt("price"));
                product.setSort_id(rs.getInt("sort_id"));
                product.setSummary(rs.getString("summary"));
                for(int i=0;i<picpath.length;i++)
                {
                    picpath[i]=rs.getString("picpath"+i);
                }
                product.setNpicpath(picpath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
        //return null;
    }

    @Override
    public int UpdateProduct(Product product) {
        String []path=product.getNpicpath();
        String sql = "update product set name = ?,"
                + "type_id=?,"
                + "brand_id=?,"
                + "price=?,"
                + "sort_id=?,"
                + "summary=?,"
                + "picpath0=?,"
                + "picpath1=?,picpath2=?,picpath3=?,picpath4=?,picpath5=?,"
                + "operator=?,operate_time=now()"
                + " where nid =?";
        Object[] params = new Object[]{product.getName(), product.getType_id(), product.getBrand_id(), product.getPrice(),
                product.getSort_id(), product.getSummary(), path[0],path[1],path[2],
                path[3],path[4],path[5], product.getOperator(),product.getNid()};
        int result = exceuteUpdate(sql, params);
        return result;
    }
}
