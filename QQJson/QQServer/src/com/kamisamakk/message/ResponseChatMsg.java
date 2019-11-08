package com.kamisamakk.message;

public class ResponseChatMsg {
    private String type;
    private String userId;
    private String friendId;
    private String chatMsg;
    public ResponseChatMsg() {
        this.type=JsonMessage.CHAT;
    }

    public ResponseChatMsg(String userId, String friendId, String chatMsg) {
        this.userId = userId;
        this.friendId = friendId;
        this.chatMsg = chatMsg;
        this.type=JsonMessage.CHAT;
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

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(String chatMsg) {
        this.chatMsg = chatMsg;
    }
}
