package com.zartre.app.healthy.task;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import com.zartre.app.healthy.PostFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GetRestIntentService extends IntentService {
    public static final String TAG = "GetRestIntentService";
    public static final String PARAM_IN_URL = "url";

    private OkHttpClient okHttpClient = new OkHttpClient();

    public GetRestIntentService() {
        super("GetRestIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        final String url = intent.getStringExtra(PARAM_IN_URL);
        Log.d(TAG, "onHandleIntent: " + url);
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "onHandleIntent: succeed");
            PostFragment.onReceiveResult(result);
        } catch (IOException e) {
            Log.d(TAG, "onHandleIntent: " + e.getLocalizedMessage());
        }
    }
}
