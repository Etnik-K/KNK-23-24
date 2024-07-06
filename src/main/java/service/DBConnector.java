package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static String URL = "jdbc:mysql://localhost:3306/knk2024";

    private static String USER = "root";

    private static String PASSWORD = "etnik";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
//                e.printStackTrace();
                System.err.println("SQL Excepption...");
                System.err.println("SQL State " + e.getSQLState() + " - " + e.getMessage());
            }
        }
    }
}