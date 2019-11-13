package org.jbit.news.dao;


import org.jbit.news.entity.Admin;

import java.util.Map;

public interface AdminDao {
    //查找是否登录成功
    public Admin findAdmin(String uname, String password);
    public int findUserCount(Map<String, Object> searchMap);
}