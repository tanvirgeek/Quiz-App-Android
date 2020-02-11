package com.tanvirgeek.quizeexamapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsersAPI {

    @GET("users_read.php")
    Call<List<User>> getUsers();

   /* @DELETE("delete_user.php?id={id}")
    Call<Void> deletePost(@Path("id") int id);*/

    @DELETE("delete_user.php")
    Call<Void> deletePost(@Query("id") int id);
}
