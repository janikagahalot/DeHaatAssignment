package com.dehaat.dehaatassignment.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "author_table")
public class Author {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String author_name;
    @ColumnInfo(name = "bio")
    private String author_bio;

    public Author(@NonNull String name, String bio) {
        this.author_name = name;
        this.author_bio = bio;
    }

    @NonNull
    public String getAuthorName() {
        return author_name;
    }

    public void setAuthorName(@NonNull String author_name) {
        this.author_name = author_name;
    }

    public String getAuthorBio() {
        return author_bio;
    }

    public void setAuthorBio(String author_bio) {
        this.author_bio = author_bio;
    }
}
