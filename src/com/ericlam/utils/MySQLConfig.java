package com.ericlam.utils;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public class MySQLConfig {
    private JSONObject jsonObject;
    private static MySQLConfig mySQLConfig;

    public static MySQLConfig loadConfig() {
        if (mySQLConfig == null) mySQLConfig = new MySQLConfig();
        return mySQLConfig;
    }

    private MySQLConfig() { //load the config
        try{
            FileReader fileReader = new FileReader("config.json");
            Object object = new JSONParser().parse(fileReader);
            jsonObject = (JSONObject) object;
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    JSONObject getJsonObject() {
        return jsonObject;
    } //get config map

    public int getWebPort() { // get web port
        return (int)(long)jsonObject.get("webPort");
    }
}
