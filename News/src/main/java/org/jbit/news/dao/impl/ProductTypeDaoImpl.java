package org.jbit.news.dao.impl;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.ProductTypeDao;
import org.jbit.news.entity.ProductType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductTypeDaoImpl extends BaseDao implements ProductTypeDao {
    @Override
    public List<Map<String, Object>> findProductTypeList(Map<String, Object> searchMap) {
        String sql = "select t.nid,t.type,t.typeparent_id,t.summary,t.sort_id,"
                + "t.operator,DATE_FORMAT(t.operate_time,'%Y-%m-%d %h:%i:%s') as oprDate"
                + " from product_type t"
                + " where 1=1 ";
        //商品类别模糊查询
        if (searchMap != null && searchMap.get("productType") != null) {
            sql += " and  t.type like '%" + searchMap.get("productType") + "%'";
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
                map.put("productTypeId",rs.getInt("nid"));
                map.put("productTypeOprTime",rs.getString("oprDate"));
                map.put("productType",rs.getString("type"));
                map.put("productTypeParentId",rs.getString("typeparent_id"));
                map.put("productTypeSummary",rs.getString("summary"));
                map.put("productTypeSortId",rs.getInt("sort_id"));
                map.put("productTypeOperator",rs.getString("operator"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int findProductTypeCount(Map<String, Object> searchMap) {
        String sql = "select count(nid) from product_type t where 1=1 ";
        if (searchMap != null && searchMap.get("productTypeId") != null) {
            sql += " and  t.nid='" + searchMap.get("productTypeId") + "'";
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
    public int AddProductType(ProductType productType) {
        String sql = "insert into product_type(type,typeparent_id,"
                + "sort_id,summary,"
                + "operator,operate_time)"
                + "values(?,?"
                + ",?,?,?,now())";
        Object[] params = new Object[]{productType.getType(),productType.getTypeparent_id(),productType.getSort_id(),
                productType.getSummary(),productType.getOperator()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public int DeleteProductType(String nid) {
        String sql0 = "select count(typeparent_id) from product_type where typeparent_id='"
                + nid + "'";
        String sql = "DELETE FROM product_type WHERE nid='"
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
    public ProductType GetProductType(String nid) {
        String sql = "select t.nid,t.type,t.typeparent_id,t.summary,t.sort_id,"
                + "t.operator,DATE_FORMAT(t.operate_time,'%Y-%m-%d %h:%i:%s') as oprDate"
                + " from product_type t"
                + " where 1=1 ";
        if (nid != null) {
            sql += " and nid = '"+nid+"'";
        }
        ResultSet rs = querySql(sql);
        ProductType productType = new ProductType();
        try {
            if (rs.next()) {
                productType.setNid(rs.getInt("nid"));
                productType.setType(rs.getString("type"));
                productType.setTypeparent_id(rs.getInt("typeparent_id"));
                productType.setSort_id(rs.getInt("sort_id"));
                productType.setSummary(rs.getString("summary"));
                productType.setOperator(rs.getString("operator"));
                productType.setOperate_time(rs.getString("oprDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productType;
    }

    @Override
    public int UpdateProductType(ProductType productType) {
        String sql = "update product_type set type = ?,"
                + "typeparent_id=?,"
                + "sort_id=?,"
                + "summary=?,"
                + "operator=?,operate_time=now()"
                + " where nid =?";
        Object[] params = new Object[]{productType.getType(),productType.getTypeparent_id(),
                productType.getSort_id(),productType.getSummary(),productType.getOperator(),productType.getNid()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public List<Map<String, Object>> findTypeLeftList(Map<String, Object> searchMap) {
        String sql = "select p.nid,p.type,p.typeparent_id"
                + " from product_type p "
                + " where 1=1 ";
        int isParent=0;
        if (searchMap!=null && searchMap.get("typeParentId")!=null) {
            sql += " and  p.typeparent_id = '"+ searchMap.get("typeParentId") + "'";
        }
        if (searchMap!=null && searchMap.get("typeParentIdEqualsNull")!=null) {
            sql += " and  p.typeparent_id is null ";
        }
        //System.out.println(sql);
        if (searchMap!=null && searchMap.get("pageSize")!=null
                && searchMap.get("currentPage")!=null ) {
            String currentPage = (String)searchMap.get("currentPage");//0,1*5
            String pageSize = (String)searchMap.get("pageSize");//5,
            int indexCount = Integer.parseInt(currentPage)*Integer.parseInt(pageSize);
            sql += " limit "+ indexCount + ","+pageSize;
        }
        if (searchMap!=null && searchMap.get("orderBySortNoDesc")!=null) {
            sql += " order by sort_no desc ";
        }
        ResultSet rs = querySql(sql);
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        try {
            while(rs.next()) {
                String path="productServlet?action=productlist&type_id="+rs.getInt("nid");
                Map<String, Object> map = new HashMap<String,Object>();
                map.put("name",rs.getString("type"));
                map.put("id",rs.getInt("nid"));
                if(rs.getString("typeparent_id")==null)
                {isParent = 1;}
                if (isParent>0) {
                    map.put("isParent",true);
                }
                else {
                    map.put("isParent",false);
                    //map.put("url",path);
                }
                map.put("typeParentId",rs.getInt("typeparent_id"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
