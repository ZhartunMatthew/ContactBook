package com.zhartunmatthew.web.contactbook.dbmanager;

import java.util.ResourceBundle;

class ConfigManager {
    private final static String PROPERTIES_PATH = "database";
    private static ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_PATH);

    private ConfigManager(){}

    static String getUrl(){
        return resBundle.getString("url");
    }

    static String getUser(){
        return resBundle.getString("user");
    }

    static String getPassword(){
        return resBundle.getString("password");
    }
}
