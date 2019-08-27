package com.ahmetmb.calculateaverage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class ActivitySplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var downToUpButton = AnimationUtils.loadAnimation(this, R.anim.down_to_up_button)
        buttonSplash.animation = downToUpButton

        buttonSplash.setOnClickListener {

            object : CountDownTimer(1000, 1000){
                override fun onFinish() {
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onTick(millisUntilFinished: Long) {
                    
                }

            }.start()

        }

    }
}
