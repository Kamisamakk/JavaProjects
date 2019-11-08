package com.kamisamakk.Client;

import com.kamisamakk.ui.LoginFrame;

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
    private FileInputStream inputStream;
    private BufferedOutputStream outputStream;
    public SendFile(File file, int port)
    {
        this.file=file;
        this.port=port;
    }
    @Override
    public void run() {
        LoginFrame.getLoginFrame().getProgressBar().setMaximum((int)file.length());
        try {
            serverSocket=new ServerSocket(port);
            socket =serverSocket.accept();
            inputStream=new FileInputStream(file);
            outputStream=new BufferedOutputStream(socket.getOutputStream());
            byte[] data=new byte[1024];
            int num=0;
            while (true){
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
            JOptionPane.showMessageDialog(null,"文件传输成功");
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
