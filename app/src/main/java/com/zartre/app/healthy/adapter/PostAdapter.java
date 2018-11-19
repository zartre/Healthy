package com.zartre.app.healthy.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zartre.app.healthy.CommentFragment;
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
    public PostViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int i) {
        final View _postItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post_card, parent, false);
        final PostViewHolder postViewHolder = new PostViewHolder(_postItem);
        _postItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PostAdapter", "onClick: " + posts.get(i).getId());
                final Fragment fragment = new CommentFragment();
                final Bundle bundle = new Bundle();
                bundle.putInt("postId", posts.get(i).getId());
                fragment.setArguments(bundle);

                FragmentManager fm = ((AppCompatActivity) parent.getContext()).getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.main_view, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
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
