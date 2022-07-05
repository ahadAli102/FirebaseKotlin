package com.ahad.firebasekotlin.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahad.firebasekotlin.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        button_reset_password.setOnClickListener {
            val email = edit_text_email.text.toString().trim()
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this@ResetPasswordActivity, "Email sent", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@ResetPasswordActivity, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}