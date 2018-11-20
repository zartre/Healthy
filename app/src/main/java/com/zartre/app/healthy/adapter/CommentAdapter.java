package com.zartre.app.healthy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zartre.app.healthy.R;
import com.zartre.app.healthy.data.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView _title, _body, _author, _email;
        public CommentViewHolder(View v) {
            super(v);
            _title = v.findViewById(R.id.comment_card_title);
            _body = v.findViewById(R.id.comment_card_body);
            _author = v.findViewById(R.id.comment_card_author);
            _email = v.findViewById(R.id.comment_card_email);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {
        final View _commentItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comment_card, parent, false);
        final CommentViewHolder commentViewHolder = new CommentViewHolder(_commentItem);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        final String TITLE_FORMAT = "%d : %d";
        final Comment COMMENT = comments.get(i);
        commentViewHolder._title.setText(String.format(TITLE_FORMAT, COMMENT.getPostId(), COMMENT.getId()));
        commentViewHolder._body.setText(COMMENT.getBody());
        commentViewHolder._author.setText(COMMENT.getName());
        commentViewHolder._email.setText(COMMENT.getEmail());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
