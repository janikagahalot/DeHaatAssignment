package com.dehaat.dehaatassignment.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "author_table")
public class Author {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String authorName;

    @ColumnInfo(name = "bio")
    private String authorBio;

    public Author() {

    }
    public Author(@NonNull String name, String bio) {
        this.authorName = name;
        this.authorBio = bio;
    }

    public void setAuthorBio(String authorBio) {
        this.authorBio = authorBio;
    }

    public void setAuthorName(@NonNull String authorName) {
        this.authorName = authorName;
    }

    @NonNull
    public String getAuthorName() {
        return this.authorName;
    }

    public String getAuthorBio() {
        return this.authorBio;
    }
}
