package com.kamisamakk.message;

public class RequestLogin {
    private String type;
    private String userId;
    private String userPassword;

    public RequestLogin(String userId, String userPassword) {
        this.type = JsonMessage.LOGIN;
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public RequestLogin() {
        this.type = JsonMessage.LOGIN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
