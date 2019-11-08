package com.kamisamakk.bean;

public class User {
    private String userId;
    private String userPassword;
    private String userName;
    private String sex;

    public User() {
        super();
    }

    public User(String userId, String userPassword, String userName, String sex) {
        super();
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userPassword=" + userPassword
                + ", userName=" + userName + ", sex=" + sex + "]";
    }

}
