package serv.Tests;

import com.asoluter.litest.Objects.DataBase;

public class Tests {
    private static DataBase dataBase;

    public static DataBase getDataBase() {
        return dataBase;
    }

    public static void setDataBase(DataBase dataBase) {
        Tests.dataBase = dataBase;
    }
}
