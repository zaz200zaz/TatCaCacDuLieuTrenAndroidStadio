package com.example.imagepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView imageView;
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUSES_CODE = 12345;
    boolean isPermissionGranred = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

// start cropping activity for pre-acquired image saved on the device
        CropImage.activity(imageUri)
                .start(this);

// for fragment (DO NOT use `getActivity()`)
        CropImage.activity()
                .start(getContext(), this);
    }
    private void checkPermission(){
        if((ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)){
            isPermissionGranred = true;
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,permissions,REQUSES_CODE);
        }
    }
}