package com.tengo.learningplatform.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {

    private static volatile ConnectionPool instance;
    private final BlockingQueue<MockConnection> availableConnections;

    private ConnectionPool(int connectionCount) {
        if (connectionCount <= 0) {
            throw new IllegalArgumentException("Connection count must be positive");
        }
        this.availableConnections = new ArrayBlockingQueue<>(connectionCount);
        for (int i = 1; i <= connectionCount; i++) {
            availableConnections.add(new MockConnection(i));
        }
    }

    public static ConnectionPool getInstance(int connectionCount) {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool(connectionCount);
                }
            }
        }
        return instance;
    }

    public MockConnection getConnection() throws InterruptedException {
        return availableConnections.take();
    }

    public void releaseConnection(MockConnection connection) throws InterruptedException {
        if (connection == null) {
            return;
        }
        availableConnections.put(connection);
    }
}
