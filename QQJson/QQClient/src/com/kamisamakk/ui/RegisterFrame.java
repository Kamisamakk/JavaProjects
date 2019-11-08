package com.kamisamakk.ui;

import com.kamisamakk.Client.Register;
import com.kamisamakk.bean.User;
import com.kamisamakk.message.JsonMessage;
import com.kamisamakk.message.RequestRegister;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class RegisterFrame extends JFrame {
    private JTextField txtPwd = new JTextField();
    private JTextField txtName = new JTextField();
    private JTextField txtSex = new JTextField();
    private JButton btnReg = new JButton();
    private static RegisterFrame registerFrame;

    public static RegisterFrame getRegisterFrame()
    {
        if(registerFrame == null)
        {
            registerFrame = new RegisterFrame();
            return registerFrame;
        }
        else return registerFrame;
    }

    private RegisterFrame() {
        // TODO Auto-generated constructor stub
        this.setLayout(null);
        txtPwd.setText("密码");
        txtPwd.setBounds(10, 10, 100, 30);
        this.add(txtPwd);

        txtName.setText("用户名");
        txtName.setBounds(10, 50, 100, 30);
        this.add(txtName);

        txtSex.setText("性别");
        txtSex.setBounds(10, 90, 100, 30);
        this.add(txtSex);

        btnReg.setBounds(10, 130, 100, 30);
        btnReg.setText("注册");
        this.add(btnReg);

        this.setSize(200, 200);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent a) {
                super.windowClosing(a);
                int key = JOptionPane.showConfirmDialog(null, "是否要退出", "温馨提示", JOptionPane.OK_CANCEL_OPTION);
                if (key == JOptionPane.OK_CANCEL_OPTION)
                    System.exit(0);

            }
        });

        btnReg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                // TODO Auto-generated method stub
                super.mouseClicked(arg0);
                //发送注册消息给服务器
                User user=new User(null,txtPwd.getText(),txtName.getText(),txtSex.getText());
                RequestRegister requestRegister=new RequestRegister(user);
                String msg= JsonMessage.ObjToJson(requestRegister);
                Register.getRegister().send(msg);
            }
        });


    }
}
