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
    public static final String PARAM_IN_ACTION = "action";
    public static final String PARAM_OUT_BODY = "body";

    private OkHttpClient okHttpClient = new OkHttpClient();

    public GetRestIntentService() {
        super("GetRestIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        final String url = intent.getStringExtra(PARAM_IN_URL);
        final String action = intent.getStringExtra(PARAM_IN_ACTION);
        Log.d(TAG, "onHandleIntent: " + url);
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body() != null ? response.body().string() : "";
            Log.d(TAG, "onHandleIntent: succeed: " + action);

            Intent broadcastIntent = new Intent(action);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(PARAM_OUT_BODY, result);
            sendBroadcast(broadcastIntent);
        } catch (IOException e) {
            Log.d(TAG, "onHandleIntent: " + e.getLocalizedMessage());
        }
    }
}
