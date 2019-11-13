package org.jbit.news.dao;

import org.jbit.news.entity.ProductType;

import java.util.List;
import java.util.Map;

public interface ProductTypeDao {
    /**
     * 获取商品类别列表
     *
     * @param searchMap
     * @return
     */
    public List<Map<String, Object>> findProductTypeList(Map<String, Object> searchMap);
    public int findProductTypeCount(Map<String, Object> searchMap);
    public int AddProductType(ProductType productType);
    public int DeleteProductType(String nid);
    public ProductType GetProductType(String nid);
    public int UpdateProductType(ProductType productType);
    public List<Map<String, Object>> findTypeLeftList(Map<String, Object> searchMap);
}
