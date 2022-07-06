package com.ahad.firebasekotlin.realtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahad.firebasekotlin.R

class RealtimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime)
    }
    override fun onStart() {
        super.onStart()
        overridePendingTransition(R.anim.slide_in_left,
            R.anim.slide_out_left);
    }
}