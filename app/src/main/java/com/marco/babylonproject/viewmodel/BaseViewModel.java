package com.marco.babylonproject.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.marco.babylonproject.usecase.UseCase;

public abstract class BaseViewModel<T> extends ViewModel {

    public abstract void onRefreshPulled();
}
