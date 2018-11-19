package com.zartre.app.healthy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import com.zartre.app.healthy.data.Post;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private List<Post> posts;
    private Context context;

    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        this.context = context;
        this.posts = objects;
    }
}
