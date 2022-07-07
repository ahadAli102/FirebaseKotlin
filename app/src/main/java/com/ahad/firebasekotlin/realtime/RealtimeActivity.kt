package com.ahad.firebasekotlin.realtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ahad.firebasekotlin.R

class RealtimeActivity : AppCompatActivity() {
    lateinit var viewModel: AuthorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModelFactory = AuthorsViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthorViewModel::class.java)
        Log.d(TAG, "onCreate: ${viewModel.hashCode()}")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime)
    }
    override fun onStart() {
        super.onStart()
        overridePendingTransition(R.anim.slide_in_left,
            R.anim.slide_out_left);
    }
    companion object{
        private const val TAG = "TAG:RealTime"
    }
}