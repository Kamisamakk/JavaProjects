package com.kamisamakk.ui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

        txtName.setText("昵称");
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
    }
}
