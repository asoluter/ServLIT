package serv.DataControls;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

class ReportManager {
    static void makeAnsCSV(ResultSet resultSet, String path){
        try {
            FileWriter writer=new FileWriter(path);
            writer.write("Answer_id;Test_id;Answer_text\n");
            while (resultSet.next()){
                writer.append(String.valueOf(resultSet.getInt(1)));
                writer.append(';');
                writer.append(String.valueOf(resultSet.getInt(2)));
                writer.append(';');
                writer.append(resultSet.getString(3));
                writer.append('\n');
            }
            writer.flush();
            writer.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    static void makeContCSV(ResultSet resultSet, String path){
        try {
            FileWriter writer=new FileWriter(path);
            writer.write("Contest_id;Contest_name;Ending;Available\n");
            while (resultSet.next()){
                writer.append(String.valueOf(resultSet.getInt(1)));
                writer.append(';');
                writer.append(resultSet.getString(2));
                writer.append(';');
                writer.append(resultSet.getDate(3).toString());
                writer.append(';');
                writer.append(String.valueOf(resultSet.getBoolean(4)));
                writer.append('\n');
            }
            writer.flush();
            writer.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    static void makeResCSV(ResultSet resultSet, String path){
        try {
            FileWriter writer=new FileWriter(path);
            writer.write("Result_id;Contest_id;User_id;Result;Sent\n");
            while(resultSet.next()){
                writer.append(String.valueOf(resultSet.getInt(1)));
                writer.append(';');
                writer.append(String.valueOf(resultSet.getInt(2)));
                writer.append(';');
                writer.append(String.valueOf(resultSet.getInt(3)));
                writer.append(';');
                writer.append(String.valueOf(resultSet.getInt(5)));
                writer.append(';');
                writer.append(resultSet.getDate(6).toString());
                writer.append('\n');
            }


            writer.flush();
            writer.close();

        } catch (IOException | SQLException e) {
        e.printStackTrace();
        }
    }


    static void makeTestCSV(ResultSet resultSet, String path){
        try{
            FileWriter writer=new FileWriter(path);
            writer.write("Test_id;Contest_id;Test_name;Question\n");

            while (resultSet.next()){
                writer.append(String.valueOf(resultSet.getInt(1)));
                writer.append(';');
                writer.append(String.valueOf(resultSet.getInt(2)));
                writer.append(';');
                writer.append(resultSet.getString(3));
                writer.append(';');
                writer.append(resultSet.getString(4));
                writer.append('\n');
            }

            writer.flush();
            writer.close();

        }catch(IOException | SQLException e){
        e.printStackTrace();
        }
    }


    static void makeUserCSV(ResultSet resultSet, String path){
        try {
            FileWriter writer=new FileWriter(path);
            writer.write("User_id;Login;Password;E-Mail;Name;Birth;Checked;Hash\n");
            while (resultSet.next()){
                writer.append(String.valueOf(resultSet.getInt(1)));
                writer.append(';');
                writer.append(resultSet.getString(2));
                writer.append(';');
                writer.append(resultSet.getString(3));
                writer.append(';');
                writer.append(resultSet.getString(4));
                writer.append(';');
                writer.append(resultSet.getString(5));
                writer.append(';');
                writer.append(resultSet.getDate(6).toString());
                writer.append(';');
                writer.append(String.valueOf(resultSet.getBoolean(7)));
                writer.append(';');
                writer.append(resultSet.getString(8));
                writer.append('\n');
            }

            writer.flush();
            writer.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
