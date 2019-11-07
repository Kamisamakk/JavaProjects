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
        return null;
    }

    @Override
    public ArrayList<User> friends(String user_id) {
        return null;
    }
}
