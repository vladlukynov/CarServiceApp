package com.example.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433;database=CarService;encrypt=true;trustServerCertificate=true;";
    private static final String userName = "carservice";
    private static final String password = "qwerty123";

    public static void TestConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, userName, password);
    }
}
