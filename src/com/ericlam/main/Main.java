package com.ericlam.main;

import com.ericlam.handler.ClubHandler;
import com.ericlam.utils.MySQLConfig;
import com.ericlam.utils.OutPutResources;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class Main {
    public static void main(String[] args) {
        if (!OutPutResources.output()){
            System.out.println("Configuration Output failed. shutting down the server.");//avoid error
            return;
        }
        int port = MySQLConfig.loadConfig().getWebPort();
        System.out.println("Launching Http API Server, using port: " + port); //can be customizable
        try{
            Server server = new Server(port);
            ServletContextHandler context = new ServletContextHandler();
            context.addServlet(new ServletHolder(new ClubHandler()), "/club"); //handler class with path /club
            context.setMaxFormContentSize(50);
            server.setHandler(context);
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
