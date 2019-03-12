package com.marco.babylonproject.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.repository.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPostsUseCase extends UseCase<String, List<Post>> {

    private MutableLiveData<List<Post>> posts = new MutableLiveData<>();

    @Override
    public LiveData<List<Post>> execute(@Nullable String s) {
        Repository.postsApi api = Repository.getRetrofitInstance().create(Repository.postsApi.class);
        api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
        return posts;
    }
}
