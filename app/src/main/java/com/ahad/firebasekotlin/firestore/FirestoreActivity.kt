package com.ahad.firebasekotlin.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

import com.ahad.firebasekotlin.R

class FirestoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)
        setupActionBarWithNavController(findNavController(R.id.firestore_fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.firestore_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}