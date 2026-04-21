package com.tengo.learningplatform.concurrency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MockConnection {

    private final int id;
    private final Map<String, String> fakeStorage = new ConcurrentHashMap<>();

    public MockConnection(int id) {
        this.id = id;
    }

    public String create(String key, String value) {
        fakeStorage.put(key, value);
        return "connection-" + id + " created " + key;
    }

    public String get(String key) {
        return fakeStorage.getOrDefault(key, "not-found");
    }

    public String update(String key, String value) {
        fakeStorage.put(key, value);
        return "connection-" + id + " updated " + key;
    }

    public String delete(String key) {
        String removed = fakeStorage.remove(key);
        return removed == null ? "connection-" + id + " nothing-to-delete"
                : "connection-" + id + " deleted " + key;
    }

    public int getId() {
        return id;
    }
}
