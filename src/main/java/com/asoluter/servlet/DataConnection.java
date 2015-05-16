package com.asoluter.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
    private static String dbName = "jdbc:postgresql://127.0.0.1:5432/tomcat";
    private static String dbDriver = "org.postgresql.Driver";
    private static String userName = "admingpesvdt";
    private static String password = "rtxgV-7j3gE_";

    public static Connection getConnecion(){
        try {
            Class.forName(dbDriver);
            return DriverManager.getConnection(dbName,userName,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
