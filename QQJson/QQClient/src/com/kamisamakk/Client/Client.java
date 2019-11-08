package com.kamisamakk.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter writer;
    private static Client client;

    public static Client getClient()
    {
        if(client!=null)
        {
            return client;
        }else{
            client=new Client();
            return client;
        }
    }

    public Client()
    {
        try {
            socket=new Socket("127.0.0.1",10086);
            writer=new PrintWriter(socket.getOutputStream());
            new Thread(new ClientHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //发送请求信息
    public void send(String msg)
    {
        writer.println(msg);
        writer.flush();
    }
    //重连服务器
    public void reconnect()
    {
        while (true)
        {
            try {
                socket=new Socket("127.0.0.1",10086);
                writer=new PrintWriter(socket.getOutputStream());
                if(socket.isConnected())
                {
                    System.out.println("连接上服务器");
                    break;
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("无法连接服务器");
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
