package com.ui;

import com.client.Client;
import com.client.SendFile;
import com.message.Message;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class LoginFrame extends JFrame {
    //登陆控件
    JTextField txtUserField;
    JTextField txtPwdfField;
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
    private static LoginFrame frame;

    public static LoginFrame getLoginFrame() {
        if (frame == null) {
            frame = new LoginFrame();
        }
        return frame;
    }

    private LoginFrame() {
        System.out.println(Thread.currentThread().getName());
        // TODO Auto-generated constructor stub
        txtUserField = new JTextField();
        txtPwdfField = new JTextField();
        btnLogin = new JButton();
        btnLogin.setText("登录");

        btnReg = new JButton();
        btnReg.setText("注册");

        //左侧登录块
        this.setLayout(null);
        this.add(txtUserField);
        txtUserField.setBounds(10, 10, 100, 30);
        this.add(txtPwdfField);
        txtPwdfField.setBounds(10, 50, 100, 30);
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

        txtUserField.setText("QQ号");
        txtPwdfField.setText("密码");

        //登录的监听事件
        btnLogin.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String msg= Message.requestLogin(txtUserField.getText(),txtPwdfField.getText());
                Client.getClient().send(msg);
            }
        });

        //发送消息监听事件
        btnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String msg=Message.requestMessage(txtUserField.getText(),list.getSelectedValue().toString(),txtSend.getText());
                Client.getClient().send(msg);
            }
        });

        //发送文件监听事件
        btnSendFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //文件选择器
                JFileChooser chooser=new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//文件名和文件
                //返回一个int值，分别是 JFileChooser.APPROVE_OPTION,
                // JFileChooser.CANCEL_OPTION和JFileChooser.ERROR_OPTION。
                // 在获取用户选择的文件之前，通常先验证返回值是否为APPROVE_OPTION.
                int num=chooser.showOpenDialog(null);
                //若选择了文件，则打印选择了什么文件
                if(num==JFileChooser.APPROVE_OPTION)
                {
                    File file=chooser.getSelectedFile();//获取文件
                    String msg=Message.requestSendFile(txtUserField.getText(),list.getSelectedValue().toString(),file.getName(),file.length(),"127.0.0.1",10001);
                    Client.getClient().send(msg);
                    new Thread(new SendFile(file,10001)).start();
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
        return txtPwdfField;
    }

    public void setTxtPwdField(JTextField txtPwdfField) {
        this.txtPwdfField = txtPwdfField;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }

    public JButton getBtnReg() {
        return btnReg;
    }

    public void setBtnReg(JButton btnReg) {
        this.btnReg = btnReg;
    }

    public JTextField getTxtPwdfField() {
        return txtPwdfField;
    }

    public void setTxtPwdfField(JTextField txtPwdfField) {
        this.txtPwdfField = txtPwdfField;
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

    public JButton getBtnSendFile() {
        return btnSendFile;
    }

    public void setBtnSendFile(JButton btnSendFile) {
        this.btnSendFile = btnSendFile;
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
