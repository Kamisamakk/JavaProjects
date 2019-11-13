package org.jbit.news.dao;

import org.jbit.news.entity.ShoppingCartProduct;
import org.jbit.news.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ShoppingCartDao {
    public List<Map<String, Object>> findProductList(Map<String, Object> searchMap);
    public int AddProduct(ShoppingCartProduct shoppingCartProduct, User user) throws SQLException;
    public int ReduceProduct(ShoppingCartProduct shoppingCartProduct);
}
