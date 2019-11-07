package com.kamisamakk.Client;

import com.kamisamakk.bean.User;
import com.kamisamakk.message.JsonMessage;
import com.kamisamakk.message.ResponseLogin;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
        boolean isOnline=true;
        while (isOnline)
        {
            try {
                //接收服务器的信息
                String msg=reader.readLine();
                JSONObject jsonObject=JSONObject.fromObject(msg);
                String type=jsonObject.getString("type");
                if(type.equals(JsonMessage.LOGIN))
                {
                    login(jsonObject);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void login(JSONObject jsonObject) {
        ResponseLogin responseLogin=(ResponseLogin)JSONObject.toBean(jsonObject,ResponseLogin.class);
        User user=responseLogin.getUser();
        if(user!=null)
        {
            JOptionPane.showMessageDialog(null,"登录成功");
        }
        else {
            JOptionPane.showMessageDialog(null,"登录失败");
        }
    }
}
