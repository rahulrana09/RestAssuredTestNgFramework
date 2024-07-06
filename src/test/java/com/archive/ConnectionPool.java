package com.archive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private String url;
    private String user;
    private String password;
    private List<Connection> pool;
    private int poolSize;

    public ConnectionPool(String url, String user, String password, int poolSize) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.poolSize = poolSize;
        this.pool = new ArrayList<>(poolSize);
        initializePool();
    }

    private void initializePool() {
        try {
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                pool.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        while (pool.isEmpty()) {
            try {
                wait(); // Wait until a connection is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new SQLException("Interrupted while waiting for a connection");
            }
        }
        return pool.remove(pool.size() - 1);
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            pool.add(connection);
            notify(); // Notify waiting threads that a connection is available
        }
    }

    public void closeAllConnections() throws SQLException {
        for (Connection connection : pool) {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }



}



