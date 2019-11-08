package com.kamisamakk.ui;

import com.kamisamakk.Client.Client;
import com.kamisamakk.Client.SendFile;
import com.kamisamakk.message.JsonMessage;
import com.kamisamakk.message.RequestLogin;
import com.kamisamakk.message.RequestChatMsg;
import com.kamisamakk.message.RequestSendFile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class LoginFrame extends JFrame {
    //登陆控件
    JTextField txtUserField;
    JTextField txtPwdField;
    JButton btnLogin;
    JButton btnReg;
    //好友区控件
    JList list;
    DefaultListModel model;
    //消息区控件
    JTextArea txtHistory = new JTextArea();
    JTextArea txtSend = new JTextArea();
    JButton btnSend = new JButton();
    //发送文件控件
    JTextField txtSendFile = new JTextField();
    JButton btnSendFile = new JButton();
    JProgressBar progressBar = new JProgressBar();
    JLabel labSendFile = new JLabel();
    private static LoginFrame loginFrame;

    public static LoginFrame getLoginFrame() {
        if (loginFrame == null) {
            loginFrame = new LoginFrame();
        }
        return loginFrame;
    }

    public LoginFrame()
    {
        System.out.println(Thread.currentThread().getName());
        // TODO Auto-generated constructor stub
        txtUserField = new JTextField();
        txtPwdField = new JTextField();
        btnLogin = new JButton();
        btnLogin.setText("登录");

        btnReg = new JButton();
        btnReg.setText("注册");

        //左侧登录块
        this.setLayout(null);
        this.add(txtUserField);
        txtUserField.setBounds(10, 10, 100, 30);
        this.add(txtPwdField);
        txtPwdField.setBounds(10, 50, 100, 30);
        this.add(btnLogin);
        btnLogin.setBounds(10, 90, 100, 30);
        btnReg.setBounds(10, 130, 100, 30);
        this.add(btnReg);

        // JList是一个view，要添加数据，就是添加到model
        model = new DefaultListModel();// Model
        list = new JList(model);// View
        this.add(list);
        list.setBounds(120, 10, 100, 420);

        //消息块
        txtHistory.setBounds(240, 10, 300, 200);
        this.add(txtHistory);
        txtSend.setBounds(240, 220, 300, 200);
        this.add(txtSend);
        btnSend.setBounds(460, 430, 80, 30);
        this.add(btnSend);
        btnSend.setText("发送");

        //发送文件
        txtSendFile.setBounds(10, 470, 300, 30);
        this.add(txtSendFile);
        labSendFile.setText("发送文件");
        labSendFile.setBounds(10, 440, 80, 30);
        this.add(labSendFile);

        btnSendFile.setBounds(10, 520, 100, 30);
        btnSendFile.setText("发送文件");
        this.add(btnSendFile);

        //进度条
        progressBar.setBounds(320, 470, 200, 30);
        this.add(progressBar);
        progressBar.setMaximum(100);
        progressBar.setValue(0);

        //窗口布局
        setSize(600, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent a)
            {
                super.windowClosing(a);
                int key=JOptionPane.showConfirmDialog(null,"是否要退出", "温馨提示",JOptionPane.OK_CANCEL_OPTION);
                if(key==JOptionPane.OK_CANCEL_OPTION)
                    System.exit(0);
            }
        });

        btnReg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                RegisterFrame.getRegisterFrame();
            }
        });

        //登录的监听事件
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                RequestLogin requestLogin=new RequestLogin(txtUserField.getText(),txtPwdField.getText());
                String msg= JsonMessage.ObjToJson(requestLogin);
                Client.getClient().send(msg);
            }
        });

        btnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                RequestChatMsg requestSendMsg=new RequestChatMsg(txtUserField.getText(),list.getSelectedValue().toString(),txtSend.getText());
                String chatMsg=JsonMessage.ObjToJson(requestSendMsg);
                Client.getClient().send(chatMsg);
            }
        });

        btnSendFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser=new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int num=chooser.showOpenDialog(null);
                if(num==JFileChooser.APPROVE_OPTION){
                    System.out.println("选择了打开按钮");
                    File file=chooser.getSelectedFile();
                    RequestSendFile requestSendFile=new RequestSendFile(txtUserField.getText(),list.getSelectedValue().toString(),file.getName(),file.length(),"127.0.0.1",10085);
                    String msg=JsonMessage.ObjToJson(requestSendFile);
                    Client.getClient().send(msg);
                    new Thread(new SendFile(file,10085)).start();
                }
            }
        });
    }

    public JTextField getTxtUserField() {
        return txtUserField;
    }

    public void setTxtUserField(JTextField txtUserField) {
        this.txtUserField = txtUserField;
    }

    public JTextField getTxtPwdField() {
        return txtPwdField;
    }

    public void setTxtPwdField(JTextField txtPwdField) {
        this.txtPwdField = txtPwdField;
    }

    public JList getList() {
        return list;
    }

    public void setList(JList list) {
        this.list = list;
    }

    public DefaultListModel getModel() {
        return model;
    }

    public void setModel(DefaultListModel model) {
        this.model = model;
    }

    public JTextArea getTxtHistory() {
        return txtHistory;
    }

    public void setTxtHistory(JTextArea txtHistory) {
        this.txtHistory = txtHistory;
    }

    public JTextArea getTxtSend() {
        return txtSend;
    }

    public void setTxtSend(JTextArea txtSend) {
        this.txtSend = txtSend;
    }

    public JTextField getTxtSendFile() {
        return txtSendFile;
    }

    public void setTxtSendFile(JTextField txtSendFile) {
        this.txtSendFile = txtSendFile;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public JLabel getLabSendFile() {
        return labSendFile;
    }

    public void setLabSendFile(JLabel labSendFile) {
        this.labSendFile = labSendFile;
    }


}
