package serv.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {

    private static String dbName = "jdbc:postgresql://localhost:5432/litest";
    private static String dbDriver = "org.postgresql.Driver";
    private static String userName = "postgres";
    private static String password = "admin";

    public static Connection getConnecion(){
        try {

            Class.forName(dbDriver);
            return DriverManager.getConnection(dbName,userName,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
