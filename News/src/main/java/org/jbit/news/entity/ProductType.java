package org.jbit.news.entity;

public class ProductType {
    private int nid;
    private String type;
    private int typeparent_id;
    private String summary;
    private int sort_id;
    private String operator;
    private String operate_time;

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getType() {
        return type;
    }

    public void setType(String brand) {
        this.type = brand;
    }

    public int getTypeparent_id() {
        return typeparent_id;
    }

    public void setTypeparent_id(int typeparent_id) {
        this.typeparent_id = typeparent_id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(String operate_time) {
        this.operate_time = operate_time;
    }
}
