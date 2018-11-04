package com.zartre.app.healthy;

public class SleepRecord {
    private String date;
    private String sleepStart;
    private String sleepEnd;

    public String getDate() {
        return date;
    }

    public String getSleepStart() {
        return sleepStart;
    }

    public String getSleepEnd() {
        return sleepEnd;
    }

    public SleepRecord(String date, String sleepStart, String sleepEnd) {
        this.date = date;
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
    }
}
