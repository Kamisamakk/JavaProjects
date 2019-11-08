package com.kamisamakk.server;

import com.kamisamakk.bean.User;
import com.kamisamakk.dao.DaoFactory;
import com.kamisamakk.message.*;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private User user;
    private static Map<String,Socket> onlineMap=new HashMap<>();
    public ServerHandler(Socket socket) throws IOException {
        this.socket=socket;
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer=new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                String msg=reader.readLine();
                System.out.println(msg);
                if(msg==null)
                {
                    close();
                    break;
                }
                JSONObject object=JSONObject.fromObject(msg);
                String type=object.getString("type");
                if(type.equals(JsonMessage.LOGIN))
                {
                    login(object);
                }else if(type.equals(JsonMessage.FRIENDS))
                {
                    sendFriendList(object);
                }else if(type.equals(JsonMessage.CHAT))
                {
                    sendChatMsg(object);
                }else if(type.equals(JsonMessage.REGISTER))
                {
                    register(object);
                }else if(type.equals(JsonMessage.FILE))
                {
                    sendFile(object);
                }
            } catch (IOException | SQLException e) {
                //e.printStackTrace();
                System.out.println("用户退出");
                close();
                break;
            }
        }
    }

    private void sendFile(JSONObject object) {
        String socket=object.getString("recvId");
        Socket friendsocket=onlineMap.get(socket);
        if(friendsocket!=null)
        {
            try {
                PrintWriter writer = new PrintWriter(friendsocket.getOutputStream());
                writer.println(object);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //注册
    private void register(JSONObject object) {
        String msg=object.getString("user");
        JSONObject jsonObject=JSONObject.fromObject(msg);
        User user=(User)JSONObject.toBean(jsonObject,User.class);
        User newUser=DaoFactory.getUserDao().register(user);
        ResponseRegister responseRegister=new ResponseRegister(newUser);
        String reMsg=JsonMessage.ObjToJson(responseRegister);
        writer.println(reMsg);
        writer.flush();
    }

    //转发消息
    private void sendChatMsg(JSONObject object) {
        String socket=object.getString("friendId");
        Socket friendSocket=onlineMap.get(socket);
        if(friendSocket!=null)
        {
            try {
                writer=new PrintWriter(friendSocket.getOutputStream());
                writer.println(object);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //响应好友列表请求
    private void sendFriendList(JSONObject object) throws SQLException {
        String userId=object.getString("userId");
        ArrayList<User> friendList=DaoFactory.getUserDao().friends(userId);
        String reMsg="";
        if(friendList!=null)
        {
            ResponseFriends responseFriends=new ResponseFriends(friendList);
            reMsg=JsonMessage.ObjToJson(responseFriends);
            writer.println(reMsg);
            writer.flush();
        }
    }

    private void close() {
        if(user!=null)
        {
            synchronized (ServerHandler.class)
            {
                onlineMap.remove(user.getUserId());
            }
            try {
                socket.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //登录
    private void login(JSONObject object) throws SQLException {
        String userId=object.getString("userId");
        String userPassword=object.getString("userPassword");
        String reMsg="";
        user = DaoFactory.getUserDao().login(userId,userPassword);
        if(user!=null)
        {
            synchronized (ServerHandler.class) {
                if (onlineMap.containsKey(user.getUserId())) {
                    //用户已登录
                    ResponseLogin responseLogin = new ResponseLogin();
                    responseLogin.setType(JsonMessage.RELOGIN);
                    reMsg = JsonMessage.ObjToJson(responseLogin);
                } else {
                    //首次登录
                    onlineMap.put(user.getUserId(), socket);
                    System.out.println(onlineMap.toString());
                    ResponseLogin responseLogin = new ResponseLogin(user);
                    reMsg = JsonMessage.ObjToJson(responseLogin);
                }
            }
        }else {
            //用户名或密码错误、用户不存在
            ResponseLogin responseLogin=new ResponseLogin();
            responseLogin.setType(JsonMessage.FAIL);
            reMsg=JsonMessage.ObjToJson(responseLogin);
        }
        writer.println(reMsg);
        writer.flush();
    }
}
