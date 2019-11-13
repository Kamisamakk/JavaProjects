package org.jbit.news.entity;

import java.util.Date;

public class User {
    private int tid;
    private int uId;
    private String uPassword;
    private String uName;
    private String uNumber;
    private int uSex;//0 男 1女
    private String QQ;
    private String hobby;
    private int ustates;//0 冻结 1正常
    private String operator;
    private Date operateTime;
    private String headPicPath;

    public String getHeadPicPath() {
        return headPicPath;
    }

    public void setHeadPicPath(String headPicPath) {
        this.headPicPath = headPicPath;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public int getuSex() {
        return uSex;
    }

    public void setuSex(int uSex) {
        this.uSex = uSex;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public int getUstates() {
        return ustates;
    }

    public void setUstates(int ustates) {
        this.ustates = ustates;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
