package org.jbit.news.dao;

import org.jbit.news.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuDao {

    /**
     * 获取菜单列表
     *
     * @param searchMap
     * @return
     */
    public List<Map<String, Object>> findMenuLeftList(Map<String, Object> searchMap);
    public List<Map<String, Object>> findMenuList(Map<String, Object> searchMap);
    /**
     * 获取菜单条数
     *
     * @param searchMap
     * @return
     */
    public int findMenuCount(Map<String, Object> searchMap);

    /**
     * 保存菜单
     *
     * @param menu
     * @return
     */
    public int saveMenu(Menu menu);

    public int updateMenu(Menu menu);
    public Menu GetMenu(Map<String,String> searchMap);
    public int deleteMenus(String nid);
}
