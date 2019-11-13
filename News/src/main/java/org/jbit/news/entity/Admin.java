package org.jbit.news.entity;

/*
 *  用户数据表
 * @author
 */


public class Admin {

    private int uid;
    private String uname;
    private String upwd;


    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }


    public String getUname() {
        return uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}
