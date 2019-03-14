package com.marco.babylonproject.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.marco.babylonproject.model.primitives.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserDetailUseCase extends UseCase<User> {
    @Override
    public LiveData<User> execute(@Nullable String id) {
        usersApi.getUserById(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                loadingListener().postValue(false);
                if (!response.isSuccessful()) {
                    errorListener().postValue("No user found");
                    return;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                loadingListener().postValue(false);
                errorListener().postValue("Network Error");
            }
        });
        return data;
    }
}
