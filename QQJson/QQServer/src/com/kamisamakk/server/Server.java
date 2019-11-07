package com.kamisamakk.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    public Server() throws IOException {
        //初始化服务端socket并且绑定10086端口
        serverSocket=new ServerSocket(10086);
        //循环监听等待客户端的连接
        while (true)
        {
            System.out.println("等待客户端连接");
            Socket socket=serverSocket.accept();
            System.out.println("客户端已连接");
            new Thread(new ServerHandler(socket)).start();
        }
    }
}
