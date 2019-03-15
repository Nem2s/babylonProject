package com.marco.babylonproject.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.constraint.ConstraintLayout;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.marco.babylonproject.R;
import com.marco.babylonproject.repository.Repository;
import com.marco.babylonproject.utility.AnimHelper;
import com.marco.babylonproject.utility.Constants;
import com.marco.babylonproject.viewmodel.PostDetailsActivityViewModel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailsActivity extends AppCompatActivity {

    @BindView(R.id.user_avatar)
    ImageView avatar;
    @BindView(R.id.user_name)
    TextView username;
    @BindView(R.id.post_title)
    TextView title;
    @BindView(R.id.post_body)
    TextView body;
    @BindView(R.id.progress_circular)
    ProgressBar progressBar;
    @BindView(R.id.post_comments)
    TextView commentsNumber;
    @BindView(R.id.tv_info)
    TextView info;
    @BindView(R.id.parent)
    ConstraintLayout parent;

    AtomicInteger syncLoading = new AtomicInteger(2);
    PostDetailsActivityViewModel viewModel;
    String userId, postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra(Constants.USER_IDENTIFIER);
        postId = getIntent().getStringExtra(Constants.POST_IDENTIFIER);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        observeData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void observeData() {
        viewModel = ViewModelProviders.of(this).get(PostDetailsActivityViewModel.class);
        viewModel.observeUser(userId).observe(this, user -> {
            if (user != null) {
                Glide.with(this)
                        .load(Repository.getAvatarUrl(user.getId().toString()))
                        .apply(RequestOptions.circleCropTransform())
                        .into(avatar);
                username.setText(user.getName());
            }
        });
        viewModel.observePost(postId).observe(this, post -> {
            if (post != null) {
                title.setText(post.getTitle());
                body.setText(post.getBody());
            }
        });
        viewModel.observeComments(postId).observe(this, comments -> {
            if (comments != null) {
                String number = String.valueOf(comments.size());
                commentsNumber.setText(number.concat(" comments."));
            }
        });
        viewModel.observeErrorOnComments().observe(this, error -> {
            if (error != null) {
                info.setText(error);
                AnimHelper.animateInfoBanner(info);
                hideView();
            }
        });
        viewModel.observeErrorOnUser().observe(this, error -> {
            if (error != null) {
                info.setText(error);
                AnimHelper.animateInfoBanner(info);
                hideView();
            }
        });
        viewModel.observeErrorOnPost().observe(this, error -> {
            if (error != null) {
                info.setText(error);
                AnimHelper.animateInfoBanner(info);
                hideView();
            }
        });
        viewModel.observeLoadingOnComments().observe(this, finish -> {
            if (syncLoading.get() == 0) {
                showView();
            } else {
                syncLoading.decrementAndGet();
            }
        });
        viewModel.observeLoadingOnPost().observe(this, isLoading -> {
            if (syncLoading.get() == 0) {
                showView();
            } else {
                syncLoading.decrementAndGet();
            }
        });
        viewModel.observeLoadingOnUser().observe(this, isLoading -> {
            if (syncLoading.get() == 0) {
                showView();
            } else {
                syncLoading.decrementAndGet();
            }
        });
    }

    private void showView() {
        progressBar.setVisibility(View.GONE);
        syncLoading.set(2);
        parent.setVisibility(View.VISIBLE);
    }

    private void hideView() {
        parent.setVisibility(View.GONE);
    }
}
