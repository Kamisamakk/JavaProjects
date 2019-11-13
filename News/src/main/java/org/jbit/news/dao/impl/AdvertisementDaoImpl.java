package org.jbit.news.dao.impl;

import org.jbit.news.dao.AdvertisementDao;
import org.jbit.news.dao.BaseDao;
import org.jbit.news.entity.Advertisement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvertisementDaoImpl extends BaseDao implements AdvertisementDao {
    @Override
    public List<Map<String, Object>> findAdList(Map<String, Object> searchMap) {
        String sql = "select t.nid,t.ntitle,t.nstates,"
                + " DATE_FORMAT(t.nbeginDate,'%m-%d %h:%i:%s') as beginDate,DATE_FORMAT(t.nendDate,'%m-%d %h:%i:%s') as endDate,"
                + "DATE_FORMAT(t.noperate_time,'%m-%d %h:%i:%s') as oprDate,t.noperator"
                + " from advertisement t "
                + " where 1=1 " + " and t.check_states= 0";
        if (searchMap != null && searchMap.get("title") != null) {
            sql += " and  t.ntitle like '%" + searchMap.get("title") + "%'";
        }
        if (searchMap != null && searchMap.get("pageSize") != null
                && searchMap.get("currentPage") != null) {
            String currentPage = (String) searchMap.get("currentPage");//0,1*5
            String pageSize = (String) searchMap.get("pageSize");//5,
            int indexCount = Integer.parseInt(currentPage) * Integer.parseInt(pageSize);
            sql += " limit " + indexCount + "," + pageSize;
        }
        ResultSet rs = querySql(sql);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("nid", rs.getString("nid"));
                //System.out.println(rs.getString("nid"));
                map.put("title", rs.getString("ntitle"));
                map.put("nstates", rs.getString("nstates"));
                // map.put("nsummary",rs.getString("nsummary"));
                //map.put("npicpath",rs.getString("npicpath"));
                map.put("beginDate", rs.getString("beginDate"));
                map.put("endDate", rs.getString("endDate"));
                map.put("oprDate", rs.getString("oprDate"));
                map.put("operator", rs.getString("noperator"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int findAdCount(Map<String, Object> searchMap) {
        String sql = "select count(nid) from advertisement t where 1=1 ";
        if (searchMap != null && searchMap.get("title") != null) {
            sql += " and  t.ntitle like '%" + searchMap.get("title") + "%'";
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
    public int saveAd(Advertisement advertisement) {
        String sql = "insert into advertisement(ntitle,nstates,"
                + "nsummary,npicpath,"
                + "nbegindate,nenddate,noperator,noperate_time,check_states)"
                + "values(?,?"
                + ",?,?,?,?,?,now(),?)";
        Object[] params = new Object[]{advertisement.getNtitle(), advertisement.getNstates(), advertisement.getNsummary(),
                advertisement.getNpicpath(), advertisement.getNbegindate(), advertisement.getNenddate(), advertisement.getNoperator(),
                advertisement.getCheck_states()};
        int result = exceuteUpdate(sql, params);

        return result;
    }

    @Override
    public Advertisement getAdvertisements(Map<String, String> searchMap) {
        String sql = "select * from advertisement where 1=1";
        if (searchMap != null && searchMap.get("nid") != null) {
            sql += " and nid = " + searchMap.get("nid");
        }
        if (searchMap != null && searchMap.get("check_states") != null) {
            sql += " and check_states = '" + searchMap.get("check_states") + "'";
        }
        ResultSet rs = querySql(sql);
        Advertisement advertisement = new Advertisement();
        try {
            if (rs.next()) {
                advertisement.setNid(rs.getInt("nid"));
                advertisement.setNtitle(rs.getString("ntitle"));
                advertisement.setNstates(rs.getString("nstates"));
                advertisement.setNsummary(rs.getString("nsummary"));
                advertisement.setNpicpath(rs.getString("npicpath"));
                advertisement.setNbegindate(rs.getString("nbegindate"));
                advertisement.setNenddate(rs.getString("nenddate"));
                advertisement.setNoperator(rs.getString("noperator"));
                advertisement.setCheck_states(rs.getInt("check_states"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return advertisement;
    }

    @Override
    public int updateAdvertisements(Advertisement advertisement) {
        String sql = "update advertisement set ntitle = ?,"
                + "nstates=?,"
                + "nsummary=?,"
                + "npicpath=?,"
                + "nbegindate=?,"
                + "nenddate=?,"
                + "noperator=?"
                + "  where nid = ? ";
        Object[] params = new Object[]{advertisement.getNtitle(), advertisement.getNstates(), advertisement.getNsummary(), advertisement.getNpicpath(),
                advertisement.getNbegindate(), advertisement.getNenddate(), advertisement.getNoperator(), advertisement.getNid()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public int updateStates(Advertisement advertisement) {
        String sql = "update advertisement set "
                + "check_states=?"
                + "  where nid = ? ";
        Object[] params = new Object[]{advertisement.getCheck_states(), advertisement.getNid()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public int deleteAdvertisements(String nid) {
        String sql = "delete from advertisement where nid=" + nid;
        int count = exceuteUpdate(sql);
        return count;
    }
}
