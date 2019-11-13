package org.jbit.news.dao.impl;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.UserDao;
import org.jbit.news.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public User findUser(String uid, String password) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        User user = null;
        //获取所有主题
        String sql = "select * from user where uid=? and upassword=? and ustates =1";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, uid);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setuId(rs.getInt("uid"));
               // user.setuName(uname);
                user.setuPassword(password);
                user.setHeadPicPath(rs.getString("headpicpath"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con, ps, rs);
        }
        return user;
    }

//    public List<Map<String, Object>> findUserList1(Map<String, Object> searchMap) {
//        String sql = "select t.tid,t.uid,t.unumber,t.ustates,t.qq,t.uname,"
//                + "DATE_FORMAT(t.operate_time,'%m-%d %h:%i:%s') as oprDate"
//                + " from user t "
//                + " where 1=1 ";
//        if (searchMap != null && searchMap.get("uid") != null) {
//            sql += " and  t.uid like '%" + searchMap.get("uid") + "%'";
//        }
//        if (searchMap != null && searchMap.get("pageSize") != null
//                && searchMap.get("currentPage") != null) {
//            String currentPage = (String) searchMap.get("currentPage");//0,1*5
//            String pageSize = (String) searchMap.get("pageSize");//5,
//            int indexCount = Integer.parseInt(currentPage) * Integer.parseInt(pageSize);
//            sql += " limit " + indexCount + "," + pageSize;
//        }
//        ResultSet rs = querySql(sql);
//        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//        try {
//            while (rs.next()) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("tid", rs.getInt("tid"));
//                map.put("userId", rs.getInt("uid"));
//                map.put("userName", rs.getString("uname"));
//                //System.out.println(rs.getString("nid"));
//                map.put("userNumber", rs.getString("unumber"));
//                if(rs.getInt("ustates")==1)
//                    map.put("userStates", "正常");
//                else  map.put("userStates", "冻结");
//                map.put("qq", rs.getString("qq"));
//                map.put("oprDate", rs.getString("oprDate"));
//                result.add(map);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    @Override
    public List<Map<String, Object>> findUserList(Map<String, Object> searchMap) {
        String sql = "select t.tid,t.uid,t.unumber,t.ustates,t.qq,"
                + "DATE_FORMAT(t.operate_time,'%m-%d %h:%i:%s') as oprDate"
                + " from user t "
                + " where 1=1 ";
        if (searchMap != null && searchMap.get("uid") != null) {
            sql += " and  t.uid like %" + searchMap.get("uid") + "%";
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
                map.put("tid", rs.getInt("tid"));
                map.put("uid", rs.getInt("uid"));
                //System.out.println(rs.getString("nid"));
                map.put("unumber", rs.getString("unumber"));
                map.put("ustates", rs.getInt("ustates"));
                // map.put("nsummary",rs.getString("nsummary"));
                //map.put("npicpath",rs.getString("npicpath"));
                map.put("qq", rs.getString("qq"));
                map.put("oprDate", rs.getString("oprDate"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int findUserCount(Map<String, Object> searchMap) {
        String sql = "select count(uid) from user t where 1=1 ";
        if (searchMap != null && searchMap.get("uname") != null) {
            sql += " and  t.uname like '%" + searchMap.get("uname") + "%'";
        }if (searchMap != null && searchMap.get("uid") != null) {
            sql += " and  t.uid ='"+searchMap.get("uid")+"'";
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
    public int saveUser(User user) {
        String sql = "insert into user(uid,upassword,"
                + "uname,unumber,"
                + "usex,qq,hobby,ustates,operator,operate_time,headpicpath)"
                + "values(?,?"
                + ",?,?,?,?,?,1,?,now(),?)";
        Object[] params = new Object[]{user.getuId(), user.getuPassword(), user.getuName(),
                user.getuNumber(), user.getuSex(), user.getQQ(),user.getHobby(),user.getOperator(),
                user.getHeadPicPath()
        };
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public int deleteUser(String uid) {
        String sql = "delete from user where uid=" + uid;
        int result = exceuteUpdate(sql);
        if(result>0)
        {
            result=1;
        }
        return result;
    }

    @Override
    public User getUser(Map<String, String> searchMap) {
        String sql = "select * from user where 1=1 ";
        if (searchMap != null && searchMap.get("userId") != null) {
            sql += " and uid = " + searchMap.get("userId");
        }
        ResultSet rs = querySql(sql);
        User user = new User();
        try {
            if (rs.next()) {
                user.setuId(rs.getInt("uid"));
                user.setuName(rs.getString("uname"));
                user.setuNumber(rs.getString("unumber"));
                user.setQQ(rs.getString("qq"));
                user.setuSex(rs.getInt("usex"));
                user.setHobby(rs.getString("hobby"));
                user.setuPassword(rs.getString("upassword"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int UpdateUser(User user) {
        String sql = "update user set uid = ?,"
                + "uname=?,"
                + "unumber=?,"
                + "usex=?,"
                + "qq=?,"
                + "upassword=?,"
                + "hobby=?,"
                + "operate_time=now()"
                + "  where uid = ? ";
        Object[] params = new Object[]{user.getuId(), user.getuName(), user.getuNumber(), user.getuSex(),
                user.getQQ(), user.getuPassword(), user.getHobby()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

    @Override
    public int Lock(User user) {
        int result = ResultLock(user);
        return result;
    }

    @Override
    public int UnLock(User user) {
        int result = ResultLock(user);
        return result;
    }

    private int ResultLock(User user)
    {
        String sql = "update user set ustates = ?"
                + "  where uid = ? ";
        Object[] params = new Object[]{user.getUstates(), user.getuId()};
        int result = exceuteUpdate(sql, params);
        return result;
    }
}
