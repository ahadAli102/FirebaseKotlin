package com.ahad.firebasekotlin.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahad.firebasekotlin.R
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class StorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        setupActionBarWithNavController(findNavController(R.id.storage_fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.storage_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}