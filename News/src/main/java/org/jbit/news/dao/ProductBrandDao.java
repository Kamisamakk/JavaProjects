package org.jbit.news.dao;

import org.jbit.news.entity.ProductBrand;

import java.util.List;
import java.util.Map;

public interface ProductBrandDao {
    /**
     * 获取商品品牌列表
     *
     * @param searchMap
     * @return
     */
    public List<Map<String, Object>> findProductBrandList(Map<String, Object> searchMap);
    public int findProductBrandCount(Map<String, Object> searchMap);
    public int AddProductBrand(ProductBrand productBrand);
    public int DeleteProductBrand(String nid);
    public ProductBrand GetProductBrand(String nid);
    public int UpdateProductBrand(ProductBrand productBrand);
}
