package com.marco.babylonproject.usecase;

import android.support.annotation.Nullable;

public abstract class UseCase <Q extends UseCase.Request, R extends UseCase.Response> {

    Request request = null;
    useCaseCallback<R> callback = null;

    abstract void executeUseCase(@Nullable Request request);

    public interface Request {

    }

    public interface Response {

    }

    public interface useCaseCallback<T> {
        void onSuccess(T response);
        void onError(Throwable t);

    }
}

