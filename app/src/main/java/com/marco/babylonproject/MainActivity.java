package com.marco.babylonproject;

import android.animation.Animator;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.marco.babylonproject.adapter.PostsAdapter;
import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.repository.Repository;
import com.marco.babylonproject.usecase.GetPostsUseCase;
import com.marco.babylonproject.utility.AnimHelper;
import com.marco.babylonproject.viewmodel.MainActivityViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_info)
    TextView infoBanner;
    @BindView(R.id.rv_posts)
    RecyclerView recyclerView;

    MainActivityViewModel viewModel;
    PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecyclerView();
        observeData();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostsAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observeData() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.observePosts().execute(null).observe(this, posts -> {
            if(posts!= null && !posts.isEmpty()) {
                adapter.setData(posts);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.observError().observe(this, text -> {
            if (text != null && !text.isEmpty()) {
                AnimHelper.animateInfoBanner(infoBanner);
            }
        });
    }


}
