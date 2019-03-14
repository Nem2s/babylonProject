package com.marco.babylonproject.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.marco.babylonproject.model.primitives.Comment;
import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.model.primitives.User;
import com.marco.babylonproject.usecase.GetCommentsPerPostUseCase;
import com.marco.babylonproject.usecase.GetPostDetailUseCase;
import com.marco.babylonproject.usecase.GetPostsUseCase;
import com.marco.babylonproject.usecase.GetUserDetailUseCase;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class PostDetailsActivityViewModel extends BaseViewModel {

    GetPostDetailUseCase getPostDetailUseCase = new GetPostDetailUseCase();
    GetUserDetailUseCase getUserDetailUseCase = new GetUserDetailUseCase();
    GetCommentsPerPostUseCase getCommentsPerPostUseCase = new GetCommentsPerPostUseCase();
    private String postId;
    private String userId;

    public LiveData<String> observeErrorOnComments() {
        return getCommentsPerPostUseCase.errorListener();
    }

    public LiveData<String> observeErrorOnUser() {
        return getUserDetailUseCase.errorListener();
    }

    public LiveData<String> observeErrorOnPost() {
        return getPostDetailUseCase.errorListener();
    }

    public LiveData<Boolean> observeLoadingOnComments() {
        return getCommentsPerPostUseCase.loadingListener();
    }

    public LiveData<Boolean> observeLoadingOnUser() {
        return getUserDetailUseCase.loadingListener();
    }

    public LiveData<Boolean> observeLoadingOnPost() {
        return getPostDetailUseCase.loadingListener();
    }

    public LiveData<Post> observePost(String id) {
        this.postId = id;
        return getPostDetailUseCase.execute(id);
    }

    public LiveData<User> observeUser(String id) {
        this.userId = id;
        return getUserDetailUseCase.execute(id);
    }

    public LiveData<List<Comment>> observeComments(String id) {
        return getCommentsPerPostUseCase.execute(id);
    }

    @Override
    public void onRefreshPulled() {
        observeComments(postId);
        observePost(postId);
        observeUser(userId);
    }
}
