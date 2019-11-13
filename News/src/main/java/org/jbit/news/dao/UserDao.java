package org.jbit.news.dao;

import org.jbit.news.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public User findUser(String uname, String password);
    /**
     * 获取列表
     *
     * @param searchMap
     * @return
     */
    public List<Map<String, Object>> findUserList(Map<String, Object> searchMap);

    /**
     * 获取条数
     *
     * @param searchMap
     * @return
     */
    public int findUserCount(Map<String, Object> searchMap);
    public int saveUser(User user);
    public int deleteUser(String uid);
    public User getUser(Map<String, String> searchMap);
    public int UpdateUser(User user);
    public int Lock(User user);
    public int UnLock(User user);
}
