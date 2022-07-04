package com.ahad.firebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.ahad.firebasekotlin.authentication.LoginActivity
import com.ahad.firebasekotlin.realtime.RealtimeActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        actionBar?.hide()
        val currentUser = FirebaseAuth.getInstance().currentUser
        lateinit var intent: Intent
        if (currentUser != null) {
            intent = Intent(this@MainActivity, RealtimeActivity::class.java)
            Toast.makeText(this@MainActivity, currentUser.uid, Toast.LENGTH_SHORT).show()
        } else {
            intent = Intent(this@MainActivity, LoginActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(intent)
            finish()
        },1000)
    }
}