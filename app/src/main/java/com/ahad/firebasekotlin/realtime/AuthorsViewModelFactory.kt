package com.ahad.firebasekotlin.realtime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AuthorsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthorViewModel() as T
    }
}