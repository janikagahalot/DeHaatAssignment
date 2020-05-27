package com.dehaat.dehaatassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthorBookDetailResponse implements Parcelable {

    @SerializedName("author_name")
    private String authorName;

    @SerializedName("author_bio")
    private String authorBio;

    @SerializedName("books")
    private List<Book> books;

    protected AuthorBookDetailResponse(Parcel in) {
        authorName = in.readString();
        authorBio = in.readString();
        books = in.createTypedArrayList(Book.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(authorName);
        dest.writeString(authorBio);
        dest.writeTypedList(books);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuthorBookDetailResponse> CREATOR = new Creator<AuthorBookDetailResponse>() {
        @Override
        public AuthorBookDetailResponse createFromParcel(Parcel in) {
            return new AuthorBookDetailResponse(in);
        }

        @Override
        public AuthorBookDetailResponse[] newArray(int size) {
            return new AuthorBookDetailResponse[size];
        }
    };

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorBio() {
        return authorBio;
    }

    public void setAuthorBio(String authorBio) {
        this.authorBio = authorBio;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
