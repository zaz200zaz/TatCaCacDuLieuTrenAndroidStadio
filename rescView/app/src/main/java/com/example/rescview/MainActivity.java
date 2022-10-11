package com.example.rescview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rescViewData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(getListUser());
        recyclerView.setAdapter(userAdapter);
    }

    private List<User> getListUser() {
        List<User> userList =  new ArrayList<>();
        userList.add(new User(android.R.drawable.ic_btn_speak_now,"ghi am","android"));
        userList.add(new User(android.R.drawable.ic_btn_speak_now,"ghi am","android"));

        userList.add(new User(android.R.drawable.ic_btn_speak_now,"ghi am","android"));

        userList.add(new User(android.R.drawable.ic_btn_speak_now,"ghi am","android"));

        userList.add(new User(android.R.drawable.ic_btn_speak_now,"ghi am","android"));
        return userList;
    }
}