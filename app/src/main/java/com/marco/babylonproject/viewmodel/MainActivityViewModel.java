package com.marco.babylonproject.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.usecase.GetPostsUseCase;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> isError = new MutableLiveData<>();
    private GetPostsUseCase getPosts = new GetPostsUseCase();

    public void onPostClicked(Post post) {

    }

    public void bound() {
    }
}
