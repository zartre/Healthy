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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;
import com.zartre.app.healthy.task.GetRestIntentService;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentFragment extends Fragment {
    public static final String TAG = "CommentFragment";
    public static final String ACTION_POST_FETCHED = "com.zartre.app.intent.POST_FETCHED";
    private final String POST_URL = "https://jsonplaceholder.typicode.com/posts";
    private int postId;

    private final Handler handler = new Handler();
    private BroadcastReceiver postReceiver;

    private TextView _postTitle, _postBody, _postAuthor, _postEmail;
    private Toolbar _toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postId = getArguments().getInt("postId");

        final Intent fetchPostIntent = new Intent(getActivity(), GetRestIntentService.class);
        fetchPostIntent.putExtra(GetRestIntentService.PARAM_IN_ACTION, ACTION_POST_FETCHED);
        fetchPostIntent.putExtra(GetRestIntentService.PARAM_IN_URL, POST_URL + "/" + postId);
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

        _postTitle = getView().findViewById(R.id.post_comments_post_title);
        _postBody = getView().findViewById(R.id.post_comments_post_body);
    }

    @Override
    public void onResume() {
        super.onResume();

        final IntentFilter POST_FILTER = new IntentFilter();
        POST_FILTER.addAction(ACTION_POST_FETCHED);
        POST_FILTER.addCategory(Intent.CATEGORY_DEFAULT);
        postReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: post fetched");
                final String RESULT = intent.getStringExtra(GetRestIntentService.PARAM_OUT_BODY);
                onReceivePost(RESULT);
            }
        };
        getActivity().registerReceiver(postReceiver, POST_FILTER);
    }

    private void onReceivePost(String JsonResult) {
        try {
            final JSONObject POST = new JSONObject(JsonResult);
            final int POST_ID = POST.getInt("id");
            final String POST_TITLE = POST.getString("title");
            final String POST_BODY = POST.getString("body");
            final String TITLE_FORMAT = getString(R.string.post_card_title);
            _postTitle.setText(String.format(TITLE_FORMAT, POST_ID, POST_TITLE));
            _postBody.setText(POST_BODY);
        } catch (JSONException e) {
            Log.d(TAG, "onReceivePost: " + e.getLocalizedMessage());
        }
    }
}
