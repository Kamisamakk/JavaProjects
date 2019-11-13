package org.jbit.news.dao;

import java.util.List;
import java.util.Map;

import org.jbit.news.entity.Topic;

public interface TopicsDao {
    //获取所有主题
    public List<Topic> getAllTopics();

    //获取主题分页列表
    public List<Topic> getTopicsPage(Map<String, String> topic);

    //更新主题
    public int updateTopic(Map<String, String> topic);

    //根据名字查找主题
    public Topic findTopicByName(String name);

    //添加主题
    public int addTopic(String name);

    //通过tid删除主题
    public int deleteTopic(String tid);

    //主题的总数量
    public int findTopicCount();
}