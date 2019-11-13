package org.jbit.news.dao;

import org.jbit.news.entity.Advertisement;

import java.util.List;
import java.util.Map;

public interface AdvertisementDao {

    /**
     * 获取广告列表
     *
     * @param searchMap
     * @return
     */
    public List<Map<String, Object>> findAdList(Map<String, Object> searchMap);

    /**
     * 获取广告条数
     *
     * @param searchMap
     * @return
     */
    public int findAdCount(Map<String, Object> searchMap);

    /**
     * 保存广告
     *
     * @param advertisement
     * @return
     */
    public int saveAd(Advertisement advertisement);

    public Advertisement getAdvertisements(Map<String, String> searchMap);

    public int updateAdvertisements(Advertisement advertisement);

    public int deleteAdvertisements(String nid);

    public int updateStates(Advertisement advertisement);
}
