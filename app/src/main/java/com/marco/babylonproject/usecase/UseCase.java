package com.marco.babylonproject.usecase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

public abstract class UseCase <Request, Response > {

    public abstract LiveData<Response> execute(@Nullable Request request);
}

