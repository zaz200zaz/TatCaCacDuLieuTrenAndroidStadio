package com.example.postalcodesearch2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCliant {
//    private static final String BASE_URL = "http://zipcloud.ibsnet.co.jp/api/search?zipcode=";
    private static final String BASE_URL = "http://zipcloud.ibsnet.co.jp/api/";
//    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static Retrofit retrofit = null;
    public static ApiInterface getApiInterface(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
