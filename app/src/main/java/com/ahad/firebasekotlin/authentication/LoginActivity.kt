package com.ahad.firebasekotlin.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.ahad.firebasekotlin.R
import com.ahad.firebasekotlin.realtime.RealtimeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    //firebase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        button_sign_in.setOnClickListener {
            val email = edit_text_email.text.toString().trim()
            val password = edit_text_password.text.toString().trim()

            if (email.isEmpty()) {
                edit_text_email.error = "Email Required"
                edit_text_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edit_text_email.error = "Valid Email Required"
                edit_text_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                edit_text_password.error = "6 char password required"
                edit_text_password.requestFocus()
                return@setOnClickListener
            }
            userLogin(email , password)
        }
        text_view_forget_password.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java))
        }
        text_view_register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        text_view_phone.setOnClickListener {
            startActivity(Intent(this@LoginActivity, PhoneAuthActivity::class.java))
        }

    }

    private fun userLogin(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                checkUserVerified()
            } else {
                Log.d(TAG, "userLogin: not successful ${task.exception?.message.toString()}")
                Toast.makeText(this@LoginActivity,  task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Log.d(TAG, "userLogin: failed ${e.message.toString()}")
            Toast.makeText(this@LoginActivity, "" + e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkUserVerified(){
        val currentUser = firebaseAuth.currentUser
        if (currentUser!=null && !currentUser.isEmailVerified) {
            Toast.makeText(this@LoginActivity, "Please verify email", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, RealtimeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    companion object {
        private const val TAG = "TAG:Login"
    }
}