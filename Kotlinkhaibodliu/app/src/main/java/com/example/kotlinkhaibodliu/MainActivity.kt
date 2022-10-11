package com.example.kotlinkhaibodliu

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn1 : Button = findViewById(R.id.button)
        btn1.setOnClickListener(
            View.OnClickListener {
                Toast.makeText(this,"btn1",Toast.LENGTH_LONG).show()
            }
        )
    }
}

