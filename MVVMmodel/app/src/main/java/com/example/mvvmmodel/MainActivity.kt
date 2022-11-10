package com.example.mvvmmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mvvmmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         var activityMainBinding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        var loginViewModel1 : LoginViewModel
        activityMainBinding.loginViewModel
    }
}