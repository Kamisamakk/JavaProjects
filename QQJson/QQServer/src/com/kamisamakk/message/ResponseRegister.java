package com.kamisamakk.message;

import com.kamisamakk.bean.User;

public class ResponseRegister {
    private User user;
    private String type;

    public ResponseRegister() {
        this.type =  JsonMessage.REGISTER;
    }

    public ResponseRegister(User user) {
        this.user = user;
        this.type = JsonMessage.REGISTER;
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
