package com.example.wichat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Hakken extends AppCompatActivity {

    private TextView txtimgHatkken;

    private ImageButton imageButtonMessage;
    private ImageButton imageButtonRenrakuSaki;
    private ImageButton imageButtonHakken;
    private ImageButton imageButtonJibun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakken);

        anhXa();

        imageButtonRenrakuSaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Hakken.this,Renrakuchou.class));
            }
        });
        imageButtonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Hakken.this,ChatGamen.class));
            }
        });
        imageButtonJibun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Hakken.this,Jibun.class));
            }
        });
    }
    private void anhXa(){
        imageButtonMessage = findViewById(R.id.imgChatGamen);
        imageButtonRenrakuSaki = findViewById(R.id.imgRenrakuSaki);
        imageButtonHakken = findViewById(R.id.imgHatkken);
        imageButtonJibun = findViewById(R.id.imgJibun);

        txtimgHatkken = findViewById(R.id.txtimgHatkken);

        imageButtonHakken.setColorFilter(Color.GREEN);
        txtimgHatkken.setTextColor(Color.GREEN);
    }

}