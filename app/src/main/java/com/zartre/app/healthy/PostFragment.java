package com.zartre.app.healthy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;
import com.zartre.app.healthy.adapter.PostAdapter;
import com.zartre.app.healthy.data.Post;
import com.zartre.app.healthy.task.GetRestIntentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {
    public static final String TAG = "PostFragment";
    public static final String ACTION_POSTS_FETCHED = "com.zartre.app.intent.POSTS_FETCHED";
    private final String POSTS_URL = "https://jsonplaceholder.typicode.com/POSTS";

    private final Handler updateViewHandler = new Handler();
    private BroadcastReceiver receiver;
    private static final List<Post> POSTS = new ArrayList<>();

    private Toolbar _toolbar;
    private RecyclerView _postRecyclerView;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // fetch POSTS
        final Intent fetchIntent = new Intent(getActivity(), GetRestIntentService.class);
        fetchIntent.putExtra(GetRestIntentService.PARAM_IN_URL, POSTS_URL);
        fetchIntent.putExtra(GetRestIntentService.PARAM_IN_ACTION, ACTION_POSTS_FETCHED);
        getActivity().startService(fetchIntent);

        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        POSTS.clear();

        _toolbar = getView().findViewById(R.id.posts_toolbar);
        _postRecyclerView = getView().findViewById(R.id.posts_list);

        createToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        final IntentFilter FILTER = new IntentFilter();
        FILTER.addAction(ACTION_POSTS_FETCHED);
        FILTER.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: intent received");
                final String RESULT = intent.getStringExtra(GetRestIntentService.PARAM_OUT_BODY);
                onReceiveResult(RESULT);
            }
        };
        getActivity().registerReceiver(receiver, FILTER);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
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

    public void onReceiveResult(String JsonResult) {
        try {
            // convert JSON string to JSON array
            final JSONArray JSON_ARRAY = new JSONArray(JsonResult);
            final int ARR_LENGTH = JSON_ARRAY.length();
            // convert each array item to Post object
            // ARR_LENGTH - 1 because post id 101 on the server is incomplete
            for (int i = 0; i < ARR_LENGTH - 1; i++) {
                final JSONObject POST_JSON_OBJ = JSON_ARRAY.getJSONObject(i);
                final Post POST = new Post(
                        POST_JSON_OBJ.getInt("id"),
                        POST_JSON_OBJ.getString("title"),
                        POST_JSON_OBJ.getString("body")
                );
                POSTS.add(POST);
            }
            updateViewHandler.post(updateViewRunnable);
        } catch (JSONException e) {
            Log.d(TAG, "onReceiveResult: " + e.getLocalizedMessage());
        }
    }

    private void updateView() {
        try {
            recyclerLayoutManager = new LinearLayoutManager(getContext());
            _postRecyclerView.setLayoutManager(recyclerLayoutManager);
            recyclerAdapter = new PostAdapter(POSTS);
            _postRecyclerView.setAdapter(recyclerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Runnable updateViewRunnable = new Runnable() {
        @Override
        public void run() {
            // update the UI through a Runnable to prevent
            // ViewRootImpl$CalledFromWrongThreadException
            updateView();
        }
    };
}
