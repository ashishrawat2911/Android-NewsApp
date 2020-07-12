package dev.ashish.newsapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class NewsViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String mParam;

    public NewsViewModelFactory(Application application, String taskId) {
        mApplication = application;
        mParam = taskId;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MyViewModel( mApplication,mParam);
    }
}
