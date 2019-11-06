package com.client;

import com.message.Message;
import com.ui.LoginFrame;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    public ClientHandler(Socket socket) {
        this.socket=socket;
        try {
            reader=new BufferedReader((new InputStreamReader(socket.getInputStream())));
            writer=new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean isOnline=true;
        while (isOnline)
        {
            //接收服务器的信息
            try {
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
                    getChatMsg(data);
                }else if(data[0].equals(Message.FILE))
                {
                    getFile(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                isOnline=false;
            }
        }
    }

    private void getFile(String[] data) {
        //System.out.println(data);
        //开启线程接收文件
        new Thread(new RecvFile(data[5],data[6],data[3],data[4])).start();
    }

    //接收聊天消息
    private void getChatMsg(String[] data) {
        Date nowDate=new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr=dateFormat.format(nowDate);
        String chatMsg=timeStr+" "+data[1]+" 说：\n"+data[3]+"\n";
        LoginFrame.getLoginFrame().getTxtHistory().append(chatMsg);
    }

    //得到好友列表
    private void getFriends(String[] data) {
        //事件分发线程
        SwingUtilities.invokeLater((new Runnable() {
            @Override
            public void run() {
                //清空好友列表
                LoginFrame.getLoginFrame().getModel().clear();
                //添加显示
                LoginFrame.getLoginFrame().getModel().addElement("我的好友");
                for (int i=1;i<data.length;i++)
                {
                    LoginFrame.getLoginFrame().getModel().addElement(data[i]);
                }
            }
        }));
    }

    //得到登录信息
    private void login(String[] data) {
        if(data[1].equals(Message.SUC))
        {
            JOptionPane.showMessageDialog(null,"登录成功");
            String msg=Message.requestFriends(LoginFrame.getLoginFrame().getTxtUserField().getText());
            writer.println(msg);
            writer.flush();
        }else if(data[1].equals(Message.RELOGIN))
        {
            JOptionPane.showMessageDialog(null,"重复登录");
        }
        else {
            JOptionPane.showMessageDialog(null,"登录失败");
        }
    }
}
