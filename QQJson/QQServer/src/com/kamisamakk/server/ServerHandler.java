package com.kamisamakk.server;

import com.kamisamakk.bean.User;
import com.kamisamakk.dao.DaoFactory;
import com.kamisamakk.message.JsonMessage;
import com.kamisamakk.message.ResponseLogin;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ServerHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private User user;
    private Map<String,Socket> onlineMap=new HashMap<>();
    public ServerHandler(Socket socket) throws IOException {
        this.socket=socket;
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer=new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        boolean onLine=true;
        while (onLine)
        {
            try {
                String msg=reader.readLine();
                System.out.println(msg);
                JSONObject object=JSONObject.fromObject(msg);
                String type=object.getString("type");
                if(type.equals(JsonMessage.LOGIN))
                {
                    login(object);
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void login(JSONObject object) throws SQLException {
        String userId=object.getString("userId");
        String userPassword=object.getString("userPassword");
        String reMsg="";
        user = DaoFactory.getUserDao().login(userId,userPassword);
        if(user!=null)
        {
            if(onlineMap.containsKey(user.getUserId()))
            {
                //用户已登录
                ResponseLogin responseLogin=new ResponseLogin();
                responseLogin.setType(JsonMessage.RELOGIN);
                reMsg=JsonMessage.ObjToJson(responseLogin);
            }
            else {
                //首次登录
                onlineMap.put(user.getUserId(),socket);
                ResponseLogin responseLogin=new ResponseLogin(user);
                reMsg=JsonMessage.ObjToJson(responseLogin);
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
