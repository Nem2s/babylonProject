package com.marco.babylonproject.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.repository.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPostsUseCase extends UseCase<List<Post>> {


    @NonNull
    @Override
    public LiveData<List<Post>> execute(@Nullable String id) {
        loadingListener().postValue(true);
        postsApi.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                loadingListener().postValue(false);
                if (!response.isSuccessful() || response.body() == null || response.body().isEmpty()) {
                    errorListener().setValue("No posts found");
                    return;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                loadingListener().postValue(false);
                errorListener().setValue("Network Error");
            }
        });
        return data;
    }
}
