package com.tengo.learningplatform.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionWorker implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionWorker.class);

    private final ConnectionPool pool;
    private final String workerName;
    private final long holdMillis;

    public ConnectionWorker(ConnectionPool pool, String workerName, long holdMillis) {
        this.pool = pool;
        this.workerName = workerName;
        this.holdMillis = holdMillis;
    }

    @Override
    public void run() {
        MockConnection connection = null;
        try {
            LOGGER.info("{} waiting for connection...", workerName);
            connection = pool.getConnection();
            LOGGER.info("{} got connection-{}", workerName, connection.getId());
            LOGGER.info(connection.create(workerName, "created-by-" + workerName));
            LOGGER.info("{} read key: {}", workerName, connection.get(workerName));
            Thread.sleep(holdMillis);
            LOGGER.info(connection.update(workerName, "updated-by-" + workerName));
            LOGGER.info(connection.delete(workerName));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warn("{} interrupted while waiting/working", workerName);
        } finally {
            if (connection != null) {
                try {
                    pool.releaseConnection(connection);
                    LOGGER.info("{} released connection-{}", workerName, connection.getId());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOGGER.warn("{} interrupted while releasing connection", workerName);
                }
            }
        }
    }
}
