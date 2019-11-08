package com.kamisamakk.message;

import com.kamisamakk.bean.User;

public class RequestFriends {
    private String userId;
    private String type;

    public RequestFriends() {
        this.type=JsonMessage.FRIENDS;
    }

    public RequestFriends(String userId) {
        this.userId = userId;
        this.type=JsonMessage.FRIENDS;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
