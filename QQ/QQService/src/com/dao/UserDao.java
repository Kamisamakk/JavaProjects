package com.dao;

import com.bean.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDao {
    public User login(String userId, String userPassword) throws SQLException;
    //public User register(User user);
    public ArrayList<User> friends(String user_id) throws SQLException;
}
