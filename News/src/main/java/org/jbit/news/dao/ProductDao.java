package org.jbit.news.dao;


import org.jbit.news.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao {
    /**
     * 获取商品列表
     *
     * @param searchMap
     * @return
     */
    public List<Map<String, Object>> findProductList(Map<String, Object> searchMap);
    public int findProductCount(Map<String, Object> searchMap);
    public int AddProduct(Product product);
    public int DeleteProduct(String nid);
    public Product GetProduct(String nid);
    public int UpdateProduct(Product product);
}
