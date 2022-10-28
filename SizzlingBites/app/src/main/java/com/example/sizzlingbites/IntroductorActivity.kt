package com.example.sizzlingbites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewPropertyAnimator
import kotlinx.android.synthetic.main.activity_introductor.*

class IntroductorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introductor)

        img.animate().translationY(-1600f).setDuration(1000).setStartDelay(4000)
        logo.animate().translationY(1400f).setDuration(1000).setStartDelay(4000)
        app_name.animate().translationY(1400f).setDuration(1000).setStartDelay(4000)
        lottie.animate().translationY(1400f).setDuration(1000).setStartDelay(4000)


    }
}




