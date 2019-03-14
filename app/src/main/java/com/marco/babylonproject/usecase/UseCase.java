package com.marco.babylonproject.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.marco.babylonproject.repository.Repository;

public abstract class UseCase<Response> {

    Repository.postsApi postsApi = Repository.getRetrofitInstance().create(Repository.postsApi.class);
    Repository.usersApi usersApi = Repository.getRetrofitInstance().create(Repository.usersApi.class);
    Repository.commentsApi commentsApi = Repository.getRetrofitInstance().create(Repository.commentsApi.class);


    protected final MutableLiveData<Response> data = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> isError = new MutableLiveData<>();

    @NonNull
    public abstract LiveData<Response> execute(@Nullable String id);

    public MutableLiveData<Boolean> loadingListener() {
        return isLoading;
    }

    public MutableLiveData<String> errorListener() {
        return isError;
    }
}

