package com.dehaat.dehaatassignment.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
