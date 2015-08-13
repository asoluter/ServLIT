package serv.Auth;

import serv.Objects.AuthObject;
import serv.Utils.DataConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MDB {
    private static String sql="SELECT* FROM users;";
    private static Map<String,String> aliases;
    private static Map<String,String> creds;
    private static ResultSet data;



    public static synchronized void init(){
        Connection connection= DataConnection.getConnecion();
        if(connection!=null){
            aliases.clear();
            creds.clear();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                data=preparedStatement.executeQuery();
                while (data.next()){
                    aliases.put(data.getString("mail"),data.getString("user_name"));
                    creds.put(data.getString("user_name"),data.getString("password"));
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //TODO: Make logging
            }

        }

    }

    public static synchronized boolean checkPass(AuthObject authObject){
        if(aliases.containsKey(authObject.getUserName())){
            if(authObject.getUserPassword()
                    .equals(creds
                                    .get(aliases
                                                    .get(authObject.getUserName()
                                                    )
                                    )
                    ))return true;
        }else{
            if(authObject.getUserPassword()
                    .equals(creds
                            .get(authObject.getUserName())))return true;
        }
        return false;
    }


}
