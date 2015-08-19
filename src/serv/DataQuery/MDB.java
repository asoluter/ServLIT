package serv.DataQuery;

import com.asoluter.litest.Objects.AuthObject;
import com.asoluter.litest.Objects.DataBase;
import serv.Utils.DataConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MDB {
    private static final String authSQL="SELECT mail,login,pass FROM users WHERE (mail=?) OR (login=?)";
    private static final String findContests="SELECT contests.cont_id, contests.name,contests.ending " +
            "FROM contests WHERE contests.available";
    private static final String findTests="DROP TABLE IF EXISTS available_contests;\n" +
            "CREATE TABLE available_contests AS (SELECT contests.cont_id FROM contests WHERE contests.available);\n" +
            "DROP TABLE IF EXISTS available_tests;\n" +
            "CREATE TABLE available_tests AS (SELECT tests.test_id, tests.cont_id, tests.test_name, tests.quest \n" +
            "                                 FROM tests INNER JOIN available_contests ON tests.cont_id=available_contests.cont_id);\n" +
            "SELECT * FROM available_tests;";

    public static synchronized boolean checkPass(AuthObject authObject){
        ResultSet resultSet;
        Connection connection=DataConnection.getConnecion();
        if (connection!=null){
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(authSQL);
                preparedStatement.setString(1,authObject.getUserName());
                preparedStatement.setString(2,authObject.getUserName());
                resultSet=preparedStatement.executeQuery();

                connection.close();

                while (resultSet.next()){
                    if(resultSet.getString("pass").equals(authObject.getUserPassword())){
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static synchronized void refresh(){
        ResultSet resultSet;
        DataBase data=new DataBase();
        Connection connection=DataConnection.getConnecion();
        if (connection!=null){
            try {
                PreparedStatement prepareContests=connection.prepareStatement(findContests);
                resultSet=prepareContests.executeQuery();
                data.setContests(resultSet);
                
                PreparedStatement prepareTests=connection.prepareStatement(findTests);
                resultSet=prepareTests.executeQuery();
                data.setTests(resultSet);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
