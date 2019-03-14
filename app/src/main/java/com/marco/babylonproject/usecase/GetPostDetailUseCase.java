package com.marco.babylonproject.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.repository.Repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPostDetailUseCase extends UseCase<Post> {


    @Override
    public LiveData<Post> execute(@Nullable String id) {
        postsApi.getPostById(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                loadingListener().postValue(false);
                if (!response.isSuccessful()) {
                    errorListener().postValue("No post info found");
                    return;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                loadingListener().postValue(false);
                errorListener().postValue("Network Error");
            }
        });
        return data;
    }
}
