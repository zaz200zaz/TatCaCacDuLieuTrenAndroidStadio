package com.example.postcodesearch3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubApi {
    @GET("search?zipcode=2520324")
    Call<User> getUser(@Path("user_name") String user);
}
