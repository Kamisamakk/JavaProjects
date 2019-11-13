package org.jbit.news.mapper;

import org.jbit.news.entity.Factory;
import java.util.List;
import java.util.Map;

public interface FactoryMapper {
    public List<Factory> FindFactoryAllList(Map<String, Object> searchMap);
    /**
     * 获取记录数
     * @param searchMap
     * @return
     */
    public int FindFactoryCount(Map<String, Object> searchMap);
    public int DelFactory(int factoryId);
    public int AddFactory(Factory factory);
    public int UpdateFactory(Factory factory);
}
