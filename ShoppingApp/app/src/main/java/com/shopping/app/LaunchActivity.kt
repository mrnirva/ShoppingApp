package com.shopping.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        init()
    }

    private fun init(){
        startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
        finish()
    }

}