package com.zartre.app.healthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SleepDB {
    private SQLiteDatabase db;
    private SleepDBHelper sleepDBHelper;

    public SleepDB(Context context) {
        sleepDBHelper = new SleepDBHelper(context);
        db = sleepDBHelper.getWritableDatabase();
    }

    public long createRecord(String date, String sleepStart, String sleepEnd) {
        ContentValues values = new ContentValues();
        values.put(sleepDBHelper.COL_DATE, date);
        values.put(sleepDBHelper.COL_SLEEP_START, sleepStart);
        values.put(sleepDBHelper.COL_SLEEP_END, sleepEnd);
        return db.insert(sleepDBHelper.TABLE_NAME, null, values);
    }
}
