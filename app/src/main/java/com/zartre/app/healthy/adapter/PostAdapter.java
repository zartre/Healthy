package com.zartre.app.healthy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zartre.app.healthy.R;
import com.zartre.app.healthy.data.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View _postItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post_card, parent, false);
        final PostViewHolder postViewHolder = new PostViewHolder(_postItem);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        final String TITLE_FORMAT = "%d : %s";
        final Post POST = posts.get(i);
        postViewHolder._title.setText(String.format(TITLE_FORMAT, POST.getId(), POST.getTitle()));
        postViewHolder._body.setText(POST.getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView _title, _body;
        public PostViewHolder(View v) {
            super(v);
            _title = v.findViewById(R.id.post_card_title);
            _body = v.findViewById(R.id.post_card_content);
        }
    }
}
