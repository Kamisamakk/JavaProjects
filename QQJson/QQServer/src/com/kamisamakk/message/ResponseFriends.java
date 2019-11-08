package com.kamisamakk.message;

import com.kamisamakk.bean.User;

import java.util.ArrayList;

public class ResponseFriends {
    private String type;
    private ArrayList<User> friendsList;

    public ResponseFriends(ArrayList<User> friendsList) {
        this.friendsList = friendsList;
        this.type=JsonMessage.FRIENDS;
    }

    public ResponseFriends() {
        this.type=JsonMessage.FRIENDS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<User> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(ArrayList<User> friendsList) {
        this.friendsList = friendsList;
    }
}
