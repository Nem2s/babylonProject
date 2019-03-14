package com.marco.babylonproject.usecase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.marco.babylonproject.model.primitives.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCommentsPerPostUseCase extends UseCase<List<Comment>> {

    @Override
    public LiveData<List<Comment>> execute(@Nullable String id) {
        postsApi.getCommentsByPostId(id).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                loadingListener().postValue(false);
                if (!response.isSuccessful()) {
                    errorListener().postValue("No comments found");
                    return;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                loadingListener().postValue(false);
                errorListener().postValue("Network Error");
            }
        });
        return data;
    }
}
