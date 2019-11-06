package com.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//服务端
public class Server {
    private ServerSocket serverSocket;
    public Server()
    {
        try {
            //初始化服务端socket并且绑定10086端口
            serverSocket=new ServerSocket(10086);
            Socket socket=null;
            //记录客户端数量
            int count=0;
            //循环监听等待客户端的连接
            while (true) {
                System.out.println("等待客户端连接");
                //创建接收接口
                socket = serverSocket.accept();
                System.out.println("客户端已连接");
                //启动监听线程
                new Thread(new ServerHandler(socket)).start();
                if (!socket.isConnected())
                    count++;
                System.out.println("客户端的数量："+count);
                InetAddress address=socket.getInetAddress();
                System.out.println("当前客户端的IP："+address.getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
