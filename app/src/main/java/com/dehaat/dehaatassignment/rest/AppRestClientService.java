package com.dehaat.dehaatassignment.rest;

import com.dehaat.dehaatassignment.model.AuthorsResponse;
import com.dehaat.dehaatassignment.model.LoginResponse;
import com.dehaat.dehaatassignment.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppRestClientService {

    @POST("/dehaat/login")
    Call<LoginResponse> login(@Body User user);

    @GET("/dehaat/authors")
    Call<AuthorsResponse> getListOfAuthors();

}
