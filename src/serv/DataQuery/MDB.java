package serv.DataQuery;

import com.asoluter.litest.Objects.AuthObject;
import com.asoluter.litest.Objects.DataBase;
import serv.Tests.Tests;
import serv.Utils.DataConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

public class MDB {
    private static final String authSQL="SELECT mail,login,pass FROM users WHERE (mail=?) OR (login=?)";
    private static final String takeContests="SELECT contests.cont_id, contests.name,contests.ending " +
            "FROM contests WHERE contests.available";
    private static final String findTests="DROP TABLE IF EXISTS available_contests;\n" +
            "CREATE TABLE available_contests AS (SELECT contests.cont_id FROM contests WHERE contests.available);\n" +
            "DROP TABLE IF EXISTS available_tests;\n" +
            "CREATE TABLE available_tests AS (SELECT tests.test_id, tests.cont_id, tests.test_name, tests.quest \n" +
            "                                 FROM tests,available_contests WHERE tests.cont_id IN (available_contests.cont_id));\n";
    public static final String takeTests="SELECT * FROM available_tests;\n";
    private static final String findAnswers="DROP TABLE IF EXISTS available_answers;\n" +
            "CREATE TABLE available_answers AS (SELECT answers.ans_id, answers.test_id, answers.ans_text \n" +
            "                                   FROM answers,available_tests WHERE answers.test_id IN (available_tests.test_id));\n";
    public static final String takeAnswers="SELECT * FROM available_answers;";

    static DataBase data;

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
        data=new DataBase();
        Connection connection=DataConnection.getConnecion();
        if (connection!=null){
            try {
                PreparedStatement prepareContests=connection.prepareStatement(takeContests);
                resultSet=prepareContests.executeQuery();
                makeContests(resultSet);

                
                PreparedStatement prepareTests=connection.prepareStatement(findTests);
                prepareTests.execute();
                prepareTests=connection.prepareStatement(takeTests);
                resultSet=prepareTests.executeQuery();
                makeTests(resultSet);

                PreparedStatement prepareAnswers=connection.prepareStatement(findAnswers);
                prepareAnswers.execute();
                prepareAnswers=connection.prepareStatement(takeAnswers);
                resultSet=prepareAnswers.executeQuery();
                makeAnsvers(resultSet);

                Tests.setDataBase(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static synchronized void makeContests(ResultSet resultSet){
        ArrayList<Integer> cont_cont_id=new ArrayList<Integer>();
        ArrayList<String> cont_name=new ArrayList<String >();
        ArrayList<String> cont_ending=new ArrayList<String>();
        try {
            while (resultSet.next()){
                cont_cont_id.add(resultSet.getInt(1));
                cont_name.add(resultSet.getString(2));
                cont_ending.add(String.valueOf(resultSet.getDate(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        data.setCont_cont_id(cont_cont_id);
        data.setCont_name(cont_name);
        data.setCont_ending(cont_ending);
    }

    private static synchronized void makeTests(ResultSet resultSet){
        ArrayList<Integer> test_test_id=new ArrayList<Integer>();
        ArrayList<Integer> test_cont_id=new ArrayList<Integer>();
        ArrayList<String> test_name=new ArrayList<String>();
        ArrayList<String> test_quest=new ArrayList<String>();

        try {
            while (resultSet.next()){
                test_test_id.add(resultSet.getInt(1));
                test_cont_id.add(resultSet.getInt(2));
                test_name.add(resultSet.getString(3));
                test_quest.add(resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        data.setTest_test_id(test_test_id);
        data.setTest_cont_id(test_cont_id);
        data.setTest_name(test_name);
        data.setTest_quest(test_quest);
    }

    private static synchronized void makeAnsvers(ResultSet resultSet){
        ArrayList<Integer> ans_id=new ArrayList<Integer>();
        ArrayList<Integer> ans_test_id=new ArrayList<Integer>();
        ArrayList<String> ans_text=new ArrayList<String>();

        try {
            while (resultSet.next()){
                ans_id.add(resultSet.getInt(1));
                ans_test_id.add(resultSet.getInt(2));
                ans_text.add(resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        data.setAns_id(ans_id);
        data.setAns_test_id(ans_test_id);
        data.setAns_text(ans_text);
    }

    public static synchronized void getTest(){
        
    }


}
