package com.message;

public class Message {
    public static final String LOGIN="LOGIN";//登陆消息的类型
    public static final String SPLIT="&&";//分隔符
    public static final String SUC="SUC";//成功
    public static final String FAIL="FAIL";//失败
    public static final String RELOGIN="RELOGIN";//重复登陆
    public static final String FRIENDS="FRIENDS";//好友
    public static final String CHAT="CHAT";//聊天
    public static final String FILE="FILE";//文件
    //登录消息验证
    //LOGIN&&10001&&123456
    public static String requestLogin(String userId, String userPassword)
    {
        String msg=LOGIN+SPLIT+userId+SPLIT+userPassword;
        return msg;
    }
    //获取好友列表请求
    public static String requestFriends(String userId)
    {
        String msg=FRIENDS+SPLIT+userId;
        return msg;
    }
    //发送信息请求
    public static String requestMessage(String userId,String friendId,String chatMessage)
    {
        String msg=CHAT+SPLIT+userId+SPLIT+friendId+SPLIT+chatMessage;
        return msg;
    }
    //发送文件请求
    public static String requestSendFile(String userId,String friendId,String filePath,long fileSize,String ip,int port)
    {
        String msg=FILE+SPLIT+userId+SPLIT+friendId+SPLIT+filePath+SPLIT+new Long(fileSize).toString()+SPLIT+ip+SPLIT+new Integer(port).toString();
        return msg;
    }
}
