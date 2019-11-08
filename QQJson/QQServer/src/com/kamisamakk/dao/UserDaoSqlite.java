package com.kamisamakk.dao;

import com.kamisamakk.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoSqlite implements UserDao {

    @Override
    public User login(String userId, String userPassword) throws SQLException {
        Connection connection=DBConnection.getConnection();
        String sql="select * from tbl_user where user_id=? and password=?";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1,userId);
        statement.setString(2,userPassword);
        ResultSet resultSet = statement.executeQuery();
        User user = null;
        if(resultSet.next())
        {
            user = new User();
            user.setUserId(resultSet.getString("user_id"));
            user.setUserPassword(resultSet.getString("password"));
            return user;
        }
        statement.close();
        resultSet.close();
        return user;
    }

    @Override
    public User register(User user) {
        Connection connection=DBConnection.getConnection();
        String sql="insert into tbl_user values(null,?,?,?)";
        PreparedStatement stmt=null;
        PreparedStatement stmt2=null;
        ResultSet set=null;
        try {
            stmt=connection.prepareStatement(sql);
            stmt.setString(1, user.getUserPassword());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getSex());
            int ret=stmt.executeUpdate();
            if(ret>0){
                sql="select max(user_id) max_id from tbl_user";
                stmt2=connection.prepareStatement(sql);
                set=stmt2.executeQuery();//10039
                if(set.next()){
                    String userId=set.getString("max_id");
                    User newUser=new User(userId,user.getUserPassword(),user.getUserName(), user.getSex());
                    return newUser;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(stmt!=null){
                    stmt.close();
                }
                if(stmt2!=null){
                    stmt2.close();
                }
                if(set!=null){
                    set.close();
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }

        }
        return null;
    }

    @Override
    public ArrayList<User> friends(String userId) throws SQLException {
        ArrayList<User>friendsList=new ArrayList<>();
        Connection connection=DBConnection.getConnection();
        String sql = "select * from v_friends where user_id=?";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1,userId);
        ResultSet resultSet=statement.executeQuery();
        while (true)
        {
            User user=createUserFriends(resultSet);
            if(user==null)
            {
                break;
            }
            else {
                friendsList.add(user);
            }
        }
        statement.close();;
        resultSet.close();
        return friendsList;
    }

    private User createUserFriends(ResultSet resultSet) throws SQLException {
        User user=null;
        if(resultSet.next())
        {
            user=new User();
            user.setUserId(resultSet.getString("friend_id"));
        }
        return user;
    }
}
