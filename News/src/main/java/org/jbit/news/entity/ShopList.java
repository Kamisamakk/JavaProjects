package org.jbit.news.entity;

public class ShopList {
    private int nid;
    private String no;
    private int states;//0 未审核 1审核通过 2审核未通过;
    private int money;
    private String userid;
    private String createtime;//
    private String mdftime;
    private String removetime;
    private String comment;
    private String operator;
    private String checktime;
    private String checkcomment;

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMdftime() {
        return mdftime;
    }

    public void setMdftime(String mdftime) {
        this.mdftime = mdftime;
    }

    public String getRemovetime() {
        return removetime;
    }

    public void setRemovetime(String removetime) {
        this.removetime = removetime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getChecktime() {
        return checktime;
    }

    public void setChecktime(String checktime) {
        this.checktime = checktime;
    }

    public String getCheckcomment() {
        return checkcomment;
    }

    public void setCheckcomment(String checkcomment) {
        this.checkcomment = checkcomment;
    }
}
