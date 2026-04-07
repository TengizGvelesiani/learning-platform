package com.tengo.learningplatform.interactions;

import com.tengo.learningplatform.contracts.Statused;


public abstract class Record implements Statused {

    protected String status;

    protected Record(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract String getRecordType();
}
