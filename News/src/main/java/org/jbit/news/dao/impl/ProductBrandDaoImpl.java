package org.jbit.news.dao.impl;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.ProductBrandDao;
import org.jbit.news.entity.ProductBrand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductBrandDaoImpl extends BaseDao implements ProductBrandDao {
    @Override
    public List<Map<String, Object>> findProductBrandList(Map<String, Object> searchMap) {
        String sql = "select t.nid,t.brand,t.brandparent_id,t.summary,t.sort_id,"
                + "t.operator,DATE_FORMAT(t.operate_time,'%Y-%m-%d %h:%i:%s') as oprDate"
                + " from product_brand t"
                + " where 1=1 ";
        //商品品牌模糊查询
        if (searchMap != null && searchMap.get("productBrand") != null) {
            sql += " and  t.brand like '%" + searchMap.get("productBrand") + "%'";
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
                map.put("productBrandId",rs.getInt("nid"));
                map.put("productBrandOprTime",rs.getString("oprDate"));
                map.put("productBrand",rs.getString("brand"));
                map.put("productBrandParentId",rs.getString("brandparent_id"));
                map.put("productBrandSummary",rs.getString("summary"));
                map.put("productBrandSortId",rs.getInt("sort_id"));
                map.put("productBrandOperator",rs.getString("operator"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int findProductBrandCount(Map<String, Object> searchMap) {
        String sql = "select count(nid) from product_brand t where 1=1 ";
        if (searchMap != null && searchMap.get("productId") != null) {
            sql += " and  t.nid='" + searchMap.get("productId") + "'";
        }
        ResultSet rs = querySql(sql);
        int count = 0;
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
    public int AddProductBrand(ProductBrand productBrand) {
        String sql = "insert into product_brand(brand,brandparent_id,"
                + "sort_id,summary,"
                + "operator,operate_time)"
                + "values(?,?"
                + ",?,?,?,now())";
        Object[] params = new Object[]{productBrand.getBrand(),productBrand.getBrandparent_id(),productBrand.getSort_id(),
        productBrand.getSummary(),productBrand.getOperator()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public int DeleteProductBrand(String nid) {
        String sql0 = "select count(brandparent_id) from product_brand where brandparent_id='"
                + nid + "'";
        String sql = "DELETE FROM product_brand WHERE nid='"
                + nid + "' ";
        ResultSet rs = querySql(sql0);
        int count = 0;
        int result = 0;
        try {
            if (rs.next()) {
                count = rs.getInt(1);
            }
            //删除
            if (count == 0) {
                conn = getConnection();
                pstmt = conn.prepareStatement(sql);
                result = pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ProductBrand GetProductBrand(String nid) {
        String sql = "select t.nid,t.brand,t.brandparent_id,t.summary,t.sort_id,"
                + "t.operator,DATE_FORMAT(t.operate_time,'%Y-%m-%d %h:%i:%s') as oprDate"
                + " from product_brand t"
                + " where 1=1 ";
        if (nid != null) {
            sql += " and nid = '"+nid+"'";
        }
        ResultSet rs = querySql(sql);
        ProductBrand productBrand = new ProductBrand();
        try {
            if (rs.next()) {
                productBrand.setNid(rs.getInt("nid"));
                productBrand.setBrand(rs.getString("brand"));
                productBrand.setBrandparent_id(rs.getInt("brandparent_id"));
                productBrand.setSort_id(rs.getInt("sort_id"));
                productBrand.setSummary(rs.getString("summary"));
                productBrand.setOperator(rs.getString("operator"));
                productBrand.setOperate_time(rs.getString("oprDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productBrand;
    }

    @Override
    public int UpdateProductBrand(ProductBrand productBrand) {
        String sql = "update product_brand set brand = ?,"
                + "brandparent_id=?,"
                + "sort_id=?,"
                + "summary=?,"
                + "operator=?,operate_time=now()"
                + " where nid =?";
        Object[] params = new Object[]{productBrand.getBrand(),productBrand.getBrandparent_id(),
        productBrand.getSort_id(),productBrand.getSummary(),productBrand.getOperator(),productBrand.getNid()};
        int result = exceuteUpdate(sql, params);
        return result;
    }
}
