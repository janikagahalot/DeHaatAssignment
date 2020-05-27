package com.dehaat.dehaatassignment.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.dehaat.dehaatassignment.dao.AuthorDao;
import com.dehaat.dehaatassignment.dao.BookDao;
import com.dehaat.dehaatassignment.database.AppDatabase;
import com.dehaat.dehaatassignment.model.Author;
import com.dehaat.dehaatassignment.model.AuthorBookDetailResponse;
import com.dehaat.dehaatassignment.model.AuthorsResponse;
import com.dehaat.dehaatassignment.model.Book;
import com.dehaat.dehaatassignment.rest.AppRestClient;
import com.dehaat.dehaatassignment.rest.AppRestClientService;
import com.dehaat.dehaatassignment.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {


    private AuthorDao authorDao;
    private BookDao bookDao;
    private AppRestClientService appRestClientService;
    private AppExecutors appExecutors;
    private MediatorLiveData<List<Author>> resultAuthorList = new MediatorLiveData<>();
    private MediatorLiveData<List<Book>> resultBookList = new MediatorLiveData<>();


    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        appRestClientService = AppRestClient.getInstance().getAppRestClientService();
        appExecutors = AppExecutors.getInstance();
        authorDao = db.authorDao();
        bookDao = db.bookDao();
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
        final List<AuthorBookDetailResponse> dataList = authorsResponse.data;
        if (dataList != null && dataList.size() > 0) {
            appExecutors.backgroundExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    saveDataInDb(dataList);

                    appExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            resultAuthorList.addSource(loadDataFromDb(), new Observer<List<Author>>() {
                                @Override
                                public void onChanged(List<Author> authorList) {
                                    resultAuthorList.setValue(authorList);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private void saveDataInDb(final List<AuthorBookDetailResponse> dataList) {
        appExecutors.backgroundExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Author> authors = new ArrayList<>();
                for (int i = 0; i < dataList.size(); i++) {
                    String name = dataList.get(i).getAuthorName();
                    authors.add(new Author(name, dataList.get(i).getAuthorBio()));
                    saveBooksData(name, dataList.get(i).getBooks());
                }
                authorDao.insertAll(authors);
            }
        });
    }

    private void saveBooksData(String name, List<Book> books) {
        if (books == null || books.size() == 0) return;
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            book.setAuthorName(name);
            bookDao.insert(book);
        }
    }


    public LiveData<List<Author>> getAuthorList() {
        return resultAuthorList;
    }

    public LiveData<List<Book>> getBookList(String authorName) {
        resultBookList.addSource(bookDao.getBookList(authorName), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                resultBookList.setValue(books);
            }
        });
        return resultBookList;
    }
}
