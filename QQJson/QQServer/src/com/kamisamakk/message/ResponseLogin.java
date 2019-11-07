package com.kamisamakk.message;

import com.kamisamakk.bean.User;

public class ResponseLogin {
    private String type;
    private User user;

    public ResponseLogin() {
        super();
        this.type = JsonMessage.LOGIN;
    }

    public ResponseLogin(User user) {
        this.type = JsonMessage.LOGIN;
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
