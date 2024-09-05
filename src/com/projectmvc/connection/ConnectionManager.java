package com.projectmvc.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class ConnectionManager {

    private static Connection connection;

    private ConnectionManager() {}

    public static Connection getConnection() {
        if (!ConnectionManager.class.equals(null)) {
            try {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SpotifyDB", "username", "password");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error connecting to the database", e);
            }
        }
        return connection;
    }
}
