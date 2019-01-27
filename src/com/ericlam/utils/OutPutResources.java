package com.ericlam.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class OutPutResources {
    public static boolean output(){
        InputStream is = OutPutResources.class.getResourceAsStream("/resources/config.json");
        if (is == null) return false;
        File out = new File("config.json");
        if (!out.exists()){
            try {
                FileUtils.copyInputStreamToFile(is,out);
                System.out.println("Configuration File Output Successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
