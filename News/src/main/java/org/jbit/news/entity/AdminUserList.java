package org.jbit.news.entity;

import java.util.Vector;

public class AdminUserList {
    private static Vector online=new Vector();
    public static void AddUser(String userId)
    {
        online.addElement(userId);
    }

    //移除
    public static void RemoveUser(String userId)
    {
        online.removeElement(userId);
    }
    //获取数量
    public static int GetUser()
    {
        return online.size();
    }

    public static Vector GetVector()
    {
        return online;
    }
}
