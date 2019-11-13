package org.jbit.news.entity;

import lombok.Data;

@Data
public class Factory {
    private int factory_id;
    private String factory_name;
    private String factory_web;
    private String contacts;
    private String contact_number;
    private String factory_address;
    private int sort_id;
    private String summary;
    private String operator;
    private String operate_time;
}
