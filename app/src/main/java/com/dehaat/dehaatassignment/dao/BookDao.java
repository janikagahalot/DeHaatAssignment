package com.dehaat.dehaatassignment.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dehaat.dehaatassignment.model.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * from book_table where author_name = :authorName")
    LiveData<List<Book>> getBookList(String authorName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Book book);

    @Query("DELETE FROM book_table")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM book_table WHERE author_name = :authorName")
    LiveData<Integer> bookCount(String authorName);

}
