package com.kamisamakk.message;

public class RequestChatMsg {
    private String type;
    private String sendId;
    private String friendId;
    private String chatMsg;

    public RequestChatMsg() {
        this.type=JsonMessage.CHAT;
    }

    public RequestChatMsg(String sendId, String friendId, String chatMsg) {
        this.sendId = sendId;
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

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
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
