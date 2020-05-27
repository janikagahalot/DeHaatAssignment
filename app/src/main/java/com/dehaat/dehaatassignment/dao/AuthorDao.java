package com.dehaat.dehaatassignment.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dehaat.dehaatassignment.model.Author;

import java.util.List;

@Dao
public interface AuthorDao {

    @Query("SELECT * from author_table")
    LiveData<List<Author>> getAuthorList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Author author);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Author> author);

    @Query("DELETE FROM author_table")
    void deleteAll();

}
