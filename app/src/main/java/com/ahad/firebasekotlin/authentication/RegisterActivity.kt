package com.ahad.firebasekotlin.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.ahad.firebasekotlin.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    //firebase
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseAuth = FirebaseAuth.getInstance()
        button_register.setOnClickListener {
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
            userSignUp(email, password)
        }
        text_view_login.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, RegisterActivity::class.java))
        }
    }
    private fun userSignUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    sendEmailVerification()
                }
                else{
                    Log.d(TAG, "userSignUp: not complete ${task.exception?.message}")
                    Toast.makeText(this@RegisterActivity, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                Log.d(TAG, "userSignUp: not complete ${e.message}")
                Toast.makeText(this@RegisterActivity, "Registration Failed due to " + e.message, Toast.LENGTH_SHORT).show()
            }
    }
    private fun sendEmailVerification() {
        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.sendEmailVerification()
            .addOnCompleteListener { task->
                if(task.isComplete){
                    Toast.makeText(this@RegisterActivity, "Confirmation email is sent to your account", Toast.LENGTH_SHORT).show()
                    firebaseAuth.signOut()
                    onBackPressed()
                }
                else{
                    Log.d(TAG, "sendEmailVerification: not complete ${task.exception?.message}")
                }
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "sendEmailVerification: failed ${e.message}")
                Toast.makeText(this@RegisterActivity, "Failed to send due to " + e.message, Toast.LENGTH_SHORT).show()
            }
    }


    companion object {
        private const val TAG = "TAG:Register"
    }
}