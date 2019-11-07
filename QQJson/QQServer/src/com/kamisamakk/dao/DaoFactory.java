package com.kamisamakk.dao;

import com.kamisamakk.config.Config;

import java.util.HashMap;
import java.util.Map;

public class DaoFactory {
    private static Map<String, UserDao> userDaoMap = new HashMap<String, UserDao>();
    public static void initDao()
    {
        userDaoMap.put("sqlite",new UserDaoSqlite());
    }
    //	private static String DBTYPE = "oracle";
    public static UserDao getUserDao(){
        if (Config.DBTYPE.equals("sqlite")) {
            return new UserDaoSqlite();
        } else if (Config.DBTYPE.equals("oracle")) {
           // return new UserDaoOracle();
        } else if (Config.DBTYPE.equals("mysql")) {
            //return new UserDaoMySql();
        }
        return null;

    }
}
