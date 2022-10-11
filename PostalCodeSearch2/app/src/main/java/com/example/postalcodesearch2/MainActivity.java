package com.example.postalcodesearch2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    PostsAdapter adapter;
    List<Post> postList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PostsAdapter(postList);
        recyclerView.setAdapter(adapter);

        fetchPost();
    }

    private void fetchPost() {
        progressBar .setVisibility(View.VISIBLE);
        RetrofitCliant.getApiInterface().getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                 if (response.isSuccessful()&& response.body() != null){
                     postList.addAll(response.body());
                     adapter.notifyDataSetChanged();
                     progressBar.setVisibility(View.GONE);
                 }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error:"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: "+ t.getMessage());
            }
        });
    }
}