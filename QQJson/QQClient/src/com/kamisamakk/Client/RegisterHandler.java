package com.kamisamakk.Client;

import com.kamisamakk.bean.User;
import com.kamisamakk.message.JsonMessage;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegisterHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public RegisterHandler(Socket socket) {
        this.socket = socket;
        try {
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true)
        {
            try {
                String msg = reader.readLine();
                JSONObject object = JSONObject.fromObject(msg);
                String type = object.getString("type");
                System.out.println(object);
                if (type.equals(JsonMessage.REGISTER)) {
                    register(object);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void register(JSONObject object) {
        String msg= object.getString("user");
        JSONObject jsonObject=JSONObject.fromObject(msg);
        User user=(User)JSONObject.toBean(jsonObject,User.class);
        System.out.println(user.getUserId());
        JOptionPane.showMessageDialog(null, "注册成功，请保存账号："+user.getUserId());
    }
}
