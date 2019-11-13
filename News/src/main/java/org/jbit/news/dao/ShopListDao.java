package org.jbit.news.dao;

import org.jbit.news.entity.ShopList;
import org.jbit.news.entity.ShopProduct;
import org.jbit.news.entity.ShoppingCartProduct;

import java.util.List;
import java.util.Map;

public interface ShopListDao {
    public List<Map<String, Object>> findAllList(Map<String, Object> searchMap);
    public int findListCount(Map<String, Object> searchMap);
    public int AddProductList(ShopProduct shopProduct);
    public int AddMainList(ShopList shopList);
    public int DeleteList(String nid);
    public Map<String,Object> GetList(String nid);
    public int UpdateList(ShopList shopList);
}
