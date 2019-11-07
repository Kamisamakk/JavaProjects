package com.kamisamakk.message;

import net.sf.json.JSONObject;

public class JsonMessage {
    public static final String LOGIN="LOGIN";//登陆消息的类型
    public static final String SPLIT="&&";//分隔符
    public static final String SUC="SUC";//成功
    public static final String FAIL="FAIL";//失败
    public static final String RELOGIN="RELOGIN";//重复登陆
    public static final String FRIENDS="FRIENDS";//好友
    public static final String CHAT="CHAT";//聊天
    public static final String FILE="FILE";//文件

    //对象转为Json字符串
    public static String ObjToJson(Object object)
    {
        JSONObject jsonObject=JSONObject.fromObject(object);
        return jsonObject.toString();
    }
}
