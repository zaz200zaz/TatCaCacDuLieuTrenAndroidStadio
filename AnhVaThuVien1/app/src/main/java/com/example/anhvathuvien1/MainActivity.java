package com.example.anhvathuvien1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.cast.framework.media.ImagePicker;

import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    String path;
    Uri uri;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImagePicker.Companion.with()
                ActivityResultLauncher<Intent> launcher=
                        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                            if(result.getResultCode()==RESULT_OK){
                                Uri uri=result.getData().getData();
                                // Use the uri to load the image
                            }else if(result.getResultCode()==ImagePicker.IMAGE_TYPE_UNKNOWN){
                                // Use ImagePicker.Companion.getError(result.getData()) to show an error
                            }
                        });
            }
        });
        ImagePicker.Companion.with(this)
                .crop()
                .cropOval()
                .maxResultSize(512,512,true)
                .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                .createIntentFromDialog((Function1)(new Function1(){
                    public Object invoke(Object var1){
                        this.invoke((Intent)var1);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(@NotNull Intent it){
                        Intrinsics.checkNotNullParameter(it,"it");
                        launcher.launch(it);
                    }
                }));
        textView = findViewById(R.id.textView);

    }
}