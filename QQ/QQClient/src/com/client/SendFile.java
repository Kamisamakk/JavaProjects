package com.client;

import com.ui.LoginFrame;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SendFile implements Runnable {
    private File file;
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private FileInputStream fileInputStream;
    private BufferedOutputStream bufferedOutputStream;
    public SendFile(File file, int port) {
        this.file = file;
        this.port = port;
    }

    @Override
    public void run() {
        LoginFrame.getLoginFrame().getProgressBar().setMaximum((int) file.length());
        try {
            serverSocket=new ServerSocket(port);
            socket=serverSocket.accept();
            fileInputStream=new FileInputStream(file);
            bufferedOutputStream=new BufferedOutputStream(socket.getOutputStream());
            byte[] data=new byte[1024];
            int num=0;
            while (true)
            {
                int length=fileInputStream.read();
                if(length<0)
                {
                    break;
                }
                num+=length;
                LoginFrame.getLoginFrame().getProgressBar().setValue(num);
                bufferedOutputStream.write(data,0,length);
                bufferedOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            JOptionPane.showMessageDialog(null,"文件传输成功");
            try {
                if (fileInputStream==null)
                {
                    fileInputStream.close();
                }
                if (bufferedOutputStream==null)
                {
                    bufferedOutputStream.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
