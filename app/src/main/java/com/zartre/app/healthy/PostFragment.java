package com.zartre.app.healthy;

import android.content.Context;
import android.content.Intent;
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
    private static final String TAG = "PostFragment";
    private final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static Context context;
    private static final Handler updateViewHandler = new Handler();

    private static List<Post> posts = new ArrayList<>();

    private Toolbar _toolbar;
    private static RecyclerView _postRecyclerView;
    private static RecyclerView.LayoutManager recyclerLayoutManager;
    private static RecyclerView.Adapter recyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fetch posts
        final Intent fetchIntent = new Intent(getActivity(), GetRestIntentService.class);
        fetchIntent.putExtra(GetRestIntentService.PARAM_IN_URL, POSTS_URL);
        getActivity().startService(fetchIntent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _toolbar = getView().findViewById(R.id.posts_toolbar);
        _postRecyclerView = getView().findViewById(R.id.posts_list);

        createToolbar();

        context = getContext();
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

    public static void onReceiveResult(String JsonResult) {
        try {
            // convert JSON string to JSON array
            final JSONArray JSON_ARRAY = new JSONArray(JsonResult);
            final int ARR_LENGTH = JSON_ARRAY.length();
            // convert each array item to Post object
            for (int i = 0; i < ARR_LENGTH; i++) {
                final JSONObject POST_JSON_OBJ = JSON_ARRAY.getJSONObject(i);
                final Post POST = new Post(
                        POST_JSON_OBJ.getInt("id"),
                        POST_JSON_OBJ.getString("title"),
                        POST_JSON_OBJ.getString("body")
                );
                posts.add(POST);
            }
            updateViewHandler.post(updateViewRunnable);
        } catch (JSONException e) {
            Log.d(TAG, "onReceiveResult: " + e.getLocalizedMessage());
        }
    }

    private static void updateView() {
        try {
            recyclerLayoutManager = new LinearLayoutManager(context);
            _postRecyclerView.setLayoutManager(recyclerLayoutManager);
            recyclerAdapter = new PostAdapter(posts);
            _postRecyclerView.setAdapter(recyclerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Runnable updateViewRunnable = new Runnable() {
        @Override
        public void run() {
            // update the UI through a Runnable to prevent
            // ViewRootImpl$CalledFromWrongThreadException
            updateView();
        }
    };
}
