package com.marco.babylonproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.marco.babylonproject.R;
import com.marco.babylonproject.contract.OnItemClickListener;
import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostHolder> {

    private final OnItemClickListener listener;
    List<Post> posts = new ArrayList<>();

    public PostsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<Post> data) {
        this.posts = data;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new PostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder viewHolder, int position) {
        Post item = posts.get(position);
        viewHolder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void onItemClickListener() {
    }

    public static class PostHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.post_avatar)
        ImageView avatar;
        @BindView(R.id.post_body)
        TextView body;
        @BindView(R.id.post_title)
        TextView title;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Post item, OnItemClickListener listener) {
            Glide.with(itemView)
                    .load(Repository.getAvatarUrl(item.getUserId().toString()))
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatar);
            body.setText(item.getBody());
            title.setText(item.getTitle());
            itemView.setOnClickListener(v -> {
                listener.onItemClick(item);
            });
        }
    }
}
