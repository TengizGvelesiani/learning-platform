package com.tengo.learningplatform.interactions;

import java.time.LocalDateTime;


public abstract class Transaction {

    protected boolean processed;
    protected LocalDateTime processedAt;

    protected Transaction() {
        this.processed = false;
        this.processedAt = null;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public abstract String getTransactionType();
}
