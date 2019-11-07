package com.kamisamakk.message;

import com.kamisamakk.bean.User;

public class ResponseLogin {
    private User user;
    private String type;

    public ResponseLogin() {
        this.type=JsonMessage.LOGIN;
    }

    public ResponseLogin(User user) {
        this.user = user;
        this.type=JsonMessage.LOGIN;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
