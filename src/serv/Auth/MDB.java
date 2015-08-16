package serv.Auth;

import serv.Objects.AuthObject;
import serv.Utils.DataConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MDB {
    private static final String authSQL="SELECT mail,login,pass FROM users WHERE (mail=?) OR (login=?)";



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


}
