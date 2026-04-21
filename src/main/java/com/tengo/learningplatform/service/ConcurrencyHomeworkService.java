package com.tengo.learningplatform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tengo.learningplatform.concurrency.ConnectionPool;
import com.tengo.learningplatform.concurrency.ConnectionWorker;
import com.tengo.learningplatform.concurrency.DirectConnectionThread;

public class ConcurrencyHomeworkService {

    private static final Logger LOGGER = LogManager.getLogger(ConcurrencyHomeworkService.class);

    public void runShowcase() {
        runTwoManualThreads();
        runConnectionPoolWithExecutor();
        runCompletableFutureExamples();
    }

    private void runTwoManualThreads() {
        LOGGER.info("\n--- Manual threads (Runnable + Thread) ---");
        ConnectionPool pool = ConnectionPool.getInstance(5);

        Thread runnableThread = new Thread(
                new ConnectionWorker(pool, "runnable-worker", 300),
                "runnable-worker");
        Thread extendedThread = new DirectConnectionThread(pool, "extended-thread-worker", 300);

        runnableThread.start();
        extendedThread.start();

        joinThread(runnableThread);
        joinThread(extendedThread);
    }

    private void runConnectionPoolWithExecutor() {
        LOGGER.info("\n--- Connection pool + ExecutorService(7) ---");
        ConnectionPool pool = ConnectionPool.getInstance(5);
        ExecutorService executor = Executors.newFixedThreadPool(7);
        List<Future<?>> futures = new ArrayList<>();

        try {
            for (int i = 1; i <= 7; i++) {
                String workerName = "executor-worker-" + i;
                futures.add(executor.submit(new ConnectionWorker(pool, workerName, 900)));
            }

            for (Future<?> future : futures) {
                future.get();
            }
            LOGGER.info("All executor workers finished. The app waited for every task.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Executor flow interrupted");
        } catch (ExecutionException e) {
            throw new IllegalStateException("Executor worker failed", e);
        } finally {
            executor.shutdown();
            awaitTermination(executor);
        }
    }

    private void runCompletableFutureExamples() {
        LOGGER.info("\n--- CompletableFuture demo (5+ tasks) ---");
        ExecutorService cfExecutor = Executors.newFixedThreadPool(3);

        try {
            CompletableFuture<String> future1 = CompletableFuture.supplyAsync(
                    () -> "future1-base", cfExecutor);

            CompletableFuture<Void> future2 = CompletableFuture.runAsync(
                    () -> LOGGER.info("future2 runAsync done"), cfExecutor);

            CompletionStage<String> future3 = future1.thenApply(value -> value + "-thenApply");

            CompletableFuture<Integer> future4 = CompletableFuture.supplyAsync(
                    () -> 21, cfExecutor).thenApply(number -> number * 2);

            CompletionStage<String> future5 = future3.thenCombine(
                    future4, (text, number) -> text + " | answer=" + number);

            CompletableFuture<String> future6 = CompletableFuture.completedFuture("future6-completed");

            CompletableFuture<Void> all = CompletableFuture.allOf(
                    future2,
                    future4,
                    future5.toCompletableFuture(),
                    future6);

            all.join();
            LOGGER.info("future3 result: {}", future3.toCompletableFuture().join());
            LOGGER.info("future4 result: {}", future4.join());
            LOGGER.info("future5 result: {}", future5.toCompletableFuture().join());
            LOGGER.info("future6 result: {}", future6.join());
        } finally {
            cfExecutor.shutdown();
            awaitTermination(cfExecutor);
        }
    }

    private void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Thread {} join interrupted", thread.getName());
        }
    }

    private void awaitTermination(ExecutorService executor) {
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}
