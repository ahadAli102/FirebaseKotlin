package com.ahad.firebasekotlin.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ahad.firebasekotlin.R
import kotlinx.android.synthetic.main.activity_phone_auth.*

class PhoneAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)
        layoutPhone.visibility = View.VISIBLE
        layoutVerification.visibility = View.GONE

        button_send_verification.setOnClickListener {
            val phone = edit_text_phone.text.toString().trim().toInt().toString()
            if (phone.isEmpty() || phone.length > 11 || phone.length < 10) {
                Log.d(TAG, "onCreate: invalid +88$phone")
                edit_text_phone.error = "Enter a valid phone"
                edit_text_phone.requestFocus()
                return@setOnClickListener
            }
            val phoneNumber = "+880$phone"
            Log.d(TAG, "onCreate: valid $phoneNumber")
            layoutPhone.visibility = View.GONE
            layoutVerification.visibility = View.VISIBLE
        }

        button_verify.setOnClickListener {
            val code = edit_text_code.text.toString().trim()
            if(code.isEmpty()){
                edit_text_code.error = "Code required"
                edit_text_code.requestFocus()
                return@setOnClickListener
            }

        }
    }






    companion object{
        private const val TAG = "TAG:PhoneAuth"
    }
}