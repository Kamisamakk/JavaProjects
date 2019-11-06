package com.server;

import com.bean.User;
import com.dao.DaoFactory;
import com.message.Message;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;//字节
    private PrintWriter writer;
    private String userId;
    private static Map<String,Socket> onlineMap=new HashMap<String, Socket>();

    public ServerHandler(Socket socket) {
        this.socket = socket;
        try {
            //获取输入流
            reader=new BufferedReader((new InputStreamReader(socket.getInputStream())));
            writer=new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean isOnline=true;
        while (isOnline) {
            try {
                //接收客户端发出的信息
                String msg=reader.readLine();
                String[] data=msg.split(Message.SPLIT);
                if(data[0].equals(Message.LOGIN))
                {
                    login(data);
                }else if(data[0].equals(Message.FRIENDS))
                {
                    getFriends(data);
                }else if(data[0].equals(Message.CHAT))
                {
                    sendChatMsg(data,msg);
                }else if(data[0].equals(Message.FILE))
                {
                    sendFile(data,msg);
                }
            } catch (IOException | SQLException e) {
                //e.printStackTrace();
                try {
                    //客户端断开的情况，缓存区为空的话，返回false
                    if(!reader.ready()){
                        System.out.println("用户已经退出");
                        synchronized (ServerHandler.class)
                        {
                            //移除用户
                            if(onlineMap.containsKey(userId))
                                onlineMap.remove(userId);
                        }
                    }
                    isOnline=false;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        }
    }

    private void sendFile(String[] data,String msg) throws IOException {
        Socket friendSocket=onlineMap.get(data[2]);
        if(friendSocket!=null)
        {
            PrintWriter writer=new PrintWriter(friendSocket.getOutputStream());
            writer.println(msg);
            writer.flush();
        }
    }

    private void sendChatMsg(String[] data,String chatMsg) throws IOException {
        Socket friendSocket=onlineMap.get(data[2]);
        if(friendSocket!=null)
        {
            PrintWriter writer=new PrintWriter(friendSocket.getOutputStream());
            writer.println(chatMsg);
            writer.flush();
        }
    }

    private void getFriends(String[] data) throws SQLException {
        ArrayList<User> friendsList=DaoFactory.getUserDao().friends(data[1]);
        String msg=Message.resPoneFriends(friendsList);
        writer.println(msg);
        writer.flush();
    }

    private void login(String[] data) throws SQLException {
        User user= DaoFactory.getUserDao().login(data[1],data[2]);
        String reMsg=null;
        if(user!=null)
        {
            //线程锁
            synchronized (ServerHandler.class)
            {
                if(onlineMap.containsKey(user.getUserId()))//判断用户是否在线
                {
                    reMsg=Message.resPoneLogin(Message.RELOGIN);
                }else {
                    //记录第一次登陆
                    userId=user.getUserId();
                    onlineMap.put(userId,socket);
                    reMsg=Message.resPoneLogin(Message.SUC);
                }
            }
        }else {
            reMsg=Message.resPoneLogin(Message.FAIL);
        }
        writer.println(reMsg);
        writer.flush();
    }


}
