package com.dehaat.dehaatassignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.repository.AppRepository;

import java.util.List;

public class AuthorViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Author>> authorList;

    public AuthorViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
        authorList = repository.getAuthorList();
    }

    public LiveData<List<Author>> getAuthorList() {
        return authorList;
    }

    public void clearDatabase() {
        repository.clearDatabase();
    }

    public static class Factory extends ViewModelProvider.AndroidViewModelFactory {

        @NonNull
        private final Application mApplication;

        /**
         * Creates a {@code AndroidViewModelFactory}
         *
         * @param application an application to pass in {@link AndroidViewModel}
         */
        public Factory(@NonNull Application application) {
            super(application);
            mApplication = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new AuthorViewModel(mApplication);
        }
    }
}
