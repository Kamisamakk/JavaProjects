package org.jbit.news.listener;

import org.jbit.news.entity.AdminUserList;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionUserListenner implements HttpSessionListener, HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("SessionUserListenner..attributeAdded "+" httpSessionBindingEvent.getName() "+
                httpSessionBindingEvent.getName()+" httpSessionBindingEvent.getValue() "+
                httpSessionBindingEvent.getValue());
        if(httpSessionBindingEvent.getName()!=null&&"userId".equals(httpSessionBindingEvent.getName()))
        {
            System.out.println("SessionUserListenner "+" 将用户名存入AdminUserList "+httpSessionBindingEvent.getValue());
            AdminUserList.AddUser(httpSessionBindingEvent.getValue().toString());
        }

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("SessionTextListener..attributeRemoved"
                + "..httpSessionBindingEvent.getName()="+httpSessionBindingEvent.getName()
                + "..httpSessionBindingEvent.getValue()="+httpSessionBindingEvent.getValue());
        if(httpSessionBindingEvent.getName()!=null&&"userId".equals(httpSessionBindingEvent.getName()));
        {
            System.out.println("SessionUserListenner "+" 将用户名移除AdminUserList "+httpSessionBindingEvent.getValue());
            AdminUserList.RemoveUser(httpSessionBindingEvent.getValue().toString());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("SessionTextListener..attributeReplaced"
                + "..httpSessionBindingEvent.getName()="+httpSessionBindingEvent.getName()
                + "..httpSessionBindingEvent.getValue()="+httpSessionBindingEvent.getValue());
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("SessionTextListener..sessionCreated");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("SessionTextListener..sessionDestroyed");

    }
}
