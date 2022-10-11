package com.example.wichat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_PERMISSION_CODE = 1;
    private boolean TorokuKakunin = false;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        textView = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);
        requestStoragePermission();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA)!= PackageManager.PERMISSION_DENIED){
                    Toast.makeText(MainActivity.this, "không đồng ý ", Toast.LENGTH_SHORT).show();
                }else
                    requestStoragePermission();
            }
        });

    }
    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "カメラ許可 ", Toast.LENGTH_SHORT).show();
            startActivity( new Intent(this,ManHinhCho.class));
            finish();
        } else {
            String[] mang = {Manifest.permission.CAMERA};
            requestPermissions(mang, REQUEST_PERMISSION_CODE);
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "もうカメラ許可された ", Toast.LENGTH_SHORT).show();
                startActivity( new Intent(this,ManHinhCho.class));
                finish();

            }else{
                Toast.makeText(this, "カメラ許可されてない", Toast.LENGTH_SHORT).show();
            }
        }
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
    }
}