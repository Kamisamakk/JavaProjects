package com.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Config {
    public static String DRIVER;
    public static String URL;
    public static String DBTYPE;
    public static void init(){
        Properties properties = new Properties();
        try {
            //System.out.println(new FileInputStream("config.ini"));
            properties.load(new FileInputStream("I:\\VBlog\\QQ\\QQService\\config.ini"));
            DRIVER = properties.getProperty("DRIVER");
            URL =  properties.getProperty("URL");
            DBTYPE = properties.getProperty("DBTYPE");
            System.out.println(DBTYPE);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
