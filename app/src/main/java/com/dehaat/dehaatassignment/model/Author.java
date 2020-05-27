package com.dehaat.dehaatassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "author_table")
public class Author implements Parcelable {

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

    protected Author(Parcel in) {
        authorName = in.readString();
        authorBio = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(authorName);
        dest.writeString(authorBio);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

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
