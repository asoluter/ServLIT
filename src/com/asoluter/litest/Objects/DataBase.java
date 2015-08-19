package com.asoluter.litest.Objects;

import java.io.Serializable;
import java.sql.ResultSet;

public class DataBase implements Serializable {
    private ResultSet contests;
    private ResultSet tests;

    public ResultSet getAnsvers() {
        return ansvers;
    }

    public void setAnsvers(ResultSet ansvers) {
        this.ansvers = ansvers;
    }

    public ResultSet getTests() {
        return tests;
    }

    public void setTests(ResultSet tests) {
        this.tests = tests;
    }

    public ResultSet getContests() {
        return contests;
    }

    public void setContests(ResultSet contests) {
        this.contests = contests;
    }

    private ResultSet ansvers;
}
