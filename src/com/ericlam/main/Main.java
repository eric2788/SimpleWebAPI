package com.ericlam.main;

import com.ericlam.handler.WebHandler;
import com.ericlam.utils.MySQLConfig;
import com.ericlam.utils.OutPutResources;
import org.eclipse.jetty.server.Server;

public class Main {
    public static void main(String[] args) {
        if (!OutPutResources.output()){
            System.out.println("Configuration Output failed. shutting down the server.");
            return;
        }
        int port = MySQLConfig.loadConfig().getWebPort();
        System.out.println("Launching Http API Server, using port: "+port);
        try{
            Server server = new Server(port);
            server.setHandler(new WebHandler());
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
