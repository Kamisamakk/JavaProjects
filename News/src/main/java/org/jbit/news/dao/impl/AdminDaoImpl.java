package org.jbit.news.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.jbit.news.dao.AdminDao;
import org.jbit.news.dao.BaseDao;
import org.jbit.news.entity.Admin;

public class AdminDaoImpl extends BaseDao implements AdminDao {

    public Admin findAdmin(String uname, String password) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        Admin admin = null;
        //获取所有主题
        String sql = "select * from NEWS_USERS where uname=? and upwd=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, uname);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                admin = new Admin();
                admin.setUid(rs.getInt("uid"));
                admin.setUname(uname);
                admin.setUpwd(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con, ps, rs);
        }
        return admin;
    }
    @Override
    public int findUserCount(Map<String, Object> searchMap) {
        String sql = "select count(tid) from news_users t where 1=1 ";
        if (searchMap != null && searchMap.get("uname") != null) {
            sql += " and  t.uname like '%" + searchMap.get("uname") + "%'";
        }if (searchMap != null && searchMap.get("uid") != null) {
            sql += " and  t.uid ="+searchMap.get("uid");
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

}
