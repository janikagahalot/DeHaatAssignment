package com.dehaat.dehaatassignment.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dehaat.dehaatassignment.dao.AuthorDao;
import com.dehaat.dehaatassignment.dao.BookDao;
import com.dehaat.dehaatassignment.database.AppDatabase;
import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.model.AuthorBookDetailResponse;
import com.dehaat.dehaatassignment.model.AuthorsResponse;
import com.dehaat.dehaatassignment.rest.AppRestClient;
import com.dehaat.dehaatassignment.rest.AppRestClientService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {


    private AuthorDao authorDao;
    private BookDao bookDao;
    private AppRestClientService appRestClientService;
    private MediatorLiveData<List<Author>> resultAuthorList = new MediatorLiveData<>();


    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        appRestClientService = AppRestClient.getInstance().getAppRestClientService();
        authorDao = db.authorDao();
        fetchAuthors();
    }

    private LiveData<List<Author>> loadDataFromDb() {
        return authorDao.getAuthorList();

    }

    public void fetchAuthors() {
        final LiveData<List<Author>> dbSource = loadDataFromDb();
        resultAuthorList.addSource(dbSource, new Observer<List<Author>>() {
            @Override
            public void onChanged(List<Author> authorList) {
                resultAuthorList.removeSource(dbSource);
                if (authorList == null || authorList.size() == 0) {
                    fetchDataFromServer();
                } else {
                    resultAuthorList.addSource(dbSource, new Observer<List<Author>>() {
                        @Override
                        public void onChanged(List<Author> authorList) {
                            resultAuthorList.setValue(authorList);
                        }
                    });
                }
            }
        });
    }

    private void fetchDataFromServer() {
        Call<AuthorsResponse> call1 = appRestClientService.getListOfAuthors();
        call1.enqueue(new Callback<AuthorsResponse>() {
            @Override
            public void onResponse(Call<AuthorsResponse> call, Response<AuthorsResponse> response) {
                AuthorsResponse authorsResponse = response.body();
                if (authorsResponse == null) return;
                processFetchedData(authorsResponse);
            }

            @Override
            public void onFailure(Call<AuthorsResponse> call, Throwable t) {

            }
        });
    }

    private void processFetchedData(AuthorsResponse authorsResponse) {
        List<AuthorBookDetailResponse> dataList = authorsResponse.data;
        if (dataList != null && dataList.size() > 0) {
            saveDataInDb(dataList);

            resultAuthorList.addSource(loadDataFromDb(), new Observer<List<Author>>() {
                @Override
                public void onChanged(List<Author> authorList) {
                    resultAuthorList.setValue(authorList);
                }
            });

        }
    }

    private void saveDataInDb(final List<AuthorBookDetailResponse> dataList) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Author> authors = new ArrayList<>();
                for (int i = 0; i < dataList.size(); i++) {
                    authors.add(new Author(dataList.get(i).getAuthorName(), dataList.get(i).getAuthorBio()));
                }
                authorDao.insertAll(authors);
            }
        });
    }


    public LiveData<List<Author>> getAuthorList() {
        return resultAuthorList;
    }
}
