package com.zartre.app.healthy.task;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

public class GetRestIntentService extends IntentService {
    private static final String TAG = "GetRestIntentService";
    public GetRestIntentService() {
        super("GetRestIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        SystemClock.sleep(5000);
        Log.d(TAG, "onHandleIntent: done");
    }
}
