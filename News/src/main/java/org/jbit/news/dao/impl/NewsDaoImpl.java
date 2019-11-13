package org.jbit.news.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.NewsDao;
import org.jbit.news.entity.News;

/**
 * 新闻DAO实现类
 *
 * @author Administrator
 */
public class NewsDaoImpl extends BaseDao implements NewsDao {

    @Override
    public List<Map<String, Object>> findNewsList(Map<String, Object> searchMap) {
        String sql = "select t.ntitle,t.nauthor,p.tname,"
                + " DATE_FORMAT(t.nmodifydate,'%Y-%m-%d') as oprDate,t.nid,t.ntid  "
                + " from news t "
                + " left join topic p on p.tid = t.ntid "
                + " where 1=1 ";
        if (searchMap != null && searchMap.get("title") != null) {
            sql += " and  t.ntitle like '%" + searchMap.get("title") + "%'";
        }
        if (searchMap != null && searchMap.get("pageSize") != null
                && searchMap.get("currentPage") != null) {
            String currentPage = (String) searchMap.get("currentPage");//0,1*5
            String pageSize = (String) searchMap.get("pageSize");//5,
            int indexCount = Integer.parseInt(currentPage) * Integer.parseInt(pageSize);
            sql += " limit " + indexCount + "," + pageSize;
        }
        ResultSet rs = querySql(sql);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("title", rs.getString("ntitle"));
                map.put("author", rs.getString("nauthor"));
                map.put("tname", rs.getString("tname"));
                map.put("oprDate", rs.getString("oprDate"));
                map.put("nid", rs.getInt("nid"));
                map.put("ntid", rs.getInt("ntid"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int findNewsCount(Map<String, Object> searchMap) {
        String sql = "select count(nid) from news t where 1=1 ";
        if (searchMap != null && searchMap.get("title") != null) {
            sql += " and  t.ntitle like '%" + searchMap.get("title") + "%'";
        }
        ResultSet rs = querySql(sql);
        int count = 0;
        try {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int saveNews(News news) {
        String sql = "insert into news(ntid,ntitle,nauthor,"
                + "nsummary,ncontent,npicpath,"
                + "ncreatedate,nmodifydate)"
                + "values(?,?,?"
                + ",?,?,?,now(),now())";
        Object[] params = new Object[]{news.getNtid(), news.getNtitle(),
                news.getNauthor(), news.getNsummary(), news.getNcontent(),
                news.getNpicpath()};
        int result = exceuteUpdate(sql, params);
        return result;
    }

}
