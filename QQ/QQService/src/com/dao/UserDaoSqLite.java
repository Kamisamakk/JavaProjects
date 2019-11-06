package com.dao;

import com.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class UserDaoSqLite implements UserDao {
    @Override
    public User login(String userId, String userPassword) throws SQLException {
        Connection connection=DBConnection.getConnection();
        String sql="select * from tbl_user where user_id=? and password=?";
        PreparedStatement statement;
        statement=connection.prepareStatement(sql);
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
    public ArrayList<User> friends(String userId) throws SQLException {
        ArrayList<User> friendsList=new ArrayList<User>();
        Connection connection=DBConnection.getConnection();
        String sql="select * from v_friends where user_id=?";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1,userId);
        ResultSet resultSet=statement.executeQuery();
        while (true){
            //读取第一条数据
            User user=createFriendsList(resultSet);
            if(user==null)
            {
                //为空跳出循环
                break;
            }else {
                friendsList.add(user);
            }
        }
        statement.close();
        resultSet.close();
        return friendsList;
    }

    private User createFriendsList(ResultSet resultSet) throws SQLException {
        User user=null;
        if(resultSet.next())
        {
            user=new User();
            user.setUserId(resultSet.getString("friend_id"));
        }
        return user;
    }
}
