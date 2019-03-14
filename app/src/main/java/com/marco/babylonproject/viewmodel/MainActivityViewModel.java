package com.marco.babylonproject.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.usecase.GetPostsUseCase;

import java.util.List;

public class MainActivityViewModel extends BaseViewModel<List<Post>> {
    private GetPostsUseCase useCase = new GetPostsUseCase();

    public LiveData<List<Post>> observePosts() {
        return useCase.execute(null);
    }

    public LiveData<String> observeError() {
        return useCase.errorListener();
    }

    public LiveData<Boolean> observeLoading() {
        return useCase.loadingListener();
    }

    public void onRefreshPulled() {
        useCase.execute(null);
    }


    //For testing purpose
    public void setUseCase(GetPostsUseCase useCase) {
        this.useCase = useCase;
    }
}
