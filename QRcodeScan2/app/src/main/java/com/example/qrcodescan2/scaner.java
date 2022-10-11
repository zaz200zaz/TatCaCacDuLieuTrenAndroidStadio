package com.example.qrcodescan2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class scaner extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaner);
    }
}