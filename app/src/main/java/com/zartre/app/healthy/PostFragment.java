package com.zartre.app.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;
import okhttp3.OkHttpClient;

public class PostFragment extends Fragment {
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
}
