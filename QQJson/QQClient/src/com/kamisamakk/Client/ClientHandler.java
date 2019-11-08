package com.kamisamakk.Client;

import com.kamisamakk.bean.User;
import com.kamisamakk.message.JsonMessage;
import com.kamisamakk.message.RequestFriends;
import com.kamisamakk.message.ResponseLogin;
import com.kamisamakk.ui.LoginFrame;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    public ClientHandler(Socket socket) throws IOException {
        this.socket=socket;
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer=new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                //接收服务器的信息
                String msg=reader.readLine();
                if(msg==null)
                {
                    System.out.println("客户端与服务器断开连接");
                    Client.getClient().reconnect();
                    break;
                }
                JSONObject jsonObject=JSONObject.fromObject(msg);
                String type=jsonObject.getString("type");
                if(type.equals(JsonMessage.LOGIN))
                {
                    login(jsonObject);
                }else if(type.equals(JsonMessage.RELOGIN))
                {
                    JOptionPane.showMessageDialog(null,"请勿重复登录");
                }else if(type.equals(JsonMessage.FAIL))
                {
                    JOptionPane.showMessageDialog(null,"登录失败");
                }else if(type.equals(JsonMessage.FRIENDS))
                {
                    getFriendsList(jsonObject);
                }else if(type.equals(JsonMessage.CHAT))
                {
                    getChatMsg(jsonObject);
                }else if(type.equals(JsonMessage.FILE))
                {
                    recvFile(jsonObject);
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("客户端与服务器断开连接");
                reconnect();
            }
        }
    }

    private void recvFile(JSONObject jsonObject) {
        String ip=jsonObject.getString("ip");
        String port=jsonObject.getString("port");
        String fileName=jsonObject.getString("fileName");
        String fileSize=jsonObject.getString("fileSize");
        //开启接收文件线程
        new Thread(new RecvFile(ip,Integer.valueOf(port),fileName,fileSize)).start();
    }

    private void getChatMsg(JSONObject jsonObject) {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow=format.format(date);
        String sendId=jsonObject.getString("sendId");
        String chatMsg=jsonObject.getString("chatMsg");
        String chat=timeNow+" "+sendId+" 说:\n"+chatMsg+"\n";
        LoginFrame.getLoginFrame().getTxtHistory().append(chat);
    }

    private void getFriendsList(JSONObject jsonObject) {
        JSONArray jsonArray=jsonObject.getJSONArray("friendsList");
        ArrayList<User> friendsList=(ArrayList<User>) JSONArray.toCollection(jsonArray,User.class);
        //System.out.println(friendsList);
        LoginFrame.getLoginFrame().getModel().clear();
        LoginFrame.getLoginFrame().getModel().addElement("我的好友");
        for (User user:friendsList) {
            LoginFrame.getLoginFrame().getModel().addElement(user.getUserId());
        }
    }

    private void reconnect() {
        Client.getClient().reconnect();
        this.socket=Client.getClient().getSocket();
        try {
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(JSONObject jsonObject) {
        ResponseLogin responseLogin=(ResponseLogin)JSONObject.toBean(jsonObject,ResponseLogin.class);
        User user=responseLogin.getUser();
        if(user!=null)
        {
            JOptionPane.showMessageDialog(null,"登录成功");
            RequestFriends requestFriends=new RequestFriends(user.getUserId());
            String msg=JsonMessage.ObjToJson(requestFriends);
            Client.getClient().send(msg);
        }
        else {
            JOptionPane.showMessageDialog(null,"登录失败");
        }
    }
}
