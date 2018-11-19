package com.zartre.app.healthy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;
import com.zartre.app.healthy.data.Post;
import com.zartre.app.healthy.task.GetRestIntentService;
import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {
    private static final String TAG = "PostFragment";
    private final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";

    private OkHttpClient okHttpClient = new OkHttpClient();

    private static List<Post> posts = new ArrayList<>();

    private Toolbar _toolbar;
    private RecyclerView _postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _toolbar = getView().findViewById(R.id.posts_toolbar);
        _postList = getView().findViewById(R.id.posts_list);

        createToolbar();

        // fetch posts
        final Intent fetchIntent = new Intent(getActivity(), GetRestIntentService.class);
        fetchIntent.putExtra(GetRestIntentService.PARAM_IN_URL, POSTS_URL);
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

    public static void onReceiveResult(String JsonResult) {
        try {
            final JSONArray jsonArray = new JSONArray(JsonResult);
            final int ARR_LENGTH = jsonArray.length();
            for (int i = 0; i < ARR_LENGTH; i++) {
                final JSONObject postJsonObject = jsonArray.getJSONObject(i);
                Post post = new Post(
                        postJsonObject.getInt("id"),
                        postJsonObject.getString("title"),
                        postJsonObject.getString("body")
                );
                posts.add(post);
            }
        } catch (JSONException e) {
            Log.d(TAG, "onReceiveResult: " + e.getLocalizedMessage());
        }
    }

    private void updateView() {}
}
