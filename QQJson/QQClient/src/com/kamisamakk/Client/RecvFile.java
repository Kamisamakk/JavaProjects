package com.kamisamakk.Client;

import com.kamisamakk.ui.LoginFrame;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RecvFile implements Runnable {
    private String ip;
    private int port;
    private String fileName;
    private String fileSize;
    private Socket socket;
    private FileOutputStream outputStream;
    private BufferedInputStream inputStream;

    public RecvFile(String ip, int port, String fileName, String fileSize) {
        this.ip = ip;
        this.port = port;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    @Override
    public void run() {
        LoginFrame.getLoginFrame().getProgressBar().setMaximum(Integer.valueOf(fileSize));
        try {
            socket=new Socket(ip,port);
            System.out.println(port);
            outputStream=new FileOutputStream("I:/VBlog/QQJson/QQClient/temp/"+fileName);
            inputStream=new BufferedInputStream(socket.getInputStream());
            byte[] data=new byte[1024];
            int num=0;
            while (true)
            {
                int length=inputStream.read(data);
                if(length<0)
                {
                    break;
                }
                num+=length;
                LoginFrame.getLoginFrame().getProgressBar().setValue(num);
                outputStream.write(data,0,length);
                outputStream.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            JOptionPane.showMessageDialog(null,"文件接收成功");
            try {
                if(outputStream==null) {
                    outputStream.close();
                }
                if(inputStream==null) {
                    inputStream.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
