package org.jbit.news.mapper;

import org.jbit.news.entity.Organization;

import java.util.List;
import java.util.Map;

public interface OrganizationMapper {
    public List<Organization> FindOrganizationAllList(Map<String, Object> searchMap);
    /**
     * 获取记录数
     * @param searchMap
     * @return
     */
    public int FindOrganizationCount(Map<String, Object> searchMap);

}
