package com.message;

import com.bean.User;

import java.util.ArrayList;

public class Message {
    public static final String LOGIN="LOGIN";//登陆消息的类型
    public static final String SPLIT="&&";//分隔符
    public static final String SUC="SUC";//成功
    public static final String FAIL="FAIL";//失败
    public static final String FRIENDS="FRIENDS";
    public static final String RELOGIN="RELOGIN";//重复登陆
    public static final String CHAT="CHAT";//聊天
    public static final String FILE="FILE";//文件
    //响应客户端登陆
    public static String resPoneLogin(String result){
        String msg = LOGIN+SPLIT+result;
        return msg;
    }
    //响应客户端好友请求
    public static String resPoneFriends(ArrayList<User> friendsLIst)
    {
        // FRIENDS&&好友1&&好友2
        String msg=FRIENDS;
        for (User user:friendsLIst) {
            msg+=SPLIT+user.getUserId();
        }
        return msg;
    }

}
