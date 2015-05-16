package com.asoluter.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    protected String token="";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter=resp.getWriter();

        String login=req.getParameter("uname");
        String password=req.getParameter("pass");

        if(isLogin(login,password)){
            printWriter.write(token);
        }
        else {

            printWriter.write(110);
        }

        printWriter.close();
    }

    protected boolean isLogin(String login,String password){

        return false;
    }
}
