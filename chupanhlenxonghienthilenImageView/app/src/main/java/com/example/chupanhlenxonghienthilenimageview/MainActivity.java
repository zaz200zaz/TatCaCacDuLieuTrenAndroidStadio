package com.example.chupanhlenxonghienthilenimageview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Bitmap circularBitmap;
    int camera_code = 1;
    ImageView imageView ;
    private String curentPhotoPath;//写真の住所
    private int MY_REQUEST_CODE=101;
    private Uri imagePath;
    private String urlImageMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlImageMessage = "";
        setContentView(R.layout.activity_main);
        imageView =findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},101);
                }else{
                    moCamera();
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},101);
                }
            }

            private void moCamera() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==MY_REQUEST_CODE&&resultCode==RESULT_OK){
//            upLoadImage();
            String sfdsafs = urlImageMessage;

            Bitmap bitmap = BitmapFactory.decodeFile(curentPhotoPath);
//            circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
            imageView.setImageBitmap(circularBitmap);
//            imgSendMessageView.setVisibility(View.VISIBLE);

        }
//        if (requestCode==camera_code&&requestCode == RESULT_OK&&data!=null){
//            Bundle bundle = data.getExtras();
//            Bitmap bitmap = (Bitmap) bundle.get("data");
//            imageView.setImageBitmap(bitmap);
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}