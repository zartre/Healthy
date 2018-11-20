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
import com.zartre.app.healthy.adapter.CommentAdapter;
import com.zartre.app.healthy.data.Comment;
import com.zartre.app.healthy.task.GetRestIntentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {
    public static final String TAG = "CommentFragment";
    public static final String ACTION_COMMENTS_FETCHED = "com.zartre.app.intent.COMMENTS_FETCHED";

    private final String POST_URL = "https://jsonplaceholder.typicode.com/posts";
    private final String POST_COMMENTS_PATH = "/comments";
    private int postId;

    private final Handler handler = new Handler();
    private BroadcastReceiver commentsReceiver;
    private static final List<Comment> COMMENTS = new ArrayList<>();

    private Toolbar _toolbar;
    private RecyclerView _commentsRecyclerView;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postId = getArguments().getInt("postId");
        Log.d(TAG, "onCreate: postId = " + postId);

        final String POST_COMMENTS_URL = POST_URL + "/" + postId + POST_COMMENTS_PATH;
        Log.d(TAG, "onCreate: constructed URL " + POST_COMMENTS_URL);

        final Intent fetchPostIntent = new Intent(getActivity(), GetRestIntentService.class);
        fetchPostIntent.putExtra(GetRestIntentService.PARAM_IN_ACTION, ACTION_COMMENTS_FETCHED);
        fetchPostIntent.putExtra(GetRestIntentService.PARAM_IN_URL, POST_COMMENTS_URL);
        getActivity().startService(fetchPostIntent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_comments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        COMMENTS.clear();

        _toolbar = getView().findViewById(R.id.post_comments_toolbar);
        _commentsRecyclerView = getView().findViewById(R.id.post_comments_list);

        createToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();

        final IntentFilter POST_FILTER = new IntentFilter();
        POST_FILTER.addAction(ACTION_COMMENTS_FETCHED);
        POST_FILTER.addCategory(Intent.CATEGORY_DEFAULT);
        commentsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: post fetched");
                final String RESULT = intent.getStringExtra(GetRestIntentService.PARAM_OUT_BODY);
                onReceivePost(RESULT);
            }
        };
        getActivity().registerReceiver(commentsReceiver, POST_FILTER);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(commentsReceiver);
    }

    private void onReceivePost(String JsonResult) {
        try {
            // convert JSON string to JSON array
            final JSONArray JSON_ARRAY = new JSONArray(JsonResult);
            final int ARR_LENGTH = JSON_ARRAY.length();
            // convert each array item to Comment object
            for (int i = 0; i < ARR_LENGTH; i++) {
                final JSONObject COMMENT_JSON_OBJ = JSON_ARRAY.getJSONObject(i);
                final Comment COMMENT = new Comment(
                        COMMENT_JSON_OBJ.getInt("id"),
                        COMMENT_JSON_OBJ.getInt("postId"),
                        COMMENT_JSON_OBJ.getString("name"),
                        COMMENT_JSON_OBJ.getString("email"),
                        COMMENT_JSON_OBJ.getString("body")
                );
                COMMENTS.add(COMMENT);
                handler.post(updateViewRunnable);
            }
        } catch (JSONException je) {
            Log.d(TAG, "onReceivePost: " + je.getLocalizedMessage());
        } catch (Exception e) {
            Log.d(TAG, "onReceivePost: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void createToolbar() {
        _toolbar.setTitle("Comments");
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void updateView() {
        try {
            recyclerLayoutManager = new LinearLayoutManager(getContext());
            _commentsRecyclerView.setLayoutManager(recyclerLayoutManager);
            recyclerAdapter = new CommentAdapter(COMMENTS);
            _commentsRecyclerView.setAdapter(recyclerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Runnable updateViewRunnable = new Runnable() {
        @Override
        public void run() {
            updateView();
        }
    };
}
