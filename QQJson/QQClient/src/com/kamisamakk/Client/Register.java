package com.kamisamakk.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Register {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private static Register register;
    public static Register getRegister(){
        if(register!=null)
        {
            return register;
        }else {
            register=new Register();
            return register;
        }
    }

    public Register() {
        try {
            socket=new Socket("106.52.55.169",10086);
            writer=new PrintWriter(socket.getOutputStream());
            new Thread(new RegisterHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg)
    {
        writer.println(msg);
        writer.flush();
    }
}
