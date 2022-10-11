package com.example.wichat;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

public class ManHinhCho extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_PERMISSION_CODE = 1;
    private ImageView imageView;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cho);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }else{
            imageView = findViewById(R.id.imageView);
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                countDownTimer = new CountDownTimer(5000, 100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(ManHinhCho.this, MiTouRoku.class);
                        startActivity(intent);
                        finish();
                    }
                }.start();
            }
        }

    }
}