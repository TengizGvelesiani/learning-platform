package com.tengo.learningplatform.concurrency;

public class DirectConnectionThread extends Thread {

    private final Runnable delegate;

    public DirectConnectionThread(ConnectionPool pool, String workerName, long holdMillis) {
        super("direct-" + workerName);
        this.delegate = new ConnectionWorker(pool, workerName, holdMillis);
    }

    @Override
    public void run() {
        delegate.run();
    }
}
