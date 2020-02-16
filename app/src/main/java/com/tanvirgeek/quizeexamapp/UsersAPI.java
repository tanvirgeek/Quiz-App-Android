package com.tanvirgeek.quizeexamapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsersAPI {

    @GET("users_read.php")
    Call<List<User>> getUsers();

   /* @DELETE("delete_user.php?id={id}")
    Call<Void> deletePost(@Path("id") int id);*/

    @DELETE("delete_user.php")
    Call<Void> deletePost(@Query("id") int id);

    @GET("register.php")
    Call<User> createUser(@Query("userName") String userName, @Query("password")String password, @Query("email")String email, @Query("fullName")String fullName, @Query("collegeName")String collegeName, @Query("gender")String gender, @Query("dob")String dob );
}
