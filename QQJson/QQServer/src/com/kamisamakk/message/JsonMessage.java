package com.kamisamakk.message;

import net.sf.json.JSONObject;

public class JsonMessage {
    public static final String LOGIN="LOGIN";//登陆消息的类型
    public static final String REGISTER="REGISTER";//注册消息的类型
    public static final String SUC="SUC";//成功
    public static final String FAIL="FAIL";//失败
    public static final String FRIENDS="FRIENDS";
    public static final String RELOGIN="RELOGIN";//重复登陆
    public static final String CHAT="CHAT";//聊天
    public static final String FILE="FILE";//文件

    public static String ObjToJson(Object object)
    {
        JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }

}
