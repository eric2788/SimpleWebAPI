package com.ericlam.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.simple.JSONObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLDataSourceManager {
    private DataSource dataSource;
    private static SQLDataSourceManager SQLDataSourceManager;

    public static SQLDataSourceManager getInstance() {
        if (SQLDataSourceManager == null) SQLDataSourceManager = new SQLDataSourceManager();
        return SQLDataSourceManager;
    }

    private SQLDataSourceManager(){
        JSONObject jsonObject = MySQLConfig.loadConfig().getJsonObject();
        HikariConfig config = new HikariConfig();
        String host = (String) jsonObject.get("host");
        long port = (long) jsonObject.get("port");
        String database = (String) jsonObject.get("database");
        String username = (String) jsonObject.get("user");
        String password = (String) jsonObject.get("password");
        if(username == null || database == null || password == null){
            System.out.println("Config file is null, MySQLManager shutting down...");
            return;
        }
        String poolname = "WebApplication - Pool";
        int minsize = 5;
        int maxsize = 10;
        boolean SSL = (boolean) jsonObject.get("useSSL");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        String jdbc = "jdbc:mysql://" + host + ":" + port + "/" + database + "?" + "useSSL=" + SSL;
        config.setJdbcUrl(jdbc);
        config.setPoolName(poolname);
        config.setMaximumPoolSize(maxsize);
        config.setMinimumIdle(minsize);
        config.setUsername(username);
        config.setPassword(password);
        /*config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setAutoCommit(false);*/
        config.addDataSourceProperty("useSSL",SSL);
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        //config.addDataSourceProperty("useUnicode",true);
        config.addDataSourceProperty("characterEncoding","utf8");

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
