package com.example.postalcodesearch2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("search?zipcode=2520324")
    Call<List<Post>> getPosts();
//    @GET("/post")
//    Call<List<Post>> getPosts();

}
