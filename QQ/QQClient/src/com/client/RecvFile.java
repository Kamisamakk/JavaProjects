package com.client;

import com.ui.LoginFrame;

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
    private FileOutputStream fileOutputStream;
    private BufferedInputStream bufferedInputStream;

    public RecvFile(String ip, String port, String fileName, String fileSize) {
        this.ip = ip;
        this.port = Integer.valueOf(port);
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    @Override
    public void run() {
        LoginFrame.getLoginFrame().getProgressBar().setMaximum(Integer.parseInt(fileSize));
        try {
            socket=new Socket(ip,port);
            fileOutputStream=new FileOutputStream("I:/VBlog/QQ/QQClient/temp/"+fileName);
            //字符流
            bufferedInputStream=new BufferedInputStream(socket.getInputStream());
            byte[]data=new byte[1024];
            int num=0;
            while (true)
            {
                int length=bufferedInputStream.read();
                if(length<0)
                {
                    break;
                }
                num+=length;
                LoginFrame.getLoginFrame().getProgressBar().setValue(num);
                //存入本地
                fileOutputStream.write(data,0,length);
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            JOptionPane.showMessageDialog(null,"文件接收成功");
            try {
                if(fileOutputStream==null) {
                    fileOutputStream.close();
                }
                if(bufferedInputStream==null) {
                    bufferedInputStream.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
