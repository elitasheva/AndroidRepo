package com.android.summerread.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.summerread.database.BookDatabase;
import com.android.summerread.repository.BookRepo;
import com.android.summerread.repository.Repo;

public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public CustomViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CustomViewModel.class)) {
            BookDatabase db = BookDatabase.getDatabase(application);
            Repo repo = new BookRepo(db.bookDao());
            return (T) new CustomViewModel(repo);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
