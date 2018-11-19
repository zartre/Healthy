package com.zartre.app.healthy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.zartre.app.healthy.R;
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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View _postItem = LayoutInflater.from(context).inflate(R.layout.fragment_post_card, parent, false);
        final TextView _title = _postItem.findViewById(R.id.post_card_title);
        final TextView _body = _postItem.findViewById(R.id.post_card_content);
        final Post post = posts.get(position);
        final String titleFormat = context.getString(R.string.post_card_title);
        _title.setText(String.format(titleFormat, post.getId(), post.getTitle()));
        _body.setText(post.getBody());
        return _postItem;
    }
}
