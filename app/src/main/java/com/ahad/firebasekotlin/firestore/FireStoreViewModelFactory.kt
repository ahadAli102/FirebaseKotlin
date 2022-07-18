package com.ahad.firebasekotlin.firestore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FireStoreViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FireStoreViewModel() as T
    }
}