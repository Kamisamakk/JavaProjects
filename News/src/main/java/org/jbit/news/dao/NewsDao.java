package org.jbit.news.dao;

import java.util.List;
import java.util.Map;

import org.jbit.news.entity.News;

/**
 * 新闻DAO
 *
 * @author Administrator
 */
public interface NewsDao {

    /**
     * 获取新闻列表
     *
     * @param searchMap
     * @return
     */
    public List<Map<String, Object>> findNewsList(Map<String, Object> searchMap);

    /**
     * 获取新闻条数
     *
     * @param searchMap
     * @return
     */
    public int findNewsCount(Map<String, Object> searchMap);

    /**
     * 保存新闻
     *
     * @param news
     * @return
     */
    public int saveNews(News news);
}
