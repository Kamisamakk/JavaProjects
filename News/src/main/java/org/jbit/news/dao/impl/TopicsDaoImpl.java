package org.jbit.news.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.TopicsDao;
import org.jbit.news.entity.Topic;

public class TopicsDaoImpl extends BaseDao implements TopicsDao {


    //获取主题分页列表
    public List<Topic> getTopicsPage(Map<String, String> topicMap) {
        List<Topic> list = new ArrayList<Topic>();
        //获取所有主题
        String sql = "select * from topic t where 1=1 ";
        String pageSize = topicMap.get("pageSize");
        String currentPage = topicMap.get("currentPage");
        sql += " limit " + Integer.parseInt(pageSize) * Integer.parseInt(currentPage) + " ," + pageSize;
        try {
            rs = querySql(sql);
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTid(rs.getInt("tid"));
                topic.setTname(rs.getString("tname"));
                list.add(topic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return list;
    }

    public List<Topic> getAllTopics() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        List<Topic> list = new ArrayList<Topic>();
        //获取所有主题
        String sql = "select * from topic";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setTid(rs.getInt("tid"));
                topic.setTname(rs.getString("tname"));
                list.add(topic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con, ps, rs);
        }
        return list;
    }

    public int deleteTopic(String tid) {
        String sql = "delete from topic where tid = ?";
        return exceuteUpdate(sql, new Integer[]{Integer.valueOf(tid)});
    }

    public int updateTopic(Map topic) {
        String sql = "update topic set tname=? "
                + "where tid = ?";
        return exceuteUpdate(sql, new Object[]{topic.get("tname"), topic.get("tid")});
    }

    public Topic findTopicByName(String name) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Topic topic = null;
        String sql = "select * from topic where tname=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                topic = new Topic();
                topic.setTid(rs.getInt("tid"));
                topic.setTname(rs.getString("tname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con, ps, rs);
        }
        return topic;
    }

    public int addTopic(String name) {
        String sql = "insert into topic(TNAME) values(?)";
        return exceuteUpdate(sql, new Object[]{name});
    }

    //主题的总数量
    public int findTopicCount() {
        String sql = "select count(tid) from topic ";
        ResultSet rs = querySql(sql);
        int count = 0;
        try {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return count;
    }
}
