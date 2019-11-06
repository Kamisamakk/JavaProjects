package com.dao;

import com.config.Config;

import java.util.HashMap;
import java.util.Map;

public class DaoFactory {
    private static Map<String, UserDao> userDaoMap = new HashMap<String, UserDao>();

    public static UserDao getUserDao(){
        if (Config.DBTYPE.equals("sqlite")) {
            return new UserDaoSqLite();
        } else if (Config.DBTYPE.equals("oracle")) {
            //return new UserDaoOracle();
        } else if (Config.DBTYPE.equals("mysql")) {
            //return new UserDaoMySql();
        }
        return null;
    }
}
