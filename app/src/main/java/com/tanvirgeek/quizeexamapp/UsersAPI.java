package com.tanvirgeek.quizeexamapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsersAPI {

     @GET("users_read.php")
    Call<List<User>> getUsers();
}
