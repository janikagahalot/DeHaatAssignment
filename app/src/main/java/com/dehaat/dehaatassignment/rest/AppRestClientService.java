package com.dehaat.dehaatassignment.rest;

import com.dehaat.dehaatassignment.model.Author;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppRestClientService {

    @POST("/dehaat/login")
    Call<JsonElement> login();

    @GET("/dehaat/authors")
    Call<JsonElement> getListOfAuthors();

}
