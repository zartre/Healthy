package com.zartre.app.healthy;

public class SleepRecord {
    private int rowId;
    private String date;
    private String sleepStart;
    private String sleepEnd;

    public int getRowId() { return rowId; }

    public String getDate() {
        return date;
    }

    public String getSleepStart() {
        return sleepStart;
    }

    public String getSleepEnd() {
        return sleepEnd;
    }

    public SleepRecord(int pos, String date, String sleepStart, String sleepEnd) {
        this.rowId = pos;
        this.date = date;
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
    }
}
