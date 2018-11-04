package com.zartre.app.healthy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SleepDBHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "healthy.db";
    static final int DB_VERSION = 1;
    static final String TABLE_NAME = "sleep";
    static final String COL_ID = "id";
    static final String COL_DATE = "date";
    static final String COL_SLEEP_START = "sleep_start";
    static final String COL_SLEEP_END = "sleep_end";
    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_DATE + " TEXT NOT NULL, " +
            COL_SLEEP_START + " TEXT NOT NULL, " +
            COL_SLEEP_END + " TEXT NOT NULL);";

    public SleepDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
