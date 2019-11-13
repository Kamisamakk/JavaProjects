package org.jbit.news.dao.impl;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.MenuDao;
import org.jbit.news.entity.Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuDaoImpl extends BaseDao implements MenuDao {
    @Override
    public List<Map<String, Object>> findMenuList(Map<String, Object> searchMap) {
        String sql = "select t.nid,t.ntitle,t.nparent_id,t.nsummary,t.nsort_id,"
                + " t.noperator,DATE_FORMAT(t.noperate_time,'%Y-%m-%d %h:%i:%s') as noprDate,t.njsp"
                + " from menu t "
                + " where 1=1 ";
        if (searchMap != null && searchMap.get("menuName") != null) {
            sql += " and  t.ntitle like '%" + searchMap.get("menuName") + "%'";
        }
        if (searchMap != null && searchMap.get("menuId") != null) {
            sql += " and  t.nid = '" + searchMap.get("menuId") + "'";
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
                map.put("menuId",rs.getInt("nid"));
                map.put("menuName",rs.getString("ntitle"));
                map.put("menuParentName",rs.getString("nparent_id"));
                map.put("menuSummary",rs.getString("nsummary"));
                map.put("menuSortId",rs.getInt("nsort_id"));
                map.put("menuOperator",rs.getString("noperator"));
                map.put("menuOperateTime",rs.getString("noprDate"));
                map.put("njsp",rs.getString("njsp"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public List<Map<String, Object>> findMenuLeftList(Map<String, Object> searchMap) {
        String sql = "select t.nid,t.ntitle,t.nparent_id,t.nsummary,t.nsort_id,"
                + " t.noperator,DATE_FORMAT(t.noperate_time,'%Y-%m-%d %h:%i:%s') as noprDate,t.njsp,t.isParent"
                + " from menu t "
                + " where 1=1 ";
        if (searchMap!=null && searchMap.get("menuParentId")!=null) {
            sql += " and  t.nparent_id = '"+ searchMap.get("menuParentId") + "'";
        }
        if (searchMap!=null && searchMap.get("menuParentIdEqualsNull")!=null) {
            sql += " and  t.nparent_id is null ";
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
            sql += " order by nsort_id desc ";
        }
        ResultSet rs = querySql(sql);
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        try {
            while(rs.next()) {
                Map<String, Object> map = new HashMap<String,Object>();
                map.put("name",rs.getString("ntitle"));
                map.put("url",rs.getString("njsp"));
                map.put("id",rs.getInt("nid"));
                int isParent = rs.getInt("isParent");
                if (isParent>0) {
                    map.put("isParent",true);
                }
                else {
                    map.put("isParent",false);
                }
                map.put("menuParentId",rs.getInt("nparent_id"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int findMenuCount(Map<String, Object> searchMap) {
        String sql = "select count(nid) from menu_old t where 1=1 ";
        if (searchMap != null && searchMap.get("menuName") != null&&!"".equals(searchMap.get("menuName")) ) {
            sql += " and  t.ntitle ='" + searchMap.get("menuName") + "'";
        }
        if (searchMap != null && searchMap.get("menuNameLike") != null) {
            sql += " and  t.ntitle like '%" + searchMap.get("menuNameLike") + "%'";
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
    public int saveMenu(Menu menu) {
        String sql = "insert into menu_old(ntitle,nparent_id,"
                + "nsummary,nsort_id,"
                + "noperator,noperate_time,njsp,isParent)"
                + "values(?,?"
                + ",?,?,?,now(),?,?)";
        Object[] params = new Object[]{menu.getNtitle(), menu.getNparent_id(), menu.getNsummary(),
                menu.getNsort_id(), menu.getNoperator(), menu.getNjsp(),menu.getIsParent()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public int updateMenu(Menu menu) {
        String sql = "update menu_old set ntitle = ?,"
                + "nparent_id=?,"
                + "nsummary=?,"
                + "nsort_id=?,"
                + "njsp=?"
                + "  where nid = ? ";
        Object[] params = new Object[]{menu.getNtitle(), menu.getNparent_id(), menu.getNsummary(), menu.getNsort_id(),
                menu.getNjsp(), menu.getNid()};
        System.out.println(sql);
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public Menu GetMenu(Map<String, String> searchMap) {
        String sql = "select * from menu_old where 1=1 ";
        if (searchMap != null && searchMap.get("nid") != null) {
            sql += " and nid = " + searchMap.get("nid");
        }
        ResultSet rs = querySql(sql);
        Menu menu = new Menu();
        try {
            if (rs.next()) {
                menu.setNid(rs.getInt("nid"));
                menu.setNtitle(rs.getString("ntitle"));
                menu.setNparent_id(rs.getString("nparent_id"));
                menu.setNsummary(rs.getString("nsummary"));
                menu.setNsort_id(rs.getInt("nsort_id"));
                menu.setNjsp(rs.getString("njsp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menu;
    }

    @Override
    public int deleteMenus(String nid) {
        String sql0 = "select count(nparent_id) from menu_old where nparent_id='"
                + nid + "'";
        String sql = "DELETE FROM menu WHERE nid='"
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
//        String sql = "delete from menu where nid=? and nparent_id != 'null'";
//        int count = exceuteUpdate(sql, nid);
//        return count;
    }
}
