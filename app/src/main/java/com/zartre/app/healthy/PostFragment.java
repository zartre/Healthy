package com.zartre.app.healthy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;
import com.zartre.app.healthy.task.GetRestIntentService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class PostFragment extends Fragment {
    private static final String TAG = "PostFragment";
    private final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private OkHttpClient okHttpClient = new OkHttpClient();

    private Toolbar _toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _toolbar = getView().findViewById(R.id.posts_toolbar);

        createToolbar();

        final Intent fetchIntent = new Intent(getActivity(), GetRestIntentService.class);
        getActivity().startService(fetchIntent);
    }

    private void createToolbar() {
        _toolbar.setTitle("Posts");
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private String fetchPosts(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.d(TAG, "fetchPosts: " + e.getLocalizedMessage());
            return e.getLocalizedMessage();
        }
    }
}
