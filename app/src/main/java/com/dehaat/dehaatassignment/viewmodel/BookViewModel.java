package com.dehaat.dehaatassignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dehaat.dehaatassignment.model.Book;
import com.dehaat.dehaatassignment.repository.AppRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Book>> bookList;

    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public LiveData<List<Book>> getBookList(String authorName) {
        return repository.getBookList(authorName);
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
            return (T) new BookViewModel(mApplication);
        }
    }

}
