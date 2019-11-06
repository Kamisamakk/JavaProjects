package com.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

//客户端
public class Client {
    private Socket socket;
    private static Client client;
    private static PrintWriter writer;

    public static Client getClient() {
        if (client == null)
        {
            client=new Client();
        }
        return client;
    }

    public Client()
    {
        try {
            socket=new Socket("127.0.0.1",10086);
            writer=new PrintWriter(socket.getOutputStream());
            //启动监听线程
            new Thread(new ClientHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void send(String msg)
    {
        writer.println(msg);
        writer.flush();
    }
}
