package com.projectmvc.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public final class ConnectionManager {

    private final List<Connection> availableConnections;
    private final List<Connection> usedConnections;
    private static final byte INITIAL_POOL_SIZE = 5;
    private static final String URL = "jdbc:mysql://localhost:3306/SpotifyDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Snick123";

    public ConnectionManager() {
        availableConnections = new ArrayList<>(INITIAL_POOL_SIZE);
        usedConnections = new ArrayList<>();
        for (byte i = 0; i < INITIAL_POOL_SIZE; i++) {
            availableConnections.add(createConnection());
        }
    }

    public Connection borrowConnection() {
        if (availableConnections.isEmpty()) {
            throw new RuntimeException("No available connections");
        }
        final Connection connection = availableConnections.remove(availableConnections.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public void returnConnection(final Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            availableConnections.add(connection);
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException("Error creating connection", e);
        }
    }

    public int getAvailableConnectionCount() {
        return availableConnections.size();
    }
}
