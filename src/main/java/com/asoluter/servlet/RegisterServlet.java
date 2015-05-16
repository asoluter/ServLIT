package com.asoluter.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RegisterServlet extends HttpServlet {

    PrintWriter printWriter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        printWriter=resp.getWriter();

        String firstName =req.getParameter("fname");
        String secondName=req.getParameter("sname");
        String userName=req.getParameter("uname");
        String password=req.getParameter("pass");
        String mail=req.getParameter("mail");

        if(userName!=null&&password!=null&&mail!=null){
            Regiser(firstName, secondName, userName, password, mail);
        }
        else {
            printWriter.print("6");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    protected void Regiser(String firstName,String secondName,String userName,String password,String mail){
        try {
            Connection con=DataConnection.getConnecion();
            if (con != null) {

                if(!CheckAvailable(con,userName,mail))return;

                String sql= "INSERT INTO users (user_name, password, first_name, second_name, mail) VALUES (?,?,?,?,?);";
                PreparedStatement prs=con.prepareStatement(sql);
                prs.setString(1,userName);
                prs.setString(2,password);
                prs.setString(3,firstName);
                prs.setString(4,secondName);
                prs.setString(5,mail);
                prs.executeUpdate();
                printWriter.print("0");
                con.close();
            }
            else {
                printWriter.print("1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            printWriter.print("2");
        }

    }

    protected boolean CheckAvailable(Connection con, String uname,String mail){
        String sql="select user_name from users";
        try {
            PreparedStatement prs=con.prepareStatement(sql);
            ResultSet rs=prs.executeQuery();
            while(rs.next()){
                if(rs.getString("user_name").equals(uname))
                {
                    printWriter.print("3");
                    return false;
                }
            }

            if(mail!=null){
                String sqlm="select mail from users";
                PreparedStatement prsm=con.prepareStatement(sqlm);
                ResultSet rsm=prsm.executeQuery();
                while (rsm.next()){
                    if(rsm.getString("mail").equals(mail)){
                        printWriter.print("4");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
