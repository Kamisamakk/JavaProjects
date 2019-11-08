package com.kamisamakk.message;

import com.kamisamakk.bean.User;

public class RequestRegister {
    private User user;
    private String type;

    public RequestRegister() {
        this.type =  JsonMessage.REGISTER;
    }

    public RequestRegister(User user) {
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
